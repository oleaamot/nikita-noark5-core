package nikita.model.noark5.v4.metadata;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.MEETING_PARTICIPANT_FUNCTION;

// Noark 5v4 Møtedeltakerfunksjon
@Entity
@Table(name = "meeting_participant_function")
// Enable soft delete
@SQLDelete(sql = "UPDATE meeting_participant_function SET delete" +
        "d = true WHERE id = ?")
@Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_meeting_participant_function_id"))
public class MeetingParticipantFunction extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return MEETING_PARTICIPANT_FUNCTION;
    }
}
