package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.Series;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.SeriesHateoasSerializer;

import java.util.ArrayList;
import java.util.List;

@JsonSerialize(using = SeriesHateoasSerializer.class)
public class SeriesHateoas implements IHateoasNoarkObject {

    private List<Link> links = new ArrayList<>();
    private Series series;
    private List<Series> seriesList;

    public SeriesHateoas(Series series) {
        this.series = series;
    }

    public SeriesHateoas(List<Series> seriesList) {
        this.seriesList = seriesList;
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

    public List<Series> getSeriesList() {
        return seriesList;
    }

    public void setSeriesList(List<Series> seriesList) {
        this.seriesList = seriesList;
    }

    public INoarkSystemIdEntity getSystemIdEntity() {
        return series;
    }

    @SuppressWarnings("unchecked")
    public List<INoarkSystemIdEntity> getSystemIdEntityList() {
        return (ArrayList<INoarkSystemIdEntity>) (ArrayList) seriesList;
    }
}
