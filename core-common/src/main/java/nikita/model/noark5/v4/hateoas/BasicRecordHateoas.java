package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.BasicRecord;
import nikita.util.serializers.noark5v4.hateoas.BasicRecordHateoasSerializer;

import java.util.List;

/**
 * Created by tsodring on 12/9/16.
 *
 * Using composition rather than inheritance. Although this class is really only a placeholder for the BasicRecord object
 * along with the hateoas links. It's not intended that you will manipulate the BasicRecord object from here.
 *
 */
@JsonSerialize(using = BasicRecordHateoasSerializer.class)
public class BasicRecordHateoas implements IHateoasLinks {

    protected List<Link> links;
    BasicRecord basicRecord;

    public BasicRecordHateoas(BasicRecord basicRecord){
        this.basicRecord = basicRecord;
    }

    public List<Link> getLinks() {
        return links;
    }
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public BasicRecord getBasicRecord() {
        return basicRecord;
    }
    public void setBasicRecord(BasicRecord basicRecord) {
        this.basicRecord = basicRecord;
    }
}
