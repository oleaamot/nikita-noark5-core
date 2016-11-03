package no.arkivlab.hioa.nikita.webapp.web.model.hateoas;

import nikita.model.noark5.v4.DocumentObject;
import org.springframework.hateoas.Resource;

public class DocumentObjectResource extends Resource<DocumentObject> {

    public DocumentObjectResource(DocumentObject documentObject) {
        super(documentObject);
    }
}
