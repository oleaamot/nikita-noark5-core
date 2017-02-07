package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.DocumentObject;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.DocumentObjectHateoasSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tsodring on 12/9/16.
 *
 * Using composition rather than inheritance. Although this class is really only a placeholder for the DocumentObject object
 * along with the hateoas links. It's not intended that you will manipulate the DocumentObject object from here.
 *
 */
@JsonSerialize(using = DocumentObjectHateoasSerializer.class)
public class DocumentObjectHateoas implements IHateoasNoarkObject {

    protected List<Link> links = new ArrayList<>();
    DocumentObject documentObject;
    private List<DocumentObject> documentObjectList;

    public DocumentObjectHateoas(DocumentObject documentObject){
        this.documentObject = documentObject;
    }

    public DocumentObjectHateoas(List<DocumentObject> documentObjectList) {
        this.documentObjectList = documentObjectList;
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

    public DocumentObject getDocumentObject() {
        return documentObject;
    }
    public void setDocumentObject(DocumentObject documentObject) {
        this.documentObject = documentObject;
    }

    public List<DocumentObject> getDocumentObjectList() {
        return documentObjectList;
    }

    public void setDocumentObjectList(List<DocumentObject> documentObjectList) {
        this.documentObjectList = documentObjectList;
    }

    public INoarkSystemIdEntity getSystemIdEntity() {
        return documentObject;
    }

    @SuppressWarnings("unchecked")
    public List<INoarkSystemIdEntity> getSystemIdEntityList() {
        return (ArrayList<INoarkSystemIdEntity>) (ArrayList) documentObjectList;
    }
}
