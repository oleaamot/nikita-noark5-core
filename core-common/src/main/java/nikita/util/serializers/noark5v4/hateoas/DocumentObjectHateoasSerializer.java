package nikita.util.serializers.noark5v4.hateoas;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.common.collect.Iterables;
import nikita.model.noark5.v4.DocumentObject;
import nikita.model.noark5.v4.hateoas.DocumentObjectHateoas;
import nikita.util.CommonUtils;

import java.io.IOException;
import java.lang.reflect.Type;

import static nikita.config.N5ResourceMappings.*;

/**
 * Serialise an outgoing DocumentObject object as JSON.
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

public class DocumentObjectHateoasSerializer extends StdSerializer<DocumentObjectHateoas> {

    public DocumentObjectHateoasSerializer() {
        super(DocumentObjectHateoas.class);
    }

    @Override
    public void serialize(DocumentObjectHateoas documentObjectHateoas, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

<<<<<<< HEAD
        Iterable<DocumentObject> documentObjectIterable = documentObjectHateoas.getDocumentObjectList();
        if (documentObjectIterable != null) {
=======
        Iterable<DocumentObject> documentObjectIterable = documentObjectHateoas.getDocumentObjectIterable();
        if (documentObjectIterable != null && Iterables.size(documentObjectIterable) > 0) {
>>>>>>> master
            jgen.writeStartObject();
            jgen.writeFieldName(DOCUMENT_OBJECT);
            jgen.writeStartArray();
            for (DocumentObject documentObject : documentObjectIterable) {
                serializeDocumentObject(documentObject, documentObjectHateoas, jgen, provider);
            }
            jgen.writeEndArray();
            CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, documentObjectHateoas.getLinks());
            jgen.writeEndObject();
        } else if (documentObjectHateoas.getDocumentObject() != null) {
            serializeDocumentObject(documentObjectHateoas.getDocumentObject(), documentObjectHateoas, jgen, provider);
        }
        // It's an empty object, so returning empty Hateoas links
        else {
            jgen.writeStartObject();
            CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, null);
            jgen.writeEndObject();
        }
    }

    private void serializeDocumentObject(DocumentObject documentObject, DocumentObjectHateoas documentObjectHateoas,
                                         JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        // handle DocumentObject properties
        CommonUtils.Hateoas.Serialize.printSystemIdEntity(jgen, documentObject);

        if (documentObject.getVersionNumber() != null) {
            jgen.writeStringField(DOCUMENT_OBJECT_VERSION_NUMBER, Integer.toString(documentObject.getVersionNumber()));
        }
        if (documentObject.getVariantFormat() != null) {
            jgen.writeStringField(DOCUMENT_OBJECT_VARIANT_FORMAT, documentObject.getVariantFormat());
        }
        if (documentObject.getFormat() != null) {
            jgen.writeStringField(DOCUMENT_OBJECT_FORMAT, documentObject.getFormat());
        }
        if (documentObject.getFormatDetails() != null) {
            jgen.writeStringField(DOCUMENT_OBJECT_FORMAT_DETAILS, documentObject.getFormatDetails());
        }
        CommonUtils.Hateoas.Serialize.printCreateEntity(jgen, documentObject);
        if (documentObject.getReferenceDocumentFile() != null) {
            jgen.writeStringField(DOCUMENT_OBJECT_REFERENCE_DOCUMENT_FILE, documentObject.getReferenceDocumentFile());
        }
        if (documentObject.getChecksum() != null) {
            jgen.writeStringField(DOCUMENT_OBJECT_CHECKSUM, documentObject.getChecksum());
        }
        if (documentObject.getChecksumAlgorithm() != null) {
            jgen.writeStringField(DOCUMENT_OBJECT_CHECKSUM_ALGORITHM, documentObject.getChecksumAlgorithm());
        }
        if (documentObject.getFileSize() != null) {
            jgen.writeStringField(DOCUMENT_OBJECT_FILE_SIZE, Long.toString(documentObject.getFileSize()));
        }
        CommonUtils.Hateoas.Serialize.printElectronicSignature(jgen, documentObject);
        CommonUtils.Hateoas.Serialize.printConversion(jgen, documentObject);
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, documentObjectHateoas.getLinks());
        jgen.writeEndObject();
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        throw new UnsupportedOperationException();
    }
}
