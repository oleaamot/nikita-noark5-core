package no.arkivlab.hioa.nikita.webapp.web.model.hateoas;

import nikita.model.noark5.v4.Fonds;
import org.springframework.hateoas.Resource;

public class FondsResource extends Resource<Fonds> {

    public FondsResource(Fonds fonds) {
        super(fonds);
    }
}
