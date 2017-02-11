package nikita.model.noark5.v4.hateoas;

import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;

import java.util.List;

public interface IHateoasNoarkObject {
    List<Link> getLinks(INoarkSystemIdEntity entity);

    List<INoarkSystemIdEntity> getList();

    void addLink(INoarkSystemIdEntity entity, Link link);

    void addSelfLink(Link selfLink);

    List<Link> getSelfLinks();

    boolean isSingleEntity();
}