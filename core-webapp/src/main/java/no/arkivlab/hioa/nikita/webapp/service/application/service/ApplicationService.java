package no.arkivlab.hioa.nikita.webapp.service.application.service;

import no.arkivlab.hioa.nikita.webapp.model.application.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

import static nikita.config.Constants.*;

@Service
@Transactional
public class ApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);

    /**
     * Creates a list of the supported supported login methods.
     * These are: JWT
     *
     * @return
     */

    public void addLoginInformation(List<ConformityLevel> conformityLevels) {
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        ConformityLevel loginJWT = new ConformityLevel();
        loginJWT.setHref(uri + SLASH + LOGIN_PATH);
        loginJWT.setRel(NIKITA_CONFORMANCE_REL + LOGIN_REL_PATH + SLASH + LOGIN_JWT + SLASH);
        conformityLevels.add(loginJWT);
    }

    /**
     * Creates a list of the officially supported resource links.
     * These are: arkivstruktur, sakarkiv, metadata, administrasjon and loggingogsporing
     *
     * @return
     */

    public void addConformityLevels(List<ConformityLevel> conformityLevels) {

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();

        // ConformityLevel : arkivstruktur
        ConformityLevel conformityLevelFondsStructure = new ConformityLevel();
        conformityLevelFondsStructure.setHref(uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH);
        conformityLevelFondsStructure.setRel(NOARK_CONFORMANCE_REL + NOARK_FONDS_STRUCTURE_PATH + SLASH);
        conformityLevels.add(conformityLevelFondsStructure);

        // ConformityLevel : sakarkiv
        ConformityLevel conformityLevelCaseHandling = new ConformityLevel();
        conformityLevelCaseHandling.setHref(uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_CASE_HANDLING_PATH);
        conformityLevelCaseHandling.setRel(NOARK_CONFORMANCE_REL + NOARK_CASE_HANDLING_PATH + SLASH);
        conformityLevels.add(conformityLevelCaseHandling);

        // ConformityLevel : metadata
        ConformityLevel conformityLevelMetadata = new ConformityLevel();
        conformityLevelMetadata.setHref(uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH);
        conformityLevelMetadata.setRel(NIKITA_CONFORMANCE_REL + NOARK_METADATA_PATH + SLASH);
        conformityLevels.add(conformityLevelMetadata);

        /*
        // These will be added as the development progresses.
        // They are not really specified properly in the interface standard.
        // ConformityLevel : administrasjon
        ConformityLevel conformityLevelAdministration = new ConformityLevel();
        conformityLevelAdministration.setHref(uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_ADMINISTRATION_PATH);
        conformityLevelAdministration.setRel(NOARK_CONFORMANCE_REL + NOARK_ADMINISTRATION_PATH + SLASH);
        conformityLevels.add(conformityLevelAdministration);

        // ConformityLevel : loggingogsporing
        ConformityLevel conformityLevelLogging = new ConformityLevel();
        conformityLevelLogging.setHref(uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_LOGGING_PATH);
        conformityLevelLogging.setRel(NOARK_CONFORMANCE_REL + NOARK_LOGGING_PATH + SLASH);
        conformityLevels.add(conformityLevelLogging);
        */
    }

    public ApplicationDetails getApplicationDetails() {
        ApplicationDetails applicationDetails;
        ArrayList<ConformityLevel> conformityLevels = new ArrayList(10);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!username.equals("anonymousUser")) {
            addConformityLevels(conformityLevels);
        }

        /* Show login relation also for logged in users to allow user
         * change also when logged in.
         */
        addLoginInformation(conformityLevels);

        applicationDetails = new ApplicationDetails(conformityLevels);
        return applicationDetails;
    }

    public FondsStructureDetails getFondsStructureDetails() {
        return new FondsStructureDetails();
    }

    public MetadataDetails getMetadataDetails() {
        return new MetadataDetails();
    }

    public CaseHandlingDetails getCaseHandlingDetails() {
        return new CaseHandlingDetails();
    }
}
