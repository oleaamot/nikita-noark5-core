package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.RegistryEntry;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.RegistryEntryHateoasSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tsodring on 11/1/17.
 *
 * Using composition rather than inheritance. Although this class is really only a placeholder for the RegistryEntry object
 * along with the hateoas links. It's not intended that you will manipulate the RegistryEntry object from here.
 *
 */
@JsonSerialize(using = RegistryEntryHateoasSerializer.class)
public class RegistryEntryHateoas implements IHateoasNoarkObject {

    protected List<Link> links = new ArrayList<>();
    RegistryEntry registryEntry;
    private List<RegistryEntry> registryEntryList;

    public RegistryEntryHateoas(RegistryEntry registryEntry){
        this.registryEntry = registryEntry;
    }

    public RegistryEntryHateoas(List<RegistryEntry> registryEntryList) {
        this.registryEntryList = registryEntryList;
    }

    public List<Link> getLinks() {
        return links;
    }
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(Link link) {
        this.links.add(link);
    }

    public RegistryEntry getRegistryEntry() {
        return registryEntry;
    }
    public void setRegistryEntry(RegistryEntry registryEntry) {
        this.registryEntry = registryEntry;
    }

    public List<RegistryEntry> getRegistryEntryList() {
        return registryEntryList;
    }

    public void setRegistryEntryList(List<RegistryEntry> registryEntryList) {
        this.registryEntryList = registryEntryList;
    }

    public INoarkSystemIdEntity getSystemIdEntity() {
        return registryEntry;
    }

    @SuppressWarnings("unchecked")
    public List<INoarkSystemIdEntity> getSystemIdEntityList() {
        return (ArrayList<INoarkSystemIdEntity>) (ArrayList) registryEntryList;
    }
}
