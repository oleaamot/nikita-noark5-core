package nikita.model.noark5.v4;

import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static nikita.config.N5ResourceMappings.SIGN_OFF;


@Entity
@Table(name = "sign_off")
// Enable soft delete of SignOff
@SQLDelete(sql="UPDATE sign_off SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class SignOff implements Serializable, INoarkSystemIdEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_sign_off_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique = true)
    @Audited
    protected String systemId;

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
    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;
    @Version
    @Column(name = "version")
    protected Long version;
    /** M215 referanseAvskrivesAvJournalpost */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_record_id")
    protected RegistryEntry referenceSignedOffRecord;
    /** M??? - referanseAvskrivesAvKorrespondansepart
     * Note this is new to v4, I think. Missing Metatdata number */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pk_correspondence_part_id")
    protected CorrespondencePart referenceSignedOffCorrespondencePart;
    // Links to RegistryEnty
    @ManyToMany(mappedBy = "referenceSignOff")
    protected Set<RegistryEntry> referenceRecord = new HashSet<RegistryEntry>();
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String getBaseTypeName() {
        return SIGN_OFF;
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
                ", version='" + version + '\'' +
                ", id=" + id +
                '}';
    }
}