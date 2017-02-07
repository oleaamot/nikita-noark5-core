package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.FondsCreator;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.FondsCreatorHateoasSerializer;

import java.util.ArrayList;
import java.util.List;

@JsonSerialize(using = FondsCreatorHateoasSerializer.class)
public class FondsCreatorHateoas implements IHateoasNoarkObject {

    protected List<Link> links;
    protected FondsCreator fondsCreator;
    private List <FondsCreator> fondsCreatorList;

    public FondsCreatorHateoas(FondsCreator fondsCreator) {
        this.fondsCreator = fondsCreator;
    }

    public FondsCreatorHateoas(List<FondsCreator> fondsCreatorList) {
        this.fondsCreatorList = fondsCreatorList;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void addLink(Link link) {
        this.links.add(link);
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

    public List<FondsCreator> getfondsCreatorList() {
        return fondsCreatorList;
    }

    public void setfondsCreatorList(List<FondsCreator> fondsCreatorList) {
        this.fondsCreatorList = fondsCreatorList;
    }
    public INoarkSystemIdEntity getSystemIdEntity() {
        return fondsCreator;
    }

    @SuppressWarnings("unchecked")
    public List<INoarkSystemIdEntity> getSystemIdEntityList() {
        return (ArrayList<INoarkSystemIdEntity>) (ArrayList) fondsCreatorList;
    }
}
