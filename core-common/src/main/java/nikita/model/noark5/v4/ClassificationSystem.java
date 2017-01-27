package nikita.model.noark5.v4;

import nikita.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.lang.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "classification_system")
// Enable soft delete of ClassificationSystem
@SQLDelete(sql="UPDATE classification_system SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class ClassificationSystem implements INoarkGeneralEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_classification_system_id", nullable = false, insertable = true, updatable = false)
    protected long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id")
    @Audited
    protected String systemId;

    /**
     * M086 - klassifikasjonstype (xs:string)
     */
    @Column(name = "classification_type")
    @Audited
    protected String classificationType;

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

    // Links to Series
    @OneToMany(mappedBy = "referenceClassificationSystem")
    protected Set<Series> referenceSeries = new HashSet<Series>();

    // Links to child Classes
    @OneToMany(mappedBy = "referenceClassificationSystem")
    protected Set<Class> referenceClass = new HashSet<Class>();

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

    public String getClassificationType() {
        return classificationType;
    }

    public void setClassificationType(String classificationType) {
        this.classificationType = classificationType;
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

    @Override
    public String toString() {
        return "ClassificationSystem [id=" + id + ", systemId=" + systemId
                + ", classificationType=" + classificationType + ", title="
                + title + ", description=" + description + ", createdDate="
                + createdDate + ", createdBy=" + createdBy + ", finalisedDate="
                + finalisedDate + ", finalisedBy=" + finalisedBy
                + ", referenceSeries=" + referenceSeries + ", referenceClass="
                + referenceClass + "]";
    }

}
