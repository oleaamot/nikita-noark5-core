package no.arkivlab.hioa.nikita.webapp.web.model.hateoas;

import nikita.model.noark5.v4.Series;
import org.springframework.hateoas.Resource;

public class SeriesResource extends Resource<Series> {

    public SeriesResource(Series series) {
        super(series);
    }
}
