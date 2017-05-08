package no.arkivlab.hioa.nikita.webapp.web.controller.browser;

import nikita.config.Constants;
import nikita.util.CommonUtils;
import no.arkivlab.hioa.nikita.webapp.model.application.ApplicationDetails;
import no.arkivlab.hioa.nikita.webapp.model.application.ConformityLevel;
import no.arkivlab.hioa.nikita.webapp.web.controller.ApplicationController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import static nikita.config.Constants.*;

/**
 * Created by tsodring on 5/5/17.
 */
@RestController("BrowserApplicationController")
@RequestMapping(value = "/")
public class BrowserApplicationController extends ApplicationController {
/*
    @RequestMapping(method = {RequestMethod.GET})
    public ModelAndView identifyForBrowser(HttpServletRequest request) {
        return new ModelAndView("webapp/browser/applicationlist", "applicationDetails", getApplicationDetails());
    }
*/
}
