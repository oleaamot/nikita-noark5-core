package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.Series;
import nikita.util.serializers.noark5v4.hateoas.SeriesHateoasSerializer;

import java.util.ArrayList;
import java.util.List;

@JsonSerialize(using = SeriesHateoasSerializer.class)
public class SeriesHateoas implements IHateoasLinks {

    private List<Link> links = new ArrayList<>();
    private Series series;
    private Iterable<Series> seriesIterable;

    public SeriesHateoas(Series series) {
        this.series = series;
    }

    public SeriesHateoas(Iterable<Series> seriesIterable) {
        this.seriesIterable = seriesIterable;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(Link link) {
        links.add(link);
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Iterable<Series> getSeriesIterable() {return seriesIterable;}

    public void setSeriesIterable(Iterable<Series> seriesIterable) {this.seriesIterable = seriesIterable;}
}
