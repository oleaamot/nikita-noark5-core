package nikita.model.noark5.v4.hateoas;

import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;

import java.util.List;
import java.util.Set;

public interface IHateoasNoarkObject {
    Set<Link> getLinks(INikitaEntity entity);

    List<INikitaEntity> getList();

    void addLink(INikitaEntity entity, Link link);

    void addSelfLink(Link selfLink);

    Set<Link> getSelfLinks();

    boolean isSingleEntity();
}