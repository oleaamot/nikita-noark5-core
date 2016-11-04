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
@Table(name = "registry_entry")
@Inheritance(strategy = InheritanceType.JOINED)
// Enable soft delete of RegistryEntry
@SQLDelete(sql="UPDATE registry_entry SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class RegistryEntry extends BasicRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * M013 - journalaar (xs:integer)
     */
    @Column(name = "record_year")
    @Audited
    protected Integer recordYear;

    /**
     * M014 - journalsekvensnummer (xs:integer)
     */
    @Column(name = "record_sequence_number")
    @Audited
    protected Integer recordSequenceNumber;

    /**
     * M015 - journalpostnummer (xs:integer)
     */
    @Column(name = "registry_entry_number")
    @Audited
    protected Integer registryEntryNumber;

    /**
     * M082 - journalposttype (xs:string)
     */
    @Column(name = "registry_entry_type")
    @Audited
    protected String registryEntryType;

    /**
     * M053 - journalstatus (xs:string)
     */
    @Column(name = "record_status")
    @Audited
    protected String recordStatus;

    /**
     * M101 - journaldato (xs:date)
     */
    @Column(name = "record_date")
    @Temporal(TemporalType.DATE)
    @Audited
    protected Date recordDate;

    /**
     * M103 - dokumentetsDato (xs:date)
     */
    @Column(name = "document_date")
    @Temporal(TemporalType.DATE)
    @Audited
    protected Date documentDate;

    /**
     * M104 - mottattDato (xs:dateTime)
     */
    @Column(name = "received_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Audited
    protected Date receivedDate;

    /**
     * M105 - sendtDato (xs:dateTime)
     */
    @Column(name = "sent_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Audited
    protected Date sentDate;

    /**
     * M109 - forfallsdato (xs:date)
     */
    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    @Audited
    protected Date dueDate;

    /**
     * M110 - offentlighetsvurdertDato (xs:date)
     */
    @Column(name = "freedom_assessment_date")
    @Temporal(TemporalType.DATE)
    @Audited
    protected Date freedomAssessmentDate;

    /**
     * M304 - antallVedlegg (xs:integer)
     */
    @Column(name = "number_of_attachments")
    @Audited
    protected Integer numberOfAttachments;

    /**
     * M106 - utlaantDato (xs:date)
     */
    @Column(name = "loaned_date")
    @Temporal(TemporalType.DATE)
    @Audited
    protected Date loanedDate;

    /**
     * M309 - utlaantTil (xs:string)
     */
    @Column(name = "loaned_to")
    @Audited
    protected Date loanedTo;

    /**
     * M308 - journalenhet (xs:string)
     */
    @Column(name = "records_management_unit")
    @Audited
    protected String recordsManagementUnit;

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    // Links to CorrespondencePart
    @ManyToMany
    @JoinTable(name = "registry_entry_correspondence_part",
            joinColumns = @JoinColumn(name = "f_pk_record_id",
                    referencedColumnName = "pk_record_id"),
            inverseJoinColumns = @JoinColumn(name = "f_pk_correspondence_part_id",
                    referencedColumnName = "pk_correspondence_part_id"))
    protected Set<CorrespondencePart> referenceCorrespondencePart = new HashSet<CorrespondencePart>();

    // Links to Workflow
    @OneToMany(mappedBy = "referenceRegistryEntry")
    protected Set<Workflow> referenceRecord = new HashSet<Workflow>();

    // Links to SignOff
    @ManyToMany
    @JoinTable(name = "registry_entry_sign_off",
            joinColumns = @JoinColumn(name = "f_pk_record_id",
                    referencedColumnName = "pk_record_id"),
            inverseJoinColumns = @JoinColumn(name = "f_pk_sign_off_id",
                    referencedColumnName = "pk_sign_off_id"))

    protected Set<SignOff> referenceSignOff = new HashSet<SignOff>();

    // Links to Precedence
    @ManyToMany
    @JoinTable(name = "registry_entry_precedence",
            joinColumns = @JoinColumn(name = "f_pk_record_id",
                    referencedColumnName = "pk_record_id"),
            inverseJoinColumns = @JoinColumn(name = "f_pk_precedence_id",
                    referencedColumnName = "pk_precedence_id"))

    protected Set<Precedence> referencePrecedence = new HashSet<Precedence>();

    public Integer getRecordYear() {
        return recordYear;
    }

    public void setRecordYear(Integer recordYear) {
        this.recordYear = recordYear;
    }

    public Integer getRecordSequenceNumber() {
        return recordSequenceNumber;
    }

    public void setRecordSequenceNumber(Integer recordSequenceNumber) {
        this.recordSequenceNumber = recordSequenceNumber;
    }

    public Integer getRegistryEntryNumber() {
        return registryEntryNumber;
    }

    public void setRegistryEntryNumber(Integer registryEntryNumber) {
        this.registryEntryNumber = registryEntryNumber;
    }

    public String getRegistryEntryType() {
        return registryEntryType;
    }

    public void setRegistryEntryType(String registryEntryType) {
        this.registryEntryType = registryEntryType;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public Date getDocumentDate() {
        return documentDate;
    }

    public void setDocumentDate(Date documentDate) {
        this.documentDate = documentDate;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getFreedomAssessmentDate() {
        return freedomAssessmentDate;
    }

    public void setFreedomAssessmentDate(Date freedomAssessmentDate) {
        this.freedomAssessmentDate = freedomAssessmentDate;
    }

    public Integer getNumberOfAttachments() {
        return numberOfAttachments;
    }

    public void setNumberOfAttachments(Integer numberOfAttachments) {
        this.numberOfAttachments = numberOfAttachments;
    }

    public Date getLoanedDate() {
        return loanedDate;
    }

    public void setLoanedDate(Date loanedDate) {
        this.loanedDate = loanedDate;
    }

    public Date getLoanedTo() {
        return loanedTo;
    }

    public void setLoanedTo(Date loanedTo) {
        this.loanedTo = loanedTo;
    }

    public String getRecordsManagementUnit() {
        return recordsManagementUnit;
    }

    public void setRecordsManagementUnit(String recordsManagementUnit) {
        this.recordsManagementUnit = recordsManagementUnit;
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

    public Set<Workflow> getReferenceRecord() {
        return referenceRecord;
    }

    public void setReferenceRecord(Set<Workflow> referenceRecord) {
        this.referenceRecord = referenceRecord;
    }

    public Set<CorrespondencePart> getReferenceCorrespondencePart() {
        return referenceCorrespondencePart;
    }

    public void setReferenceCorrespondencePart(Set<CorrespondencePart> referenceCorrespondencePart) {
        this.referenceCorrespondencePart = referenceCorrespondencePart;
    }

    public Set<SignOff> getReferenceSignOff() {
        return referenceSignOff;
    }

    public void setReferenceSignOff(Set<SignOff> referenceSignOff) {
        this.referenceSignOff = referenceSignOff;
    }

    public Set<Precedence> getReferencePrecedence() {
        return referencePrecedence;
    }

    public void setReferencePrecedence(Set<Precedence> referencePrecedence) {
        this.referencePrecedence = referencePrecedence;
    }

    @Override
    public String toString() {
        return super.toString() + " RegistryEntry{" +
                "recordsManagementUnit='" + recordsManagementUnit + '\'' +
                ", loanedTo=" + loanedTo +
                ", loanedDate=" + loanedDate +
                ", numberOfAttachments=" + numberOfAttachments +
                ", freedomAssessmentDate=" + freedomAssessmentDate +
                ", dueDate=" + dueDate +
                ", sentDate=" + sentDate +
                ", receivedDate=" + receivedDate +
                ", documentDate=" + documentDate +
                ", recordDate=" + recordDate +
                ", recordStatus='" + recordStatus + '\'' +
                ", registryEntryType='" + registryEntryType + '\'' +
                ", registryEntryNumber=" + registryEntryNumber +
                ", recordSequenceNumber=" + recordSequenceNumber +
                ", recordYear=" + recordYear +
                '}';
    }
}