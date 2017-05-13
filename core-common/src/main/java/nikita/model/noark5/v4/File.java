package nikita.model.noark5.v4;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.model.noark5.v4.interfaces.*;
import nikita.model.noark5.v4.secondary.*;
import nikita.util.deserialisers.FileDeserializer;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

import static nikita.config.N5ResourceMappings.FILE;

@Entity
@Table(name = "file")
@Inheritance(strategy = InheritanceType.JOINED)
// Enable soft delete of File
// @SQLDelete(sql="UPDATE file SET deleted = true WHERE pk_file_id = ? and version = ?")
// @Where(clause="deleted <> true")
@Indexed(index = "file")
@JsonDeserialize(using = FileDeserializer.class)
@AttributeOverride(name = "id", column = @Column(name = "pk_file_id"))
public class File extends NoarkGeneralEntity  implements IDocumentMedium, IStorageLocation, IKeyword, IClassified,
        IDisposal, IScreening, IComment, ICrossReference
{
    /**
     * M003 - mappeID (xs:string)
     */
    @Column(name = "file_id")
    @Audited
    @Field
    private String fileId;

    /**
     * M025 - offentligTittel (xs:string)
     */
    @Column(name = "official_title")
    @Audited
    @Field
    private String officialTitle;

    /**
     * M300 - dokumentmedium (xs:string)
     */
    @Column(name = "document_medium")
    @Audited
    private String documentMedium;

    // Link to StorageLocation
    @ManyToMany (fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
    @JoinTable(name = "file_storage_location", joinColumns = @JoinColumn(name = "f_pk_file_id",
            referencedColumnName = "pk_file_id"), inverseJoinColumns = @JoinColumn(name = "f_pk_storage_location_id",
            referencedColumnName = "pk_storage_location_id"))
    private Set<StorageLocation> referenceStorageLocation = new TreeSet<>();

    // Links to Keywords
    @ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
    @JoinTable(name = "file_keyword", joinColumns = @JoinColumn(name = "f_pk_file_id",
            referencedColumnName = "pk_file_id"), inverseJoinColumns = @JoinColumn(name = "f_pk_keyword_id",
            referencedColumnName = "pk_keyword_id"))
    private Set<Keyword> referenceKeyword = new TreeSet<>();

    // Link to parent File
    @ManyToOne(fetch = FetchType.LAZY)
    private File referenceParentFile;

    // Links to child Files
    @OneToMany(mappedBy = "referenceParentFile")
    private Set<File> referenceChildFile = new TreeSet<>();

    // Link to Series
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_series_id", referencedColumnName = "pk_series_id")
    private Series referenceSeries;

    // Link to Class
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_class_id", referencedColumnName = "pk_class_id")
    private Class referenceClass;

    // Links to Records
    @OneToMany(mappedBy = "referenceFile")
    private Set<Record> referenceRecord = new TreeSet<>();

    // Links to Comments
    @ManyToMany (cascade=CascadeType.PERSIST)
    @JoinTable(name = "file_comment", joinColumns = @JoinColumn(name = "f_pk_file_id",
            referencedColumnName = "pk_file_id"), inverseJoinColumns = @JoinColumn(name = "f_pk_comment_id",
            referencedColumnName = "pk_comment_id"))
    private Set<Comment> referenceComment = new TreeSet<>();

    // Links to Classified
    @ManyToOne (cascade=CascadeType.PERSIST)
    @JoinColumn(name = "file_classified_id", referencedColumnName = "pk_classified_id")
    @JsonIgnore
    private Classified referenceClassified;

    // Link to Disposal
    @ManyToOne (cascade=CascadeType.PERSIST)
    @JoinColumn(name = "file_disposal_id", referencedColumnName = "pk_disposal_id")
    private Disposal referenceDisposal;

    // Link to Screening
    @ManyToOne (cascade=CascadeType.PERSIST)
    @JoinColumn(name = "file_screening_id", referencedColumnName = "pk_screening_id")
    private Screening referenceScreening;

    @OneToMany(mappedBy = "referenceFile")
    private Set<CrossReference> referenceCrossReference;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getOfficialTitle() {
        return officialTitle;
    }

    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }

    public String getDocumentMedium() {
        return documentMedium;
    }

    public void setDocumentMedium(String documentMedium) {
        this.documentMedium = documentMedium;
    }

    @Override
    public String getBaseTypeName() {
        return FILE;
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

    @Override
    public Set<StorageLocation> getReferenceStorageLocation() {
        return referenceStorageLocation;
    }

    @Override
    public void setReferenceStorageLocation(Set<StorageLocation> referenceStorageLocation) {
        this.referenceStorageLocation = referenceStorageLocation;
    }

    public Set<Keyword> getReferenceKeyword() {
        return referenceKeyword;
    }

    public void setReferenceKeyword(Set<Keyword> referenceKeyword) {
        this.referenceKeyword = referenceKeyword;
    }

    public File getReferenceParentFile() {
        return referenceParentFile;
    }

    public void setReferenceParentFile(File referenceParentFile) {
        this.referenceParentFile = referenceParentFile;
    }

    public Set<File> getReferenceChildFile() {
        return referenceChildFile;
    }

    public void setReferenceChildFile(Set<File> referenceChildFile) {
        this.referenceChildFile = referenceChildFile;
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

    public Set<Record> getReferenceRecord() {
        return referenceRecord;
    }

    public void setReferenceRecord(Set<Record> referenceRecord) {
        this.referenceRecord = referenceRecord;
    }


    public Set<Comment> getReferenceComment() {
        return referenceComment;
    }

    public void setReferenceComment(Set<Comment> referenceComment) {
        this.referenceComment = referenceComment;
    }

    @Override
    public Set<CrossReference> getReferenceCrossReference() {
        return referenceCrossReference;
    }

    @Override
    public void setReferenceCrossReference(Set<CrossReference> referenceCrossReference) {
        this.referenceCrossReference = referenceCrossReference;
    }

    /**
     * Identify who is the parent of this object.
     */
    public NoarkEntity chooseParent() {
        if (null != referenceParentFile) {
            return referenceParentFile;
        }
        else if (null != referenceClass) {
            return referenceClass;
        }
        else if (null != referenceSeries) {
            return referenceSeries;
        }
        else { // This should be impossible, a File cannot exist without a parent
            throw new NoarkEntityNotFoundException("Could not find parent object for " + this.toString());
        }
    }

    @Override
    public String toString() {
        return "File{" + super.toString() +
                ", documentMedium='" + documentMedium + '\'' +
                ", officialTitle='" + officialTitle + '\'' +
                ", fileId='" + fileId + '\'' +
                '}';
    }
}
