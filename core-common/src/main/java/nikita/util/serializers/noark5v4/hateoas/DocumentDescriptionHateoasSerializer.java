package nikita.util.serializers.noark5v4.hateoas;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.common.collect.Iterables;
import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.hateoas.DocumentDescriptionHateoas;
import nikita.util.CommonUtils;

import java.io.IOException;
import java.lang.reflect.Type;

import static nikita.config.Constants.DATE_FORMAT;
import static nikita.config.N5ResourceMappings.*;

/**
 * Serialise an outgoing DocumentDescription object as JSON.
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

public class DocumentDescriptionHateoasSerializer extends StdSerializer<DocumentDescriptionHateoas> {

    public DocumentDescriptionHateoasSerializer() {
        super(DocumentDescriptionHateoas.class);
    }

    @Override
    public void serialize(DocumentDescriptionHateoas documentDescriptionHateoas, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

<<<<<<< HEAD
        Iterable<DocumentDescription> documentDescriptionIterable = documentDescriptionHateoas.getDocumentDescriptionList();
        if (documentDescriptionIterable != null) {
=======
        Iterable<DocumentDescription> documentDescriptionIterable = documentDescriptionHateoas.getDocumentDescriptionIterable();
        if (documentDescriptionIterable != null && Iterables.size(documentDescriptionIterable) > 0) {
>>>>>>> master
            jgen.writeStartObject();
            jgen.writeFieldName(DOCUMENT_DESCRIPTION);
            jgen.writeStartArray();
            for (DocumentDescription documentDescription : documentDescriptionIterable) {
                serializeDocumentDescription(documentDescription, documentDescriptionHateoas, jgen, provider);
            }
            jgen.writeEndArray();
            CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, documentDescriptionHateoas.getLinks());
            jgen.writeEndObject();
        } else if (documentDescriptionHateoas.getDocumentDescription() != null) {
            serializeDocumentDescription(documentDescriptionHateoas.getDocumentDescription(), documentDescriptionHateoas, jgen, provider);
        }
        // It's an empty object, so returning empty Hateoas links _links : []
        else {
            jgen.writeStartObject();
            CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, null);
            jgen.writeEndObject();
        }
    }

    private void serializeDocumentDescription(DocumentDescription documentDescription, DocumentDescriptionHateoas documentDescriptionHateoas,
                                              JsonGenerator jgen, SerializerProvider provider) throws IOException {


        jgen.writeStartObject();

        // handle DocumentDescription properties
        CommonUtils.Hateoas.Serialize.printSystemIdEntity(jgen, documentDescription);
        if (documentDescription.getDocumentType() != null) {
            jgen.writeStringField(DOCUMENT_DESCRIPTION_DOCUMENT_TYPE, documentDescription.getDocumentType());
        }
        if (documentDescription.getDocumentStatus() != null) {
            jgen.writeStringField(DOCUMENT_DESCRIPTION_, documentDescription.getDocumentStatus());
        }
        CommonUtils.Hateoas.Serialize.printTitleAndDescription(jgen, documentDescription);
        if (documentDescription.getDocumentNumber() != null) {
            jgen.writeStringField(DOCUMENT_DESCRIPTION_DOCUMENT_NUMBER, Integer.toString(documentDescription.getDocumentNumber()));
        }
        CommonUtils.Hateoas.Serialize.printCreateEntity(jgen, documentDescription);
        if (documentDescription.getAssociationDate()!= null) {
            jgen.writeStringField(DOCUMENT_DESCRIPTION_ASSOCIATION_DATE, DATE_FORMAT.format(documentDescription.getAssociationDate()));
        }
        if (documentDescription.getAssociatedWithRecordAs() != null) {
            jgen.writeStringField(DOCUMENT_DESCRIPTION_ASSOCIATED_WITH_RECORD_AS, documentDescription.getAssociatedWithRecordAs());

        }
        CommonUtils.Hateoas.Serialize.printComment(jgen, documentDescription);
        CommonUtils.Hateoas.Serialize.printDisposal(jgen, documentDescription);
        CommonUtils.Hateoas.Serialize.printDisposalUndertaken(jgen, documentDescription);
        CommonUtils.Hateoas.Serialize.printDeletion(jgen, documentDescription);
        CommonUtils.Hateoas.Serialize.printScreening(jgen, documentDescription);
        CommonUtils.Hateoas.Serialize.printClassified(jgen, documentDescription);
        CommonUtils.Hateoas.Serialize.printElectronicSignature(jgen, documentDescription);
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, documentDescriptionHateoas.getLinks());
        jgen.writeEndObject();
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        throw new UnsupportedOperationException();
    }
}
