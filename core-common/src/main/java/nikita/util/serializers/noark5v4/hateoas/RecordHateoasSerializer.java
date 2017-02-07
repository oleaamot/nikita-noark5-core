package nikita.util.serializers.noark5v4.hateoas;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.common.collect.Iterables;
import nikita.model.noark5.v4.Record;
import nikita.model.noark5.v4.hateoas.RecordHateoas;
import nikita.util.CommonUtils;
import nikita.util.exceptions.NikitaEntityException;

import java.io.IOException;
import java.lang.reflect.Type;

import static nikita.config.Constants.DATE_TIME_FORMAT;
import static nikita.config.N5ResourceMappings.*;

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

public class RecordHateoasSerializer extends StdSerializer<RecordHateoas> {

    public RecordHateoasSerializer() {
        super(RecordHateoas.class);
    }

    @Override
    public void serialize(RecordHateoas recordHateoas, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

<<<<<<< HEAD
        Iterable<Record> recordIterable = recordHateoas.getRecordList();
        if (recordIterable != null) {
=======
        Iterable<Record> recordIterable = recordHateoas.getRecordIterable();
        if (recordIterable != null && Iterables.size(recordIterable) > 0) {
>>>>>>> master
            jgen.writeStartObject();
            jgen.writeFieldName(REGISTRATION);
            jgen.writeStartArray();
            for (Record record : recordIterable) {
                serializeRecord(record, recordHateoas, jgen, provider);
            }
            jgen.writeEndArray();
            CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, recordHateoas.getLinks());
            jgen.writeEndObject();
        } else if (recordHateoas.getRecord() != null) {
            serializeRecord(recordHateoas.getRecord(), recordHateoas, jgen, provider);
        }
        // It's an empty object, so returning empty Hateoas links _links : []
        else {
            jgen.writeStartObject();
            CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, null);
            jgen.writeEndObject();
        }
    }

    private void serializeRecord(Record record, RecordHateoas recordHateoas,
                                 JsonGenerator jgen, SerializerProvider provider) throws IOException {

        if (record == null) {
            // TODO: Should we just be serialising an empty string in this case?
            // This case should never occur. A non existing Record should be exception handled
            throw new NikitaEntityException("When serializing a Record entity, the entity was null");
        }

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
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, recordHateoas.getLinks());
        jgen.writeEndObject();
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        throw new UnsupportedOperationException();
    }
}
