package no.arkivlab.hioa.nikita.webapp.handlers.hateoas;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.Link;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.ISeriesHateoasHandler;
import org.springframework.stereotype.Component;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add SeriesHateoas links with Series specific information
 * <p>
 * Not sure if there is a difference in what should be returned of links for various CRUD operations so keeping
 * possibility of separate calls for CRUD functions, at the moment. See HateoasHandler
 * <p>
 * Some of these will require ROLE_RECORD_KEEPER  e.g. screening etc., others will readable for all users.
 * <p>
 * StorageLocation should only be possible if documentMedium is not electronic
 * <p>
 * StorageLocation supports addOne, addAll, findAll
 *
 */
@Component("seriesHateoasHandler")
public class SeriesHateoasHandler extends HateoasHandler implements ISeriesHateoasHandler {

    @Override
    public void addEntityLinks(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        addDocumentMedium(entity, hateoasNoarkObject);
        addNewRegistration(entity, hateoasNoarkObject);
        addNewFile(entity, hateoasNoarkObject);
        addNewCaseFile(entity, hateoasNoarkObject);
        addNewClassificationSystem(entity, hateoasNoarkObject);
        addRegistration(entity, hateoasNoarkObject);
        addFile(entity, hateoasNoarkObject);
        addCaseFile(entity, hateoasNoarkObject);
        addClassificationSystem(entity, hateoasNoarkObject);
        addSeriesSuccessor(entity, hateoasNoarkObject);
        addNewSeriesSuccessor(entity, hateoasNoarkObject);
        addSeriesPrecursor(entity, hateoasNoarkObject);
        addNewSeriesPrecursor(entity, hateoasNoarkObject);
        addFonds(entity, hateoasNoarkObject);
        addSeriesStatus(entity, hateoasNoarkObject);
        addClassified(entity, hateoasNoarkObject);
        addNewClassified(entity, hateoasNoarkObject);
        addDisposal(entity, hateoasNoarkObject);
        addNewDisposal(entity, hateoasNoarkObject);
        addDisposalUndertaken(entity, hateoasNoarkObject);
        addNewDisposalUndertaken(entity, hateoasNoarkObject);
        addDeletion(entity, hateoasNoarkObject);
        addNewDeletion(entity, hateoasNoarkObject);
        addScreening(entity, hateoasNoarkObject);
        addNewScreening(entity, hateoasNoarkObject);
        addNewStorageLocation(entity, hateoasNoarkObject);
        addListStorageLocation(entity, hateoasNoarkObject);
        addNewListStorageLocation(entity, hateoasNoarkObject);
    }

