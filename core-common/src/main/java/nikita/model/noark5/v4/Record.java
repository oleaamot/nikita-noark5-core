package nikita.model.noark5.v4;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.model.noark5.v4.interfaces.IClassified;
import nikita.model.noark5.v4.interfaces.IDisposal;
import nikita.model.noark5.v4.interfaces.IScreening;
import nikita.model.noark5.v4.interfaces.entities.INoarkCreateEntity;
import nikita.model.noark5.v4.secondary.Classified;
import nikita.model.noark5.v4.secondary.Disposal;
import nikita.model.noark5.v4.secondary.Screening;
import nikita.util.deserialisers.RecordDeserializer;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import static nikita.config.N5ResourceMappings.REGISTRATION;

@Entity
@Table(name = "record")
@Inheritance(strategy = InheritanceType.JOINED)
// Enable soft delete of Record
// @SQLDelete(sql = "UPDATE record SET deleted = true WHERE pk_record_id = ? and version = ?")
// @Where(clause = "deleted <> true")
//@Indexed(index = "record")
@JsonDeserialize(using = RecordDeserializer.class)
@AttributeOverride(name = "id", column = @Column(name = "pk_record_id"))
public class Record extends NoarkEntity implements INoarkCreateEntity, IClassified, IScreening, IDisposal {

    /**
     * M600 - opprettetDato (xs:dateTime)
     */
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Audited
    @Field
    private Date createdDate;

    /**
     * M601 - opprettetAv (xs:string)
     */
    @Column(name = "created_by")
    @Audited
    @Field
    private String createdBy;

    /**
     * M604 - arkivertDato (xs:dateTime)
     */
    @Column(name = "archived_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Audited
    @Field
    private Date archivedDate;

    /**
     * M605 - arkivertAv (xs:string)
     */
    @Column(name = "archived_by")
    @Audited
    @Field
    private String archivedBy;

    // Link to File
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_file_id", referencedColumnName = "pk_file_id")
    private File referenceFile;

    // Link to Series
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_series_id", referencedColumnName = "pk_series_id")
    private Series referenceSeries;

    // Link to Class
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_class_id", referencedColumnName = "pk_class_id")
    private Class referenceClass;

    // Links to DocumentDescriptions
/*    @OneToMany(mappedBy = "referenceDocumentObject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecordDocumentDescription> referenceRecordDocumentDescription = new ArrayList<>();
*/

    @ManyToMany
    @JoinTable(name = "record_document_description", joinColumns = @JoinColumn(name = "f_pk_record_id",
            referencedColumnName = "pk_record_id"),
            inverseJoinColumns = @JoinColumn(name = "f_pk_document_description_id",
                    referencedColumnName = "pk_document_description_id"))
    private Set<DocumentDescription> referenceDocumentDescription = new TreeSet<>();

    // Links to DocumentObjects
    @OneToMany(mappedBy = "referenceRecord", fetch = FetchType.LAZY)
    private Set<DocumentObject> referenceDocumentObject = new TreeSet<>();

    // Links to Classified
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "record_classified_id", referencedColumnName = "pk_classified_id")
    private Classified referenceClassified;

    // Link to Disposal
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "record_disposal_id", referencedColumnName = "pk_disposal_id")
    private Disposal referenceDisposal;

    // Link to Screening
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "record_screening_id", referencedColumnName = "pk_screening_id")
    private Screening referenceScreening;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getArchivedDate() {
        return archivedDate;
    }

    public void setArchivedDate(Date archivedDate) {
        this.archivedDate = archivedDate;
    }

    public String getArchivedBy() {
        return archivedBy;
    }

    public void setArchivedBy(String archivedBy) {
        this.archivedBy = archivedBy;
    }

    @Override
    public String getBaseTypeName() {
        return REGISTRATION;
    }

    public File getReferenceFile() {
        return referenceFile;
    }

    public void setReferenceFile(File referenceFile) {
        this.referenceFile = referenceFile;
    }

    public Series getReferenceSeries() {
        return referenceSeries;
    }

    public void setReferenceSeries(Series referenceSeries) {
        this.referenceSeries = referenceSeries;
    }

    public Class getReferenceClass() {
        return referenceClass;
    }

    public void setReferenceClass(Class referenceClass) {
        this.referenceClass = referenceClass;
    }

    public Set<DocumentDescription> getReferenceDocumentDescription() {
        return referenceDocumentDescription;
    }

    public void setReferenceDocumentDescription(
            Set<DocumentDescription> referenceDocumentDescription) {
        this.referenceDocumentDescription = referenceDocumentDescription;
    }

    public Set<DocumentObject> getReferenceDocumentObject() {
        return referenceDocumentObject;
    }

    public void setReferenceDocumentObject(
            Set<DocumentObject> referenceDocumentObject) {
        this.referenceDocumentObject = referenceDocumentObject;
    }

    @Override
    public Classified getReferenceClassified() {
        return referenceClassified;
    }

    @Override
    public void setReferenceClassified(Classified referenceClassified) {
        this.referenceClassified = referenceClassified;
    }

    @Override
    public Disposal getReferenceDisposal() {
        return referenceDisposal;
    }

    @Override
    public void setReferenceDisposal(Disposal referenceDisposal) {
        this.referenceDisposal = referenceDisposal;
    }

    @Override
    public Screening getReferenceScreening() {
        return referenceScreening;
    }

    @Override
    public void setReferenceScreening(Screening referenceScreening) {
        this.referenceScreening = referenceScreening;
    }

    /**
     * Identify who is the parent of this object.
     */
    public NoarkEntity chooseParent() {
        if (null != referenceFile) {
            return referenceFile;
        }
        else if (null != referenceClass) {
            return referenceClass;
        }
        else if (null != referenceSeries) {
            return referenceSeries;
        }
        else { // This should be impossible, a record cannot exist without a parent
            throw new NoarkEntityNotFoundException("Could not find parent object for " + this.toString());
        }
    }

    @Override
    public String toString() {
        return "Record{" + super.toString() + 
                "archivedBy='" + archivedBy + '\'' +
                ", archivedDate=" + archivedDate +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
