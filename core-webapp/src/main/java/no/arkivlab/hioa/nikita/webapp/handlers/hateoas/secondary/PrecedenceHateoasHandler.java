package no.arkivlab.hioa.nikita.webapp.handlers.hateoas.secondary;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.Link;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.HateoasHandler;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.secondary.IPrecedenceHateoasHandler;
import org.springframework.stereotype.Component;

import static nikita.config.Constants.*;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add PrecedenceHateoas links with Precedence specific information
 * <p>
 * Not sure if there is a difference in what should be returned of links for various CRUD operations so keeping them
 * separate calls at the moment.
 */
@Component("precedenceHateoasHandler")
public class PrecedenceHateoasHandler extends HateoasHandler implements IPrecedenceHateoasHandler {

    @Override
    public void addEntityLinks(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        // Metadata RELS
         /*addPrecedenceStatus(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);
         addNewCaseFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);
         addCaseFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);
         addNewPrecedence(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);
         addPrecedence(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);
         addNewRegistryEntry(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);
         addRegistryEntry(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject);*/
    }

    @Override
    public void addPrecedenceStatus(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        //hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
          //      NOARK_METADATA_PATH + SLASH + entity.getBaseTypeName(), REL_METADATA_, false));
    }

    @Override
    public void addNewCaseFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_METADATA_PATH + SLASH + entity.getBaseTypeName(), REL_METADATA_CASE_STATUS, false));
    }

    @Override
    public void addCaseFile(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        //hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
          //      NOARK_METADATA_PATH + SLASH + entity.getBaseTypeName(), REL_METADATA_, false));
    }

    @Override
    public void addNewPrecedence(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        //hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
          //      NOARK_METADATA_PATH + SLASH + entity.getBaseTypeName(), REL_METADATA_, false));
    }

    @Override
    public void addPrecedence(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        //hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
          //      NOARK_METADATA_PATH + SLASH + entity.getBaseTypeName(), REL_METADATA_, false));
    }

    @Override
    public void addNewRegistryEntry(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        //hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
          //      NOARK_METADATA_PATH + SLASH + entity.getBaseTypeName(), REL_METADATA_, false));
    }

    @Override
    public void addRegistryEntry(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        //hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
          //      NOARK_METADATA_PATH + SLASH + entity.getBaseTypeName(), REL_METADATA_, false));
    }

}
