package nikita.webapp.handlers.hateoas.casehandling;

import nikita.common.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.common.model.noark5.v4.hateoas.Link;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.webapp.hateoas.HateoasHandler;
import nikita.webapp.hateoas.interfaces.secondary.ICorrespondencePartHateoasHandler;
import org.springframework.stereotype.Component;

import static nikita.common.config.Constants.*;
import static nikita.common.config.N5ResourceMappings.CORRESPONDENCE_PART_TYPE;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add CorrespondencePartHateoas links with CorrespondencePart specific information
 * <p>
 * Not sure if there is a difference in what should be returned of links for various CRUD operations so keeping them
 * separate calls at the moment.
 */
@Component("correspondencePartUnitHateoasHandler")
public class CorrespondencePartUnitHateoasHandler extends HateoasHandler implements ICorrespondencePartHateoasHandler {

    @Override
    public void addEntityLinksOnNew(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        addCorrespondencePartType(entity, hateoasNoarkObject);
    }

    @Override
    public void addEntityLinksOnTemplate(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        addCorrespondencePartType(entity, hateoasNoarkObject);
    }

    @Override
    public void addCorrespondencePartType(INikitaEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath +
                HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH + CORRESPONDENCE_PART_TYPE,
                REL_METADATA_CORRESPONDENCE_PART_TYPE,
                false));
    }

}
