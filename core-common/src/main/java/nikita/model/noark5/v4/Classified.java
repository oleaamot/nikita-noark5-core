package nikita.model.noark5.v4;

import nikita.model.noark5.v4.interfaces.entities.IClassifiedEntity;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static nikita.config.N5ResourceMappings.CLASSIFIED;

/**
 * Created by tsodring on 4/10/16.
 */

@Entity
@Table(name = "classified")
// Enable soft delete of Classified
@SQLDelete(sql="UPDATE classified SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class Classified implements INikitaEntity, INoarkSystemIdEntity, IClassifiedEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_classified_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * systemID (xs:string). Not part of Noark standard. Added so access via systemId is consistent
     */
    @Column(name = "system_id", unique=true)
    @Audited
    protected String systemId;

    /** M506 - gradering (xs:string) **/
    @Column(name="classification")
    @Audited
    protected String classification;

    /** M624 - graderingsdato (xs:dateTime) **/
    @Column(name="classification_date")
    @Audited
    protected Date classificationDate;

    /** M629 - gradertAv (xs:string) */
    @Column(name = "classification_by")
    @Audited
    protected String classificationBy;

    /** M626 - nedgraderingsdato (xs:dateTime) **/
    @Column(name="classification_downgraded_date")
    @Audited
    protected Date classificationDowngradedDate;

    /** M627 - nedgradertAv (xs:string) **/
    @Column(name = "classification_downgraded_by")
    @Audited
    protected String classificationDowngradedBy;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    @Version
    @Column(name = "version")
    protected Long version;

    // Links to Series
    @OneToMany(mappedBy = "referenceClassified")
    protected Set<Series> referenceSeries = new HashSet<Series>();

    // Links to Klass
    @OneToMany(mappedBy = "referenceClassified")
    protected Set<Class> referenceClass = new HashSet<Class>();

    // Links to File
    @OneToMany(mappedBy = "referenceClassified")
    protected Set<File> referenceFile = new HashSet<File>();

    // Links to Record
    @OneToMany(mappedBy = "referenceClassified")
    protected Set<Record> referenceRecord = new HashSet<Record>();

    // Links to DocumentDescription
    @OneToMany(mappedBy = "referenceClassified")
    protected Set<DocumentDescription> referenceDocumentDescription = new HashSet<DocumentDescription>();

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getSystemId() {
        return systemId;
    }

    @Override
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Date getClassificationDate() {
        return classificationDate;
    }

    public void setClassificationDate(Date classificationDate) {
        this.classificationDate = classificationDate;
    }

    public String getClassificationBy() {
        return classificationBy;
    }

    public void setClassificationBy(String classificationBy) {
        this.classificationBy = classificationBy;
    }

    public Date getClassificationDowngradedDate() {
        return classificationDowngradedDate;
    }

    public void setClassificationDowngradedDate(Date classificationDowngradedDate) {
        this.classificationDowngradedDate = classificationDowngradedDate;
    }

    public String getClassificationDowngradedBy() {
        return classificationDowngradedBy;
    }

    public void setClassificationDowngradedBy(String classificationDowngradedBy) {
        this.classificationDowngradedBy = classificationDowngradedBy;
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

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String getBaseTypeName() {
        return CLASSIFIED;
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
        return "Classified{" +
                "id=" + id +
                ", classification='" + classification + '\'' +
                ", classificationDate=" + classificationDate +
                ", classificationBy='" + classificationBy + '\'' +
                ", classificationDowngradedDate=" + classificationDowngradedDate +
                ", classificationDowngradedBy='" + classificationDowngradedBy + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}