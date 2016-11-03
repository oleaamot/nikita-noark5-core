package no.arkivlab.hioa.nikita.webapp.web.model.hateoas;

import nikita.model.noark5.v4.DocumentDescription;
import org.springframework.hateoas.Resource;

public class DocumentDescriptionResource extends Resource<DocumentDescription> {

    public DocumentDescriptionResource(DocumentDescription documentDescription) {
        super(documentDescription);
    }
}
