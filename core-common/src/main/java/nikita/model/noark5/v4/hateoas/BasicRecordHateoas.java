package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.BasicRecord;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.BasicRecordHateoasSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tsodring on 12/9/16.
 *
 * Using composition rather than inheritance. Although this class is really only a placeholder for the BasicRecord object
 * along with the hateoas links. It's not intended that you will manipulate the BasicRecord object from here.
 *
 */
@JsonSerialize(using = BasicRecordHateoasSerializer.class)
public class BasicRecordHateoas implements IHateoasNoarkObject {

    protected List<Link> links = new ArrayList<>();
    BasicRecord basicRecord;
    private List<BasicRecord> basicRecordList;

    public BasicRecordHateoas(BasicRecord basicRecord){
        this.basicRecord = basicRecord;
    }

    public BasicRecordHateoas(List<BasicRecord> basicRecordList) {
        this.basicRecordList = basicRecordList;
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

    public BasicRecord getBasicRecord() {
        return basicRecord;
    }
    public void setBasicRecord(BasicRecord basicRecord) {
        this.basicRecord = basicRecord;
    }

    public List<BasicRecord> getBasicRecordList() {
        return basicRecordList;
    }

    public void setBasicRecordList(List<BasicRecord> basicRecordList) {
        this.basicRecordList = basicRecordList;
    }

    public INoarkSystemIdEntity getSystemIdEntity() {
        return basicRecord;
    }

    @SuppressWarnings("unchecked")
    public List<INoarkSystemIdEntity> getSystemIdEntityList() {
        return (ArrayList<INoarkSystemIdEntity>) (ArrayList) basicRecordList;
    }
}
