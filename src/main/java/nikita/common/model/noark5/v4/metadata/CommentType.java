package nikita.common.model.noark5.v4.metadata;

import nikita.common.config.N5ResourceMappings;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// Noark 5v4 Merknadstype
@Entity
@Table(name = "comment_type")
// Enable soft delete
// @SQLDelete(sql = "UPDATE comment_type SET deleted = true WHERE pk_comment_type_id = ? and version = ?")
// @Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_comment_type_id"))
public class CommentType extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.COMMENT_TYPE;
    }
}
