package nikita.model.noark5.v4.hateoas;

import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;

import java.util.List;

public interface IHateoasNoarkObject {
    List<Link> getLinks(INikitaEntity entity);

    List<INikitaEntity> getList();

    void addLink(INikitaEntity entity, Link link);

    void addSelfLink(Link selfLink);

    void addLink(Link selfLink);

    List<Link> getSelfLinks();

    boolean isSingleEntity();
}
