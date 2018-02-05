package nikita.model.noark5.v4.secondary;

import nikita.model.noark5.v4.Class;
import nikita.model.noark5.v4.*;
import nikita.model.noark5.v4.interfaces.entities.IDisposalEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static nikita.config.N5ResourceMappings.DISPOSAL;

/**
 * Created by tsodring on 4/10/16.
 */
@Entity
@Table(name = "disposal")
// Enable soft delete of Disposal
// @SQLDelete(sql="UPDATE disposal SET deleted = true WHERE pk_disposal_id = ? and version = ?")
// @Where(clause="deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_disposal_id"))
public class Disposal extends NoarkEntity implements IDisposalEntity {

    private static final long serialVersionUID = 1L;

    /** M450 - kassasjonsvedtak (xs:string) */
    @Column(name = "disposal_decision")
    @Audited
    private String disposalDecision;

    /** M453 - kassasjonshjemmel (xs:string) */
    @Column(name = "disposal_authority")
    @Audited
    private String disposalAuthority;

    /** M451 - bevaringstid (xs:integer) */
    @Column(name = "preservation_time")
    @Audited
    private Integer preservationTime;

    /** M452 - kassasjonsdato (xs:date) */
    @Column(name = "disposal_date")
    @Audited
    private Date disposalDate;

    // Links to Series
    @OneToMany(mappedBy = "referenceDisposal")
    private List<Series> referenceSeries = new ArrayList<>();

    // Links to Class
    @OneToMany(mappedBy = "referenceDisposal")
    private List<Class> referenceClass = new ArrayList<>();

    // Links to File
    @OneToMany(mappedBy = "referenceDisposal")
    private List<File> referenceFile = new ArrayList<>();

    // Links to Record
    @OneToMany(mappedBy = "referenceDisposal")
    private List<Record> referenceRecord = new ArrayList<>();

    // Links to DocumentDescription
    @OneToMany(mappedBy = "referenceDisposal")
    private List<DocumentDescription> referenceDocumentDescription = new ArrayList<>();

    public String getDisposalDecision() {
        return disposalDecision;
    }

    public void setDisposalDecision(String disposalDecision) {
        this.disposalDecision = disposalDecision;
    }

    public String getDisposalAuthority() {
        return disposalAuthority;
    }

    public void setDisposalAuthority(String disposalAuthority) {
        this.disposalAuthority = disposalAuthority;
    }

    public Integer getPreservationTime() {
        return preservationTime;
    }

    public void setPreservationTime(Integer preservationTime) {
        this.preservationTime = preservationTime;
    }

    public Date getDisposalDate() {
        return disposalDate;
    }

    public void setDisposalDate(Date disposalDate) {
        this.disposalDate = disposalDate;
    }

    @Override
    public String getBaseTypeName() {
        return DISPOSAL;
    }

    public List<Series> getReferenceSeries() {
        return referenceSeries;
    }

    public void setReferenceSeries(List<Series> referenceSeries) {
        this.referenceSeries = referenceSeries;
    }

    public List<Class> getReferenceClass() {
        return referenceClass;
    }

    public void setReferenceClass(List<Class> referenceClass) {
        this.referenceClass = referenceClass;
    }

    public List<File> getReferenceFile() {
        return referenceFile;
    }

    public void setReferenceFile(List<File> referenceFile) {
        this.referenceFile = referenceFile;
    }

    public List<Record> getReferenceRecord() {
        return referenceRecord;
    }

    public void setReferenceRecord(List<Record> referenceRecord) {
        this.referenceRecord = referenceRecord;
    }

    public List<DocumentDescription> getReferenceDocumentDescription() {
        return referenceDocumentDescription;
    }

    public void setReferenceDocumentDescription(List<DocumentDescription> referenceDocumentDescription) {
        this.referenceDocumentDescription = referenceDocumentDescription;
    }

    @Override
    public String toString() {
        return "Disposal{" + super.toString() +
                "disposalDate=" + disposalDate +
                ", preservationTime=" + preservationTime +
                ", disposalAuthority='" + disposalAuthority + '\'' +
                ", disposalDecision='" + disposalDecision + '\'' +
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
        Disposal rhs = (Disposal) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(disposalDate, rhs.disposalDate)
                .append(preservationTime, rhs.preservationTime)
                .append(disposalAuthority, rhs.disposalAuthority)
                .append(disposalDecision, rhs.disposalDecision)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(disposalDate)
                .append(preservationTime)
                .append(disposalAuthority)
                .append(disposalDecision)
                .toHashCode();
    }
}
