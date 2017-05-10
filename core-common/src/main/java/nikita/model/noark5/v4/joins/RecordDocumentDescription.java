package nikita.model.noark5.v4.joins;

import nikita.model.noark5.v4.DocumentDescription;
import nikita.model.noark5.v4.Record;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by tsodring on 5/10/17.
 */
@Entity
@Table(name = "XXXXXrecord_document_description")
public class RecordDocumentDescription {
    @EmbeddedId
    private RecordDocumentDescriptionId id;
    @ManyToOne
    @MapsId("recordId")
    private Record record;
    @ManyToOne
    @MapsId("documentDescriptionId")
    private DocumentDescription documentDescription;

    private RecordDocumentDescription() {
    }

    public RecordDocumentDescription(Record record, DocumentDescription documentDescription) {
        this.record = record;
        this.documentDescription = documentDescription;
        this.id = new RecordDocumentDescriptionId(record.getId(), documentDescription.getId());
    }

    //Getters and setters omitted for brevity
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecordDocumentDescription that = (RecordDocumentDescription) o;
        return Objects.equals(record, that.record) &&
                Objects.equals(documentDescription, that.documentDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(record, documentDescription);
    }
}

