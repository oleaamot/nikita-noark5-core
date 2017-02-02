package nikita.util.serializers.noark5v4.hateoas;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.common.collect.Iterables;
import nikita.model.noark5.v4.File;
import nikita.model.noark5.v4.hateoas.FileHateoas;
import nikita.util.CommonUtils;

import java.io.IOException;
import java.lang.reflect.Type;

import static nikita.config.N5ResourceMappings.*;

/**
 * Serialise an outgoing File object as JSON.
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

public class FileHateoasSerializer extends StdSerializer<FileHateoas> {

    public FileHateoasSerializer() {
        super(FileHateoas.class);
    }

    @Override
    public void serialize(FileHateoas fileHateoas, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        Iterable<File> fileIterable = fileHateoas.getFileIterable();
        if (fileIterable != null && Iterables.size(fileIterable) > 0) {
            jgen.writeStartObject();
            jgen.writeFieldName(FILE);
            jgen.writeStartArray();
            for (File file : fileIterable) {
                serializeFile(file, fileHateoas, jgen, provider);
            }
            jgen.writeEndArray();
            CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, fileHateoas.getLinks());
            jgen.writeEndObject();
        } else if (fileHateoas.getFile() != null) {
            serializeFile(fileHateoas.getFile(), fileHateoas, jgen, provider);
        }
        // It's an empty object, so returning empty Hateoas links _links : []
        else {
            jgen.writeStartObject();
            CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, null);
            jgen.writeEndObject();
        }
    }

    private void serializeFile(File file, FileHateoas fileHateoas,
                               JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        CommonUtils.Hateoas.Serialize.printSystemIdEntity(jgen, file);
        CommonUtils.Hateoas.Serialize.printStorageLocation(jgen, file);
        if (file.getFileId()!= null) {
            jgen.writeStringField(FILE_ID, file.getFileId());
            if (file.getTitle() != null) {
                jgen.writeStringField(TITLE, file.getTitle());
            }
            if (file.getOfficialTitle() != null) {
                jgen.writeStringField(FILE_PUBLIC_TITLE, file.getOfficialTitle());
            }
            if (file.getDescription() != null) {
                jgen.writeStringField(DESCRIPTION, file.getDescription());
            }
        }
        CommonUtils.Hateoas.Serialize.printDocumentMedium(jgen, file);
        CommonUtils.Hateoas.Serialize.printKeyword(jgen, file);
        CommonUtils.Hateoas.Serialize.printCreateEntity(jgen, file);
        CommonUtils.Hateoas.Serialize.printFinaliseEntity(jgen, file);
        if (file.getReferenceSeries() != null && file.getReferenceSeries().getSystemId() != null) {
            jgen.writeStringField(SERIES_REFERENCE, file.getReferenceSeries().getSystemId());
        }
        //TODO: CommonCommonUtils.Hateoas.Serialize.printCrossReference(jgen, file.getReferenceCrossReference());
        CommonUtils.Hateoas.Serialize.printComment(jgen, file);
        CommonUtils.Hateoas.Serialize.printDisposal(jgen, file);
        CommonUtils.Hateoas.Serialize.printScreening(jgen, file);
        CommonUtils.Hateoas.Serialize.printClassified(jgen, file);
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, fileHateoas.getLinks());
        jgen.writeEndObject();
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        throw new UnsupportedOperationException();
    }
}
