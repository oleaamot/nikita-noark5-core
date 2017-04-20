package nikita.model.noark5.v4.hateoas;

import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;

import java.util.List;
import java.util.Set;

public interface IHateoasNoarkObject {
    Set<Link> getLinks(INoarkSystemIdEntity entity);

    List<INoarkSystemIdEntity> getList();

    void addLink(INoarkSystemIdEntity entity, Link link);

    void addSelfLink(Link selfLink);

    Set<Link> getSelfLinks();

    boolean isSingleEntity();
}