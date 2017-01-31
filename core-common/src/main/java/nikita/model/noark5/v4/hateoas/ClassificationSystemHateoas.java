package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.ClassificationSystem;
import nikita.util.serializers.noark5v4.hateoas.ClassificationSystemHateoasSerializer;

import java.util.List;

@JsonSerialize(using = ClassificationSystemHateoasSerializer.class)
public class ClassificationSystemHateoas implements IHateoasLinks {

    protected List<Link> links;
    protected ClassificationSystem classificationSystem;
    private Iterable<ClassificationSystem> classificationSystemIterable;

    public ClassificationSystemHateoas(ClassificationSystem classificationSystem) {this.classificationSystem = classificationSystem;}

    public ClassificationSystemHateoas(Iterable<ClassificationSystem> classificationSystemIterable) {
        this.classificationSystemIterable = classificationSystemIterable;
    }

    public List<Link> getLinks() {
        return links;
    }
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public ClassificationSystem getClassificationSystem() {
        return classificationSystem;
    }
    public void setClassificationSystem(ClassificationSystem classificationSystem) {
        this.classificationSystem = classificationSystem;
    }

    public Iterable<ClassificationSystem> getClassificationSystemIterable() {
        return classificationSystemIterable;
    }

    public void setClassificationSystemIterable(Iterable<ClassificationSystem> classificationSystemIterable) {
        this.classificationSystemIterable = classificationSystemIterable;
    }
}
