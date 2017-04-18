package nikita.model.noark5.v4;

import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

import static nikita.config.N5ResourceMappings.MEETING_PARTICIPANT;

@Entity
@Table(name = "meeting_participant")
// Enable soft delete of MeetingParticipant
@SQLDelete(sql="UPDATE meeting_participant SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class MeetingParticipant implements Serializable, INoarkSystemIdEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_meeting_participant_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique = true)
    @Audited
    protected String systemId;

    /**
     * M372 - moetedeltakerNavn (xs:string)
     */
    @Column(name = "meeting_participant_name")
    @Audited
    protected String meetingParticipantName;

    /**
     * M373 - moetedeltakerFunksjon (xs:string)
     */
    @Column(name = "meeting_participant_function")
    @Audited
    protected String meetingParticipantFunction;
    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;
    @Version
    @Column(name = "version")
    protected Long version;
    // Link to MeetingFile
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_participant_file_id", referencedColumnName = "pk_file_id")
    protected MeetingFile referenceMeetingFile;
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
        return "MeetingParticipant{" +
                "meetingParticipantFunction='" + meetingParticipantFunction + '\'' +
                ", meetingParticipantName='" + meetingParticipantName + '\'' +
                ", version='" + version + '\'' +
                ", id=" + id +
                '}';
    }
}