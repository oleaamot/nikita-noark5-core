package nikita.util.serializers.noark5v4.hateoas;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.google.common.collect.Iterables;
import nikita.model.noark5.v4.Series;
import nikita.model.noark5.v4.hateoas.SeriesHateoas;
import nikita.util.CommonUtils;

import java.io.IOException;
import java.lang.reflect.Type;

import static nikita.config.Constants.DATE_FORMAT;
import static nikita.config.N5ResourceMappings.*;

/**
 *
 * Serialise an outgoing Series object as JSON.
 *
 * Having an own serializer is done to have more fine grained control over the output. We need to be able to especially
 * control the HATEOAS links and the actual format of the HATEOAS links might change over time with the standard. This
 * allows us to be able to easily adapt to any changes
 *
 * Only Norwegian property names are used on the outgoing JSON property names and are in accordance with the Noark
 * standard.
 *
 * Note. Only values that are part of the standard are included in the JSON. Properties like 'id' or 'deleted' are not
 * exported
 *
 */
public class SeriesHateoasSerializer extends StdSerializer<SeriesHateoas> {

    public SeriesHateoasSerializer() {
        super(SeriesHateoas.class);
    }

    @Override
    public void serialize(SeriesHateoas seriesHateoas, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        Iterable<Series> seriesIterable = seriesHateoas.getSeriesList();
        if (seriesIterable != null && Iterables.size(seriesIterable) > 0) {
            jgen.writeStartObject();
            jgen.writeFieldName(SERIES);
            jgen.writeStartArray();
            for (Series series : seriesIterable) {
                serializeSeries(series, seriesHateoas, jgen, provider);
            }
            jgen.writeEndArray();
            CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, seriesHateoas.getLinks());
            jgen.writeEndObject();
        }
        else if (seriesHateoas.getSeries() != null) {
            serializeSeries(seriesHateoas.getSeries(), seriesHateoas, jgen, provider);
        }
        // It's an empty object, so returning empty Hateoas links _links : []
        else {
            jgen.writeStartObject();
            CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, null);
            jgen.writeEndObject();
        }

    }

    private void serializeSeries(Series series, SeriesHateoas seriesHateoas, JsonGenerator jgen,
                                 SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        CommonUtils.Hateoas.Serialize.printSystemIdEntity(jgen, series);
        CommonUtils.Hateoas.Serialize.printTitleAndDescription(jgen, series);
        if (series.getSeriesStatus() != null) {
            jgen.writeStringField(SERIES_STATUS, series.getSeriesStatus());
        }
        CommonUtils.Hateoas.Serialize.printDocumentMedium(jgen, series);
        CommonUtils.Hateoas.Serialize.printStorageLocation(jgen, series);
        CommonUtils.Hateoas.Serialize.printCreateEntity(jgen, series);
        CommonUtils.Hateoas.Serialize.printFinaliseEntity(jgen, series);
        if (series.getSeriesStartDate() != null) {
            jgen.writeStringField(SERIES_START_DATE, DATE_FORMAT.format(series.getSeriesStartDate()));
        }
        if (series.getSeriesEndDate() != null) {
            jgen.writeStringField(SERIES_END_DATE, DATE_FORMAT.format(series.getSeriesEndDate()));
        }
        if (series.getReferencePrecursor() != null && series.getReferencePrecursor().getSystemId() != null) {
            jgen.writeStringField(SERIES_PRECURSOR, series.getReferencePrecursor().getSystemId());
        }
        if (series.getReferenceSuccessor() != null && series.getReferenceSuccessor().getSystemId() != null) {
            jgen.writeStringField(SERIES_SUCCESSOR, series.getReferenceSuccessor().getSystemId());
        }
        CommonUtils.Hateoas.Serialize.printDisposal(jgen, series);
        CommonUtils.Hateoas.Serialize.printDisposalUndertaken(jgen, series);
        CommonUtils.Hateoas.Serialize.printDeletion(jgen, series);
        CommonUtils.Hateoas.Serialize.printScreening(jgen, series);
        CommonUtils.Hateoas.Serialize.printClassified(jgen, series);
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, seriesHateoas.getLinks());
        jgen.writeEndObject();
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        throw new UnsupportedOperationException();
    }
}
