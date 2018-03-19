package nikita.common.model.noark5.v4.casehandling;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.common.config.Constants;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.File;
import nikita.common.model.noark5.v4.interfaces.ICaseParty;
import nikita.common.model.noark5.v4.interfaces.IPrecedence;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.util.deserialisers.casehandling.CaseFileDeserializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


// TODO: You are missing M209 referanseSekundaerKlassifikasjon


@Entity
@Table(name = "case_file")
// Enable soft delete of CaseFile
// @SQLDelete(sql="UPDATE case_file SET deleted = true WHERE pk_file_id = ? and version = ?")
// @Where(clause="deleted <> true")
//@Indexed(index = "case_file")
@JsonDeserialize(using = CaseFileDeserializer.class)
public class CaseFile extends File implements Serializable, INikitaEntity,
        IPrecedence, ICaseParty {

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
    @NotNull
    @Column(name = "case_date", nullable = false)
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
    @NotNull
    @Column(name = "case_responsible", nullable = false)
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
    @NotNull
    @Column(name = "case_status", nullable = false)
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

    protected List<CaseParty> referenceCaseParty = new ArrayList<CaseParty>();
    // Links to Precedence
    @ManyToMany
    @JoinTable(name = "case_file_precedence",
            joinColumns = @JoinColumn(name = "f_pk_case_file_id",
                    referencedColumnName = "pk_file_id"),
            inverseJoinColumns = @JoinColumn(name = "f_pk_precedence_id",
                    referencedColumnName = "pk_precedence_id"))

    protected List<Precedence> referencePrecedence = new ArrayList<Precedence>();
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
        return N5ResourceMappings.CASE_FILE;
    }

    @Override
    public String getFunctionalTypeName() {
        return Constants.NOARK_CASE_HANDLING_PATH;
    }

    public List<CaseParty> getReferenceCaseParty() {
        return referenceCaseParty;
    }

    public void setReferenceCaseParty(List<CaseParty> referenceCaseParty) {
        this.referenceCaseParty = referenceCaseParty;
    }

    public List<Precedence> getReferencePrecedence() {
        return referencePrecedence;
    }

    public void setReferencePrecedence(List<Precedence> referencePrecedence) {
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
        CaseFile rhs = (CaseFile) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(caseSequenceNumber, rhs.caseSequenceNumber)
                .append(caseYear, rhs.caseYear)
                .append(caseDate, rhs.caseDate)
                .append(caseResponsible, rhs.caseResponsible)
                .append(caseStatus, rhs.caseStatus)
                .append(recordsManagementUnit, rhs.recordsManagementUnit)
                .append(administrativeUnit, rhs.administrativeUnit)
                .append(loanedDate, rhs.loanedDate)
                .append(loanedTo, rhs.loanedTo)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(caseSequenceNumber)
                .append(caseYear)
                .append(caseDate)
                .append(caseResponsible)
                .append(caseStatus)
                .append(recordsManagementUnit)
                .append(administrativeUnit)
                .append(loanedDate)
                .append(loanedTo)
                .toHashCode();
    }
}
