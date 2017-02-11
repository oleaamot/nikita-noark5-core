package nikita.util.serializers.noark5v4.hateoas;

import com.fasterxml.jackson.core.JsonGenerator;
import nikita.model.noark5.v4.Series;
import nikita.model.noark5.v4.hateoas.HateoasNoarkObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.CommonUtils;
import nikita.util.serializers.noark5v4.hateoas.interfaces.IHateoasSerializer;

import java.io.IOException;

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
public class SeriesHateoasSerializer extends HateoasSerializer implements IHateoasSerializer {

    @Override
    public void serializeNoarkEntity(INoarkSystemIdEntity noarkSystemIdEntity,
                                     HateoasNoarkObject seriesHateoas, JsonGenerator jgen) throws IOException {
        Series series = (Series) noarkSystemIdEntity;
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
        CommonUtils.Hateoas.Serialize.printHateoasLinks(jgen, seriesHateoas.getLinks(series));
        jgen.writeEndObject();
    }
}
