package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.Record;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.RecordHateoasSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tsodring on 12/9/16.
 *
 * Using composition rather than inheritance. Although this class is really only a placeholder for the Record object
 * along with the hateoas links. It's not intended that you will manipulate the Record object from here.
 *
 */
@JsonSerialize(using = RecordHateoasSerializer.class)
public class RecordHateoas implements IHateoasNoarkObject {

    protected List<Link> links = new ArrayList<>();
    Record record;
    private List<Record> recordList;

    public RecordHateoas(Record record){
        this.record = record;
    }

    public RecordHateoas(List<Record> recordList) {
        this.recordList = recordList;
    }

    public List<Link> getLinks() {
        return links;
    }
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public void addLink(Link link) {
        this.links.add(link);
    }

    public Record getRecord() {
        return record;
    }
    public void setRecord(Record record) {
        this.record = record;
    }

    public List<Record> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }

    public INoarkSystemIdEntity getSystemIdEntity() {
        return record;
    }

    @SuppressWarnings("unchecked")
    public List<INoarkSystemIdEntity> getSystemIdEntityList() {
        return (ArrayList<INoarkSystemIdEntity>) (ArrayList) recordList;
    }
}
