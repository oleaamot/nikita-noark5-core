package nikita.model.noark5.v4;

import nikita.model.noark5.v4.interfaces.entities.IDisposalUndertakenEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import static nikita.config.N5ResourceMappings.DISPOSAL_UNDERTAKEN;

@Entity
@Table(name = "disposal_undertaken")
// Enable soft delete of DisposalUndertaken
// @SQLDelete(sql="UPDATE disposal_undertaken SET deleted = true WHERE pk_disposal_undertaken_id = ? and version = ?")
// @Where(clause="deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_disposal_undertaken_id"))
public class DisposalUndertaken extends NoarkEntity implements IDisposalUndertakenEntity {

    private static final long serialVersionUID = 1L;

    /** M631 - kassertAv (xs:string) */
    @Column(name = "disposal_by")
    @Audited
    private String disposalBy;

    /** M630 - kassertDato (xs:dateTime) */
    @Column(name = "disposal_date")
    @Audited
    private Date disposalDate;

    // Links to Series
    @ManyToMany(mappedBy = "referenceDisposalUndertaken")
    private Set<Series> referenceSeries = new TreeSet<>();

    // Links to DocumentDescription
    @ManyToMany(mappedBy = "referenceDisposalUndertaken")
    private Set<DocumentDescription> referenceDocumentDescription = new TreeSet<>();

    public String getDisposalBy() {
        return disposalBy;
    }

    public void setDisposalBy(String disposalBy) {
        this.disposalBy = disposalBy;
    }

    public Date getDisposalDate() {
        return disposalDate;
    }

    public void setDisposalDate(Date disposalDate) {
        this.disposalDate = disposalDate;
    }

    @Override
    public String getBaseTypeName() {
        return DISPOSAL_UNDERTAKEN;
    }

    public Set<Series> getReferenceSeries() {
        return referenceSeries;
    }

    public void setReferenceSeries(Set<Series> referenceSeries) {
        this.referenceSeries = referenceSeries;
    }

    public Set<DocumentDescription> getReferenceDocumentDescription() {
        return referenceDocumentDescription;
    }

    public void setReferenceDocumentDescription(Set<DocumentDescription> referenceDocumentDescription) {
        this.referenceDocumentDescription = referenceDocumentDescription;
    }

    @Override
    public String toString() {
        return "DisposalUndertaken{" + super.toString() + 
                ", disposalBy='" + disposalBy + '\'' +
                ", disposalDate=" + disposalDate +
                '}';
    }
}
