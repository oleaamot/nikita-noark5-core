package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.DocumentObject;
import nikita.util.serializers.noark5v4.hateoas.DocumentObjectHateoasSerializer;

import java.util.List;

/**
 * Created by tsodring on 12/9/16.
 *
 * Using composition rather than inheritance. Although this class is really only a placeholder for the DocumentObject object
 * along with the hateoas links. It's not intended that you will manipulate the DocumentObject object from here.
 *
 */
@JsonSerialize(using = DocumentObjectHateoasSerializer.class)
public class DocumentObjectHateoas implements IHateoasLinks {

    protected List<Link> links;
    DocumentObject documentObject;
    private Iterable<DocumentObject> documentObjectIterable;

    public DocumentObjectHateoas(DocumentObject documentObject){
        this.documentObject = documentObject;
    }

    public DocumentObjectHateoas(Iterable<DocumentObject> documentObjectIterable) {
        this.documentObjectIterable = documentObjectIterable;
    }

    public List<Link> getLinks() {
        return links;
    }
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public DocumentObject getDocumentObject() {
        return documentObject;
    }
    public void setDocumentObject(DocumentObject documentObject) {
        this.documentObject = documentObject;
    }

    public Iterable<DocumentObject> getDocumentObjectIterable() {
        return documentObjectIterable;
    }

    public void setDocumentObjectIterable(Iterable<DocumentObject> documentObjectIterable) {
        this.documentObjectIterable = documentObjectIterable;
    }
}
