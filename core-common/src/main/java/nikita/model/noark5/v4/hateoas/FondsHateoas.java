package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.Fonds;
import nikita.util.serializers.noark5v4.hateoas.FondsHateoasSerializer;

import java.util.List;

@JsonSerialize(using = FondsHateoasSerializer.class)
public class FondsHateoas implements IHateoasLinks {

    protected List<Link> links;
    protected Fonds fonds;
    private Iterable<Fonds> fondsIterable;

    public FondsHateoas(Fonds fonds) {
        this.fonds = fonds;
    }

    public FondsHateoas(Iterable<Fonds> fondsIterable) {
        this.fondsIterable = fondsIterable;
    }

    public List<Link> getLinks() {
        return links;
    }
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Fonds getFonds() {
        return fonds;
    }
    public void setFonds(Fonds fonds) {
        this.fonds = fonds;
    }

    public Iterable<Fonds> getFondsIterable() {
        return fondsIterable;
    }

    public void setFondsIterable(Iterable<Fonds> fondsIterable) {
        this.fondsIterable = fondsIterable;
    }
}
