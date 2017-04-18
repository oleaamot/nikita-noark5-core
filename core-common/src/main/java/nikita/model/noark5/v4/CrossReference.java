package nikita.model.noark5.v4;

import nikita.model.noark5.v4.interfaces.entities.ICrossReferenceEntity;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;

import static nikita.config.N5ResourceMappings.CROSS_REFERENCE;

@Entity
@Table(name = "cross_reference")
// Enable soft delete of CrossReference
@SQLDelete(sql="UPDATE cross_reference SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class CrossReference implements ICrossReferenceEntity, INoarkSystemIdEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_cross_reference_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique = true)
    @Audited
    protected String systemId;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;
    /** M219 - referanseTilKlasse (xs:string)
     * points to systemId of the referenced Class
     **/
    @Column(name="class_system_id")
    protected String referenceToClass;
    /** M210 - referanseTilMappe (xs:string)
     * points to systemId of the referenced File
     **/
    @Column(name="file_system_id")
    protected String referenceToFile;
    /** M212 - referanseTilRegistrering (xs:string)
     * points to systemId of the referenced Record
     **/
    @Column(name="record_system_id")
    protected String referenceToRecord;
    @Version
    @Column(name = "version")
    protected Long version;
    // Link to Class
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cross_reference_class_id", referencedColumnName = "pk_class_id")
    protected Class referenceClass;
    // Link to File
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cross_reference_file_id", referencedColumnName = "pk_file_id")
    protected File referenceFile;
    // Link to BasicRecord
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cross_reference_basic_record_id", referencedColumnName = "pk_record_id")
    protected Record referenceBasicRecord;
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

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
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
        return CROSS_REFERENCE;
    }

    @Override
    public String getReferenceToClass() {
        return referenceToClass;
    }

    public void setReferenceToClass(String referenceToClass) {
        this.referenceToClass = referenceToClass;
    }

    @Override
    public String getReferenceToFile() {
        return referenceToFile;
    }

    public void setReferenceToFile(String referenceToFile) {
        this.referenceToFile = referenceToFile;
    }

    @Override
    public String getReferenceToRecord() {
        return referenceToRecord;
    }

    public void setReferenceToRecord(String referenceToRecord) {
        this.referenceToRecord = referenceToRecord;
    }

    public Class getReferenceClass() {
        return referenceClass;
    }

    public void setReferenceClass(Class referenceClass) {
        this.referenceClass = referenceClass;
    }

    public File getReferenceFile() {
        return referenceFile;
    }

    public void setReferenceFile(File referenceFile) {
        this.referenceFile = referenceFile;
    }

    public Record getReferenceBasicRecord() {
        return referenceBasicRecord;
    }

    public void setReferenceBasicRecord(Record referenceBasicRecord) {
        this.referenceBasicRecord = referenceBasicRecord;
    }

    @Override
    public String toString() {
        return "CrossReference{" +
                "id=" + id +
                ", deleted=" + deleted +
                ", ownedBy='" + ownedBy + '\'' +
                ", referenceToClass='" + referenceToClass + '\'' +
                ", referenceToFile='" + referenceToFile + '\'' +
                ", referenceToRecord='" + referenceToRecord + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}