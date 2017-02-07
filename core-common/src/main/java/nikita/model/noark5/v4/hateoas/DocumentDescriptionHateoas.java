package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.DocumentDescriptionHateoasSerializer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tsodring on 12/9/16.
 *
 * Using composition rather than inheritance. Although this class is really only a placeholder for the DocumentDescription object
 * along with the hateoas links. It's not intended that you will manipulate the DocumentDescription object from here.
 *
 */
@JsonSerialize(using = DocumentDescriptionHateoasSerializer.class)
public class DocumentDescriptionHateoas implements IHateoasNoarkObject {

    protected List<Link> links = new ArrayList<>();
    private DocumentDescription documentDescription;
    private List<DocumentDescription> documentDescriptionList;

    public DocumentDescriptionHateoas(DocumentDescription documentDescription){
        this.documentDescription = documentDescription;
    }

    public DocumentDescriptionHateoas(List<DocumentDescription> documentDescriptionList) {
        this.documentDescriptionList = documentDescriptionList;
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

    public DocumentDescription getDocumentDescription() {
        return documentDescription;
    }
    public void setDocumentDescription(DocumentDescription documentDescription) {
        this.documentDescription = documentDescription;
    }

    public List<DocumentDescription> getDocumentDescriptionList() {
        return documentDescriptionList;
    }

    public void setDocumentDescriptionList(List<DocumentDescription> documentDescriptionList) {
        this.documentDescriptionList = documentDescriptionList;
    }

    public INoarkSystemIdEntity getSystemIdEntity() {
        return documentDescription;
    }

    @SuppressWarnings("unchecked")
    public List<INoarkSystemIdEntity> getSystemIdEntityList() {
        return (ArrayList<INoarkSystemIdEntity>) (ArrayList) documentDescriptionList;
    }
}
