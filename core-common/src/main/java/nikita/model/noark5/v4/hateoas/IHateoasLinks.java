package nikita.model.noark5.v4.hateoas;

import java.util.List;

public interface IHateoasLinks {
    List<Link> getLinks();
    void setLinks(List<Link> links);
}