package no.arkivlab.hioa.nikita.webapp.web.controller;

import com.codahale.metrics.annotation.Counted;
import io.swagger.annotations.Api;
import no.arkivlab.hioa.nikita.webapp.model.application.ApplicationDetails;
import no.arkivlab.hioa.nikita.webapp.model.application.ConformityLevel;
import no.arkivlab.hioa.nikita.webapp.model.application.FondsStructureDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static nikita.config.Constants.*;

/**
 * REST controller that returns information about the Noark 5 cores conformity to standards.
 */
@RestController
@RequestMapping(value = "/", produces = {NOARK5_V4_CONTENT_TYPE})
@Api(value = "Application", description = "Links to where the various interfaces can be accessed from")
public class ApplicationController {

    @Autowired
    Environment env;

    /**
     * identify the interfaces the core supports
     *
     * @return the application details along with the correct response code (200 OK, or 500 Internal Error)
     */
    // API - All GET Requests (CRUD - READ)
    @Counted
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity <ApplicationDetails> identify() {

        ApplicationDetails applicationDetails;

        try {
            applicationDetails = new ApplicationDetails(getConformityLevels());
        }
        catch (Exception e) {
            return new ResponseEntity <> (HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity <> (applicationDetails, HttpStatus.OK);
    }

    @Counted
    @RequestMapping(value = HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<FondsStructureDetails> fondsStructure() {
        return new ResponseEntity<>(new FondsStructureDetails(), HttpStatus.OK);
    }

    @Counted
    @RequestMapping(value = HATEOAS_API_PATH + SLASH + NOARK_CASE_HANDLING_PATH + SLASH, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<FondsStructureDetails> caseHandling() {
        return new ResponseEntity<>(new FondsStructureDetails(), HttpStatus.OK);
    }

    /**
     * reads the application-PROFILE.yml file and picks up the official conformity levels, but also any additional
     * interfaces that the core supports (api/gui etc). Returns this as an List of ConformityObjects
     *
     * @return
     * @throws Exception if there is a problem reading the contents from the property yaml files
     */

    protected List<ConformityLevel> getConformityLevels() throws Exception {

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();
        String[] officialConformityLevels = env.getProperty("nikita-noark5-core.details.conformity-levels.official", String[].class);
        ArrayList <ConformityLevel> conformityLevelsList = new ArrayList<>();
        // If we later have other conformity levels, you will need to include description values here
        // this can be things like mass-import conformity or byggesakarkiv etc
        // This should be the only place you have to add support for this.
        // The namespaces may change, which means we might have to rebuild the yaml files and how we build conformity
        // descriptions. Currently the namespace is hardcoded in NOARK_CONFORMANCE_REL =
        // "http://rel.kxml.no/noark5/v4/api/"; and the conformity description (e.g. sakarkiv) is added

        // Approach. Sort the list of conformity levels from the application-PROFILE.yaml file
        // and make sure you have the order sorted in the correct order in code
        Arrays.sort(officialConformityLevels);
        if (!Arrays.equals(officialConformityLevels, new String[]{NOARK_FONDS_STRUCTURE_PATH, NOARK_CASE_HANDLING_PATH})) {
            throw new Exception("Minimum number of conformance levels not supported.");
        }

        for (int i=0; i<officialConformityLevels.length; i++) {
            ConformityLevel conformityLevel = new ConformityLevel();
            String href = uri + SLASH + HATEOAS_API_PATH + SLASH + officialConformityLevels[i] + SLASH;
            String rel = NOARK_CONFORMANCE_REL + officialConformityLevels[i];
            conformityLevel.setHref(href);
            conformityLevel.setRel(rel);
            conformityLevelsList.add(conformityLevel);
        }

        // Here we add the addresses for services that the core supports that are not officially part of the standard
        // interface descriptions like byggesak, masseimport may become standardised later, but unofficial interfaces
        // can be listed here
        String[] nonOfficialConformityLevels = env.getProperty("nikita-noark5-core.details.conformity-levels.non-official", String[].class);

        for (int i=0; i<nonOfficialConformityLevels.length; i++) {
            ConformityLevel conformityLevel = new ConformityLevel();
            String href = uri + SLASH + nonOfficialConformityLevels[i] + SLASH;
            String rel = NIKITA_CONFORMANCE_REL + nonOfficialConformityLevels[i];
            conformityLevel.setHref(href);
            conformityLevel.setRel(rel);
            conformityLevelsList.add(conformityLevel);
        }

        return conformityLevelsList;
    }
}