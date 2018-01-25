package nikita.model.noark5.v4.meeting;

import nikita.model.noark5.v4.File;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import static nikita.config.N5ResourceMappings.MEETING_FILE;

@Entity
@Table(name = "meeting_file")
// Enable soft delete of MeetingFile
// @SQLDelete(sql="UPDATE meeting_file SET deleted = true WHERE pk_file_id = ? and version = ?")
// @Where(clause="deleted <> true")
public class MeetingFile extends File {


    /**
     * M008 - moetenummer (xs:string)
     */
    @Column(name = "meeting_number")
    @Audited
    private String meetingNumber;

    /**
     * M370 - utvalg (xs:string)
     */
    @Column(name = "committee")
    @Audited
    private String committee;

    /**
     * M102 - moetedato (xs:date)
     */
    @Column(name = "loaned_date")
    @Audited
    private Date meetingDate;

    /**
     * M371 - moetested (xs:string)
     */
    @Column(name = "meeting_place")
    @Audited
    private String meetingPlace;

    /**
     * M221 - referanseForrigeMoete (xs:string)
     **/
    // Link to next Meeting
    @OneToOne(fetch = FetchType.LAZY)
    private MeetingFile referenceNextMeeting;

    /**
     * M222 - referanseNesteMoete (xs:string)
     **/

    // Link to previous Meeting
    // TODO: This links to id, not systemId. Fix!
    @OneToOne(fetch = FetchType.LAZY)
    private MeetingFile referencePreviousMeeting;

    // Links to MeetingParticipant
    @OneToMany(mappedBy = "referenceMeetingFile")
    private Set<MeetingParticipant> referenceMeetingParticipant = new TreeSet<>();

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
        return "MeetingFile{" + super.toString() + 
                "meetingNumber='" + meetingNumber + '\'' +
                ", committee='" + committee + '\'' +
                ", meetingDate=" + meetingDate +
                ", meetingPlace='" + meetingPlace + '\'' +
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
        MeetingFile rhs = (MeetingFile) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(meetingNumber, rhs.meetingNumber)
                .append(committee, rhs.committee)
                .append(meetingDate, rhs.meetingDate)
                .append(meetingPlace, rhs.meetingPlace)
                .isEquals();
    }
}
