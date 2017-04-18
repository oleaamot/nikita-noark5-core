package nikita.model.noark5.v4.metadata;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.SCREENING_DOCUMENT;

// Noark 5v4 Skjermingdokument
@Entity
@Table(name = "screening_document")
// Enable soft delete
@SQLDelete(sql = "UPDATE screening_document SET delete" +
        "d = true WHERE id = ?")
@Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_screening_document_id"))
public class ScreeningDocument extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return SCREENING_DOCUMENT;
    }
}
