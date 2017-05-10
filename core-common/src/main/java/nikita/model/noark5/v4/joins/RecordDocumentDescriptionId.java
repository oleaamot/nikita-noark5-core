package nikita.model.noark5.v4.joins;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by tsodring on 5/10/17.
 */

@Embeddable
public class RecordDocumentDescriptionId implements Serializable {
    private Long recordId;
    private Long documentDescriptionId;
    private RecordDocumentDescriptionId() {}

    public RecordDocumentDescriptionId(Long recordId, Long documentDescriptionId) {
        this.recordId = recordId;
        this.documentDescriptionId = documentDescriptionId;
    }
    public Long getRecordId() {
        return recordId;
    }
    public Long getDocumentDescriptionId() {
        return documentDescriptionId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordDocumentDescriptionId that = (RecordDocumentDescriptionId) o;
        return Objects.equals(recordId, that.recordId) &&
                Objects.equals(documentDescriptionId, that.documentDescriptionId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(recordId, documentDescriptionId);
    }
}
