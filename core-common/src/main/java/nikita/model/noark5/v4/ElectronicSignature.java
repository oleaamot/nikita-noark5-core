package nikita.model.noark5.v4;

import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static nikita.config.N5ResourceMappings.ELECTRONIC_SIGNATURE;

@Entity
@Table(name = "electronic_signature")
// Enable soft delete of ElectronicSignature
@SQLDelete(sql="UPDATE electronic_signature SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class ElectronicSignature implements Serializable, INoarkSystemIdEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_electronic_signature_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique = true)
    @Audited
    protected String systemId;

    /**
     * M507 - elektronisksignatursikkerhetsnivaa (xs:string)
     */
    @Column(name = "electronic_signature_security_level")
    @Audited
    protected String electronicSignatureSecurityLevel;

    /**
     * M508 - elektronisksignaturverifisert (xs:string)
     */
    @Column(name = "electronic_signature_verified")
    @Audited
    protected String electronicSignatureVerified;

    /**
     * M622 - verifisertDato (xs:date)
     */
    @Column(name = "verified_date")
    @Audited
    protected Date verifiedDate;

    /**
     * M623 - verifisertAv (xs:string)
     */
    @Column(name = "verified_by")
    @Audited
    protected String verifiedBy;
    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;
    @Version
    @Column(name = "version")
    protected Long version;
    @OneToOne
    @JoinColumn(name="pk_record_id")
    protected RegistryEntry referenceRegistryEntry;
    @OneToOne
    @JoinColumn(name="pk_document_object_id")
    protected DocumentObject referenceDocumentObject;
    @OneToOne
    @JoinColumn(name="pk_document_description_id")
    protected DocumentDescription referenceDocumentDescription;
    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getElectronicSignatureSecurityLevel() {
        return electronicSignatureSecurityLevel;
    }

    public void setElectronicSignatureSecurityLevel(String electronicSignatureSecurityLevel) {
        this.electronicSignatureSecurityLevel = electronicSignatureSecurityLevel;
    }

    public String getElectronicSignatureVerified() {
        return electronicSignatureVerified;
    }

    public void setElectronicSignatureVerified(String electronicSignatureVerified) {
        this.electronicSignatureVerified = electronicSignatureVerified;
    }

    public Date getVerifiedDate() {
        return verifiedDate;
    }

    public void setVerifiedDate(Date verifiedDate) {
        this.verifiedDate = verifiedDate;
    }

    public String getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(String verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String getBaseTypeName() {
        return ELECTRONIC_SIGNATURE;
    }

    public RegistryEntry getReferenceRegistryEntry() {
        return referenceRegistryEntry;
    }

    public void setReferenceRegistryEntry(RegistryEntry referenceRegistryEntry) {
        this.referenceRegistryEntry = referenceRegistryEntry;
    }

    public DocumentObject getReferenceDocumentObject() {
        return referenceDocumentObject;
    }

    public void setReferenceDocumentObject(DocumentObject referenceDocumentObject) {
        this.referenceDocumentObject = referenceDocumentObject;
    }

    public DocumentDescription getReferenceDocumentDescription() {
        return referenceDocumentDescription;
    }

    public void setReferenceDocumentDescription(DocumentDescription referenceDocumentDescription) {
        this.referenceDocumentDescription = referenceDocumentDescription;
    }

    @Override
    public String toString() {
        return "ElectronicSignature{" +
                "id=" + id +
                ", version='" + version + '\'' +
                ", electronicSignatureSecurityLevel='" + electronicSignatureSecurityLevel + '\'' +
                ", electronicSignatureVerified='" + electronicSignatureVerified + '\'' +
                '}';
    }
}