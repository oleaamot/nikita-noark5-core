package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.Fonds;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.FondsHateoasSerializer;

import java.util.ArrayList;
import java.util.List;

@JsonSerialize(using = FondsHateoasSerializer.class)
public class FondsHateoas implements IHateoasNoarkObject {

    protected List<Link> links = new ArrayList<>();
    protected Fonds fonds;
    private List<Fonds> fondsList;

    public FondsHateoas(Fonds fonds) {
        this.fonds = fonds;
    }

    public FondsHateoas(List<Fonds> fondsList) {
        this.fondsList = fondsList;
    }

    public List<Link> getLinks() {
        return links;
    }
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(Link link) {
        this.links.add(link);
    }

    public Fonds getFonds() {
        return fonds;
    }
    public void setFonds(Fonds fonds) {
        this.fonds = fonds;
    }

    public List<Fonds> getFondsList() {
        return fondsList;
    }

    public void setFondsList(List<Fonds> fondsList) {
        this.fondsList = fondsList;
    }

    public INoarkSystemIdEntity getSystemIdEntity() {
        return fonds;
    }

    @SuppressWarnings("unchecked")
    public List<INoarkSystemIdEntity> getSystemIdEntityList() {
        return (ArrayList<INoarkSystemIdEntity>) (ArrayList) fondsList;
    }
}
