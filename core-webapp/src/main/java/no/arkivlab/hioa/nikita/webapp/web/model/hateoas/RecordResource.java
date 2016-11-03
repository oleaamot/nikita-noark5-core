package no.arkivlab.hioa.nikita.webapp.web.model.hateoas;

import nikita.model.noark5.v4.Record;
import org.springframework.hateoas.Resource;

public class RecordResource extends Resource<Record> {

    public RecordResource(Record record) {
        super(record);
    }
}
