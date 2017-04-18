package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.RegistryEntryHateoasSerializer;

import java.util.List;

import static nikita.config.N5ResourceMappings.REGISTRY_ENTRY;

/**
 * Created by tsodring on 11/1/17.
 *
 * Using composition rather than inheritance. Although this class is really only a placeholder for the RegistryEntry object
 * along with the hateoas links. It's not intended that you will manipulate the RegistryEntry object from here.
 *
 */
@JsonSerialize(using = RegistryEntryHateoasSerializer.class)
public class RegistryEntryHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public RegistryEntryHateoas(INoarkSystemIdEntity entity) {
        super(entity);
    }

    public RegistryEntryHateoas(List<INoarkSystemIdEntity> entityList) {
        super(entityList, REGISTRY_ENTRY);
    }

}