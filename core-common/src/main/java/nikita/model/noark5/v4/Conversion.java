package nikita.model.noark5.v4;

import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;

import static nikita.config.N5ResourceMappings.CONVERSION;

@Entity
@Table(name = "conversion")
// Enable soft delete of Conversion
@SQLDelete(sql="UPDATE conversion SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class Conversion implements INoarkSystemIdEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_comment_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique = true)
    @Audited
    protected String systemId;

    /** M615 - konvertertDato (xs:dateTime) */
    @Column(name = "converted_date")
    @Audited
    protected Date convertedDate;

    /** M616 - konvertertAv (xs:string) */
    @Column(name = "converted_by")
    @Audited
    protected String convertedBy;

    /** M712 - konvertertFraFormat (xs:string) */
    @Column(name = "converted_from_format")
    @Audited
    protected String convertedFromFormat;

    /** M713 - konvertertTilFormat (xs:string) */
    @Column(name = "converted_to_format")
    @Audited
    protected String convertedToFormat;

    /** M714 - konverteringsverktoey (xs:string) */
    @Column(name = "conversion_tool")
    @Audited
    protected String conversionTool;

    /** M715 - konverteringskommentar (xs:string) */
    @Column(name = "conversion_comment")
    @Audited
    protected String conversionComment;
    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;
    @Version
    @Column(name = "version")
    protected Long version;
    // Link to DocumentObject
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversion_document_object_id", referencedColumnName = "pk_document_object_id")
    protected DocumentObject referenceDocumentObject;
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

    public Date getConvertedDate() {
        return convertedDate;
    }

    public void setConvertedDate(Date convertedDate) {
        this.convertedDate = convertedDate;
    }

    public String getConvertedBy() {
        return convertedBy;
    }

    public void setConvertedBy(String convertedBy) {
        this.convertedBy = convertedBy;
    }

    public String getConvertedFromFormat() {
        return convertedFromFormat;
    }

    public void setConvertedFromFormat(String convertedFromFormat) {
        this.convertedFromFormat = convertedFromFormat;
    }

    public String getConvertedToFormat() {
        return convertedToFormat;
    }

    public void setConvertedToFormat(String convertedToFormat) {
        this.convertedToFormat = convertedToFormat;
    }

    public String getConversionTool() {
        return conversionTool;
    }

    public void setConversionTool(String conversionTool) {
        this.conversionTool = conversionTool;
    }

    public String getConversionComment() {
        return conversionComment;
    }

    public void setConversionComment(String conversionComment) {
        this.conversionComment = conversionComment;
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
        return CONVERSION;
    }

    public DocumentObject getReferenceDocumentObject() {
        return referenceDocumentObject;
    }

    public void setReferenceDocumentObject(DocumentObject referenceDocumentObject) {
        this.referenceDocumentObject = referenceDocumentObject;
    }

    @Override
    public String toString() {
        return "Conversion{" +
                "id=" + id +
                ", convertedDate=" + convertedDate +
                ", convertedBy='" + convertedBy + '\'' +
                ", convertedFromFormat='" + convertedFromFormat + '\'' +
                ", convertedToFormat='" + convertedToFormat + '\'' +
                ", conversionTool='" + conversionTool + '\'' +
                ", conversionComment='" + conversionComment + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}