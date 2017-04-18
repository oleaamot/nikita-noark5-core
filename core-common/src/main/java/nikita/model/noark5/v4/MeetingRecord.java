package nikita.model.noark5.v4;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

import static nikita.config.N5ResourceMappings.MEETING_RECORD;

@Entity
@Table(name = "meeting_record")
@Inheritance(strategy = InheritanceType.JOINED)
// Enable soft delete of MeetingRecord
@SQLDelete(sql="UPDATE meeting_record SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class MeetingRecord extends BasicRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * M085 - moeteregistreringstype (xs:string)
     */
    @Column(name = "meeting_record_type")
    @Audited
    protected String meetingRecordType;

    /**
     * M088 - moetesakstype (xs:string)
     */
    @Column(name = "meeting_case_type")
    @Audited
    protected String meetingCaseType;

    /**
     * M305 - administrativEnhet (xs:string)
     */
    @Column(name = "meeting_record_status")
    @Audited
    protected String meetingRecordStatus;

    /**
     * M305 - administrativEnhet (xs:string)
     */
    @Column(name = "administrative_unit")
    @Audited
    protected String administrativeUnit;

    /**
     * M307 - saksbehandler
     */
    @Column(name = "case_handler")
    @Audited
    protected String caseHandler;
    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;
    /**
     * M223 - referanseTilMoeteregistrering (xs:string)
     **/
    // Link to "to"  MeetingRegistration
    // TODO: This should link to sysemId, not id!
    @OneToOne(fetch = FetchType.LAZY)
    protected MeetingRecord referenceToMeetingRegistration;
    /**
     * M224 - referanseFraMoeteregistrering (xs:string)
     **/
    // Link to "from" MeetingRegistration
    @OneToOne(fetch = FetchType.LAZY)
    protected MeetingRecord referenceFromMeetingRegistration;
    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    public String getMeetingRecordType() {
        return meetingRecordType;
    }

    public void setMeetingRecordType(String meetingRecordType) {
        this.meetingRecordType = meetingRecordType;
    }

    public String getMeetingCaseType() {
        return meetingCaseType;
    }

    public void setMeetingCaseType(String meetingCaseType) {
        this.meetingCaseType = meetingCaseType;
    }

    public String getMeetingRecordStatus() {
        return meetingRecordStatus;
    }

    public void setMeetingRecordStatus(String meetingRecordStatus) {
        this.meetingRecordStatus = meetingRecordStatus;
    }

    public String getAdministrativeUnit() {
        return administrativeUnit;
    }

    public void setAdministrativeUnit(String administrativeUnit) {
        this.administrativeUnit = administrativeUnit;
    }

    public String getCaseHandler() {
        return caseHandler;
    }

    public void setCaseHandler(String caseHandler) {
        this.caseHandler = caseHandler;
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
        return MEETING_RECORD;
    }

    public MeetingRecord getReferenceToMeetingRegistration() {
        return referenceToMeetingRegistration;
    }

    public void setReferenceToMeetingRegistration(MeetingRecord referenceToMeetingRegistration) {
        this.referenceToMeetingRegistration = referenceToMeetingRegistration;
    }

    public MeetingRecord getReferenceFromMeetingRegistration() {
        return referenceFromMeetingRegistration;
    }

    public void setReferenceFromMeetingRegistration(MeetingRecord referenceFromMeetingRegistration) {
        this.referenceFromMeetingRegistration = referenceFromMeetingRegistration;
    }

    @Override
    public String toString() {
        return "MeetingRecord{" +
                "referenceFromMeetingRegistration=" + referenceFromMeetingRegistration +
                ", referenceToMeetingRegistration=" + referenceToMeetingRegistration +
                ", caseHandler='" + caseHandler + '\'' +
                ", administrativeUnit='" + administrativeUnit + '\'' +
                ", meetingRecordStatus='" + meetingRecordStatus + '\'' +
                ", meetingCaseType='" + meetingCaseType + '\'' +
                ", meetingRecordType='" + meetingRecordType + '\'' +
                '}';
    }
}