package no.arkivlab.hioa.nikita.webapp.web.model.hateoas;

import nikita.model.noark5.v4.File;
import org.springframework.hateoas.Resource;

public class FileResource extends Resource<File> {

    public FileResource(File file) {
        super(file);
    }
}
