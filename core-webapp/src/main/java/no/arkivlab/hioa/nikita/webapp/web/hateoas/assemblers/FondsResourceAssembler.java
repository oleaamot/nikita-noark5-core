package no.arkivlab.hioa.nikita.webapp.web.hateoas.assemblers;

import nikita.model.noark5.v4.Fonds;
import no.arkivlab.hioa.nikita.webapp.web.controller.hateoas.FondsHateoasController;
import no.arkivlab.hioa.nikita.webapp.web.model.hateoas.FondsResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

/**
 *
 * Resource assembler for Fonds objects. Allows for more fine grained control of creating HATEOAS objects
 *
 */
@Component
public class FondsResourceAssembler extends ResourceAssemblerSupport<Fonds, FondsResource> {

    FondsResourceAssembler() {
        super(FondsHateoasController.class, FondsResource.class);
    }

    @Override
    public FondsResource toResource(Fonds fonds) {

        FondsResource fondsResource = instantiateResource(fonds);


        return fondsResource;
    }

    @Override
    protected FondsResource instantiateResource(Fonds fonds) {
        return new FondsResource(fonds);
    }

}