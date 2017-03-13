package no.arkivlab.hioa.nikita.webapp.handlers.hateoas;

import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.Link;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.IClassificationSystemHateoasHandler;
import org.springframework.stereotype.Component;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;

/**
 * Created by tsodring on 2/6/17.
 * <p>
 * Used to add ClassificationSystemHateoas links with ClassificationSystem specific information
 * <p>
 * Not sure if there is a difference in what should be returned of links for various CRUD operations so keeping them
 * separate calls at the moment.
 *
 * Note: SecondaryClassification seems to have a different form in the interface than in the standard. Leaving
 * it for the moment, but must bre visited. Perhaps a mangelmelding to get a better description of it
 *
 * The problem I see is that you now declare a classificationsystem as a secondary classificationsystem.
 * Does this preclude classes belonging to a primary classification system from being assigned as a
 * sekundaerklassifikasjon?
 *
 * A define secondary clasification system is useful in that it might allow arkivskapere to create activity
 * descriptions increasing the overall use of "tagging" within the system.
 *
 * The following should be added to Classificationtype in db
 Gårds- og bruksnummer GBN
 Funksjonsbasert, hierarkisk FH
 Emnebasert, hierarkisk arkivnøkkel EH
 Emnebasert, ett nivå E1
 K-koder KK Mangefasettert, ikke hierarki MF
 Objektbasert UO
 FødselsnummerPNR

 ny-klassifikasjon and ny-sekundaerklassifiikasjon should be visible of the root

 What is the purpose of this REL?
 http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-arkivdel/
 It must be a 'helper'. Normally you create a arkivdel and assign classificationsystem
 Here ,it must just be a shortcut, but how do you assign the arkivdel to its arkiv? This must be wrong!!
 */
@Component("classificationSystemHateoasHandler")
public class ClassificationSystemHateoasHandler extends HateoasHandler implements IClassificationSystemHateoasHandler {

    @Override
    public void addEntityLinks(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {

        // links for primary entities
        addNewClassificationSystem(entity, hateoasNoarkObject);
        addNewSecondaryClassificationSystem(entity, hateoasNoarkObject);
        addSecondaryClassificationSystem(entity, hateoasNoarkObject);
        addClass(entity, hateoasNoarkObject);
        addNewClass(entity, hateoasNoarkObject);
        addSeries(entity, hateoasNoarkObject);
        // links for secondary entities
        // No secondary entities
        // links for metadata entities
        addClassificationType(entity, hateoasNoarkObject);
    }

    @Override
    public void addClass(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH + REFERENCE_CLASS
                + SLASH, REL_FONDS_STRUCTURE_CLASS, false));
    }

    @Override
    public void addNewClass(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH + NEW_REFERENCE_CLASS
                + SLASH, REL_FONDS_STRUCTURE_NEW_CLASS, false));
    }

    @Override
    public void addSeries(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH + SERIES
                + SLASH, REL_FONDS_STRUCTURE_SERIES, false));
    }

    @Override
    public void addNewClassificationSystem(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH +
                NEW_CLASSIFICATION_SYSTEM + SLASH, REL_FONDS_STRUCTURE_NEW_CLASSIFICATION_SYSTEM, false));
    }

    @Override
    public void addNewSecondaryClassificationSystem(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH +
                NEW_SECONDARY_CLASSIFICATION + SLASH, REL_FONDS_STRUCTURE_NEW_SECONDARY_CLASSIFICATION, false));
    }

    @Override
    public void addSecondaryClassificationSystem(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_FONDS_STRUCTURE_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH +
                SECONDARY_CLASSIFICATION + SLASH, REL_FONDS_STRUCTURE_SECONDARY_CLASSIFICATION, false));
    }

    @Override
    public void addClassificationType(INoarkSystemIdEntity entity, IHateoasNoarkObject hateoasNoarkObject) {
        hateoasNoarkObject.addLink(entity, new Link(contextPath + HATEOAS_API_PATH + SLASH +
                NOARK_METADATA_PATH + SLASH + FILE + SLASH + entity.getSystemId() + SLASH + CLASSIFICATION_SYSTEM_TYPE
                + SLASH, REL_METADATA_CLASSIFICATION_SYSTEM_TYPE, false));
    }
}
