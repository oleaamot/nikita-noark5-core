package nikita.model.noark5.v4;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;

import static nikita.config.N5ResourceMappings.MEETING_PARTICIPANT;

@Entity
@Table(name = "meeting_participant")
// Enable soft delete of MeetingParticipant
// @SQLDelete(sql="UPDATE meeting_participant SET deleted = true WHERE pk_meeting_participant_id = ? and version = ?")
// @Where(clause="deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_meeting_participant_id"))
public class MeetingParticipant extends NoarkEntity {

    /**
     * M372 - moetedeltakerNavn (xs:string)
     */
    @Column(name = "meeting_participant_name")
    @Audited
    private String meetingParticipantName;

    /**
     * M373 - moetedeltakerFunksjon (xs:string)
     */
    @Column(name = "meeting_participant_function")
    @Audited
    private String meetingParticipantFunction;

    // Link to MeetingFile
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_participant_file_id", referencedColumnName = "pk_file_id")
    private MeetingFile referenceMeetingFile;


    public String getMeetingParticipantName() {
        return meetingParticipantName;
    }

    public void setMeetingParticipantName(String meetingParticipantName) {
        this.meetingParticipantName = meetingParticipantName;
    }

    public String getMeetingParticipantFunction() {
        return meetingParticipantFunction;
    }

    public void setMeetingParticipantFunction(String meetingParticipantFunction) {
        this.meetingParticipantFunction = meetingParticipantFunction;
    }
    @Override
    public String getBaseTypeName() {
        return MEETING_PARTICIPANT;
    }

    public MeetingFile getReferenceMeetingFile() {
        return referenceMeetingFile;
    }

    public void setReferenceMeetingFile(MeetingFile referenceMeetingFile) {
        this.referenceMeetingFile = referenceMeetingFile;
    }

    @Override
    public String toString() {
        return "MeetingParticipant{" + super.toString() + 
                "meetingParticipantFunction='" + meetingParticipantFunction + '\'' +
                ", meetingParticipantName='" + meetingParticipantName + '\'' +
                '}';
    }
}
