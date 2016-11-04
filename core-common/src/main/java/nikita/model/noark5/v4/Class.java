package nikita.model.noark5.v4;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "class")
// Enable soft delete of Class
@SQLDelete(sql="UPDATE class SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Class implements Serializable {

    private static final long serialVersionUID = 1L;

    public Class() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_class_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id")
    @Audited
    protected String systemId;

    /**
     * M002 - klasseID (xs:string)
     */
    @Column(name = "class_id")
    @Audited
    protected String classId;

    /**
     * M020 - tittel (xs:string)
     */
    @Column(name = "title")
    @Audited
    protected String title;

    /**
     * M021 - beskrivelse (xs:string)
     */
    @Column(name = "description")
    @Audited
    protected String description;

    /**
     * M600 - opprettetDato (xs:dateTime)
     */
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Audited
    protected Date createdDate;

    /**
     * M601 - opprettetAv (xs:string)
     */
    @Column(name = "created_by")
    @Audited
    protected String createdBy;

    /**
     * M602 - avsluttetDato (xs:dateTime)
     */
    @Column(name = "finalised_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Audited
    protected Date finalisedDate;

    /**
     * M603 - avsluttetAv (xs:string)
     */
    @Column(name = "finalised_by")
    @Audited
    protected String finalisedBy;

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    // Links to Keywords
    @ManyToMany
    @JoinTable(name = "class_keyword", joinColumns = @JoinColumn(name = "f_pk_class_id", referencedColumnName = "pk_class_id"), inverseJoinColumns = @JoinColumn(name = "f_pk_keyword_id", referencedColumnName = "pk_keyword_id"))
    protected Set<Keyword> referenceKeyword = new HashSet<Keyword>();

    // Link to ClassificationSystem
    @ManyToOne
    @JoinColumn(name = "class_classification_system_id", referencedColumnName = "pk_classification_system_id")
    protected ClassificationSystem referenceClassificationSystem;

    // Link to parent Class
    @ManyToOne
    protected Class referenceParentClass;

    // Links to child Classes
    @OneToMany(mappedBy = "referenceParentClass")
    protected Set<Class> referenceChildClass = new HashSet<Class>();

    // Links to Files
    @OneToMany(mappedBy = "referenceClass")
    protected Set<File> referenceFile = new HashSet<File>();

    // Links to Records
    @OneToMany(mappedBy = "referenceClass")
    protected Set<Record> referenceRecord = new HashSet<Record>();

    // Links to Classified
    @ManyToOne
    @JoinColumn(name = "class_classified_id", referencedColumnName = "pk_classified_id")
    protected Classified referenceClassified;

    // Link to Disposal
    @ManyToOne
    @JoinColumn(name = "class_disposal_id", referencedColumnName = "pk_disposal_id")
    protected Disposal referenceDisposal;

    // Link to Screening
    @ManyToOne
    @JoinColumn(name = "class_screening_id", referencedColumnName = "pk_screening_id")
    protected Screening referenceScreening;

    @OneToOne(mappedBy = "referenceToClass")
    protected CrossReference referenceToCrossReference;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public Date getFinalisedDate() {
        return finalisedDate;
    }

    public void setFinalisedDate(Date finalisedDate) {
        this.finalisedDate = finalisedDate;
    }

    public String getFinalisedBy() {
        return finalisedBy;
    }

    public void setFinalisedBy(String finalisedBy) {
        this.finalisedBy = finalisedBy;
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

    public Set<Keyword> getReferenceKeyword() {
        return referenceKeyword;
    }

    public void setReferenceKeyword(Set<Keyword> referenceKeyword) {
        this.referenceKeyword = referenceKeyword;
    }

    public ClassificationSystem getReferenceClassificationSystem() {
        return referenceClassificationSystem;
    }

    public void setReferenceClassificationSystem(
            ClassificationSystem referenceClassificationSystem) {
        this.referenceClassificationSystem = referenceClassificationSystem;
    }

    public Class getReferenceParentClass() {
        return referenceParentClass;
    }

    public void setReferenceParentClass(Class referenceParentClass) {
        this.referenceParentClass = referenceParentClass;
    }

    public Set<Class> getReferenceChildClass() {
        return referenceChildClass;
    }

    public void setReferenceChildClass(Set<Class> referenceChildClass) {
        this.referenceChildClass = referenceChildClass;
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

    public Classified getReferenceClassified() {
        return referenceClassified;
    }

    public void setReferenceClassified(Classified referenceClassified) {
        this.referenceClassified = referenceClassified;
    }

    public Disposal getReferenceDisposal() {
        return referenceDisposal;
    }

    public void setReferenceDisposal(Disposal referenceDisposal) {
        this.referenceDisposal = referenceDisposal;
    }


    public Screening getReferenceScreening() {
        return referenceScreening;
    }

    public void setReferenceScreening(Screening referenceScreening) {
        this.referenceScreening = referenceScreening;
    }

    public CrossReference getReferenceToCrossReference() {
        return referenceToCrossReference;
    }

    public void setReferenceToCrossReference(CrossReference referenceToCrossReference) {
        this.referenceToCrossReference = referenceToCrossReference;
    }

    @Override
    public String toString() {
        return "Class{" +
                "finalisedBy='" + finalisedBy + '\'' +
                ", finalisedDate=" + finalisedDate +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", classId='" + classId + '\'' +
                ", systemId='" + systemId + '\'' +
                ", id=" + id +
                '}';
    }
}