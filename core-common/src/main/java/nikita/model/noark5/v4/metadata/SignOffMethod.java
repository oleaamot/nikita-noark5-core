package nikita.model.noark5.v4.metadata;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.SIGN_OFF_METHOD;

// Noark 5v4 Avskrivningsmaate
@Entity
@Table(name = "sign_off_method")
// Enable soft delete
@SQLDelete(sql = "UPDATE sign_off_method SET delete" +
        "d = true WHERE id = ?")
@Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_sign_off_method_id"))
public class SignOffMethod extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return SIGN_OFF_METHOD;
    }
}
