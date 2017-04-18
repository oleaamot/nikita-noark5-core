package nikita.model.noark5.v4.metadata;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.ELECTRONIC_SIGNATURE_VERIFIED;

// Noark 5v4
@Entity
@Table(name = "electronic_signature_verified")
// Enable soft delete
@SQLDelete(sql = "UPDATE electronic_signature_verified SET delete" +
        "d = true WHERE id = ?")
@Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_electronic_signature_verified_id"))
public class ElectronicSignatureVerified extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return ELECTRONIC_SIGNATURE_VERIFIED;
    }
}
