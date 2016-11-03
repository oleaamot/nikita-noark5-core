package no.arkivlab.hioa.nikita.webapp.web.model.hateoas;

import nikita.model.noark5.v4.ClassificationSystem;
import org.springframework.hateoas.Resource;

public class ClassificationSystemResource extends Resource<ClassificationSystem> {

    public ClassificationSystemResource(ClassificationSystem classificationSystem) {
        super(classificationSystem);
    }
}
