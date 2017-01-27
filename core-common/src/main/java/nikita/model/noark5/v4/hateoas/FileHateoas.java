package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.File;
import nikita.util.serializers.noark5v4.hateoas.FileHateoasSerializer;

import java.util.List;

/**
 * Created by tsodring on 12/9/16.
 *
 * Using composition rather than inheritance. Although this class is really only a placeholder for the File object
 * along with the hateoas links. It's not intended that you will manipulate the file object from here.
 *
 */
@JsonSerialize(using = FileHateoasSerializer.class)
public class FileHateoas implements IHateoasLinks {

    protected List<Link> links;
    File file;

    public FileHateoas(File file){
        this.file = file;
    }

    public List<Link> getLinks() {
        return links;
    }
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }
}
