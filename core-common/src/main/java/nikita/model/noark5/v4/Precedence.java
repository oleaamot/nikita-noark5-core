package nikita.model.noark5.v4;

import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.model.noark5.v4.interfaces.entities.IPrecedenceEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static nikita.config.N5ResourceMappings.PRECEDENCE;

@Entity
@Table(name = "precedence")
// Enable soft delete of Precedence
@SQLDelete(sql="UPDATE precedence SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class Precedence implements IPrecedenceEntity, INoarkSystemIdEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_precedence_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique = true)
    @Audited
    protected String systemId;

    /**
     * M111 - presedensDato (xs:date)
     */
    @Column(name = "precedence_date")
    @Audited
    protected Date precedenceDate;

    /**
     * M600 - opprettetDato (xs:dateTime)
     */
    @Column(name = "created_date")
    @Audited
    protected Date createdDate;

    /**
     * M602 - opprettetAv (xs:string)
     */
    @Column(name = "created_by")
    @Audited
    protected String createdBy;

    /**
     * M020 - tittel (xs:string)
     */
    @Column(name = "title")
    @Audited
    protected String title;

    /**
     * M021 - beskrivelse (xs:string)
     */
    @Column(name = "description")
    @Audited
    protected String description;

    /**
     * M311 - presedensHjemmel (xs:string)
     */
    @Column(name = "precedence_authority")
    @Audited
    protected String precedenceAuthority;

    /**
     * M312 - rettskildefaktor (xs:string)
     */
    @Column(name = "source_of_law")
    @Audited
    protected String sourceOfLaw;

    /**
     * M628 - presedensGodkjentDato (xs:date)
     */
    @Column(name = "precedence_approved_date")
    @Audited
    protected Date precedenceApprovedDate;

    /**
     * M629 - presedensGodkjentAv (xs:string)
     */
    @Column(name = "precedence_approved_by")
    @Audited
    protected String precedenceApprovedBy;

    /**
     * M602 avsluttetDato (xs:dateTime)
     */
    @Column(name = "finalised_date")
    @Audited
    protected Date finalisedDate;

    /**
     * M603 - avsluttetAv (xs:string)
     */
    @Column(name = "finalised_by")
    @Audited
    protected String finalisedBy;

    /**
     * M056 - presedensStatus (xs:string)
     */
    @Column(name = "precedence_status")
    @Audited
    protected String precedenceStatus;
    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;
    @Version
    @Column(name = "version")
    protected Long version;
    // Link to RegistryEntry
    @ManyToMany(mappedBy = "referencePrecedence")
    protected Set<RegistryEntry> referenceRegistryEntry = new HashSet<RegistryEntry >();
    // Links to CaseFiles
    @ManyToMany(mappedBy = "referencePrecedence")
    protected Set<CaseFile> referenceCaseFile = new HashSet<CaseFile>();
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

    public Date getPrecedenceDate() {
        return precedenceDate;
    }

    public void setPrecedenceDate(Date precedenceDate) {
        this.precedenceDate = precedenceDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrecedenceAuthority() {
        return precedenceAuthority;
    }

    public void setPrecedenceAuthority(String precedenceAuthority) {
        this.precedenceAuthority = precedenceAuthority;
    }

    public String getSourceOfLaw() {
        return sourceOfLaw;
    }

    public void setSourceOfLaw(String sourceOfLaw) {
        this.sourceOfLaw = sourceOfLaw;
    }

    public Date getPrecedenceApprovedDate() {
        return precedenceApprovedDate;
    }

    public void setPrecedenceApprovedDate(Date precedenceApprovedDate) {
        this.precedenceApprovedDate = precedenceApprovedDate;
    }

    public String getPrecedenceApprovedBy() {
        return precedenceApprovedBy;
    }

    public void setPrecedenceApprovedBy(String precedenceApprovedBy) {
        this.precedenceApprovedBy = precedenceApprovedBy;
    }

    public Date getFinalisedDate() {
        return finalisedDate;
    }

    public void setFinalisedDate(Date finalisedDate) {
        this.finalisedDate = finalisedDate;
    }

    public String getFinalisedBy() {
        return finalisedBy;
    }

    public void setFinalisedBy(String finalisedBy) {
        this.finalisedBy = finalisedBy;
    }

    public String getPrecedenceStatus() {
        return precedenceStatus;
    }

    public void setPrecedenceStatus(String precedenceStatus) {
        this.precedenceStatus = precedenceStatus;
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
        return PRECEDENCE;
    }

    public Set<RegistryEntry> getReferenceRegistryEntry() {
        return referenceRegistryEntry;
    }

    public void setReferenceRegistryEntry(Set<RegistryEntry> referenceRegistryEntry) {
        this.referenceRegistryEntry = referenceRegistryEntry;
    }

    public Set<CaseFile> getReferenceCaseFile() {
        return referenceCaseFile;
    }

    public void setReferenceCaseFile(Set<CaseFile> referenceCaseFile) {
        this.referenceCaseFile = referenceCaseFile;
    }

    @Override
    public String toString() {
        return "Precedence{" +
                "precedenceStatus='" + precedenceStatus + '\'' +
                ", finalisedBy='" + finalisedBy + '\'' +
                ", finalisedDate=" + finalisedDate +
                ", precedenceApprovedBy='" + precedenceApprovedBy + '\'' +
                ", precedenceApprovedDate=" + precedenceApprovedDate +
                ", sourceOfLaw='" + sourceOfLaw + '\'' +
                ", precedenceAuthority='" + precedenceAuthority + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", precedenceDate='" + precedenceDate + '\'' +
                ", version='" + version + '\'' +
                ", id=" + id +
                '}';
    }
}