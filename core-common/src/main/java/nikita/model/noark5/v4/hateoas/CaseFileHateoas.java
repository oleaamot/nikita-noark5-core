package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.CaseFile;
import nikita.util.serializers.noark5v4.hateoas.CaseFileHateoasSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tsodring on 12/9/16.
 *
 * Using composition rather than inheritance. Although this class is really only a placeholder for the File object
 * along with the hateoas links. It's not intended that you will manipulate the file object from here.
 *
 */
@JsonSerialize(using = CaseFileHateoasSerializer.class)
public class CaseFileHateoas implements IHateoasLinks {

    protected List<Link> links = new ArrayList<>();
    CaseFile caseFile;

    public CaseFileHateoas(CaseFile caseFile){
        this.caseFile = caseFile;
    }

    public List<Link> getLinks() {
        return links;
    }
    public void setLinks(List<Link> links) {
        this.links = links;
    }
    public void addLink(Link link) {links.add(link);}

    public CaseFile getCaseFile() {
        return caseFile;
    }
    public void setCaseFile(CaseFile caseFile) {
        this.caseFile = caseFile;
    }
}