    @Override
    /**
     * Get a list of Series status values (GET)
     */
    public void addSeriesStatus(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_METADATA_PATH + SLASH + FONDS_STATUS + SLASH, REL_METADATA_FONDS_STATUS, false));
    }

    @Override
    /**
     * Get the successor Series (GET)
     */
    public void addSeriesSuccessor(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES + SLASH + entity.getSystemId() + SLASH
                + SERIES_ASSOCIATE_AS_SUCCESSOR + SLASH, REL_FONDS_STRUCTURE_SUCCESSOR,
                false));
    }

    @Override
    /**
     * Associate an existing Series (A) as the successor of another existing Series (B). (A) becomes the
     * successor to (B). A is identified first, B is identified through a ref link (PUT)
     */
    public void addNewSeriesSuccessor(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES + SLASH + entity.getSystemId() + SLASH
                + NEW_SERIES_SUCCESSOR + SLASH, REL_FONDS_STRUCTURE_NEW_SUCCESSOR,
                false));

    }

    @Override
    /**
     * Get the precursor Series object (GET)
     */
    public void addSeriesPrecursor(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES + SLASH + entity.getSystemId() + SLASH +
                SERIES_PRECURSOR + SLASH, REL_FONDS_STRUCTURE_PRECURSOR,
                false));
    }

    @Override
    /**
     * Associate an existing Series (A) as the precursor  of another existing Series (B). (A) becomes the
     * precursor to (B). A is identified first, B is identified through a ref link (PUT)
     */
    public void addNewSeriesPrecursor(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES + SLASH + entity.getSystemId() + SLASH +
                NEW_SERIES_PRECURSOR + SLASH, REL_FONDS_STRUCTURE_NEW_PRECURSOR,
                false));
    }

    @Override
    /**
     * Add a new registration to a Series (POST)
     */
    public void addNewRegistration(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        if (authorisation.canCreateRegistrationAttachedToSeries()) {
            hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                    NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES + SLASH + entity.getSystemId() + SLASH +
                    NEW_RECORD + SLASH, REL_FONDS_STRUCTURE_NEW_REGISTRATION,
                    false));
        }
    }

    @Override
    /**
     * Add a new File to a Series (POST)
     */
    public void addNewFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        if (authorisation.canCreateFileAttachedToSeries()) {
            hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                    NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES + SLASH + entity.getSystemId() + SLASH
                    + NEW_FILE + SLASH, REL_FONDS_STRUCTURE_NEW_FILE, false));
        }
    }

    @Override
    /**
     * Add a new CaseFile to a Series (POST)
     */
    public void addNewCaseFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        if (authorisation.canCreateFileAttachedToSeries()) {
            hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                    NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES + SLASH + entity.getSystemId() + SLASH
                    + NEW_CASE_FILE + SLASH, REL_FONDS_STRUCTURE_NEW_CASE_FILE, false));
        }
    }
    @Override
    /**
     * Associate an existing ClassificationSystem as the precursor of an existing Series (PUT)
     */
    public void addNewClassificationSystem(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        if (authorisation.canCreateClassifcationSystemAttachedToSeries()) {
            hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                    NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASSIFICATION_SYSTEM +
                    SLASH + entity.getSystemId() + SLASH + NEW_CLASSIFICATION_SYSTEM + SLASH,
                    REL_FONDS_STRUCTURE_NEW_CLASSIFICATION_SYSTEM, false));
        }
    }

    @Override
    /**
     * Get a list of Registration objects associated with a Series (paginated) (GET)
     */
    public void addRegistration(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRATION +
                SLASH + entity.getSystemId() + SLASH + REGISTRATION + SLASH, REL_FONDS_STRUCTURE_REGISTRATION,
                false));
    }

    @Override
    /**
     * Get a list of File objects associated with a Series (paginated) (GET)
     */
    public void addFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES +
                SLASH + entity.getSystemId() + SLASH + FILE + SLASH, REL_FONDS_STRUCTURE_FILE,
                false));
    }

    @Override
    /**
     * Get a list of CaseFile objects associated with a Series (paginated) (GET)
     */
    public void addCaseFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES +
                SLASH + entity.getSystemId() + SLASH + CASE_FILE + SLASH, REL_FONDS_STRUCTURE_CASE_FILE,
                false));
    }

    @Override
    /**
     * Get the ClassificationSystem associated with the Series (GET)
     */
    public void addClassificationSystem(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES +
                SLASH + entity.getSystemId() + SLASH + CLASSIFICATION_SYSTEM + SLASH,
                REL_FONDS_STRUCTURE_CLASSIFICATION_SYSTEM, false));

    }

    @Override
    /**
     * Get the Fonds associated with the Series (GET)
     */
    public void addFonds(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES +
                SLASH + entity.getSystemId() + SLASH + FONDS + SLASH,
                REL_FONDS_STRUCTURE_FONDS, false));
    }

    @Override
    /**
     * Get the Classified associated with the Series (GET)
     */
    public void addClassified(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES + SLASH + entity.getSystemId() + SLASH + CLASSIFIED
                + SLASH, REL_FONDS_STRUCTURE_CLASSIFIED, false));
    }

    @Override
    /**
     * Add a new Classified to a Series (POST)
     */
    public void addNewClassified(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES + SLASH + entity.getSystemId() + SLASH +
                NEW_CLASSIFIED + SLASH, REL_FONDS_STRUCTURE_NEW_CLASSIFIED, false));
    }

    @Override
    /**
     * Get the Disposal associated with the Series (GET)
     */
    public void addDisposal(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES + SLASH + entity.getSystemId() + SLASH + DISPOSAL
                + SLASH, REL_FONDS_STRUCTURE_DISPOSAL, false));
    }

    @Override
    /**
     * Add a new Disposal to a Series (POST)
     */
    public void addNewDisposal(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES + SLASH + entity.getSystemId() + SLASH + NEW_DISPOSAL
                + SLASH, REL_FONDS_STRUCTURE_NEW_DISPOSAL, false));
    }

    @Override
    /**
     * Get the DisposalUndertaken associated with the Series (GET)
     */
    public void addDisposalUndertaken(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES + SLASH + entity.getSystemId() + SLASH + DISPOSAL_UNDERTAKEN
                + SLASH, REL_FONDS_STRUCTURE_DISPOSAL_UNDERTAKEN, false));
    }

    @Override
    /**
     * Add a new DisposalUndertaken to a Series (POST)
     */
    public void addNewDisposalUndertaken(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES + SLASH + entity.getSystemId() + SLASH + NEW_DISPOSAL_UNDERTAKEN
                + SLASH, REL_FONDS_STRUCTURE_NEW_DISPOSAL_UNDERTAKEN, false));
    }

    @Override
    /**
     * Get the Deletion associated with the Series object (GET)
     */
    public void addDeletion(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES + SLASH + entity.getSystemId() + SLASH + DELETION
                + SLASH, REL_FONDS_STRUCTURE_DELETION, false));
    }

    @Override
    /**
     * Add a new Deletion to a Series (POST)
     */
    public void addNewDeletion(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES + SLASH + entity.getSystemId() + SLASH + NEW_DELETION
                + SLASH, REL_FONDS_STRUCTURE_NEW_DELETION, false));
    }

    @Override
    /**
     * Get the Screening associated with the Series (GET)
     */
    public void addScreening(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES + SLASH + entity.getSystemId() + SLASH + SCREENING
                + SLASH, REL_FONDS_STRUCTURE_SCREENING, false));
    }

    @Override
    /**
     * Add a new Screening to a Series (POST)
     */
    public void addNewScreening(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES + SLASH + entity.getSystemId() + SLASH + NEW_SCREENING
                + SLASH, REL_FONDS_STRUCTURE_NEW_SCREENING, false));
    }

    @Override
    /**
     * Get a list of StorageLocation associated with the Series  (GET)
     */
    public void addListStorageLocation(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES + SLASH + entity.getSystemId() + SLASH +
                STORAGE_LOCATION + SLASH, REL_FONDS_STRUCTURE_STORAGE_LOCATION, false));
    }

    @Override
    /**
     * Add a new StorageLocation to be associated with the Series (POST)
     */
    public void addNewStorageLocation(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES + SLASH + entity.getSystemId() + SLASH +
                NEW_STORAGE_LOCATION + SLASH, REL_FONDS_STRUCTURE_NEW_STORAGE_LOCATION, false));
    }

    @Override
    /**
     * Add a new list of StorageLocation to be associated with the Series (POST)
     */
    public void addNewListStorageLocation(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES + SLASH + entity.getSystemId() + SLASH +
                NEW_STORAGE_LOCATIONS + SLASH, REL_FONDS_STRUCTURE_NEW_STORAGE_LOCATION_LIST, false));
    }

}
