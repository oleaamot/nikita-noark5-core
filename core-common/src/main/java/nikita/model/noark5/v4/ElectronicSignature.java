package nikita.model.noark5.v4;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "electronic_signature")
// Enable soft delete of ElectronicSignature
@SQLDelete(sql="UPDATE electronic_signature SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class ElectronicSignature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_electronic_signature_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M507 - elektroniskSignaturSikkerhetsnivaa (xs:string)
     */
    @Column(name = "electronic_signature_security_level")
    @Audited
    protected String electronicSignatureSecurityLevel;

    /**
     * M508 - elektroniskSignaturVerifisert (xs:string)
     */
    @Column(name = "electronic_sSignature_verified")
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

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    @OneToOne
    @JoinColumn(name="pk_record_id")
    protected Record referenceBasicRecord;

    @OneToOne
    @JoinColumn(name="pk_document_object_id")
    protected DocumentObject referenceDocumentObject;

    @OneToOne
    @JoinColumn(name="pk_document_description_id")
    protected DocumentDescription referenceDocumentDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Record getReferenceBasicRecord() {
        return referenceBasicRecord;
    }

    public void setReferenceBasicRecord(Record referenceBasicRecord) {
        this.referenceBasicRecord = referenceBasicRecord;
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
                ", electronicSignatureSecurityLevel='" + electronicSignatureSecurityLevel + '\'' +
                ", electronicSignatureVerified='" + electronicSignatureVerified + '\'' +
                '}';
    }
}