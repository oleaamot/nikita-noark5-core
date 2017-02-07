package nikita.model.noark5.v4.hateoas;

import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;

import java.util.List;

public interface IHateoasNoarkObject {
    List<Link> getLinks();
    void setLinks(List<Link> links);

    void addLink(Link link);

    INoarkSystemIdEntity getSystemIdEntity();

    List<INoarkSystemIdEntity> getSystemIdEntityList();

}