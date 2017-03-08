package nikita.util.serializers.noark5v4.hateoas;

import com.fasterxml.jackson.core.JsonGenerator;
import nikita.model.noark5.v4.DocumentObject;
import nikita.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.CommonUtils;
import nikita.util.serializers.noark5v4.hateoas.interfaces.IHateoasSerializer;

import java.io.IOException;

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

public class DocumentObjectHateoasSerializer extends HateoasSerializer implements IHateoasSerializer {

    @Override
    public void serializeNoarkEntity(INoarkSystemIdEntity noarkSystemIdEntity,
                                     HateoasNoarkObject documentObjectHateoas, JsonGenerator jgen
    ) throws IOException {

        DocumentObject documentObject = (DocumentObject) noarkSystemIdEntity;

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
        if (documentObject.getOriginalFilename() != null) {
            jgen.writeStringField(DOCUMENT_OBJECT_FILE_NAME, documentObject.getOriginalFilename());
        }
        if (documentObject.getMimeType() != null) {
            jgen.writeStringField(DOCUMENT_OBJECT_MIME_TYPE, documentObject.getMimeType());
        }
        CommonUtils.Hateoas.Serialize.printElectronicSignature(jgen, documentObject);
        CommonUtils.Hateoas.Serialize.printConversion(jgen, documentObject);
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, documentObjectHateoas.getLinks(documentObject));
        jgen.writeEndObject();
    }
}
