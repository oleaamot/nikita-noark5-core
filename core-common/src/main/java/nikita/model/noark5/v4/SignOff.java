package nikita.model.noark5.v4;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "sign_off")
// Enable soft delete of SignOff
@SQLDelete(sql="UPDATE sign_off SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class SignOff implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_sign_off_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /** M617 - avskrivningsdato */
    @Column(name = "sign_off_date")
    @Audited
    protected Date signOffDate;

    /** M618 - avskrevetAv */
    @Column(name = "sign_off_name")
    @Audited
    protected String signOffBy;

    /** M619 - avskrivningsmaate */
    @Column(name = "sign_off_method")
    @Audited
    protected String signOffMethod;

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    /** M215 referanseAvskrivesAvJournalpost */
    @OneToOne
    @JoinColumn(name="system_id")
    protected RegistryEntry referenceSignedOffRecord;

    /** M??? - referanseAvskrivesAvKorrespondansepart
     * Note this is new to v4, I think. Missing Metatdata number */
    @OneToOne
    @JoinColumn(name="pk_record_id")
    protected CorrespondencePart referenceSignedOffCorrespondencePart;

    // Links to RegistryEnty
    @ManyToMany(mappedBy = "referenceSignOff")
    protected Set<RegistryEntry> referenceRecord = new HashSet<RegistryEntry>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSignOffDate() {
        return signOffDate;
    }

    public void setSignOffDate(Date signOffDate) {
        this.signOffDate = signOffDate;
    }

    public String getSignOffBy() {
        return signOffBy;
    }

    public void setSignOffBy(String signOffBy) {
        this.signOffBy = signOffBy;
    }

    public String getSignOffMethod() {
        return signOffMethod;
    }

    public void setSignOffMethod(String signOffMethod) {
        this.signOffMethod = signOffMethod;
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

    public RegistryEntry getReferenceSignedOffRecord() {
        return referenceSignedOffRecord;
    }

    public void setReferenceSignedOffRecord(RegistryEntry referenceSignedOffRecord) {
        this.referenceSignedOffRecord = referenceSignedOffRecord;
    }

    public CorrespondencePart getReferenceSignedOffCorrespondencePart() {
        return referenceSignedOffCorrespondencePart;
    }

    public void setReferenceSignedOffCorrespondencePart(CorrespondencePart referenceSignedOffCorrespondencePart) {
        this.referenceSignedOffCorrespondencePart = referenceSignedOffCorrespondencePart;
    }

    public Set<RegistryEntry> getReferenceRecord() {
        return referenceRecord;
    }

    public void setReferenceRecord(Set<RegistryEntry> referenceRecord) {
        this.referenceRecord = referenceRecord;
    }

    @Override
    public String toString() {
        return "SignOff{" +
                "signOffMethod='" + signOffMethod + '\'' +
                ", signOffBy='" + signOffBy + '\'' +
                ", signOffDate=" + signOffDate +
                ", id=" + id +
                '}';
    }
}