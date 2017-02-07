package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.File;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.FileHateoasSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tsodring on 12/9/16.
 *
 * Using composition rather than inheritance. Although this class is really only a placeholder for the File object
 * along with the hateoas links. It's not intended that you will manipulate the file object from here.
 *
 */
@JsonSerialize(using = FileHateoasSerializer.class)
public class FileHateoas implements IHateoasNoarkObject {

    protected List<Link> links = new ArrayList<>();
    File file;
    private List<File> fileList;

    public FileHateoas(File file){
        this.file = file;
    }

    public FileHateoas(List<File> fileList) {
        this.fileList = fileList;
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

    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public INoarkSystemIdEntity getSystemIdEntity() {
        return file;
    }

    @SuppressWarnings("unchecked")
    public List<INoarkSystemIdEntity> getSystemIdEntityList() {
        return (ArrayList<INoarkSystemIdEntity>) (ArrayList) fileList;
    }
}
