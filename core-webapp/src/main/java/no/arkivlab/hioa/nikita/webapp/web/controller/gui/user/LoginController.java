package no.arkivlab.hioa.nikita.webapp.web.controller.gui.user;

import no.arkivlab.hioa.nikita.webapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static nikita.config.Constants.HATEOAS_API_PATH;
import static nikita.config.Constants.SLASH;
import static nikita.config.N5ResourceMappings.FONDS;

@Controller
class LoginController {

    //registry.addViewController("/login") was removed and is now handled by LoginController.
    //  loginPage.htm was updated to show baseUrl and variations so we the front-end gets what it needs
    @Autowired
    private IUserService userService;

    @Value("${server.address}")
    private String serverAddress;

    @Value("${server.port}")
    private String serverPort;

    @Value("${server.contextPath}")
    private String contextPath;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            HttpServletRequest request) throws Exception {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }
        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.addObject("baseUrl", "http://" + serverAddress + ":" + serverPort + SLASH);
        model.addObject("contextPath", contextPath);
        model.addObject("api", HATEOAS_API_PATH + SLASH);
        model.addObject("startingPoint", "http://" + serverAddress + ":" + serverPort + contextPath +
                HATEOAS_API_PATH + SLASH + FONDS + SLASH);
        model.setViewName("webapp/login/loginPage");
        return model;
    }
}