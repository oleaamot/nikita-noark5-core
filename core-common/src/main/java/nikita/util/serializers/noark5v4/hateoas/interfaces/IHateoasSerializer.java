package nikita.util.serializers.noark5v4.hateoas.interfaces;

import com.fasterxml.jackson.core.JsonGenerator;
import nikita.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;

import java.io.IOException;

/**
 * Created by tsodring on 2/9/17.
 */
public interface IHateoasSerializer {
    void serializeNoarkEntity(INoarkSystemIdEntity entity, HateoasNoarkObject hateoasObject, JsonGenerator jgen)
            throws IOException;
}
