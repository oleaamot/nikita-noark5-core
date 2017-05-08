package nikita.model.noark5.v4.secondary;

import nikita.model.noark5.v4.CaseFile;
import nikita.model.noark5.v4.NoarkGeneralEntity;
import nikita.model.noark5.v4.RegistryEntry;
import nikita.model.noark5.v4.interfaces.entities.IPrecedenceEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import static nikita.config.N5ResourceMappings.PRECEDENCE;

@Entity
@Table(name = "precedence")
// Enable soft delete of Precedence
@SQLDelete(sql="UPDATE precedence SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_precedence_id"))
public class Precedence extends NoarkGeneralEntity implements IPrecedenceEntity{

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
    private Set<RegistryEntry> referenceRegistryEntry = new TreeSet< >();

    // Links to CaseFiles
    @ManyToMany(mappedBy = "referencePrecedence")
    private Set<CaseFile> referenceCaseFile = new TreeSet<>();


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
        return "Precedence{" + super.toString() + 
                "precedenceStatus='" + precedenceStatus + '\'' +
                ", precedenceApprovedBy='" + precedenceApprovedBy + '\'' +
                ", precedenceApprovedDate=" + precedenceApprovedDate +
                ", sourceOfLaw='" + sourceOfLaw + '\'' +
                ", precedenceAuthority='" + precedenceAuthority + '\'' +
                ", precedenceDate='" + precedenceDate + '\'' +
                '}';
    }
}
