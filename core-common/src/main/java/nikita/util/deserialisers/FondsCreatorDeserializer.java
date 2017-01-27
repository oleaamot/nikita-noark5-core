package nikita.util.deserialisers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.util.exceptions.NikitaMalformedInputDataException;
import nikita.model.noark5.v4.FondsCreator;
import nikita.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;
import nikita.util.deserialisers.interfaces.ObligatoryPropertiesCheck;
import nikita.util.CommonUtils;

import java.io.IOException;

/**
 * Created by tsodring on 1/6/17.
 *
 * Deserialise an incoming FondsCreator JSON object.
 *
 * Having a own deserialiser is done to have more fine grained control over the input. This allows us to be less strict
 * with property names, allowing for both English and Norwegian property names
 *
 * Both English and Norwegian property names can be used in the incoming JSON as well as there being no requirement with
 * regards to small and large letters in property names.
 *
 * Note this implementation expects that the fondsCreator object to deserialise is in compliance with the Noark standard where
 * certain properties i.e. createdBy and createdDate are set by the core, not the caller. This deserializer will not 
 * enforce this and will deserialize a fondsCreator object correctly. This is because e.g the import interface will require 
 * such functionality.
 *
 *  - Testing of compliance of properties is handled by the core, either in FondsCreatorController or FondsCreatorService
 * 
 * Note. Currently we do not include 'id' or 'deleted' properties. 'id' is a primary key and it is assumed this is 
 * taken care of by the DBMS and 'deleted' is a field internal to the core to handle soft delete. Importing soft deleted
 * objects is something we do not consider necessary.
 *
 * Note:
 *  - Unknown property values in the JSON will trigger an exception
 *  - Missing obligatory property values in the JSON will trigger an exception
 */
public class FondsCreatorDeserializer extends JsonDeserializer implements ObligatoryPropertiesCheck {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public FondsCreator deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {

        FondsCreator fondsCreator = new FondsCreator();
        ObjectNode objectNode = mapper.readTree(jsonParser);

        // Deserialise general properties
        CommonUtils.Hateoas.Deserialize.deserialiseFondsCreator(fondsCreator, objectNode);

        // Check that all obligatory values are present
        checkForObligatoryFondsCreatorValues(fondsCreator);

        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            throw new NikitaMalformedInputDataException("The FondsCreator object you tried to create is malformed. The "
                    + "following objects are not recognised as FondsCreator properties [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]");
        }

        return fondsCreator;
    }

    @Override
    /**
     *
     * FondsCreator is not a INoarkGeneralEntity
     */
    public void checkForObligatoryNoarkValues(INoarkGeneralEntity noarkEntity) {}

    /**
     *
     * FondsCreator is not a INoarkGeneralEntity
     */
    public void checkForObligatoryFondsCreatorValues(FondsCreator fondsCreator) {}

}


