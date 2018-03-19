package nikita.common.model.noark5.v4.metadata;

import nikita.common.config.N5ResourceMappings;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// Noark 5v4 Avskrivningsmaate
@Entity
@Table(name = "sign_off_method")
// Enable soft delete
// @SQLDelete(sql = "UPDATE sign_off_method SET deleted = true WHERE pk_sign_off_method_id = ? and version = ?")
// @Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_sign_off_method_id"))
public class SignOffMethod extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.SIGN_OFF_METHOD;
    }
}
