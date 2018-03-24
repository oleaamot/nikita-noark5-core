package nikita.common.model.noark5.v4.joins;

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

    private RecordDocumentDescriptionId() {
    }

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
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof RecordDocumentDescriptionId))
            return false;
        RecordDocumentDescriptionId otherId = (RecordDocumentDescriptionId) other;
        return Objects.equals(recordId, otherId.recordId) &&
                Objects.equals(documentDescriptionId, otherId.documentDescriptionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId, documentDescriptionId);
    }
}
