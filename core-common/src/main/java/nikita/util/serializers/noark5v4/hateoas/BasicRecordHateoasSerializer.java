package nikita.util.serializers.noark5v4.hateoas;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.common.collect.Iterables;
import nikita.model.noark5.v4.BasicRecord;
import nikita.model.noark5.v4.hateoas.BasicRecordHateoas;
import nikita.util.CommonUtils;

import java.io.IOException;
import java.lang.reflect.Type;

import static nikita.config.Constants.DATE_TIME_FORMAT;
import static nikita.config.N5ResourceMappings.*;

/**
 * Serialise an outgoing BasicRecord object as JSON.
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

public class BasicRecordHateoasSerializer extends StdSerializer<BasicRecordHateoas> {

    public BasicRecordHateoasSerializer() {
        super(BasicRecordHateoas.class);
    }

    @Override
    public void serialize(BasicRecordHateoas basicRecordHateoas, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        Iterable<BasicRecord> basicRecordIterable = basicRecordHateoas.getBasicRecordIterable();
        if (basicRecordIterable != null && Iterables.size(basicRecordIterable) > 0) {
            jgen.writeStartObject();
            jgen.writeFieldName(BASIC_RECORD);
            jgen.writeStartArray();
            for (BasicRecord basicRecord : basicRecordIterable) {
                serializeBasicRecord(basicRecord, basicRecordHateoas, jgen, provider);
            }
            jgen.writeEndArray();
            jgen.writeEndObject();
        } else if (basicRecordHateoas.getBasicRecord() != null) {
            serializeBasicRecord(basicRecordHateoas.getBasicRecord(), basicRecordHateoas, jgen, provider);
        }
        // It's an empty object, so returning empty Hateoas links _links : []
        else {
            jgen.writeStartObject();
            CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, null);
            jgen.writeEndObject();
        }
    }

    private void serializeBasicRecord(BasicRecord basicRecord, BasicRecordHateoas basicRecordHateoas,
                                      JsonGenerator jgen, SerializerProvider provider) throws IOException {

        jgen.writeStartObject();

        // handle the record properties first
        CommonUtils.Hateoas.Serialize.printSystemIdEntity(jgen, basicRecord);
        CommonUtils.Hateoas.Serialize.printCreateEntity(jgen, basicRecord);
        if (basicRecord.getArchivedDate() != null) {
            jgen.writeStringField(RECORD_ARCHIVED_DATE, DATE_TIME_FORMAT.format(basicRecord.getArchivedDate()));
        }
        if (basicRecord.getArchivedBy()!= null) {
            jgen.writeStringField(RECORD_ARCHIVED_BY, basicRecord.getArchivedBy());
        }
        CommonUtils.Hateoas.Serialize.printDisposal(jgen, basicRecord);
        CommonUtils.Hateoas.Serialize.printScreening(jgen, basicRecord);
        CommonUtils.Hateoas.Serialize.printClassified(jgen, basicRecord);
        // handle general basicRecord properties
        if (basicRecord.getTitle() != null) {
            jgen.writeStringField(TITLE, basicRecord.getTitle());
        }
        if (basicRecord.getOfficialTitle() != null) {
            jgen.writeStringField(FILE_PUBLIC_TITLE, basicRecord.getOfficialTitle());
        }
        if (basicRecord.getDescription() != null) {
            jgen.writeStringField(DESCRIPTION, basicRecord.getDescription());
        }
        CommonUtils.Hateoas.Serialize.printKeyword(jgen, basicRecord);
        CommonUtils.Hateoas.Serialize.printDocumentMedium(jgen, basicRecord);
        CommonUtils.Hateoas.Serialize.printStorageLocation(jgen, basicRecord);
        CommonUtils.Hateoas.Serialize.printComment(jgen, basicRecord);
        // TODO: FIX THIS CommonCommonUtils.Hateoas.Serialize.printCrossReference(jgen, basicRecord);
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, basicRecordHateoas.getLinks());
        jgen.writeEndObject();
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        throw new UnsupportedOperationException();
    }
}
