package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.Class;
import nikita.util.serializers.noark5v4.hateoas.ClassHateoasSerializer;

import java.util.List;

@JsonSerialize(using = ClassHateoasSerializer.class)
public class ClassHateoas implements IHateoasLinks {

    protected List<Link> links;
    protected Class klass;

    public ClassHateoas(Class klass)
        {this.klass = klass;}

    public List<Link> getLinks() {
        return links;
    }
    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Class getKlass() {
        return klass;
    }
    public void setKlass(Class klass) {
        this.klass = klass;
    }
}
