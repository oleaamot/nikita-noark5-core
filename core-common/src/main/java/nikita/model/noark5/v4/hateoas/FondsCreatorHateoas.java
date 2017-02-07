package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.FondsCreator;
import nikita.util.serializers.noark5v4.hateoas.FondsCreatorHateoasSerializer;

import java.util.List;

@JsonSerialize(using = FondsCreatorHateoasSerializer.class)
public class FondsCreatorHateoas implements IHateoasLinks {

    protected List<Link> links;
    protected FondsCreator fondsCreator;
    private Iterable<FondsCreator> fondsIterable;

    public FondsCreatorHateoas(FondsCreator fondsCreator) {
        this.fondsCreator = fondsCreator;
    }

    public FondsCreatorHateoas(Iterable<FondsCreator> fondsIterable) {
        this.fondsIterable = fondsIterable;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public FondsCreator getFondsCreator() {
        return fondsCreator;
    }

    public void setFondsCreator(FondsCreator fondsCreator) {
        this.fondsCreator = fondsCreator;
    }

    public Iterable<FondsCreator> getFondsCreatorIterable() {
        return fondsIterable;
    }

    public void setFondsCreatorIterable(Iterable<FondsCreator> fondsIterable) {
        this.fondsIterable = fondsIterable;
    }
}
