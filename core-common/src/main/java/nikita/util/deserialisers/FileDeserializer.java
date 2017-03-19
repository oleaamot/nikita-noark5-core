package nikita.util.deserialisers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.model.noark5.v4.File;
import nikita.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;
import nikita.util.CommonUtils;
import nikita.util.deserialisers.interfaces.ObligatoryPropertiesCheck;
import nikita.util.exceptions.NikitaMalformedInputDataException;

import java.io.IOException;

import static nikita.config.N5ResourceMappings.FILE_ID;
import static nikita.config.N5ResourceMappings.FILE_PUBLIC_TITLE;

/**
 * Created by tsodring on 1/6/17.
 *
 * Deserialise an incoming File JSON object.
 *
 * Having a own deserialiser is done to have more fine grained control over the input. This allows us to be less strict
 * with property names, allowing for both English and Norwegian property names
 *
 * Both English and Norwegian property names can be used in the incoming JSON as well as there being no requirement with
 * regards to small and large letters in property names.
 *
 * Note this implementation expects that the File object to deserialise is in compliance with the Noark standard where
 * certain properties i.e. createdBy and createdDate are set by the core, not the caller. This deserializer will not 
 * enforce this and will deserialize a file object correctly. This is because e.g the import interface will require
 * such functionality.
 *
 *  - Testing of compliance of properties is handled by the core, either in FileController or FileService
 * 
 * Note. Currently we do not include 'id' or 'deleted' properties. 'id' is a primary key and it is assumed this is 
 * taken care of by the DBMS and 'deleted' is a field internal to the core to handle soft delete. Importing soft deleted
 * objects is something we do not consider necessary.
 *
 * Note:
 *  - Unknown property values in the JSON will trigger an exception
 *  - Missing obligatory property values in the JSON will trigger an exception
 */
public class FileDeserializer extends JsonDeserializer implements ObligatoryPropertiesCheck {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public File deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {
        File file = new File();
        ObjectNode objectNode = mapper.readTree(jsonParser);

        // Deserialise general properties
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkEntity(file, objectNode);
        CommonUtils.Hateoas.Deserialize.deserialiseDocumentMedium(file, objectNode);
        CommonUtils.Hateoas.Deserialize.deserialiseStorageLocation(file, objectNode);
        CommonUtils.Hateoas.Deserialize.deserialiseKeyword(file, objectNode);

        // Deserialize fileId
        JsonNode currentNode = objectNode.get(FILE_ID);
        if (null != currentNode) {
            file.setFileId(currentNode.textValue());
            objectNode.remove(FILE_ID);
        }
        // Deserialize officialTitle
        currentNode = objectNode.get(FILE_PUBLIC_TITLE);
        if (null != currentNode) {
            file.setOfficialTitle(currentNode.textValue());
            objectNode.remove(FILE_PUBLIC_TITLE);
        }
        // TODO: FIX THIS CommonCommonUtils.Hateoas.Deserialize.deserialiseCrossReference(file, objectNode);
        CommonUtils.Hateoas.Deserialize.deserialiseComments(file, objectNode);
        file.setReferenceDisposal(CommonUtils.Hateoas.Deserialize.deserialiseDisposal(objectNode));
        file.setReferenceScreening(CommonUtils.Hateoas.Deserialize.deserialiseScreening(objectNode));
        file.setReferenceClassified(CommonUtils.Hateoas.Deserialize.deserialiseClassified(objectNode));

        // Check that all obligatory values are present
        checkForObligatoryNoarkValues(file);
        checkForObligatoryFileValues(file);

        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            throw new NikitaMalformedInputDataException("The mappe you tried to create is malformed. The "
                    + "following fields are not recognised as mappe fields  [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]");
        }
        return file;
    }

    @Override
    /**
     *
     * The only general noark field that is mandatory, according to arkivstruktur.xsd, when creating the object is
     * 'title'
     */
    public void checkForObligatoryNoarkValues(INoarkGeneralEntity noarkEntity) {

        if (noarkEntity.getTitle() == null) {
            throw new NikitaMalformedInputDataException("The mappe you tried to create is malformed. The "
                    + "tittel field is mandatory, and you have submitted an empty value.");
        }
    }
    /**
     *
     * The only file field that is mandatory, according to arkivstruktur.xsd, when creating the object is
     * 'fileId'
     */
    public void checkForObligatoryFileValues(File file) {

        if (file.getFileId() == null) {
            throw new NikitaMalformedInputDataException("The mappe you tried to create is malformed. The "
                    + "mappeID field is mandatory, and you have submitted an empty value.");
        }
    }
}
