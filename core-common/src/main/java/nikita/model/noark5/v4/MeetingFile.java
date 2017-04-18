package nikita.model.noark5.v4;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static nikita.config.N5ResourceMappings.MEETING_FILE;

@Entity
@Table(name = "meeting_file")
// Enable soft delete of MeetingFile
@SQLDelete(sql="UPDATE meeting_file SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class MeetingFile extends File implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * M008 - moetenummer (xs:string)
     */
    @Column(name = "meeting_number")
    @Audited
    protected String meetingNumber;

    /**
     * M370 - utvalg (xs:string)
     */
    @Column(name = "committee")
    @Audited
    protected String committee;

    /**
     * M102 - moetedato (xs:date)
     */
    @Column(name = "loaned_date")
    @Audited
    protected Date meetingDate;

    /**
     * M371 - moetested (xs:string)
     */
    @Column(name = "meeting_place")
    @Audited
    protected String meetingPlace;
    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;
    /**
     * M221 - referanseForrigeMoete (xs:string)
     **/
    // Link to next Meeting
    @OneToOne(fetch = FetchType.LAZY)
    protected MeetingFile referenceNextMeeting;
    /**
     * M222 - referanseNesteMoete (xs:string)
     **/
    // Link to previous Meeting
    // TODO: This links to id, not systemId. Fix!
    @OneToOne(fetch = FetchType.LAZY)
    protected MeetingFile referencePreviousMeeting;
    // Links to MeetingParticipant
    @OneToMany(mappedBy = "referenceMeetingFile")
    protected Set<MeetingParticipant> referenceMeetingParticipant = new HashSet<MeetingParticipant>();
    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    public String getMeetingNumber() {
        return meetingNumber;
    }

    public void setMeetingNumber(String meetingNumber) {
        this.meetingNumber = meetingNumber;
    }

    public String getCommittee() {
        return committee;
    }

    public void setCommittee(String committee) {
        this.committee = committee;
    }

    public Date getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }

    public String getMeetingPlace() {
        return meetingPlace;
    }

    public void setMeetingPlace(String meetingPlace) {
        this.meetingPlace = meetingPlace;
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

    @Override
    public String getBaseTypeName() {
        return MEETING_FILE;
    }

    public MeetingFile getReferenceNextMeeting() {
        return referenceNextMeeting;
    }

    public void setReferenceNextMeeting(MeetingFile referenceNextMeeting) {
        this.referenceNextMeeting = referenceNextMeeting;
    }

    public MeetingFile getReferencePreviousMeeting() {
        return referencePreviousMeeting;
    }

    public void setReferencePreviousMeeting(MeetingFile referencePreviousMeeting) {
        this.referencePreviousMeeting = referencePreviousMeeting;
    }

    public Set<MeetingParticipant> getReferenceMeetingParticipant() {
        return referenceMeetingParticipant;
    }

    public void setReferenceMeetingParticipant(Set<MeetingParticipant> referenceMeetingParticipant) {
        this.referenceMeetingParticipant = referenceMeetingParticipant;
    }

    @Override
    public String toString() {
        return "MeetingFile{" +
                "meetingNumber='" + meetingNumber + '\'' +
                ", committee='" + committee + '\'' +
                ", meetingDate=" + meetingDate +
                ", meetingPlace='" + meetingPlace + '\'' +
                '}';
    }
}