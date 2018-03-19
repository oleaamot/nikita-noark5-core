package nikita.common.util.deserialisers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.Series;
import nikita.common.util.CommonUtils;
import nikita.common.util.exceptions.NikitaMalformedInputDataException;

import java.io.IOException;

/**
 * Created by tsodring on 1/6/17.
 * <p>
 * Deserialise an incoming Series JSON object.
 * <p>
 * Having a own deserialiser is done to have more fine grained control over the input. This allows us to be less strict
 * with property names, allowing for both English and Norwegian property names
 * <p>
 * <p>
 * <p>
 * Note this implementation expects that the Series object to deserialise is in compliance with the Noark standard where
 * certain properties i.e. createdBy and createdDate are set by the core, not the caller. This deserializer will not
 * enforce this and will deserialize a arkivdel correctly. This is because e.g the import interface will require
 * such functionality.
 * <p>
 * - Testing of compliance of properties is handled by the core, either in SeriesController or SeriesService
 * <p>
 * Note. Currently we do not include 'id' or 'deleted' properties. 'id' is a primary key and it is assumed this is
 * taken care of by the DBMS and 'deleted' is a field internal to the core to handle soft delete. Importing soft deleted
 * objects is something we do not consider necessary.
 * <p>
 * Note:
 * - Unknown property values in the JSON will trigger an exception
 * - Missing obligatory property values in the JSON will trigger an exception
 */
public class SeriesDeserializer extends JsonDeserializer {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Series deserialize(JsonParser jsonParser, DeserializationContext dc)
            throws IOException {
        StringBuilder errors = new StringBuilder();

        Series series = new Series();
        ObjectNode objectNode = mapper.readTree(jsonParser);

        // Deserialise general properties
        CommonUtils.Hateoas.Deserialize.deserialiseNoarkEntity(series, objectNode, errors);
        CommonUtils.Hateoas.Deserialize.deserialiseDocumentMedium(series, objectNode, errors);
        CommonUtils.Hateoas.Deserialize.deserialiseStorageLocation(series, objectNode, errors);

        // Deserialize seriesStatus
        JsonNode currentNode = objectNode.get(N5ResourceMappings.SERIES_STATUS);
        if (null != currentNode) {
            series.setSeriesStatus(currentNode.textValue());
            objectNode.remove(N5ResourceMappings.SERIES_STATUS);
        }

        // Deserialize seriesStartDate
        series.setSeriesStartDate(CommonUtils.Hateoas.Deserialize.deserializeDate(N5ResourceMappings.SERIES_START_DATE, objectNode, errors));
        // Deserialize seriesEndDate
        series.setSeriesEndDate(CommonUtils.Hateoas.Deserialize.deserializeDate(N5ResourceMappings.SERIES_END_DATE, objectNode, errors));

        // Deserialize referencePrecursor
        currentNode = objectNode.get(N5ResourceMappings.SERIES_PRECURSOR);
        if (null != currentNode) {
            Series seriesPrecursor = new Series();
            seriesPrecursor.setSystemId(currentNode.textValue());
            series.setReferencePrecursor(seriesPrecursor);
            // TODO: Does this imply that the current arkivdel is the successor?
            // I would not set it here, as the service class has to check that
            // the seriesPrecursor object actually exists
            objectNode.remove(N5ResourceMappings.SERIES_PRECURSOR);
        }
        // Deserialize referenceSuccessor
        currentNode = objectNode.get(N5ResourceMappings.SERIES_SUCCESSOR);
        if (null != currentNode) {
            Series seriesSuccessor = new Series();
            seriesSuccessor.setSystemId(currentNode.textValue());
            series.setReferenceSuccessor(seriesSuccessor);
            // TODO: Does this imply that the current arkivdel is the precursor?
            // I would not set it here, as the service class should do this
            objectNode.remove(N5ResourceMappings.SERIES_SUCCESSOR);
        }
        series.setReferenceDisposal(CommonUtils.Hateoas.Deserialize.deserialiseDisposal(objectNode, errors));
        series.setReferenceDisposalUndertaken(CommonUtils.Hateoas.Deserialize.deserialiseDisposalUndertaken(objectNode, errors));
        series.setReferenceDeletion(CommonUtils.Hateoas.Deserialize.deserialiseDeletion(objectNode, errors));
        series.setReferenceScreening(CommonUtils.Hateoas.Deserialize.deserialiseScreening(objectNode, errors));
        series.setReferenceClassified(CommonUtils.Hateoas.Deserialize.deserialiseClassified(objectNode, errors));

        // Check that there are no additional values left after processing the tree
        // If there are additional throw a malformed input exception
        if (objectNode.size() != 0) {
            errors.append("The arkivdel you tried to create is malformed. The " +
                    "following fields are not recognised as arkivdel fields [" +
                    CommonUtils.Hateoas.Deserialize.checkNodeObjectEmpty(objectNode) + "]. ");
        }

        if (0 < errors.length())
            throw new NikitaMalformedInputDataException(errors.toString());

        return series;
    }
}
