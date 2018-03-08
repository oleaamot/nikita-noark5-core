package no.arkivlab.hioa.nikita.webapp.service.impl;

import nikita.model.noark5.v4.Fonds;
import nikita.model.noark5.v4.FondsCreator;
import nikita.model.noark5.v4.Series;
import nikita.model.noark5.v4.hateoas.FondsCreatorHateoas;
import nikita.model.noark5.v4.hateoas.FondsHateoas;
import nikita.model.noark5.v4.hateoas.SeriesHateoas;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.repository.n5v4.IFondsRepository;
import nikita.util.exceptions.NoarkEntityEditWhenClosedException;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import nikita.util.exceptions.NoarkInvalidStructureException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IFondsCreatorHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IFondsHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.ISeriesHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFondsCreatorService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFondsService;
import no.arkivlab.hioa.nikita.webapp.util.NoarkUtils;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityCreatedEvent;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.List;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class FondsService implements IFondsService {

    private static final Logger logger =
            LoggerFactory.getLogger(FondsService.class);

    @Value("${application.pagination.max-page-size}")
    private Integer maxPageSize;

    private IFondsRepository fondsRepository;
    private SeriesService seriesService;
    private IFondsCreatorService fondsCreatorService;
    private EntityManager entityManager;
    private IFondsHateoasHandler fondsHateoasHandler;
    private ISeriesHateoasHandler seriesHateoasHandler;
    private IFondsCreatorHateoasHandler fondsCreatorHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public FondsService(IFondsRepository fondsRepository,
                        SeriesService seriesService,
                        IFondsCreatorService fondsCreatorService,
                        EntityManager entityManager,
                        IFondsHateoasHandler fondsHateoasHandler,
                        ISeriesHateoasHandler seriesHateoasHandler,
                        IFondsCreatorHateoasHandler fondsCreatorHateoasHandler,
                        ApplicationEventPublisher applicationEventPublisher) {

        this.fondsRepository = fondsRepository;
        this.seriesService = seriesService;
        this.fondsCreatorService = fondsCreatorService;
        this.entityManager = entityManager;
        this.fondsHateoasHandler = fondsHateoasHandler;
        this.seriesHateoasHandler = seriesHateoasHandler;
        this.fondsCreatorHateoasHandler = fondsCreatorHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // All CREATE operations

    /**
     * Persists a new fonds object to the database. Some values are set in the
     * incoming payload (e.g. title) and some are set by the core.
     * owner, createdBy, createdDate are automatically set by the core.
     *
     * @param fonds fonds object with some values set
     * @return the newly persisted fonds object wrapped as a fondsHateaos object
     */
    @Override
    public FondsHateoas createNewFonds(@NotNull Fonds fonds) {
        NoarkUtils.NoarkEntity.Create.checkDocumentMediumValid(fonds);
        NoarkUtils.NoarkEntity.Create.setNoarkEntityValues(fonds);
        fonds.setFondsStatus(STATUS_OPEN);
        NoarkUtils.NoarkEntity.Create.setFinaliseEntityValues(fonds);
        FondsHateoas fondsHateoas = new FondsHateoas(fondsRepository.save(fonds));
        fondsHateoasHandler.addLinks(fondsHateoas, new Authorisation());
        applicationEventPublisher.publishEvent(new
                AfterNoarkEntityCreatedEvent(this, fonds));
        return fondsHateoas;
    }

    /**
     * Persists a new fonds object to the database, that is first associated
     * with a parent fonds object. Some values are set in the incoming
     * payload (e.g. title) and some are set by the core. owner, createdBy,
     * createdDate are automatically set by the core.
     * <p>
     * First we try to locate the parent. If the parent does not exist a
     * NoarkEntityNotFoundException exception is thrown
     *
     * @param childFonds          incoming fonds object with some values set
     * @param parentFondsSystemId The systemId of the parent fonds
     * @return the newly persisted fonds object
     */
    @Override
    public FondsHateoas createFondsAssociatedWithFonds(
            @NotNull String parentFondsSystemId,
            @NotNull Fonds childFonds) {

        Fonds parentFonds = getFondsOrThrow(parentFondsSystemId);
        checkFondsDoesNotContainSeries(parentFonds);
        childFonds.setReferenceParentFonds(parentFonds);
        return createNewFonds(childFonds);
    }

    /**
     * Persists a new Series object to the database. Some values are set in
     * the incoming payload (e.g. title) and some are set by the core. owner,
     * createdBy, createdDate are automatically set by the core.
     * <p>
     * First we try to locate the fonds to associate the Series with. If the
     * fonds does not exist a NoarkEntityNotFoundException exception is
     * thrown. Then we check that the fonds does not have children fonds. If it
     * does an NoarkInvalidStructureException exception is thrown. After that
     * we check that the Fonds object is not already closed.
     *
     * @param fondsSystemId The systemId of the fonds object to associate a
     *                      Series with
     * @param series        The incoming Series object
     * @return the newly persisted series object wrapped as a SeriesHateoas
     * object
     */
    @Override
    public SeriesHateoas createSeriesAssociatedWithFonds(
            @NotNull String fondsSystemId,
            @NotNull Series series) {

        Fonds fonds = getFondsOrThrow(fondsSystemId);

        checkFondsNotClosed(fonds);
        checkFondsDoesNotContainSubFonds(fonds);

        series.setReferenceFonds(fonds);
        SeriesHateoas seriesHateoas = new SeriesHateoas(seriesService.save
                (series));
        seriesHateoasHandler.addLinks(seriesHateoas, new Authorisation());
        applicationEventPublisher.publishEvent(new
                AfterNoarkEntityCreatedEvent(this, fonds));
        return seriesHateoas;
    }

    /**
     * Persists a new FondsCreator object to the database. Some values are set
     * in the incoming payload (e.g. fonds_creator_name) and some are set by
     * the core. owner,createdBy, createdDate are automatically set by the core.
     * <p>
     * First we try to locate the fonds to associate the FondsCreator with. If
     * the fonds does not exist a NoarkEntityNotFoundException exception is
     * thrown. After that we check that the Fonds object is not already closed.
     *
     * @param fondsSystemId The systemId of the fonds object you wish to
     *                      associate the fondsCreator object with
     * @param fondsCreator  incoming fondsCreator object with some values set
     * @return the newly persisted fondsCreator object wrapped as a
     * FondsCreatorHateoas object
     */
    @Override
    public FondsCreatorHateoas createFondsCreatorAssociatedWithFonds(
            @NotNull String fondsSystemId,
            @NotNull FondsCreator fondsCreator) {

        Fonds fonds = getFondsOrThrow(fondsSystemId);
        checkFondsNotClosed(fonds);

        fondsCreatorService.createNewFondsCreator(fondsCreator);

        // add references to objects in both directions
        fondsCreator.addFonds(fonds);
        fonds.getReferenceFondsCreator().add(fondsCreator);

        // create the hateoas object with links
        FondsCreatorHateoas fondsCreatorHateoas = new FondsCreatorHateoas
                (fondsCreator);
        fondsCreatorHateoasHandler.addLinks(fondsCreatorHateoas, new
                Authorisation());

        return fondsCreatorHateoas;
    }

    // All READ operations

    /**
     * Retrieve a list of FondsCreator objects associated with a given Fonds
     * from the database. First we try to locate the Fonds object. If the
     * Fonds object does not exist a NoarkEntityNotFoundException exception
     * is thrown that the caller has to deal with.
     * <p>
     * If any FondsCreator objects exist, they are wrapped in a
     * FondsCreatorHateoas object and returned to the caller.
     *
     * @param fondsSystemId The systemId of the Fonds object that you want to
     *                      retrieve associated FondsCreator objects
     * @return the fondsCreator objects wrapped as a FondsCreatorHateoas object
     */
    @Override
    public FondsCreatorHateoas findFondsCreatorAssociatedWithFonds(
            @NotNull String fondsSystemId) {

        Fonds fonds = getFondsOrThrow(fondsSystemId);

        FondsCreatorHateoas fondsCreatorHateoas = new
                FondsCreatorHateoas((List<INikitaEntity>)
                (List) fonds.getReferenceFondsCreator());
        fondsCreatorHateoasHandler.addLinks(fondsCreatorHateoas,
                new Authorisation());
        return fondsCreatorHateoas;
    }

    /**
     * Retrieve a list of Series objects associated with a given Fonds
     * from the database. First we try to locate the Fonds object. If the
     * Fonds object does not exist a NoarkEntityNotFoundException exception
     * is thrown that the caller has to deal with.
     * <p>
     * If any Series objects exist, they are wrapped in a SeriesHateoas
     * object and returned to the caller.
     *
     * @param fondsSystemId The systemId of the Fonds object that you want to
     *                      retrieve associated Series objects
     * @return the list of Series objects wrapped as a SeriesHateoas object
     *
     */
    @Override
    public SeriesHateoas findSeriesAssociatedWithFonds(
            @NotNull String fondsSystemId) {

        Fonds fonds = getFondsOrThrow(fondsSystemId);
        SeriesHateoas seriesHateoas = new
                SeriesHateoas((List<INikitaEntity>)
                (List) fonds.getReferenceSeries());

        seriesHateoasHandler.addLinks(seriesHateoas,
                new Authorisation());
        return seriesHateoas;
    }

    /**
     * Retrieve a list of StorageLocation objects associated with a given Fonds
     * from the database. First we try to locate the Fonds object. If the
     * Fonds object does not exist a NoarkEntityNotFoundException exception
     * is thrown that the caller has to deal with.
     *
     * If any StorageLocation objects exist, they are wrapped in a
     * StorageLocationHateoas object and returned to the caller.
     *
     * @param fondsSystemId The systemId of the Fonds object that you want to
     *                      retrieve associated StorageLocation objects
     *
     * @return the newly persisted fondsCreator object wrapped as a
     * StorageLocationHateoas object
     *
     */
    /*@Override
    TODO: Finish implementing this.
    public StorageLocationHateoas findStorageLocationAssociatedWithFonds(
            @NotNull String fondsSystemId) {

        Fonds fonds = getFondsOrThrow(fondsSystemId);

        StorageLocationHateoas stroageLocationHateoas = new
                StorageLocationHateoas((List<INikitaEntity>)
                (List) fonds.getReferenceStorageLocation());
        fondsCreatorHateoasHandler.addLinks(stroageLocationHateoas,
                new Authorisation());
        return stroageLocationHateoas;
    } */

    /**
     * Retrieve a single Fonds objects from the database.
     *
     * @param fondsSystemId The systemId of the Fonds object you wish to
     *                      retrieve
     * @return the Fonds object wrapped as a FondsHateoas object
     */
    @Override
    public FondsHateoas findSingleFonds(String fondsSystemId) {
        Fonds existingFonds = getFondsOrThrow(fondsSystemId);

        FondsHateoas fondsHateoas = new FondsHateoas(fondsRepository.
                save(existingFonds));
        fondsHateoasHandler.addLinks(fondsHateoas, new Authorisation());
        applicationEventPublisher.publishEvent(
                new AfterNoarkEntityUpdatedEvent(this, existingFonds));
        return fondsHateoas;
    }

    /**
     * Retrieve a list of paginated Fonds objects associated from the database.
     *
     * @param top  how many results you want to retrieve
     * @param skip how many rows of results yo uwant to skip over
     * @return the list of Fonds object wrapped as a FondsCreatorHateoas object
     */
    @Override
    public FondsHateoas findFondsByOwnerPaginated(Integer top, Integer skip) {

        if (top == null || top > maxPageSize) {
            top = maxPageSize;
        }
        if (skip == null) {
            skip = 0;
        }

        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Fonds> criteriaQuery = criteriaBuilder.createQuery(Fonds.class);
        Root<Fonds> from = criteriaQuery.from(Fonds.class);
        CriteriaQuery<Fonds> select = criteriaQuery.select(from);

        criteriaQuery.where(criteriaBuilder.equal(from.get("ownedBy"), loggedInUser));
        TypedQuery<Fonds> typedQuery = entityManager.createQuery(select);
        typedQuery.setFirstResult(skip);
        typedQuery.setMaxResults(top);

        FondsHateoas fondsHateoas = new
                FondsHateoas((List<INikitaEntity>)
                (List) typedQuery.getResultList());
        fondsHateoasHandler.addLinks(fondsHateoas, new Authorisation());
        return fondsHateoas;
    }

    /**
     * Generate a Default Fonds object that can be associated with the
     * identified Fonds.
     * <br>
     * Note. Ideally this method would be configurable based on the logged in
     * user and the business area they are working with. A generic Noark core
     * like this does not have scope for that kind of functionality.
     *
     * @param fondsSystemId The systemId of the Fonds object you wish to
     *                      generate a default Series for
     * @return the Series object wrapped as a SeriesHateoas object
     */
    @Override
    public SeriesHateoas generateDefaultSeries(@NotNull String fondsSystemId) {

        Series defaultSeries = new Series();
        defaultSeries.setSeriesStatus(STATUS_OPEN);
        defaultSeries.setDocumentMedium(DOCUMENT_MEDIUM_ELECTRONIC);
        defaultSeries.setTitle("Default Series object generated by nikita " +
                "that can be associated with Fonds with systemID " +
                fondsSystemId);
        SeriesHateoas seriesHateoas = new
                SeriesHateoas(defaultSeries);
        seriesHateoasHandler.addLinksOnNew(seriesHateoas, new Authorisation());
        return seriesHateoas;
    }

    /**
     * Generate a Default Fonds object that can be associated with the
     * identified Fonds. If fondsSystemId has a value, it is assumed you wish
     * to generate a sub-fonds.
     * <br>
     * Note. Ideally this method would be configurable based on the logged in
     * user and the business area they are working with. A generic Noark core
     * like this does not have scope for that kind of functionality.
     *
     * @param fondsSystemId The systemId of the Fonds object you wish to
     *                      generate a default sub-fonds. Null if the Fonds
     *                      is not a sub-fonds.
     * @return the Fonds object wrapped as a FondsHateoas object
     */
    @Override
    public FondsHateoas generateDefaultFonds(String fondsSystemId) {

        Fonds defaultFonds = new Fonds();

        defaultFonds.setTitle(TEST_TITLE);
        defaultFonds.setDescription(TEST_DESCRIPTION);
        defaultFonds.setDocumentMedium(DOCUMENT_MEDIUM_ELECTRONIC);
        FondsHateoas fondsHateoas = new FondsHateoas(defaultFonds);
        fondsHateoasHandler.addLinksOnNew(fondsHateoas, new Authorisation());
        return fondsHateoas;
    }

    // All UPDATE operations

    /**
     * Updates a Fonds object in the database. First we try to locate the
     * Fonds object. If the Fonds object does not exist a
     * NoarkEntityNotFoundException exception is thrown that the caller has
     * to deal with.
     * <br>
     * After this the values you are allowed to update are copied from the
     * incomingFonds object to the existingFonds object and the existingFonds
     * object will be persisted to the database when the transaction boundary
     * is over.
     * <p>
     * Note, the version corresponds to the version number, when the object
     * was initially retrieved from the database. If this number is not the
     * same as the version number when re-retrieving the Fonds object from
     * the database a NoarkConcurrencyException is thrown. Note. This happens
     * when the call to Fonds.setVersion() occurs.
     *
     * @param fondsSystemId The systemId of the fonds object to retrieve
     * @param version       The last known version number (derived from an ETAG)
     * @param incomingFonds The incoming fonds object
     */
    @Override
    public FondsHateoas handleUpdate(@NotNull String fondsSystemId,
                                     @NotNull Long version,
                                     @NotNull Fonds incomingFonds) {

        Fonds existingFonds = getFondsOrThrow(fondsSystemId);

        // Copy all the values you are allowed to copy ....
        if (null != incomingFonds.getDescription()) {
            existingFonds.setDescription(incomingFonds.getDescription());
        }
        if (null != incomingFonds.getTitle()) {
            existingFonds.setTitle(incomingFonds.getTitle());
        }
        if (null != incomingFonds.getDocumentMedium()) {
            existingFonds.setDocumentMedium(existingFonds.getDocumentMedium());
        }

        // Note this can potentially result in a NoarkConcurrencyException
        // exception
        existingFonds.setVersion(version);

        FondsHateoas fondsHateoas = new FondsHateoas(fondsRepository.
                save(existingFonds));
        fondsHateoasHandler.addLinks(fondsHateoas, new Authorisation());
        applicationEventPublisher.publishEvent(
                new AfterNoarkEntityUpdatedEvent(this, existingFonds));
        return fondsHateoas;
    }

    // All DELETE operations

    /**
     * Deletes a Fonds object from the database. First we try to locate the
     * Fonds object. If the Fonds object does not exist a
     * NoarkEntityNotFoundException exception is thrown that the caller has
     * to deal with. Note that as there is a @ManyToMany relationship between
     * Fonds and FondsCreator with a join table, we first have to
     * disassociate the link between Fonds and FondsCreator or we hit a
     * foreign key constraint issue. The same applies for Fonds and
     * StorageLocation.
     * <p>
     * In order to minimise problems that could be caused with table and
     * column names changing, constants are used to define relevant column
     * and table names.
     * <p>
     * The approach is is discussed in a nikita github issue
     * https://github.com/HiOA-ABI/nikita-noark5-core/issues/82
     *
     * @param fondsSystemId The systemId of the fonds object to retrieve
     */
    @Override
    public void deleteEntity(@NotNull String fondsSystemId) {
        Fonds fonds = getFondsOrThrow(fondsSystemId);

        // Disassociate any links between Fonds and FondsCreator
        Query q = entityManager.createNativeQuery(
                "DELETE FROM " + TABLE_FONDS_FONDS_CREATOR + " WHERE " +
                        FOREIGN_KEY_FONDS_PK + " = :id ;");
        q.setParameter("id", fonds.getId());
        q.executeUpdate();

        // Disassociate any links between Fonds and StorageLocation
        q = entityManager.createNativeQuery(
                "DELETE FROM " + TABLE_STORAGE_LOCATION + " WHERE " +
                        FOREIGN_KEY_FONDS_PK + " = :id ;");
        q.setParameter("id", fonds.getId());
        q.executeUpdate();

        // Next get hibernate to delete the Fonds object
        entityManager.remove(fonds);
        entityManager.flush();
        entityManager.clear();
    }

    // All HELPER operations

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid Fonds back. If there is no valid
     * Fonds, a NoarkEntityNotFoundException exception is thrown
     *
     * @param fondsSystemId The systemId of the fonds object to retrieve
     * @return the fonds object
     */
    private Fonds getFondsOrThrow(@NotNull String fondsSystemId) {
        Fonds fonds = fondsRepository.findBySystemId(fondsSystemId);
        if (fonds == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " Fonds, using systemId " +
                    fondsSystemId;
            logger.info(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return fonds;
    }

    /**
     * Internal helper method. Check to make sure the fonds object does not
     * have a status of 'finalised'. If the check fails, a
     * NoarkEntityEditWhenClosedException exception is thrown that the caller
     * has to deal with.
     *
     * @param fonds The fonds object
     */
    private void checkFondsNotClosed(@NotNull Fonds fonds) {
        if (fonds.getFondsStatus() != null &&
                fonds.getFondsStatus().equals(STATUS_CLOSED)) {
            String info = INFO_CANNOT_ASSOCIATE_WITH_CLOSED_OBJECT +
                    ". Fonds with fondsSystemId " + fonds.getSystemId() +
                    " has status " + STATUS_CLOSED;
            logger.info(info);
            throw new NoarkEntityEditWhenClosedException(info);
        }
    }

    /**
     * Internal helper method. Check to make sure the fonds object does not
     * have any sub-fonds. This is useful when e.g. adding a Series to a fonds
     * .You must make sure that the fonds object does not have any child
     * fonds objects.
     * <p>
     * If the check fails, a NoarkInvalidStructureException exception is
     * thrown that the caller has to deal with.
     *
     * @param fonds The fonds object
     */
    private void checkFondsDoesNotContainSubFonds(@NotNull Fonds fonds) {
        if (fonds.getReferenceChildFonds() != null &&
                fonds.getReferenceChildFonds().size() > 0) {
            String info = INFO_INVALID_STRUCTURE + " Cannot associate a " +
                    "Series with a Fonds that already has sub-fonds " +
                    fonds.getSystemId();
            logger.info(info);
            throw new NoarkInvalidStructureException(info, "Fonds", "Series");
        }
    }

    /**
     * Internal helper method. Check to make sure the fonds object does not
     * have any Series associated with it. This is useful when e.g. adding a
     * sub-fonds to a fonds. You must make sure that the fonds object
     * does not have any Series associated with it.
     * <p>
     * If the check fails, a NoarkInvalidStructureException exception is
     * thrown that the caller has to deal with.
     *
     * @param fonds The fonds object
     */
    private void checkFondsDoesNotContainSeries(@NotNull Fonds fonds) {
        if (fonds.getReferenceSeries() != null &&
                fonds.getReferenceSeries().size() > 0) {
            String info = INFO_INVALID_STRUCTURE + " Cannot associate a " +
                    "(sub) Fonds with a Fonds that already has a Series " +
                    "associated with it!" + fonds.getSystemId();
            logger.info(info);
            throw new NoarkInvalidStructureException(info, "Fonds", "Series");
        }
    }
}
