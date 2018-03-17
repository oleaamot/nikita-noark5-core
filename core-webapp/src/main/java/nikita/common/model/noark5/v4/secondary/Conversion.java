package nikita.common.model.noark5.v4.secondary;

import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.DocumentObject;
import nikita.common.model.noark5.v4.NoarkEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "conversion")
// Enable soft delete of Conversion
// @SQLDelete(sql="UPDATE conversion SET deleted = true WHERE pk_conversion_id = ? and version = ?")
// @Where(clause="deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_conversion_id"))
public class Conversion extends NoarkEntity {

    private static final long serialVersionUID = 1L;

    /**
     * M615 - konvertertDato (xs:dateTime)
     */
    @Column(name = "converted_date")
    @Audited
    private Date convertedDate;

    /**
     * M616 - konvertertAv (xs:string)
     */
    @Column(name = "converted_by")
    @Audited
    private String convertedBy;

    /**
     * M712 - konvertertFraFormat (xs:string)
     */
    @Column(name = "converted_from_format")
    @Audited
    private String convertedFromFormat;

    /**
     * M713 - konvertertTilFormat (xs:string)
     */
    @Column(name = "converted_to_format")
    @Audited
    private String convertedToFormat;

    /**
     * M714 - konverteringsverktoey (xs:string)
     */
    @Column(name = "conversion_tool")
    @Audited
    private String conversionTool;

    /**
     * M715 - konverteringskommentar (xs:string)
     */
    @Column(name = "conversion_comment")
    @Audited
    private String conversionComment;

    // Link to DocumentObject
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversion_document_object_id", referencedColumnName = "pk_document_object_id")
    private DocumentObject referenceDocumentObject;


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


    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.CONVERSION;
    }

    public DocumentObject getReferenceDocumentObject() {
        return referenceDocumentObject;
    }

    public void setReferenceDocumentObject(DocumentObject referenceDocumentObject) {
        this.referenceDocumentObject = referenceDocumentObject;
    }

    @Override
    public String toString() {
        return "Conversion{" + super.toString() +
                ", convertedDate=" + convertedDate +
                ", convertedBy='" + convertedBy + '\'' +
                ", convertedFromFormat='" + convertedFromFormat + '\'' +
                ", convertedToFormat='" + convertedToFormat + '\'' +
                ", conversionTool='" + conversionTool + '\'' +
                ", conversionComment='" + conversionComment + '\'' +
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
        Conversion rhs = (Conversion) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(convertedDate, rhs.convertedDate)
                .append(convertedBy, rhs.convertedBy)
                .append(convertedFromFormat, rhs.convertedFromFormat)
                .append(convertedToFormat, rhs.convertedToFormat)
                .append(conversionTool, rhs.conversionTool)
                .append(conversionComment, rhs.conversionComment)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(convertedDate)
                .append(convertedBy)
                .append(convertedFromFormat)
                .append(convertedToFormat)
                .append(conversionTool)
                .append(conversionComment)
                .toHashCode();
    }
}
