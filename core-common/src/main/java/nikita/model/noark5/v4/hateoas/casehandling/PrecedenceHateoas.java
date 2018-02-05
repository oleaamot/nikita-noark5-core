package nikita.model.noark5.v4.hateoas.casehandling;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.model.noark5.v4.hateoas.IHateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.util.serializers.noark5v4.hateoas.secondary.PrecedenceHateoasSerializer;

import java.util.List;

import static nikita.config.N5ResourceMappings.PRECEDENCE;

/**
 * Calls super to handle the links etc., provides a way to automatically deserialiase a PrecedenceHateoas object
 */
@JsonSerialize(using = PrecedenceHateoasSerializer.class)
public class PrecedenceHateoas extends HateoasNoarkObject implements IHateoasNoarkObject {

    public PrecedenceHateoas(INikitaEntity entity) {
        super(entity);
    }

    public PrecedenceHateoas(List<INikitaEntity> entityList) {
        super(entityList, PRECEDENCE);
    }
}
