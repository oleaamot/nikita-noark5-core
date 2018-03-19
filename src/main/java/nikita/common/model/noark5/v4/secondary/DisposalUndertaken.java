package nikita.common.model.noark5.v4.secondary;

import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.DocumentDescription;
import nikita.common.model.noark5.v4.NoarkEntity;
import nikita.common.model.noark5.v4.Series;
import nikita.common.model.noark5.v4.interfaces.entities.IDisposalUndertakenEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "disposal_undertaken")
// Enable soft delete of DisposalUndertaken
// @SQLDelete(sql="UPDATE disposal_undertaken SET deleted = true WHERE pk_disposal_undertaken_id = ? and version = ?")
// @Where(clause="deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_disposal_undertaken_id"))
public class DisposalUndertaken extends NoarkEntity implements IDisposalUndertakenEntity {

    private static final long serialVersionUID = 1L;

    /**
     * M631 - kassertAv (xs:string)
     */
    @Column(name = "disposal_by")
    @Audited
    private String disposalBy;

    /**
     * M630 - kassertDato (xs:dateTime)
     */
    @Column(name = "disposal_date")
    @Audited
    private Date disposalDate;

    // Links to Series
    @ManyToMany(mappedBy = "referenceDisposalUndertaken")
    private List<Series> referenceSeries = new ArrayList<>();

    // Links to DocumentDescription
    @ManyToMany(mappedBy = "referenceDisposalUndertaken")
    private List<DocumentDescription> referenceDocumentDescription = new ArrayList<>();

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
        return N5ResourceMappings.DISPOSAL_UNDERTAKEN;
    }

    public List<Series> getReferenceSeries() {
        return referenceSeries;
    }

    public void setReferenceSeries(List<Series> referenceSeries) {
        this.referenceSeries = referenceSeries;
    }

    public List<DocumentDescription> getReferenceDocumentDescription() {
        return referenceDocumentDescription;
    }

    public void setReferenceDocumentDescription(List<DocumentDescription> referenceDocumentDescription) {
        this.referenceDocumentDescription = referenceDocumentDescription;
    }

    @Override
    public String toString() {
        return "DisposalUndertaken{" + super.toString() +
                ", disposalBy='" + disposalBy + '\'' +
                ", disposalDate=" + disposalDate +
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
        DisposalUndertaken rhs = (DisposalUndertaken) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(disposalBy, rhs.disposalBy)
                .append(disposalDate, rhs.disposalDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(disposalBy)
                .append(disposalDate)
                .toHashCode();
    }
}
