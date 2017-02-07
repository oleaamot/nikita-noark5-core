package nikita.model.noark5.v4.hateoas;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.model.noark5.v4.Class;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.serializers.noark5v4.hateoas.ClassHateoasSerializer;

import java.util.ArrayList;
import java.util.List;

@JsonSerialize(using = ClassHateoasSerializer.class)
public class ClassHateoas implements IHateoasNoarkObject {

    protected List<Link> links = new ArrayList<>();
    protected Class klass;
    private List<Class> classList;

    public ClassHateoas(Class klass) {
        this.klass = klass;
    }

    public ClassHateoas(List<Class> classList) {
        this.classList = classList;
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

    public Class getKlass() {
        return klass;
    }
    public void setKlass(Class klass) {
        this.klass = klass;
    }

    public List<Class> getclassList() {
        return classList;
    }

    public void setclassList(List<Class> classList) {
        this.classList = classList;
    }

    public INoarkSystemIdEntity getSystemIdEntity() {
        return klass;
    }

    @SuppressWarnings("unchecked")
    public List<INoarkSystemIdEntity> getSystemIdEntityList() {
        return (ArrayList<INoarkSystemIdEntity>) (ArrayList) classList;
    }
}
