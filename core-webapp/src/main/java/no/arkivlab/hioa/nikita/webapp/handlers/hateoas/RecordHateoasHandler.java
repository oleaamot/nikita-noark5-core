package no.arkivlab.hioa.nikita.webapp.handlers.hateoas;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.Link;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IRecordHateoasHandler;
import org.springframework.stereotype.Component;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add RecordHateoas links with Record specific information
 * <p>
 * Not sure if there is a difference in what should be returned of links for various CRUD operations so keeping them
 * separate calls at the moment.
 */
@Component("recordHateoasHandler")
public class RecordHateoasHandler extends HateoasHandler implements IRecordHateoasHandler {

    @Override
    public void addEntityLinks(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {

        addReferenceSeries(entity, hateoasNoarkObject);
        addNewReferenceSeries(entity, hateoasNoarkObject);
        addReferenceClassified(entity, hateoasNoarkObject);
        addNewReferenceClassified(entity, hateoasNoarkObject);
        addReferenceDisposal(entity, hateoasNoarkObject);
        addNewReferenceDisposal(entity, hateoasNoarkObject);
        addReferenceDisposalUndertaken(entity, hateoasNoarkObject);
        addNewReferenceDisposalUndertaken(entity, hateoasNoarkObject);
        addReferenceDeletion(entity, hateoasNoarkObject);
        addNewReferenceDeletion(entity, hateoasNoarkObject);
        addReferenceScreening(entity, hateoasNoarkObject);
        addNewReferenceScreening(entity, hateoasNoarkObject);
    }

    @Override
    public void addReferenceSeries(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRATION + SLASH + entity.getSystemId() + SLASH + SERIES
                + SLASH, REL_FONDS_STRUCTURE_REFERENCE_SERIES, false));
    }

    @Override
    public void addNewReferenceSeries(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRATION + SLASH + entity.getSystemId() + SLASH + NEW_SERIES
                + SLASH, REL_FONDS_STRUCTURE_NEW_REFERENCE_SERIES, false));
    }

    @Override
    public void addReferenceClassified(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRATION + SLASH + entity.getSystemId() + SLASH + CLASSIFIED
                + SLASH, REL_FONDS_STRUCTURE_CLASSIFIED, false));
    }

    @Override
    public void addNewReferenceClassified(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRATION + SLASH + entity.getSystemId() + SLASH +
                NEW_CLASSIFIED + SLASH, REL_FONDS_STRUCTURE_NEW_CLASSIFIED, false));
    }

    @Override
    public void addReferenceDisposal(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRATION + SLASH + entity.getSystemId() + SLASH + DISPOSAL
                + SLASH, REL_FONDS_STRUCTURE_DISPOSAL, false));
    }

    @Override
    public void addNewReferenceDisposal(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRATION + SLASH + entity.getSystemId() + SLASH + NEW_DISPOSAL
                + SLASH, REL_FONDS_STRUCTURE_NEW_DISPOSAL, false));
    }

    @Override
    public void addReferenceDisposalUndertaken(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRATION + SLASH + entity.getSystemId() + SLASH + DISPOSAL_UNDERTAKEN
                + SLASH, REL_FONDS_STRUCTURE_DISPOSAL_UNDERTAKEN, false));
    }

    @Override
    public void addNewReferenceDisposalUndertaken(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRATION + SLASH + entity.getSystemId() + SLASH + NEW_DISPOSAL_UNDERTAKEN
                + SLASH, REL_FONDS_STRUCTURE_NEW_DISPOSAL_UNDERTAKEN, false));
    }

    @Override
    public void addReferenceDeletion(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRATION + SLASH + entity.getSystemId() + SLASH + DELETION
                + SLASH, REL_FONDS_STRUCTURE_DELETION, false));
    }

    @Override
    public void addNewReferenceDeletion(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRATION + SLASH + entity.getSystemId() + SLASH + NEW_DELETION
                + SLASH, REL_FONDS_STRUCTURE_NEW_DELETION, false));
    }

    @Override
    public void addReferenceScreening(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRATION + SLASH + entity.getSystemId() + SLASH + SCREENING
                + SLASH, REL_FONDS_STRUCTURE_SCREENING, false));
    }

    @Override
    public void addNewReferenceScreening(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRATION + SLASH + entity.getSystemId() + SLASH + NEW_SCREENING
                + SLASH, REL_FONDS_STRUCTURE_NEW_SCREENING, false));
    }
}
