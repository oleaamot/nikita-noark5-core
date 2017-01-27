package no.arkivlab.hioa.nikita.webapp.web.controller.gui.noark;

import nikita.config.Constants;
import nikita.config.N5ResourceMappings;
import nikita.model.noark5.v4.Fonds;
import nikita.repository.n5v4.IFondsRepository;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFondsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping(value = Constants.GUI_PATH + "/" + N5ResourceMappings.FONDS)
class FondsGUIController {

    @Autowired
    private IFondsRepository fondsRepository;

    @Autowired
    private IFondsService fondsService;

    @RequestMapping
    public ModelAndView list(Locale locale) {
        Iterable<Fonds> fonds = this.fondsRepository.findByOwnedBy(null);
        return new ModelAndView("webapp/noark/fonds/list", "fonds", fonds);
    }

    @RequestMapping("{id}")
    public ModelAndView view(@PathVariable("id") Fonds fonds) {
        return new ModelAndView("webapp/noark/fonds/view", "fonds", fonds);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView create(@Valid final Fonds fonds, final BindingResult result, final RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("webapp/noark/fonds/form", "formErrors", result.getAllErrors());
        }
        //try {
            fondsService.createNewFonds(fonds);
        //} catch (FondsnameExistsException e) {
         //   result.addError(new FieldError("fonds", "email", e.getMessage()));
         //   return new ModelAndView("webapp/fonds/form", "fonds", fonds);
        //}
        redirect.addFlashAttribute("globalMessage", "Successfully created a new fonds");
        return new ModelAndView("redirect:/gui/arkiv/{fonds.id}", "fonds.id", fonds.getId());
    }

    @RequestMapping(value = "delete/{id}")
    public ModelAndView delete(@PathVariable("id") final Long id) {
        this.fondsRepository.delete(id);
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "modify/{id}", method = RequestMethod.GET)
    public ModelAndView modifyForm(@PathVariable("id") final Fonds fonds) {
        return new ModelAndView("webapp/noark/fonds/form", "fonds", fonds);
    }

    // the form

    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(@ModelAttribute final Fonds fonds) {
        return "webapp/noark/fonds/form";
    }
}
