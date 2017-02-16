package nikita.util.serializers.noark5v4.hateoas;

import com.fasterxml.jackson.core.JsonGenerator;
import nikita.model.noark5.v4.Record;
import nikita.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.CommonUtils;
import nikita.util.serializers.noark5v4.hateoas.interfaces.IHateoasSerializer;

import java.io.IOException;

import static nikita.config.Constants.DATE_TIME_FORMAT;
import static nikita.config.N5ResourceMappings.RECORD_ARCHIVED_BY;
import static nikita.config.N5ResourceMappings.RECORD_ARCHIVED_DATE;

/**
 * Serialise an outgoing Record object as JSON.
 * <p>
 * Having an own serializer is done to have more fine grained control over the output. We need to be able to especially
 * control the HATEOAS links and the actual format of the HATEOAS links might change over time with the standard. This
 * allows us to be able to easily adapt to any changes
 * <p>
 * Only Norwegian property names are used on the outgoing JSON property names and are in accordance with the Noark
 * standard.
 * <p>
 * Note. Only values that are part of the standard are included in the JSON. Properties like 'id' or 'deleted' are not
 * exported
 */

public class RecordHateoasSerializer extends HateoasSerializer implements IHateoasSerializer {

    @Override
    public void serializeNoarkEntity(INoarkSystemIdEntity noarkSystemIdEntity,
                                     HateoasNoarkObject recordHateoas, JsonGenerator jgen) throws IOException {

        Record record = (Record) noarkSystemIdEntity;

        jgen.writeStartObject();
        CommonUtils.Hateoas.Serialize.printSystemIdEntity(jgen, record);
        CommonUtils.Hateoas.Serialize.printCreateEntity(jgen, record);

        if (record.getArchivedDate() != null) {
            jgen.writeStringField(RECORD_ARCHIVED_DATE, DATE_TIME_FORMAT.format(record.getArchivedDate()));
        }
        if (record.getArchivedBy() != null) {
            jgen.writeStringField(RECORD_ARCHIVED_BY, record.getArchivedBy());
        }

        CommonUtils.Hateoas.Serialize.printDisposal(jgen, record);
        CommonUtils.Hateoas.Serialize.printScreening(jgen, record);
        CommonUtils.Hateoas.Serialize.printClassified(jgen, record);
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, recordHateoas.getLinks(record));
        jgen.writeEndObject();
    }
}
