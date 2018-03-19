package nikita.common.model.noark5.v4.casehandling;

import nikita.common.config.Constants;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.NoarkGeneralEntity;
import nikita.common.model.noark5.v4.interfaces.entities.IPrecedenceEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "precedence")
// Enable soft delete of Precedence
// @SQLDelete(sql="UPDATE precedence SET deleted = true WHERE pk_precedence_id = ? and version = ?")
// @Where(clause="deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_precedence_id"))
public class Precedence extends NoarkGeneralEntity implements IPrecedenceEntity {

    /**
     * M111 - presedensDato (xs:date)
     */
    @Column(name = "precedence_date")
    @Audited
    private Date precedenceDate;

    /**
     * M311 - presedensHjemmel (xs:string)
     */
    @Column(name = "precedence_authority")
    @Audited
    private String precedenceAuthority;

    /**
     * M312 - rettskildefaktor (xs:string)
     */
    @Column(name = "source_of_law")
    @Audited
    private String sourceOfLaw;

    /**
     * M628 - presedensGodkjentDato (xs:date)
     */
    @Column(name = "precedence_approved_date")
    @Audited
    private Date precedenceApprovedDate;

    /**
     * M629 - presedensGodkjentAv (xs:string)
     */
    @Column(name = "precedence_approved_by")
    @Audited
    private String precedenceApprovedBy;

    /**
     * M056 - presedensStatus (xs:string)
     */
    @Column(name = "precedence_status")
    @Audited
    private String precedenceStatus;

    // Link to RegistryEntry
    @ManyToMany(mappedBy = "referencePrecedence")
    private List<RegistryEntry> referenceRegistryEntry = new ArrayList<>();

    // Links to CaseFiles
    @ManyToMany(mappedBy = "referencePrecedence")
    private List<CaseFile> referenceCaseFile = new ArrayList<>();


    public Date getPrecedenceDate() {
        return precedenceDate;
    }

    public void setPrecedenceDate(Date precedenceDate) {
        this.precedenceDate = precedenceDate;
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

    public String getPrecedenceStatus() {
        return precedenceStatus;
    }

    public void setPrecedenceStatus(String precedenceStatus) {
        this.precedenceStatus = precedenceStatus;
    }

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.PRECEDENCE;
    }

    @Override
    public String getFunctionalTypeName() {
        return Constants.NOARK_CASE_HANDLING_PATH;
    }

    public List<RegistryEntry> getReferenceRegistryEntry() {
        return referenceRegistryEntry;
    }

    public void setReferenceRegistryEntry(List<RegistryEntry> referenceRegistryEntry) {
        this.referenceRegistryEntry = referenceRegistryEntry;
    }

    public List<CaseFile> getReferenceCaseFile() {
        return referenceCaseFile;
    }

    public void setReferenceCaseFile(List<CaseFile> referenceCaseFile) {
        this.referenceCaseFile = referenceCaseFile;
    }

    @Override
    public String toString() {
        return "Precedence{" + super.toString() +
                "precedenceStatus='" + precedenceStatus + '\'' +
                ", precedenceApprovedBy='" + precedenceApprovedBy + '\'' +
                ", precedenceApprovedDate=" + precedenceApprovedDate +
                ", sourceOfLaw='" + sourceOfLaw + '\'' +
                ", precedenceAuthority='" + precedenceAuthority + '\'' +
                ", precedenceDate='" + precedenceDate + '\'' +
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
        Precedence rhs = (Precedence) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(precedenceStatus, rhs.precedenceStatus)
                .append(precedenceApprovedBy, rhs.precedenceApprovedBy)
                .append(precedenceApprovedDate, rhs.precedenceApprovedDate)
                .append(sourceOfLaw, rhs.sourceOfLaw)
                .append(precedenceAuthority, rhs.precedenceAuthority)
                .append(precedenceDate, rhs.precedenceDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(precedenceStatus)
                .append(precedenceApprovedBy)
                .append(precedenceApprovedDate)
                .append(sourceOfLaw)
                .append(precedenceAuthority)
                .append(precedenceDate)
                .toHashCode();
    }
}
