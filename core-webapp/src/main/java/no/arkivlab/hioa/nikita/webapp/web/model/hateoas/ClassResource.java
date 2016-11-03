package no.arkivlab.hioa.nikita.webapp.web.model.hateoas;

import nikita.model.noark5.v4.Class;
import org.springframework.hateoas.Resource;

public class ClassResource extends Resource<Class> {

    public ClassResource(Class klass) {
        super(klass);
    }
}
