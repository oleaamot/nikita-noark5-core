package no.arkivlab.hioa.nikita.webapp.web.controller.gui.user;

import no.arkivlab.hioa.nikita.webapp.model.security.User;
import no.arkivlab.hioa.nikita.webapp.service.IUserService;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.UsernameExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
class RegistrationController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "signup")
    public ModelAndView registrationForm() {
        return new ModelAndView("webapp/login/registrationPage", "user", new User());
    }

    @RequestMapping(value = "login/register")
    public ModelAndView registerUser(@Valid final User user, final BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("webapp/login/registrationPage", "user", user);
        }
        try {
            userService.registerNewUser(user);
        } catch (UsernameExistsException e) {
            result.addError(new FieldError("user", "email", e.getMessage()));
            return new ModelAndView("webapp/login/registrationPage", "user", user);
        }
        return new ModelAndView("redirect:/login");
    }
}