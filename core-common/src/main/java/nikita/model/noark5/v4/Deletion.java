package nikita.model.noark5.v4;

import nikita.model.noark5.v4.interfaces.entities.IDeletionEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import static nikita.config.N5ResourceMappings.DELETION;

@Entity
@Table(name = "deletion")
// Enable soft delete of Deletion
@SQLDelete(sql="UPDATE deletion SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_deletion_id"))
public class Deletion extends  NoarkEntity implements IDeletionEntity{

    private static final long serialVersionUID = 1L;

    /** M089 - slettingstype (xs:string) */
    @Column(name = "deletion_type")
    @Audited
    private String deletionType;

    /** M614 - slettetAv (xs:string) */
    @Column(name = "deletion_by")
    @Audited
    private String deletionBy;

    /** M613 slettetDato (xs:dateTime) */
    @Column(name = "deletion_date")
    @Audited
    private Date deletionDate;

    // Links to Series
    @OneToMany(mappedBy = "referenceDeletion")
    private Set<Series> referenceSeries = new TreeSet<>();

    // Links to DocumentDescription
    @OneToMany(mappedBy = "referenceDeletion")
    private Set<DocumentDescription> referenceDocumentDescription = new TreeSet<>();

    public String getDeletionType() {
        return deletionType;
    }

    public void setDeletionType(String deletionType) {
        this.deletionType = deletionType;
    }

    public String getDeletionBy() {
        return deletionBy;
    }

    public void setDeletionBy(String deletionBy) {
        this.deletionBy = deletionBy;
    }

    public Date getDeletionDate() {
        return deletionDate;
    }

    public void setDeletionDate(Date deletionDate) {
        this.deletionDate = deletionDate;
    }

    @Override
    public String getBaseTypeName() {
        return DELETION;
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
        return "Deletion{" + super.toString() + 
                "deletionDate=" + deletionDate +
                ", deletionBy='" + deletionBy + '\'' +
                ", deletionType='" + deletionType + '\'' +
                '}';
    }
}
