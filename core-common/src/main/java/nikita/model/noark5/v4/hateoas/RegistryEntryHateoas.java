package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.RegistryEntry;
import nikita.util.serializers.noark5v4.hateoas.RegistryEntryHateoasSerializer;

import java.util.List;

/**
 * Created by tsodring on 11/1/17.
 *
 * Using composition rather than inheritance. Although this class is really only a placeholder for the RegistryEntry object
 * along with the hateoas links. It's not intended that you will manipulate the RegistryEntry object from here.
 *
 */
@JsonSerialize(using = RegistryEntryHateoasSerializer.class)
public class RegistryEntryHateoas implements IHateoasLinks {

    protected List<Link> links;
    RegistryEntry registryEntry;
    private Iterable<RegistryEntry> registryEntryIterable;

    public RegistryEntryHateoas(RegistryEntry registryEntry){
        this.registryEntry = registryEntry;
    }

    public RegistryEntryHateoas(Iterable<RegistryEntry> registryEntryIterable) {
        this.registryEntryIterable = registryEntryIterable;
    }

    public List<Link> getLinks() {
        return links;
    }
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public RegistryEntry getRegistryEntry() {
        return registryEntry;
    }
    public void setRegistryEntry(RegistryEntry registryEntry) {
        this.registryEntry = registryEntry;
    }

    public Iterable<RegistryEntry> getRegistryEntryIterable() {
        return registryEntryIterable;
    }

    public void setRegistryEntryIterable(Iterable<RegistryEntry> registryEntryIterable) {
        this.registryEntryIterable = registryEntryIterable;
    }
}
