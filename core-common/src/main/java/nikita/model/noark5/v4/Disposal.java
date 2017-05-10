package nikita.model.noark5.v4;

import nikita.model.noark5.v4.interfaces.entities.IDisposalEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

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
public class Disposal extends  NoarkEntity implements IDisposalEntity {

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
    private Set<Series> referenceSeries = new TreeSet<>();

    // Links to Class
    @OneToMany(mappedBy = "referenceDisposal")
    private Set<Class> referenceClass = new TreeSet<>();

    // Links to File
    @OneToMany(mappedBy = "referenceDisposal")
    private Set<File> referenceFile= new TreeSet<>();

    // Links to Record
    @OneToMany(mappedBy = "referenceDisposal")
    private Set<Record> referenceRecord = new TreeSet<>();

    // Links to DocumentDescription
    @OneToMany(mappedBy = "referenceDisposal")
    private Set<DocumentDescription> referenceDocumentDescription = new TreeSet<>();

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

    public Set<Series> getReferenceSeries() {
        return referenceSeries;
    }

    public void setReferenceSeries(Set<Series> referenceSeries) {
        this.referenceSeries = referenceSeries;
    }

    public Set<Class> getReferenceClass() {
        return referenceClass;
    }

    public void setReferenceClass(Set<Class> referenceClass) {
        this.referenceClass = referenceClass;
    }

    public Set<File> getReferenceFile() {
        return referenceFile;
    }

    public void setReferenceFile(Set<File> referenceFile) {
        this.referenceFile = referenceFile;
    }

    public Set<Record> getReferenceRecord() {
        return referenceRecord;
    }

    public void setReferenceRecord(Set<Record> referenceRecord) {
        this.referenceRecord = referenceRecord;
    }

    public Set<DocumentDescription> getReferenceDocumentDescription() {
        return referenceDocumentDescription;
    }

    public void setReferenceDocumentDescription(Set<DocumentDescription> referenceDocumentDescription) {
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
}