/*


        // Deserialize systemId
        JsonNode currentNode = objectNode.get(SYSTEM_ID);
        String key = SYSTEM_ID;
        if (currentNode == null) {
            currentNode = objectNode.get(SYSTEM_ID_EN);
            key = SYSTEM_ID_EN;
        }
        if (currentNode != null) {
            fondsCreator.setSystemId(currentNode.textValue());
            objectNode.remove(key);
        }

        // Deserialize title
        currentNode = objectNode.get(TITLE);
        key = TITLE;
        if (currentNode == null) {
            currentNode = objectNode.get(TITLE_EN);
            key = TITLE_EN;
        }
        if (currentNode != null) {
            fondsCreator.setTitle(currentNode.textValue());
            objectNode.remove(key);
        }

        // Deserialize description
        currentNode = objectNode.get(DESCRIPTION);
        key = DESCRIPTION;
        if (currentNode == null) {
            currentNode = objectNode.get(DESCRIPTION_EN);
            key = DESCRIPTION_EN;
        }
        if (currentNode != null) {
            fondsCreator.setDescription(currentNode.textValue());
            objectNode.remove(key);
        }

        // Deserialize fondsCreatorStatus
        currentNode = objectNode.get(FondsCreator_STATUS);
        key = FondsCreator_STATUS;
        if (currentNode == null) {
            currentNode = objectNode.get(FondsCreator_STATUS_EN);
            key = FondsCreator_STATUS_EN;
        }
        if (currentNode != null) {
            fondsCreator.setFondsCreatorStatus(currentNode.textValue());
            objectNode.remove(key);
        }

        // Deserialize documentMedium
        currentNode = objectNode.get(DOCUMENT_MEDIUM);
        key = DOCUMENT_MEDIUM;
        if (currentNode == null) {
            currentNode = objectNode.get(DOCUMENT_MEDIUM_EN);
            key = DOCUMENT_MEDIUM_EN;
        }
        if (currentNode != null) {
            fondsCreator.setDocumentMedium(currentNode.textValue());
            objectNode.remove(key);
        }


        // Deserialize storageLocation
        currentNode = objectNode.get(STORAGE_LOCATION);
        key = STORAGE_LOCATION;
        if (currentNode == null) {
            currentNode = objectNode.get(STORAGE_LOCATION_EN);
            key = STORAGE_LOCATION_EN;
        }
        if (currentNode != null) {
            HashSet <FondsCreator> referenceFondsCreator = new HashSet<FondsCreator>();
            referenceFondsCreator.add(fondsCreator);

            if(currentNode.isArray() == true) {
                currentNode.iterator();
                for (JsonNode node: currentNode) {
                    String location = node.textValue();
                    StorageLocation storageLocation = new StorageLocation();
                    storageLocation.setSystemId(UUID.randomUUID().toString());
                    storageLocation.setStorageLocation(location);
                    storageLocation.setReferenceFondsCreator(referenceFondsCreator);
                    HashSet <StorageLocation> storageLocations = (HashSet <StorageLocation>) fondsCreator.getReferenceStorageLocation();
                    storageLocations.add(storageLocation);
                }
            }

            objectNode.remove(key);
        }

        // Deserialize createdDate
        currentNode = objectNode.get(CREATED_DATE);
        key = CREATED_DATE;
        if (currentNode == null) {
            currentNode = objectNode.get(CREATED_DATE_EN);
            key = CREATED_DATE_EN;
        }
        if (currentNode != null) {
            Date parsedDate = null;
            try {
                parsedDate = dateFormat.parse(currentNode.textValue());
                fondsCreator.setCreatedDate(parsedDate);
            }
            catch (ParseException e) {
                if (parsedDate != null) {
                    throw new NikitaMalformedInputDataException("The FondsCreator object you tried to create " +
                            "has a malformed opprettetData/createdDate. Your format was " + parsedDate +
                            ". Actual format is " + NOARK_DATE_TIME_FORMAT_PATTERN);
                }
            }
            objectNode.remove(key);
        }

        // Deserialize createdBy
        currentNode = objectNode.get(CREATED_BY);
        key = CREATED_BY;
        if (currentNode == null) {
            currentNode = objectNode.get(CREATED_BY_EN);
            key = CREATED_BY_EN;
        }
        if (currentNode != null) {
            fondsCreator.setCreatedBy(currentNode.textValue());
            objectNode.remove(key);
        }

        // Deserialize finalisedDate
        currentNode = objectNode.get(FINALISED_DATE);
        key = FINALISED_DATE;
        if (currentNode == null) {
            currentNode = objectNode.get(FINALISED_DATE_EN);
            key = FINALISED_DATE_EN;
        }
        if (currentNode != null) {
            Date parsedDate = null;
            try {
                parsedDate = dateFormat.parse(currentNode.textValue());
                fondsCreator.setFinalisedDate(parsedDate);
            }
            catch (ParseException e) {
                if (parsedDate != null) {
                    throw new NikitaMalformedInputDataException("The FondsCreator object you tried to create " +
                            "has a malformed avsluttetData/finalisedDate. Your format was " + parsedDate +
                            ". Actual format is " + NOARK_DATE_TIME_FORMAT_PATTERN);
                }
            }
            objectNode.remove(key);
        }

        // Deserialize finalisedBy
        currentNode = objectNode.get(FINALISED_BY);
        key = FINALISED_BY;
        if (currentNode == null) {
            currentNode = objectNode.get(FINALISED_BY_EN);
            key = FINALISED_BY_EN;
        }
        if (currentNode != null) {
            fondsCreator.setCreatedBy(currentNode.textValue());
            objectNode.remove(key);
        }



     // Deserialise
        currentNode = objectNode.get();
        key = ;
        if (currentNode == null) {
            currentNode = objectNode.get();
            key = ;
        }
        if (currentNode != null) {
            fondsCreator(currentNode.textValue());
            objectNode.remove(key);
        }

        // Deserialise
        currentNode = objectNode.get();
        key = ;
        if (currentNode == null) {
            currentNode = objectNode.get();
            key = ;
        }
        if (currentNode != null) {
            fondsCreator(currentNode.textValue());
            objectNode.remove(key);
        }
        JsonNode storageLocations = objectNode.get(STORAGE_LOCATION);

        if (storageLocations == null // no storageLocations found
                || !storageLocations.isArray()           // or author node is not an array
                || !storageLocations.elements().hasNext())   // or author node doesn't contain any authors
            return null;



        while ((currentToken = jsonParser.nextValue()) != null) {
            switch (currentToken) {
                case VALUE_STRING:
                    String value = jsonParser.getValueAsString();
                    String currentName = jsonParser.getCurrentName();


                    if (currentName == null) {
                        JsonToken last = jsonParser.getLastClearedToken();
                        String lastS = last.toString();

                        //JSONArray jsonarray = new JSONArray(jsonParser.getCurrentToken().toString());
                        throw new NikitaMalformedInputDataException("The FondsCreator object you tried to create " +
                                "is malformed in some way. The core is unable to process it! Please check the " +
                                "format of the incoming JSON payload you created and make sure it is valid. " +
                                "Sorry, but I can give no further information about this error. Maybe check logfile.");
                    }
                    if (currentName.equalsIgnoreCase(SYSTEM_ID) || currentName.equalsIgnoreCase(SYSTEM_ID_EN)) {
                        fondsCreator.setSystemId(jsonParser.getValueAsString());
                    }
                    else if (currentName.equalsIgnoreCase(TITLE) || currentName.equalsIgnoreCase(TITLE_EN)) {
                        fondsCreator.setTitle(jsonParser.getValueAsString());
                    }
                    else if (currentName.equalsIgnoreCase(DESCRIPTION) ||
                             currentName.equalsIgnoreCase(DESCRIPTION_EN)) {
                        fondsCreator.setDescription(jsonParser.getValueAsString());
                    }
                    else if (currentName.equalsIgnoreCase(FondsCreator_STATUS) ||
                             currentName.equalsIgnoreCase(FondsCreator_STATUS_EN)) {
                        fondsCreator.setFondsCreatorStatus(jsonParser.getValueAsString());
                    }
                    else if (currentName.equalsIgnoreCase(DOCUMENT_MEDIUM) ||
                             currentName.equalsIgnoreCase(DOCUMENT_MEDIUM_EN)) {
                        fondsCreator.setDocumentMedium(jsonParser.getValueAsString());
                    }
                    else if (currentName.equalsIgnoreCase(STORAGE_LOCATION) ||
                             currentName.equalsIgnoreCase(STORAGE_LOCATION_EN)) {

                        String value1 = jsonParser.getValueAsString();
                        String currentName1 = jsonParser.getCurrentName();

                        //fondsCreator.setDocumentMedium(jsonParser.getValueAsString());
                    }
                    else if (currentName.equalsIgnoreCase(CREATED_DATE) ||
                            currentName.equalsIgnoreCase(CREATED_DATE_EN)) {
                        Date parsedDate = null;
                        try {
                            if (jsonParser.getValueAsString() != null) {
                                parsedDate = dateFormat.parse(jsonParser.getValueAsString());
                                fondsCreator.setCreatedDate(parsedDate);
                            }
                        }
                        catch (ParseException e) {
                            if (parsedDate != null) {
                                throw new NikitaMalformedInputDataException("The FondsCreator object you tried to create " +
                                        "has a malformed opprettetData/createdDate. Your format was " + parsedDate +
                                        ". Actual format is " + NOARK_DATE_TIME_FORMAT_PATTERN);
                            }
                        }
                    }
                    else if (currentName.equalsIgnoreCase(CREATED_BY) || currentName.equalsIgnoreCase(CREATED_BY_EN)) {
                        fondsCreator.setCreatedBy(jsonParser.getValueAsString());
                    }
                    else if (currentName.equalsIgnoreCase(FINALISED_DATE) ||
                             currentName.equalsIgnoreCase(FINALISED_DATE_EN)) {
                        Date parsedDate = null;
                        try {
                            if (jsonParser.getValueAsString() != null) {
                                parsedDate = dateFormat.parse(jsonParser.getValueAsString());
                                fondsCreator.setFinalisedDate(parsedDate);
                            }
                        }
                        catch (ParseException e) {
                            if (parsedDate != null) {
                                throw new NikitaMalformedInputDataException("The FondsCreator object you tried to create " +
                                        "has a malformed avsluttetDato/finalisedDate. Your format was " + parsedDate +
                                        ". Actual format is " + NOARK_DATE_TIME_FORMAT_PATTERN);
                           }
                        }
                    }
                    else if (currentName.equalsIgnoreCase(FINALISED_BY) ||
                             currentName.equalsIgnoreCase(FINALISED_BY_EN)) {
                        fondsCreator.setFinalisedBy(jsonParser.getValueAsString());
                    }
                    else {
                        // If anything else is found in the JSON input, it's deemed invalid and a
                        // NikitaMalformedInputDataException exception is thrown.
                        throw new NikitaMalformedInputDataException("The FondsCreator object you tried to create is " +
                                "malformed. Found a property [" + currentName + "] that is not part of a fondsCreator " +
                                "object ");
                    }
                    break;
                case START_ARRAY:
                    String value3 = jsonParser.getValueAsString();
                    String currentName4 = jsonParser.getCurrentName();
                    break;
                case VALUE_EMBEDDED_OBJECT:
                    String value5 = jsonParser.getValueAsString();
                    String currentName5 = jsonParser.getCurrentName();
                    break;
                default:
                    break;
            } // switch
        } // while
        // Check that all obligatory values are presesnt
        if (!checkForObligatoryValues(fondsCreator)) {
            throw new NikitaMalformedInputDataException("The FondsCreator object you tried to create is malformed. The "
                    + "title field is mandatory, and you have submitted an empty value [" + fondsCreator.getTitle() +"]");
        }
        return fondsCreator;
    }


}
*/