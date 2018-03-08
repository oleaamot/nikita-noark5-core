package nikita.model.noark5.v4.casehandling;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.model.noark5.v4.BasicRecord;
import nikita.model.noark5.v4.casehandling.secondary.CorrespondencePartInternal;
import nikita.model.noark5.v4.casehandling.secondary.CorrespondencePartPerson;
import nikita.model.noark5.v4.casehandling.secondary.CorrespondencePartUnit;
import nikita.model.noark5.v4.interfaces.*;
import nikita.model.noark5.v4.secondary.ElectronicSignature;
import nikita.model.noark5.v4.secondary.SignOff;
import nikita.util.deserialisers.casehandling.RegistryEntryDeserializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static nikita.config.Constants.NOARK_CASE_HANDLING_PATH;
import static nikita.config.N5ResourceMappings.REGISTRY_ENTRY;

@Entity
@Table(name = "registry_entry")
@Inheritance(strategy = InheritanceType.JOINED)
// Enable soft delete of RegistryEntry
// @SQLDelete(sql="UPDATE registry_entry SET deleted = true WHERE pk_record_id = ? and version = ?")
// @Where(clause="deleted <> true")
//@Indexed(index = "registry_entry")
@JsonDeserialize(using = RegistryEntryDeserializer.class)
public class RegistryEntry extends BasicRecord implements IElectronicSignature,
        IPrecedence, ICorrespondencePart, ISignOff, IDocumentFlow {

    /**
     * M013 - journalaar (xs:integer)
     */
    @Column(name = "record_year")
    @Audited
    @Field
    private Integer recordYear;

    /**
     * M014 - journalsekvensnummer (xs:integer)
     */
    @Column(name = "record_sequence_number")
    @Audited
    @Field
    private Integer recordSequenceNumber;

    /**
     * M015 - journalpostnummer (xs:integer)
     */
    @Column(name = "registry_entry_number")
    @Audited
    @Field
    private Integer registryEntryNumber;

    /**
     * M082 - journalposttype (xs:string)
     */
    @NotNull
    @Column(name = "registry_entry_type", nullable = false)
    @Audited
    @Field
    private String registryEntryType;

    /**
     * M053 - journalstatus (xs:string, nullable = false)
     */
    @NotNull
    @Column(name = "record_status")
    @Audited
    @Field
    private String recordStatus;

    /**
     * M101 - journaldato (xs:date)
     */
    @NotNull
    @Column(name = "record_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @Audited
    @Field
    private Date recordDate;

    /**
     * M103 - dokumentetsDato (xs:date)
     */
    @Column(name = "document_date")
    @Temporal(TemporalType.DATE)
    @Audited
    @Field
    private Date documentDate;

    /**
     * M104 - mottattDato (xs:dateTime)
     */
    @Column(name = "received_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Audited
    @Field
    private Date receivedDate;

    /**
     * M105 - sendtDato (xs:dateTime)
     */
    @Column(name = "sent_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Audited
    @Field
    private Date sentDate;

    /**
     * M109 - forfallsdato (xs:date)
     */
    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    @Audited
    @Field
    private Date dueDate;

    /**
     * M110 - offentlighetsvurdertDato (xs:date)
     */
    @Column(name = "freedom_assessment_date")
    @Temporal(TemporalType.DATE)
    @Audited
    @Field
    private Date freedomAssessmentDate;

    /**
     * M304 - antallVedlegg (xs:integer)
     */
    @Column(name = "number_of_attachments")
    @Audited
    @Field
    private Integer numberOfAttachments;

    /**
     * M106 - utlaantDato (xs:date)
     */
    @Column(name = "loaned_date")
    @Temporal(TemporalType.DATE)
    @Audited
    private Date loanedDate;

    /**
     * M309 - utlaantTil (xs:string)
     */
    @Column(name = "loaned_to")
    @Audited
    private String loanedTo;

    /**
     * M308 - journalenhet (xs:string)
     */
    @Column(name = "records_management_unit")
    @Audited
    @Field
    private String recordsManagementUnit;

    // Links to CorrespondencePartPerson
    @ManyToMany
    @JoinTable(name = "registry_entry_correspondence_part_person",
            joinColumns = @JoinColumn(name = "f_pk_record_id",
                    referencedColumnName = "pk_record_id"),
            inverseJoinColumns = @JoinColumn(name = "f_pk_correspondence_part_person_id",
                    referencedColumnName = "pk_correspondence_part_id"))
    private List<CorrespondencePartPerson> referenceCorrespondencePartPerson = new ArrayList<>();


    // Links to CorrespondencePartUnit
    @ManyToMany
    @JoinTable(name = "registry_entry_correspondence_part_unit",
            joinColumns = @JoinColumn(name = "f_pk_record_id",
                    referencedColumnName = "pk_record_id"),
            inverseJoinColumns = @JoinColumn(name = "f_pk_correspondence_part_unit_id",
                    referencedColumnName = "pk_correspondence_part_id"))
    private List<CorrespondencePartUnit> referenceCorrespondencePartUnit = new ArrayList<>();


    // Links to CorrespondencePartInternal
    @ManyToMany
    @JoinTable(name = "registry_entry_correspondence_part_internal",
            joinColumns = @JoinColumn(name = "f_pk_record_id",
                    referencedColumnName = "pk_record_id"),
            inverseJoinColumns = @JoinColumn(name = "f_pk_correspondence_part_internal_id",
                    referencedColumnName = "pk_correspondence_part_id"))
    private List<CorrespondencePartInternal> referenceCorrespondencePartInternal = new ArrayList<>();


    // Links to DocumentFlow
    @OneToMany(mappedBy = "referenceRegistryEntry")
    private List<DocumentFlow> referenceDocumentFlow = new ArrayList<>();
    // Links to SignOff
    @ManyToMany
    @JoinTable(name = "registry_entry_sign_off",
            joinColumns = @JoinColumn(name = "f_pk_record_id",
                    referencedColumnName = "pk_record_id"),
            inverseJoinColumns = @JoinColumn(name = "f_pk_sign_off_id",
                    referencedColumnName = "pk_sign_off_id"))

    private List<SignOff> referenceSignOff = new ArrayList<>();

    // Links to Precedence
    @ManyToMany
    @JoinTable(name = "registry_entry_precedence",
            joinColumns = @JoinColumn(name = "f_pk_record_id",
                    referencedColumnName = "pk_record_id"),
            inverseJoinColumns = @JoinColumn(name = "f_pk_precedence_id",
                    referencedColumnName = "pk_precedence_id"))
    private List<Precedence> referencePrecedence = new ArrayList<>();

    // Link to ElectronicSignature
    @OneToOne
    @JoinColumn(name="pk_electronic_signature_id")
    private ElectronicSignature referenceElectronicSignature;

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

    public String getLoanedTo() {
        return loanedTo;
    }

    public void setLoanedTo(String loanedTo) {
        this.loanedTo = loanedTo;
    }

    public String getRecordsManagementUnit() {
        return recordsManagementUnit;
    }

    public void setRecordsManagementUnit(String recordsManagementUnit) {
        this.recordsManagementUnit = recordsManagementUnit;
    }

    @Override
    public String getBaseTypeName() {
        return REGISTRY_ENTRY;
    }

    @Override
    public String getFunctionalTypeName() {
        return NOARK_CASE_HANDLING_PATH;
    }

    @Override
    public List<DocumentFlow> getReferenceDocumentFlow() {
        return referenceDocumentFlow;
    }

    @Override
    public void setReferenceDocumentFlow(List<DocumentFlow> referenceDocumentFlow) {
        this.referenceDocumentFlow = referenceDocumentFlow;
    }

    public List<CorrespondencePartPerson> getReferenceCorrespondencePartPerson() {
        return referenceCorrespondencePartPerson;
    }

    public void setReferenceCorrespondencePartPerson(List<CorrespondencePartPerson> referenceCorrespondencePartPerson) {
        this.referenceCorrespondencePartPerson = referenceCorrespondencePartPerson;
    }

    public List<CorrespondencePartUnit> getReferenceCorrespondencePartUnit() {
        return referenceCorrespondencePartUnit;
    }

    public void setReferenceCorrespondencePartUnit(List<CorrespondencePartUnit> referenceCorrespondencePartUnit) {
        this.referenceCorrespondencePartUnit = referenceCorrespondencePartUnit;
    }

    public List<CorrespondencePartInternal> getReferenceCorrespondencePartInternal() {
        return referenceCorrespondencePartInternal;
    }

    public void setReferenceCorrespondencePartInternal(List<CorrespondencePartInternal> referenceCorrespondencePartInternal) {
        this.referenceCorrespondencePartInternal = referenceCorrespondencePartInternal;
    }

    public List<SignOff> getReferenceSignOff() {
        return referenceSignOff;
    }

    public void setReferenceSignOff(List<SignOff> referenceSignOff) {
        this.referenceSignOff = referenceSignOff;
    }

    public List<Precedence> getReferencePrecedence() {
        return referencePrecedence;
    }

    public void setReferencePrecedence(List<Precedence> referencePrecedence) {
        this.referencePrecedence = referencePrecedence;
    }

    public ElectronicSignature getReferenceElectronicSignature() {
        return referenceElectronicSignature;
    }

    public void setReferenceElectronicSignature(ElectronicSignature referenceElectronicSignature) {
        this.referenceElectronicSignature = referenceElectronicSignature;
    }

    @Override
    public String toString() {
        return super.toString() + " RegistryEntry{" + super.toString() +
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

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != getClass()) {
            return false;
        }
        RegistryEntry rhs = (RegistryEntry) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(recordsManagementUnit, rhs.recordsManagementUnit)
                .append(loanedTo, rhs.loanedTo)
                .append(loanedDate, rhs.loanedDate)
                .append(numberOfAttachments, rhs.numberOfAttachments)
                .append(freedomAssessmentDate, rhs.freedomAssessmentDate)
                .append(dueDate, rhs.dueDate)
                .append(sentDate, rhs.sentDate)
                .append(receivedDate, rhs.receivedDate)
                .append(documentDate, rhs.documentDate)
                .append(recordDate, rhs.recordDate)
                .append(registryEntryType, rhs.registryEntryType)
                .append(registryEntryNumber, rhs.registryEntryNumber)
                .append(recordSequenceNumber, rhs.recordSequenceNumber)
                .append(recordYear, rhs.recordYear)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(recordsManagementUnit)
                .append(loanedTo)
                .append(loanedDate)
                .append(numberOfAttachments)
                .append(freedomAssessmentDate)
                .append(dueDate)
                .append(sentDate)
                .append(receivedDate)
                .append(documentDate)
                .append(recordDate)
                .append(registryEntryType)
                .append(registryEntryNumber)
                .append(recordSequenceNumber)
                .append(recordYear)
                .toHashCode();
    }
}
