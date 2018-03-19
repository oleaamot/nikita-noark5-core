package nikita.common.model.noark5.v4.metadata;

import nikita.common.config.N5ResourceMappings;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// Noark 5v4 elektroniskSignaturVerifisert
@Entity
@Table(name = "electronic_signature_verified")
// Enable soft delete
// @SQLDelete(sql = "UPDATE electronic_signature_verified SET deleted = true WHERE pk_electronic_signature_verified_id = ? and version = ?")
// @Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_electronic_signature_verified_id"))
public class ElectronicSignatureVerified extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.ELECTRONIC_SIGNATURE_VERIFIED;
    }
}
