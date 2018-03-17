package nikita.webapp.web.controller;

import com.codahale.metrics.annotation.Counted;
import io.swagger.annotations.Api;
import nikita.common.util.CommonUtils;
import nikita.webapp.model.application.*;
import nikita.webapp.service.application.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static nikita.common.config.Constants.*;

/**
 * REST controller that returns information about the Noark 5 cores conformity to standards.
 */
@RestController("ApplicationController")
@RequestMapping(produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
@Api(value = "Application", description = "Links to where the various interfaces can be accessed from")
public class ApplicationController {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    private ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * identify the interfaces the core supports
     *
     * @return the application details along with the correct response code (200 OK, or 500 Internal Error)
     */
    // API - All GET Requests (CRUD - READ)
    @Counted
    @RequestMapping(method = {RequestMethod.GET})
    @ResponseBody
    public ResponseEntity <ApplicationDetails> identify(HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(applicationService. getApplicationDetails());
    }

    @Counted
    @RequestMapping(value = HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<FondsStructureDetails> fondsStructure(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(applicationService.getFondsStructureDetails());
    }

    @Counted
    @RequestMapping(value = HATEOAS_API_PATH + SLASH + NOARK_METADATA_PATH, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<MetadataDetails> metadataPath(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(applicationService.getMetadataDetails());
    }

    @Counted
    @RequestMapping(value = HATEOAS_API_PATH + SLASH + NOARK_ADMINISTRATION_PATH, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<AdministrationDetails> adminPath(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(applicationService.getAdministrationDetails());
    }

    @Counted
    @RequestMapping(value = HATEOAS_API_PATH + SLASH + NOARK_CASE_HANDLING_PATH, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<CaseHandlingDetails> caseHandling(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(applicationService.getCaseHandlingDetails());
    }
}
