package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.Record;
import nikita.util.serializers.noark5v4.hateoas.RecordHateoasSerializer;

import java.util.List;

/**
 * Created by tsodring on 12/9/16.
 *
 * Using composition rather than inheritance. Although this class is really only a placeholder for the Record object
 * along with the hateoas links. It's not intended that you will manipulate the Record object from here.
 *
 */
@JsonSerialize(using = RecordHateoasSerializer.class)
public class RecordHateoas implements IHateoasLinks {

    protected List<Link> links;
    Record record;

    public RecordHateoas(Record record){
        this.record = record;
    }

    public List<Link> getLinks() {
        return links;
    }
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Record getRecord() {
        return record;
    }
    public void setRecord(Record record) {
        this.record = record;
    }
}
