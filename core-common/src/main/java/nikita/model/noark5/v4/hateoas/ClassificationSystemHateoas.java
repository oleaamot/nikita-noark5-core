package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.ClassificationSystem;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.ClassificationSystemHateoasSerializer;

import java.util.ArrayList;
import java.util.List;

@JsonSerialize(using = ClassificationSystemHateoasSerializer.class)
public class ClassificationSystemHateoas implements IHateoasNoarkObject {

    protected List<Link> links = new ArrayList<>();
    protected ClassificationSystem classificationSystem;
    private List<ClassificationSystem> classificationSystemList;

    public ClassificationSystemHateoas(ClassificationSystem classificationSystem) {this.classificationSystem = classificationSystem;}

    public ClassificationSystemHateoas(List<ClassificationSystem> classificationSystemList) {
        this.classificationSystemList = classificationSystemList;
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

    public ClassificationSystem getClassificationSystem() {
        return classificationSystem;
    }
    public void setClassificationSystem(ClassificationSystem classificationSystem) {
        this.classificationSystem = classificationSystem;
    }

    public List<ClassificationSystem> getClassificationSystemList() {
        return classificationSystemList;
    }

    public void setClassificationSystemList(List<ClassificationSystem> classificationSystemList) {
        this.classificationSystemList = classificationSystemList;
    }

    public INoarkSystemIdEntity getSystemIdEntity() {
        return classificationSystem;
    }

    @SuppressWarnings("unchecked")
    public List<INoarkSystemIdEntity> getSystemIdEntityList() {
        return (ArrayList<INoarkSystemIdEntity>) (ArrayList) classificationSystemList;
    }
}
