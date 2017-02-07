package no.arkivlab.hioa.nikita.webapp.handlers.hateoas;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.Link;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IFondsHateoasHandler;
import org.springframework.stereotype.Component;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add FondsHateoas links with Fonds specific information
 */
@Component("fondsHateoasHandler")
public class FondsHateoasHandler extends HateoasHandler implements IFondsHateoasHandler {

    @Override
    public void addEntityLinks(IHateoasNoarkObject hateoasNoarkObject) {
        /*addDocumentMedium(hateoasNoarkObject);
        addFondsCreator(hateoasNoarkObject);
        addSeries(hateoasNoarkObject);
        addFonds(hateoasNoarkObject);
        addNewFondsCreator(hateoasNoarkObject);
        addSubFonds(hateoasNoarkObject);
        addFondsStatus(hateoasNoarkObject);
        addNewSeries(hateoasNoarkObject);
        addNewFonds(hateoasNoarkObject);*/
    }

    @Override
    public void addEntityLinksOnCreate(IHateoasNoarkObject hateoasNoarkObject) {
        addDocumentMedium(hateoasNoarkObject);
        addFondsCreator(hateoasNoarkObject);
        addSeries(hateoasNoarkObject);
        addFonds(hateoasNoarkObject);
        addNewFondsCreator(hateoasNoarkObject);
        addSubFonds(hateoasNoarkObject);
        addFondsStatus(hateoasNoarkObject);
        addNewSeries(hateoasNoarkObject);
        addNewFonds(hateoasNoarkObject);
    }

    public void addDocumentMedium(IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(new Link(contextPath + HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH
                + DOCUMENT_MEDIUM + SLASH, REL_METADATA_DOCUMENT_MEDIUM, false));
    }

    public void addFondsCreator(IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(new Link(contextPath + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH
                + FONDS_CREATOR + SLASH, REL_FONDS_STRUCTURE_FONDS_CREATOR, false));
    }

    public void addSeries(IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(new Link(contextPath + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH
                + SERIES + SLASH, REL_FONDS_STRUCTURE_SERIES, false));
    }

    public void addFonds(IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(new Link(contextPath + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH
                + FONDS + SLASH, REL_FONDS_STRUCTURE_FONDS, false));
    }

    public void addNewFondsCreator(IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(new Link(contextPath + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH
                + NEW_FONDS_CREATOR, REL_FONDS_STRUCTURE_NEW_FONDS_CREATOR, false));
    }

    public void addSubFonds(IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(new Link(contextPath + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH
                + SUB_FONDS + SLASH, REL_FONDS_STRUCTURE_SUB_FONDS, false));
    }

    public void addFondsStatus(IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(new Link(contextPath + HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH + SLASH
                + FONDS_STATUS + SLASH, REL_METADATA_FONDS_STATUS, false));
    }

    public void addNewFonds(IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(new Link(contextPath + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH
                + NEW_FONDS + SLASH, REL_FONDS_STRUCTURE_NEW_FONDS, false));
    }

    public void addNewSeries(IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(new Link(contextPath + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH
                + NEW_SERIES + SLASH, REL_FONDS_STRUCTURE_NEW_SERIES, false));
    }
}