package nikita.common.util.serializers.noark5v4.hateoas;

import com.fasterxml.jackson.core.JsonGenerator;
import nikita.common.model.noark5.v4.DocumentDescription;
import nikita.common.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.CommonUtils;
import nikita.common.util.serializers.noark5v4.hateoas.interfaces.IHateoasSerializer;

import java.io.IOException;

import static nikita.common.config.N5ResourceMappings.*;
import static nikita.common.util.CommonUtils.Hateoas.Serialize;

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

public class DocumentDescriptionHateoasSerializer extends HateoasSerializer implements IHateoasSerializer {

    @Override
    public void serializeNoarkEntity(INikitaEntity noarkEntity,
                                     HateoasNoarkObject documentDescriptionHateoas, JsonGenerator jgen
    ) throws IOException {
        DocumentDescription documentDescription = (DocumentDescription) noarkEntity;
        jgen.writeStartObject();

        // handle DocumentDescription properties
        CommonUtils.Hateoas.Serialize.printSystemIdEntity(jgen, documentDescription);
        if (documentDescription.getDocumentType() != null) {
            jgen.writeStringField(DOCUMENT_DESCRIPTION_DOCUMENT_TYPE, documentDescription.getDocumentType());
        }
        if (documentDescription.getDocumentStatus() != null) {
            jgen.writeStringField(DOCUMENT_DESCRIPTION_STATUS, documentDescription.getDocumentStatus());
        }
        CommonUtils.Hateoas.Serialize.printTitleAndDescription(jgen, documentDescription);
        if (documentDescription.getDocumentNumber() != null) {
            jgen.writeNumberField(DOCUMENT_DESCRIPTION_DOCUMENT_NUMBER, documentDescription.getDocumentNumber().intValue());
        }
        CommonUtils.Hateoas.Serialize.printCreateEntity(jgen, documentDescription);
        if (documentDescription.getAssociationDate() != null) {
            jgen.writeStringField(DOCUMENT_DESCRIPTION_ASSOCIATION_DATE,
                    Serialize.formatDate(documentDescription.getAssociationDate()));
        }
        if (documentDescription.getAssociationDate() != null) {
            jgen.writeStringField(DOCUMENT_DESCRIPTION_ASSOCIATION_BY,
                    documentDescription.getAssociatedBy());
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
        if (documentDescription.getStorageLocation() != null) {
            jgen.writeStringField(STORAGE_LOCATION, documentDescription.getStorageLocation());
        }
        CommonUtils.Hateoas.Serialize.printElectronicSignature(jgen, documentDescription);
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, documentDescriptionHateoas.getLinks(documentDescription));
        jgen.writeEndObject();
    }
}
