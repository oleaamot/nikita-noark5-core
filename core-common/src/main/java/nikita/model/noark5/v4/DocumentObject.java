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
@Table(name = "document_object")
// Enable soft delete of DocumentObject
@SQLDelete(sql="UPDATE document_object SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property="id")
public class DocumentObject implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_document_object_id", nullable = false, insertable = true, updatable = false)
    protected long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id")
    @Audited
    protected String systemId;

    /**
     * M005 - versjonsnummer (xs:integer)
     **/
    @Column(name = "version_number")
    @Audited
    protected Integer versionNumber;

    /**
     * M700 - variantformat (xs:string)
     */
    @Column(name = "variant_format")
    @Audited
    protected String variantFormat;

    /**
     * M701 - format (xs:string)
     */
    @Column(name = "format")
    @Audited
    protected String format;

    /**
     * M702 - formatDetaljer (xs:string)
     */
    @Column(name = "format_details")
    @Audited
    protected String formatDetails;

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
     * M218 - referanseDokumentfil (xs:string)
     */
    @Column(name = "reference_document_file")
    @Audited
    protected String referenceDocumentFile;

    /**
     * M705 - sjekksum (xs:string)
     */
    @Column(name = "checksum")
    @Audited
    protected String checksum;

    /**
     * M706 - sjekksumAlgoritme (xs:string)
     */
    @Column(name = "checksum_algorithm")
    @Audited
    protected String checksumAlgorithm;

    /**
     * M707 - filstoerrelse (xs:string)
     */
    @Column(name = "file_size")
    @Audited
    protected Long fileSize;

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    // Link to DocumentDescription
    @ManyToOne
    @JoinColumn(name = "document_object_document_description_id", referencedColumnName = "pk_document_description_id")
    protected DocumentDescription referenceDocumentDescription;

    // Link to Record
    @ManyToOne
    @JoinColumn(name = "document_object_record_id", referencedColumnName = "pk_record_id")
    protected Record referenceRecord;

    // Links to Conversion
    @OneToMany(mappedBy = "referenceDocumentObject")
    protected Set<Conversion> referenceConversion = new HashSet<Conversion>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public Integer getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Integer versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getVariantFormat() {
        return variantFormat;
    }

    public void setVariantFormat(String variantFormat) {
        this.variantFormat = variantFormat;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getFormatDetails() {
        return formatDetails;
    }

    public void setFormatDetails(String formatDetails) {
        this.formatDetails = formatDetails;
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

    public String getReferenceDocumentFile() {
        return referenceDocumentFile;
    }

    public void setReferenceDocumentFile(String referenceDocumentFile) {
        this.referenceDocumentFile = referenceDocumentFile;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getChecksumAlgorithm() {
        return checksumAlgorithm;
    }

    public void setChecksumAlgorithm(String checksumAlgorithm) {
        this.checksumAlgorithm = checksumAlgorithm;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
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

    public DocumentDescription getReferenceDocumentDescription() {
        return referenceDocumentDescription;
    }

    public void setReferenceDocumentDescription(
            DocumentDescription referenceDocumentDescription) {
        this.referenceDocumentDescription = referenceDocumentDescription;
    }

    public Record getReferenceRecord() {
        return referenceRecord;
    }

    public void setReferenceRecord(Record referenceRecord) {
        this.referenceRecord = referenceRecord;
    }

    public Set<Conversion> getReferenceConversion() {
        return referenceConversion;
    }

    public void setReferenceConversion(Set<Conversion> referenceConversion) {
        this.referenceConversion = referenceConversion;
    }

    @Override
    public String toString() {
        return "DocumentObject{" +
                "fileSize=" + fileSize +
                ", checksumAlgorithm='" + checksumAlgorithm + '\'' +
                ", checksum='" + checksum + '\'' +
                ", referenceDocumentFile='" + referenceDocumentFile + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", formatDetails='" + formatDetails + '\'' +
                ", format='" + format + '\'' +
                ", variantFormat='" + variantFormat + '\'' +
                ", versionNumber=" + versionNumber +
                ", systemId='" + systemId + '\'' +
                ", id=" + id +
                '}';
    }
}