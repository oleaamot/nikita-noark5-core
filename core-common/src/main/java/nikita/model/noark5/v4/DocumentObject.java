package nikita.model.noark5.v4;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.model.noark5.v4.interfaces.IConversion;
import nikita.model.noark5.v4.interfaces.IElectronicSignature;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.model.noark5.v4.interfaces.entities.INoarkCreateEntity;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import nikita.util.deserialisers.DocumentObjectDeserializer;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static nikita.config.N5ResourceMappings.DOCUMENT_OBJECT;

@Entity
@Table(name = "document_object")
// Enable soft delete of DocumentObject
@SQLDelete(sql="UPDATE document_object SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
@Indexed(index = "document_object")
@JsonDeserialize(using = DocumentObjectDeserializer.class)
public class DocumentObject implements INikitaEntity, INoarkSystemIdEntity, INoarkCreateEntity,
        IElectronicSignature, IConversion
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_document_object_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique=true)
    @Audited
    @Field
    protected String systemId;

    /**
     * M005 - versjonsnummer (xs:integer)
     **/
    @Column(name = "version_number")
    @Audited
    @Field
    protected Integer versionNumber;

    /**
     * M700 - variantformat (xs:string)
     */
    @Column(name = "variant_format")
    @Audited
    @Field
    protected String variantFormat;

    /**
     * M701 - format (xs:string)
     */
    @Column(name = "format")
    @Audited
    @Field
    protected String format;

    /**
     * M702 - formatDetaljer (xs:string)
     */
    @Column(name = "format_details")
    @Audited
    @Field
    protected String formatDetails;

    /**
     * M600 - opprettetDato (xs:dateTime)
     */
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Audited
    @Field
    protected Date createdDate;

    /**
     * M601 - opprettetAv (xs:string)
     */
    @Column(name = "created_by")
    @Audited
    @Field
    protected String createdBy;

    /**
     * M218 - referanseDokumentfil (xs:string)
     */
    @Column(name = "reference_document_file")
    @Audited
    @Field
    protected String referenceDocumentFile;

    /**
     * M705 - sjekksum (xs:string)
     */
    @Column(name = "checksum")
    @Audited
    @Field
    protected String checksum;

    /**
     * M706 - sjekksumAlgoritme (xs:string)
     */
    @Column(name = "checksum_algorithm")
    @Audited
    @Field
    protected String checksumAlgorithm;

    /**
     * M707 - filstoerrelse (xs:string)
     */
    @Column(name = "file_size")
    @Audited
    @Field
    protected Long fileSize;

    @Column(name = "original_filename")
    @Audited
    @Field
    protected String originalFilename;

    @Column(name = "mime_type")
    @Audited
    @Field
    protected String mimeType;

    @Column(name = "owned_by")
    @Audited
    @Field
    protected String ownedBy;

    @Version
    @Column(name = "version")
    protected Long version;

    // Link to DocumentDescription
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_object_document_description_id", referencedColumnName = "pk_document_description_id")
    protected DocumentDescription referenceDocumentDescription;
    // Link to Record
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_object_record_id", referencedColumnName = "pk_record_id")
    protected Record referenceRecord;
    // Links to Conversion
    @OneToMany(mappedBy = "referenceDocumentObject")
    protected Set<Conversion> referenceConversion = new HashSet<Conversion>();
    // Link to ElectronicSignature
    @OneToOne
    @JoinColumn(name="pk_electronic_signature_id")
    protected ElectronicSignature referenceElectronicSignature;
    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    @Field
    private Boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public void setId(Long id) {
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

    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
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
        return DOCUMENT_OBJECT;
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

    public ElectronicSignature getReferenceElectronicSignature() {
        return referenceElectronicSignature;
    }

    public void setReferenceElectronicSignature(ElectronicSignature referenceElectronicSignature) {
        this.referenceElectronicSignature = referenceElectronicSignature;
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
                ", mimeType=" + mimeType +
                ", originalFilename=" + originalFilename +
                ", systemId='" + systemId + '\'' +
                ", version='" + version + '\'' +
                ", id=" + id +
                '}';
    }
}