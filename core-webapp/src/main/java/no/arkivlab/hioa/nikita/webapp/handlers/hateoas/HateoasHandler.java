package no.arkivlab.hioa.nikita.webapp.handlers.hateoas;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.Link;
import nikita.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IHateoasHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

import static nikita.config.Constants.*;
import static nikita.config.HATEOASConstants.SELF;
import static nikita.config.N5ResourceMappings.*;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add Hateoas links with information
 * <p>
 * Note that the contextServletPath is set when addLinks is called
 */
@Component("hateoasHandler")
public class HateoasHandler implements IHateoasHandler {

    protected String contextServletPath = "";
    protected String servletPath = "";
    protected String contextPath = "";


    public void addLinks(IHateoasNoarkObject hateoasNoarkObject, HttpServletRequest request) {
        setParameters(request);
        addSelfLink(hateoasNoarkObject);
        addEntityLinks(hateoasNoarkObject);
    }

    public void addLinksOnCreate(IHateoasNoarkObject hateoasNoarkObject, HttpServletRequest request) {
        setParameters(request);
        addSelfLink(hateoasNoarkObject);
        addEntityLinksOnCreate(hateoasNoarkObject);
    }

    public void addLinksOnRead(IHateoasNoarkObject hateoasNoarkObject, HttpServletRequest request) {
        setParameters(request);
        addSelfLink(hateoasNoarkObject);
        addEntityLinksOnRead(hateoasNoarkObject);
    }

    public void addSelfLink(IHateoasNoarkObject hateoasNoarkObject) {

        if (hateoasNoarkObject.getSystemIdEntity() != null) {
            String systemId = hateoasNoarkObject.getSystemIdEntity().getSystemId();
            hateoasNoarkObject.addLink(new Link(contextPath + HATEOAS_API_PATH + SLASH +
                    NOARK_FONDS_STRUCTURE_PATH + SLASH + getEntityType(hateoasNoarkObject.getClass().getName())
                    + SLASH + systemId + SLASH,
                    getRelSelfLink(), false));
        } else if (hateoasNoarkObject.getSystemIdEntityList() != null) {

        }

    }

    // Sub class should handle this, empty links otherwise!
    public void addEntityLinks(IHateoasNoarkObject hateoasNoarkObject) {
    }

    // Sub class should handle this, empty links otherwise!
    public void addEntityLinksOnCreate(IHateoasNoarkObject hateoasNoarkObject) {
    }

    // Sub class should handle this, empty links otherwise!
    public void addEntityLinksOnRead(IHateoasNoarkObject hateoasNoarkObject) {
    }

    public void addGeneralLinks(INoarkGeneralEntity noarkGeneralEntity) {
    }

    protected String getRelSelfLink() {
        return SELF;
    }

    protected void setParameters(HttpServletRequest request) {
        this.servletPath = request.getServletPath();
        this.contextPath = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        this.contextServletPath = contextPath + servletPath + SLASH;
        this.servletPath += SLASH;
        this.contextPath += SLASH;
    }

    protected String getEntityType(String className) {

        if (className.endsWith("RegistryEntryHateoas")) {
            return REGISTRY_ENTRY;
        } else if (className.endsWith("DocumentDescriptionHateoas")) {
            return DOCUMENT_DESCRIPTION;
        } else if (className.endsWith("DocumentObjectHateoas")) {
            return DOCUMENT_OBJECT;
        } else if (className.endsWith("CaseFileHateoas")) {
            return CASE_FILE;
        } else if (className.endsWith("ClassHateoas")) {
            return CLASS;
        } else if (className.endsWith("FondsHateoas")) {
            return FONDS;
        } else if (className.endsWith("SeriesHateoas")) {
            return SERIES;
        } else if (className.endsWith("ClassificationSystemHateoas")) {
            return CLASSIFICATION_SYSTEM;
        } else if (className.endsWith("FileHateoas")) {
            return FILE;
        } else if (className.endsWith("RecordHateoas")) {
            return REGISTRATION;
        } else if (className.endsWith("BasicRecordHateoas")) {
            return BASIC_RECORD;
        }
        // consider this throwing an excpetion
        return "unknown_entity";
    }
}

