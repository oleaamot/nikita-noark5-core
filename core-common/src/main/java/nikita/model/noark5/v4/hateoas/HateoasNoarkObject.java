package nikita.model.noark5.v4.hateoas;

import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * This class solves the problem of adding Hateoas links to Noark entities
 * before they are published by the core.
 * <p>
 * A HateoasNoarkObject is used for serializing Noark objects that have a
 * systemId. A Controller will populate the entityList with the results of a
 * query to the persistence layer.  There are classes that traverses this
 * list of entities and populates corresponding entity HashMap entry with
 * Hateoas links.
 * <p>
 * See the subclasses for details of who the serializer is that reuses the
 * HashMap.
 * <p>
 * Scalability with the HashMap (1000's of entries) isn't an issue because
 * pagination is used in the core
 */
public class HateoasNoarkObject implements IHateoasNoarkObject {

    /**
     * A list of noark entities comprising a result set from a query. Using a
     * List to get implements Iterator and Collection.
     */
    private List<INikitaEntity> entityList = new ArrayList<>();

    /**
     * When a List<INikitaEntity> is in use, we make a note of the entityType
     * e.g is it a Fonds or a File or any other type.
     */
    private String entityType;

    /**
     * A Map of noark entities -> Hateoas links e.g fonds with Hateoas links
     */
    private Map<INikitaEntity, ArrayList<Link>> hateoasMap = new HashMap<>();

    /**
     * If the Hateoas object is a list of results, then the list needs its own
     * Hateoas Link to self because currently we're only adding self link
     */
    private List<Link> selfLinks = new ArrayList<>();

    /**
     * Whether or not the Hateoas object contains a single entity or a list of
     * entities. For simplicity a list is used even if the query
     * generated a single result. Makes coding other places easier
     */
    private boolean singleEntity = true;

    public HateoasNoarkObject(INikitaEntity entity) {
        entityList.add(entity);
    }

    public HateoasNoarkObject(List<INikitaEntity> entityList,
                              String entityType) {
        this.entityList.addAll(entityList);
        this.entityType = entityType;
        singleEntity = false;
    }

    @Override
    public List<Link> getLinks(INikitaEntity entity) {
        return hateoasMap.get(entity);
    }

    @Override
    public List<INikitaEntity> getList() {
        return entityList;
    }

    @Override
    public void addLink(INikitaEntity entity, Link link) {
        hateoasMap.computeIfAbsent(entity, k -> new ArrayList<>()).add(link);
    }

    @Override
    public void addSelfLink(Link selfLink) {
        selfLinks.add(selfLink);
    }

    @Override
    public void addLink(Link selfLink) {
        selfLinks.add(selfLink);
    }

    @Override
    public List<Link> getSelfLinks() {
        return selfLinks;
    }

    @Override
    public boolean isSingleEntity() {
        return singleEntity;
    }

    public String getEntityType() {
        return entityType;
    }

    @Override
    public Long getEntityVersion() {
        if (isSingleEntity() && entityList.get(0) != null)
            return entityList.get(0).getVersion();
        else
            return new Long(-1);
    }
}
