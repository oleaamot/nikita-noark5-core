package no.arkivlab.hioa.nikita.webapp.service.impl.imprt;

import nikita.model.noark5.v4.Fonds;
import nikita.model.noark5.v4.Series;
import nikita.repository.n5v4.IFondsRepository;
import nikita.util.exceptions.NikitaException;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt.IFondsImportService;
import no.arkivlab.hioa.nikita.webapp.util.NoarkUtils;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityEditWhenClosedException;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.NoarkInvalidStructureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static nikita.config.Constants.INFO_CANNOT_ASSOCIATE_WITH_CLOSED_OBJECT;
import static nikita.config.Constants.INFO_CANNOT_FIND_OBJECT;
import static nikita.config.Constants.INFO_INVALID_STRUCTURE;
import static nikita.config.N5ResourceMappings.STATUS_CLOSED;

@Service
@Transactional
public class FondsImportService implements IFondsImportService {

    private static final Logger logger = LoggerFactory.getLogger(no.arkivlab.hioa.nikita.webapp.service.impl.FondsService.class);

    @Autowired
    IFondsRepository fondsRepository;

    @Autowired
    SeriesImportService seriesImportService;

    // All CREATE operations

    /**
     * Persists a new fonds object to the database. It's assumed the caller has a valid fonds object. Minimal error
     * checking occurs here.
     *
     * @param fonds fonds object with some values set
     * @return the newly persisted fonds object
     */
    @Override
    public Fonds createNewFonds(Fonds fonds){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username == null) {
            throw new NikitaException("Security context problem. username is null! Cannot continue with " +
                    "this request!");
        }
        if (fonds.getCreatedDate() == null) {
            fonds.setCreatedDate(new Date());
        }
        if (fonds.getCreatedBy() == null) {
            fonds.setCreatedBy(username);
        }
        if (fonds.getOwnedBy() == null) {
            fonds.setOwnedBy(username);
        }
        fonds.setDeleted(false);

        return fondsRepository.save(fonds);
    }

    /**
     *
     * Persists a new fonds object to the database, that is associated with a parent fonds object. It's assumed the
     * caller has a valid fonds object. Minimal error checking occurs here.
     *
     * First we try to locate the parent. If the parent does not exist a NoarkEntityNotFoundException exception is
     * thrown
     *
     * @param childFonds incoming fonds object with some values set
     * @param parentFondsSystemId The systemId of the parent fonds
     * @return the newly persisted fonds object
     */
    @Override
    public Fonds createFondsAssociatedWithFonds(String parentFondsSystemId, Fonds childFonds) {
        Fonds persistedChildFonds = null;
        Fonds parentFonds = fondsRepository.findBySystemId(parentFondsSystemId);

        if (parentFonds == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " Fonds, using fondsSystemId " + parentFondsSystemId + " when " +
                    "trying to associate a child fonds with a parent fonds";
            logger.info(info) ;
            throw new NoarkEntityNotFoundException(info);
        }
        else if (parentFonds.getReferenceSeries() != null) {
            String info = INFO_INVALID_STRUCTURE + " Cannot associate a new child fonds with a fonds that has " +
                    "one or more series " + parentFondsSystemId;
            logger.info(info) ;
            throw new NoarkEntityNotFoundException(info);
        }
        else {
            childFonds.setReferenceParentFonds(parentFonds);
            persistedChildFonds = this.createNewFonds(childFonds);
        }
        return persistedChildFonds;
    }

    /**
     *
     * Persists a new series object to the database. Some values are set in the incoming payload (e.g. title)
     * and some are set by the core. owner, createdBy, createdDate are automatically set by the core.
     *
     * First we try to locate the parent fonds. If the parent fonds does not exist a NoarkEntityNotFoundException
     * exception is thrown. Next  we check that the fonds does not have children fonds. If it does an
     * exception is thrown.
     *
     * @param fondsSystemId
     * @param series
     * @return the newly persisted series object
     */
    @Override
    public Series createSeriesAssociatedWithFonds(String fondsSystemId, Series series) {
        Series persistedSeries = null;
        Fonds fonds = fondsRepository.findBySystemId(fondsSystemId);

        if (fonds == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " Fonds, using fondsSystemId " + fondsSystemId;
            logger.info(info) ;
            throw new NoarkEntityNotFoundException(info);
        }
        else if (fonds.getReferenceChildFonds() != null && fonds.getReferenceChildFonds().size() > 0) {
            String info = INFO_INVALID_STRUCTURE + " Cannot associate series with a fonds that has" +
                    "children fonds " + fondsSystemId;
            logger.info(info) ;
            throw new NoarkInvalidStructureException(info, "Fonds", "Series");
        }
        else if (fonds.getFondsStatus() != null && fonds.getFondsStatus().equals(STATUS_CLOSED)) {
            String info = INFO_CANNOT_ASSOCIATE_WITH_CLOSED_OBJECT + ". Fonds with fondsSystemId " + fondsSystemId +
                    "has status " + STATUS_CLOSED;
            logger.info(info) ;
            throw new NoarkEntityEditWhenClosedException(info);
        }
        else {
            series.setReferenceFonds(fonds);
            persistedSeries = seriesImportService.save(series);
        }
        return persistedSeries;
    }
}
