package nikita.model.noark5.v4.metadata;

import nikita.model.noark5.v4.casehandling.secondary.CorrespondencePart;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

import static nikita.config.N5ResourceMappings.CORRESPONDENCE_PART_TYPE;

// Noark 5v4 korrespondanseparttype
@Entity
@Table(name = "correspondence_part_type")
@AttributeOverride(name = "id", column = @Column(name = "pk_correspondence_part_type_id"))
@Audited
public class CorrespondencePartType extends UniqueCodeMetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return CORRESPONDENCE_PART_TYPE;
    }

    // Link to CorrespondencePart
    // TODO :Revisit this. You don't need to be able to pick up the reverse of this
    // relationship. It will be massive!
    @OneToMany(mappedBy = "referenceCorrespondencePartType")
    private List<CorrespondencePart> referenceCorrespondencePart;

    public List<CorrespondencePart> getReferenceCorrespondencePart() {
        return referenceCorrespondencePart;
    }

    public void setReferenceCorrespondencePart(List<CorrespondencePart> referenceCorrespondencePart) {
        this.referenceCorrespondencePart = referenceCorrespondencePart;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
