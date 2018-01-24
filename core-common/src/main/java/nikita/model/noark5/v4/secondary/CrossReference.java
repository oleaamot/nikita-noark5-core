package nikita.model.noark5.v4.secondary;

import nikita.model.noark5.v4.Class;
import nikita.model.noark5.v4.File;
import nikita.model.noark5.v4.NoarkEntity;
import nikita.model.noark5.v4.Record;
import nikita.model.noark5.v4.interfaces.entities.ICrossReferenceEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.persistence.*;

import static nikita.config.N5ResourceMappings.CROSS_REFERENCE;

@Entity
@Table(name = "cross_reference")
// Enable soft delete of CrossReference
// @SQLDelete(sql="UPDATE cross_reference SET deleted = true WHERE pk_cross_reference_id = ? and version = ?")
// @Where(clause="deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_cross_reference_id"))
public class CrossReference extends NoarkEntity implements ICrossReferenceEntity {

    private static final long serialVersionUID = 1L;

    /** M219 - referanseTilKlasse (xs:string)
     * points to systemId of the referenced Class
     **/
    @Column(name="class_system_id")
    private String referenceToClass;

    /** M210 - referanseTilMappe (xs:string)
     * points to systemId of the referenced File
     **/
    @Column(name="file_system_id")
    private String referenceToFile;

    /** M212 - referanseTilRegistrering (xs:string)
     * points to systemId of the referenced Record
     **/
    @Column(name="record_system_id")
    private String referenceToRecord;

    // Link to Class
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cross_reference_class_id", referencedColumnName = "pk_class_id")
    private Class referenceClass;

    // Link to File
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cross_reference_file_id", referencedColumnName = "pk_file_id")
    private File referenceFile;

    // Link to BasicRecord
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cross_reference_basic_record_id", referencedColumnName = "pk_record_id")
    private Record referenceBasicRecord;

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
        return "CrossReference{" + super.toString() +
                ", referenceToClass='" + referenceToClass + '\'' +
                ", referenceToFile='" + referenceToFile + '\'' +
                ", referenceToRecord='" + referenceToRecord + '\'' +
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
        CrossReference rhs = (CrossReference) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(referenceToClass, rhs.referenceToClass)
                .append(referenceToFile, rhs.referenceToFile)
                .append(referenceToRecord, rhs.referenceToRecord)
                .isEquals();
    }
}
