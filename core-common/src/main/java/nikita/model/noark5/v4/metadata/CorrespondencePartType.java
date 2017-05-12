package nikita.model.noark5.v4.metadata;

import nikita.model.noark5.v4.secondary.CorrespondencePart;

import javax.persistence.*;
import java.util.Set;

import static nikita.config.N5ResourceMappings.CORRESPONDENCE_PART_TYPE;

// Noark 5v4 korrespondanseparttype
@Entity
@Table(name = "correspondence_part_type")
// Enable soft delete
// @SQLDelete(sql = "UPDATE correspondence_part_type SET deleted = true WHERE pk_correspondence_part_type_id = ? and version = ?")
// @Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_correspondence_part_type_id"))
public class CorrespondencePartType extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return CORRESPONDENCE_PART_TYPE;
    }

    // Link to CorrespondencePart
    // TODO :Revisit this. You don't need to be able to pick up the reverse of this
    // relationship. It will be massive!
    @OneToMany(mappedBy = "referenceCorrespondencePartType")
    private Set<CorrespondencePart> referenceCorrespondencePart;

    public Set<CorrespondencePart> getReferenceCorrespondencePart() {
        return referenceCorrespondencePart;
    }

    public void setReferenceCorrespondencePart(Set<CorrespondencePart> referenceCorrespondencePart) {
        this.referenceCorrespondencePart = referenceCorrespondencePart;
    }
}
