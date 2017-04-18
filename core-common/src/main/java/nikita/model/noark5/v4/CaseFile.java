package nikita.model.noark5.v4;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.model.noark5.v4.interfaces.ICaseParty;
import nikita.model.noark5.v4.interfaces.IPrecedence;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.util.deserialisers.CaseFileDeserializer;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static nikita.config.N5ResourceMappings.CASE_FILE;


// TODO: You are missing M209 referanseSekundaerKlassifikasjon


@Entity
@Table(name = "case_file")
// Enable soft delete of CaseFile
@SQLDelete(sql="UPDATE case_file SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
@Indexed(index = "case_file")
@JsonDeserialize(using = CaseFileDeserializer.class)
public class CaseFile extends File implements Serializable, INikitaEntity, IPrecedence, ICaseParty {

    private static final long serialVersionUID = 1L;

    /**
     * M011 - saksaar (xs:integer)
     */
    @Column(name = "case_year")
    @Audited
    @Field
    protected Integer caseYear;

    /**
     * M012 - sakssekvensnummer (xs:integer)
     */
    @Column(name = "case_sequence_number")
    @Audited
    @Field
    protected Integer caseSequenceNumber;

    /**
     * M100 - saksdato (xs:date)
     */
    @Column(name = "case_date")
    @Temporal(TemporalType.DATE)
    @Audited
    @Field
    protected Date caseDate;

    /**
     * M305 - administrativEnhet (xs:string)
     */
    @Column(name = "administrative_unit")
    @Audited
    @Field
    protected String administrativeUnit;

    /**
     * M306 - saksansvarlig (xs:string)
     */
    @Column(name = "case_responsible")
    @Audited
    @Field
    protected String caseResponsible;

    /**
     * M308 - journalenhet (xs:string)
     */
    @Column(name = "records_management_unit")
    @Audited
    @Field
    protected String recordsManagementUnit;

    /**
     * M052 - saksstatus (xs:string)
     */
    @Column(name = "case_status")
    @Audited
    @Field
    protected String caseStatus;

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
    protected String loanedTo;
    @Column(name = "owned_by")
    @Audited
    @Field
    protected String ownedBy;
    // Links to CaseParty
    @ManyToMany
    @JoinTable(name = "case_file_case_file_party",
            joinColumns = @JoinColumn(name = "f_pk_case_file_id",
                    referencedColumnName = "pk_file_id"),
            inverseJoinColumns = @JoinColumn(name = "f_pk_case_party_id",
                    referencedColumnName = "pk_case_party_id"))

    protected Set<CaseParty> referenceCaseParty = new HashSet<CaseParty>();
    // Links to Precedence
    @ManyToMany
    @JoinTable(name = "case_file_precedence",
            joinColumns = @JoinColumn(name = "f_pk_case_file_id",
                    referencedColumnName = "pk_file_id"),
            inverseJoinColumns = @JoinColumn(name = "f_pk_precedence_id",
                    referencedColumnName = "pk_precedence_id"))

    protected Set<Precedence> referencePrecedence = new HashSet<Precedence>();
    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    @Field
    private Boolean deleted;

    public Integer getCaseYear() {
        return caseYear;
    }

    public void setCaseYear(Integer caseYear) {
        this.caseYear = caseYear;
    }

    public Integer getCaseSequenceNumber() {
        return caseSequenceNumber;
    }

    public void setCaseSequenceNumber(Integer caseSequenceNumber) {
        this.caseSequenceNumber = caseSequenceNumber;
    }

    public Date getCaseDate() {
        return caseDate;
    }

    public void setCaseDate(Date caseDate) {
        this.caseDate = caseDate;
    }

    public String getAdministrativeUnit() {
        return administrativeUnit;
    }

    public void setAdministrativeUnit(String administrativeUnit) {
        this.administrativeUnit = administrativeUnit;
    }

    public String getCaseResponsible() {
        return caseResponsible;
    }

    public void setCaseResponsible(String caseResponsible) {
        this.caseResponsible = caseResponsible;
    }

    public String getRecordsManagementUnit() {
        return recordsManagementUnit;
    }

    public void setRecordsManagementUnit(String recordsManagementUnit) {
        this.recordsManagementUnit = recordsManagementUnit;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
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

    @Override
    public String getBaseTypeName() {
        return CASE_FILE;
    }

    public Set<CaseParty> getReferenceCaseParty() {
        return referenceCaseParty;
    }

    public void setReferenceCaseParty(Set<CaseParty> referenceCaseParty) {
        this.referenceCaseParty = referenceCaseParty;
    }

    public Set<Precedence> getReferencePrecedence() {
        return referencePrecedence;
    }

    public void setReferencePrecedence(Set<Precedence> referencePrecedence) {
        this.referencePrecedence = referencePrecedence;
    }

    @Override
    public String toString() {
        return super.toString() + " CaseFile{" +
                "loanedTo='" + loanedTo + '\'' +
                ", loanedDate=" + loanedDate +
                ", caseStatus='" + caseStatus + '\'' +
                ", recordsManagementUnit='" + recordsManagementUnit + '\'' +
                ", caseResponsible='" + caseResponsible + '\'' +
                ", administrativeUnit='" + administrativeUnit + '\'' +
                ", caseDate=" + caseDate +
                ", caseSequenceNumber=" + caseSequenceNumber +
                ", caseYear=" + caseYear +
                '}';
    }
}
