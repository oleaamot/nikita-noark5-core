package nikita.model.noark5.v4;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.*;

@Entity
@Table(name = "cross_reference")
// Enable soft delete of CrossReference
@SQLDelete(sql="UPDATE cross_reference SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class CrossReference implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_cross_reference_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    /** M219 - referanseTilKlasse (xs:string) **/
    @OneToOne
    @JoinColumn(name="class_system_id")
    protected Class referenceToClass;

    /** M210 - referanseTilMappe (xs:string) **/
    @OneToOne
    @JoinColumn(name="file_system_id")
    protected File referenceToFile;

    /** M212 - referanseTilRegistrering (xs:string) **/
    @OneToOne
    @JoinColumn(name="record_system_id")
    protected Record referenceToRecord;

    // Link to Class
    @ManyToOne
    @JoinColumn(name = "cross_reference_class_id", referencedColumnName = "pk_class_id")
    protected Class referenceClass;

    // Link to File
    @ManyToOne
    @JoinColumn(name = "cross_reference_file_id", referencedColumnName = "pk_file_id")
    protected File referenceFile;

    // Link to Record
    @ManyToOne
    @JoinColumn(name = "cross_reference_record_id", referencedColumnName = "pk_record_id")
    protected Record referenceRecord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Class getReferenceToClass() {
        return referenceToClass;
    }

    public void setReferenceToClass(Class referenceToClass) {
        this.referenceToClass = referenceToClass;
    }

    public File getReferenceToFile() {
        return referenceToFile;
    }

    public void setReferenceToFile(File referenceToFile) {
        this.referenceToFile = referenceToFile;
    }

    public Record getReferenceToRecord() {
        return referenceToRecord;
    }

    public void setReferenceToRecord(Record referenceToRecord) {
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

    public Record getReferenceRecord() {
        return referenceRecord;
    }

    public void setReferenceRecord(Record referenceRecord) {
        this.referenceRecord = referenceRecord;
    }
}