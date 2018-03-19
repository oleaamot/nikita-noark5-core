package nikita.common.model.noark5.v4.metadata;

import nikita.common.config.N5ResourceMappings;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// Noark 5v4 Møtedeltakerfunksjon
@Entity
@Table(name = "meeting_participant_function")
// Enable soft delete
// @SQLDelete(sql = "UPDATE meeting_participant_function SET deleted = true WHERE pk_meeting_participant_function_id = ? and version = ?")
// @Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_meeting_participant_function_id"))
public class MeetingParticipantFunction extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.MEETING_PARTICIPANT_FUNCTION;
    }
}
