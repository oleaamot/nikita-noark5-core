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
 * Not sure if there is a difference in what should be returned of links for various CRUD operations so keeping them
 * separate calls at the moment.
 */
@Component("seriesHateoasHandler")
public class SeriesHateoasHandler extends HateoasHandler implements ISeriesHateoasHandler {

    @Override
    public void addEntityLinks(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        addDocumentMedium(entity, hateoasNoarkObject);
        addSeries(entity, hateoasNoarkObject);
        addNewRegistration(entity, hateoasNoarkObject);
        addNewFile(entity, hateoasNoarkObject);
        addNewClassificationSystem(entity, hateoasNoarkObject);
        addSeries(entity, hateoasNoarkObject);
        addRegistration(entity, hateoasNoarkObject);
        addFile(entity, hateoasNoarkObject);
        addClassificationSystem(entity, hateoasNoarkObject);
        addFonds(entity, hateoasNoarkObject);
        addSeriesStatus(entity, hateoasNoarkObject);
        addNewSeries(entity, hateoasNoarkObject);
    }

    @Override
    public void addEntityLinksOnCreate(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        addEntityLinks(entity, hateoasNoarkObject);
    }

    @Override
    public void addEntityLinksOnRead(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        addEntityLinks(entity, hateoasNoarkObject);
    }

    @Override
    public void addSeries(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES +
                SLASH + entity.getSystemId() + SLASH + SERIES + SLASH, REL_FONDS_STRUCTURE_SERIES, false));
    }

    @Override
    public void addSeriesStatus(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_METADATA_PATH + SLASH + FONDS_STATUS + SLASH, REL_METADATA_FONDS_STATUS, false));
    }

    @Override
    public void addNewSeries(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        if (authorisation.canCreateSeries()) {
            hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                    NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES +
                    SLASH + entity.getSystemId() + SLASH + NEW_SERIES + SLASH, REL_FONDS_STRUCTURE_NEW_SERIES,
                    false));
        }
    }

    @Override
    public void addNewRegistration(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        if (authorisation.canCreateRegistrationAttachedToSeries()) {
            hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                    NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRATION +
                    SLASH + entity.getSystemId() + SLASH + NEW_RECORD + SLASH, REL_FONDS_STRUCTURE_NEW_REGISTRATION,
                    false));
        }
    }

    @Override
    public void addNewFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        if (authorisation.canCreateRegistrationAttachedToSeries()) {
            hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                    NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES +
                    SLASH + entity.getSystemId() + SLASH + NEW_FILE + SLASH, REL_FONDS_STRUCTURE_NEW_FILE,
                    false));
        }
    }

    @Override
    public void addNewClassificationSystem(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        if (authorisation.canCreateClassifcationSystemAttachedToSeries()) {
            hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                    NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASSIFICATION_SYSTEM +
                    SLASH + entity.getSystemId() + SLASH + NEW_CLASSIFICATION_SYSTEM + SLASH,
                    REL_FONDS_STRUCTURE_NEW_CLASSIFICATION_SYSTEM, false));
        }
    }

    @Override
    public void addRegistration(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + REGISTRATION +
                SLASH + entity.getSystemId() + SLASH + REGISTRATION + SLASH, REL_FONDS_STRUCTURE_REGISTRATION,
                false));
    }

    @Override
    public void addFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE +
                SLASH + entity.getSystemId() + SLASH + FILE + SLASH, REL_FONDS_STRUCTURE_FILE,
                false));
    }

    @Override
    public void addClassificationSystem(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + CLASSIFICATION_SYSTEM +
                SLASH + entity.getSystemId() + SLASH + CLASSIFICATION_SYSTEM + SLASH,
                REL_FONDS_STRUCTURE_CLASSIFICATION_SYSTEM, false));

    }

    @Override
    public void addFonds(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FONDS +
                SLASH + entity.getSystemId() + SLASH + FONDS + SLASH,
                REL_FONDS_STRUCTURE_FONDS, false));
    }
}
