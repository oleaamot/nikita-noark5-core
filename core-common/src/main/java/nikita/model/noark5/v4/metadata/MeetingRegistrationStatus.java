package nikita.model.noark5.v4.metadata;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.MEETING_REGISTRATION_STATUS;

// Noark 5v4 Møteregistreringsstatus
@Entity
@Table(name = "meeting_registration_status")
// Enable soft delete
@SQLDelete(sql = "UPDATE meeting_registration_status SET delete" +
        "d = true WHERE id = ?")
@Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_meeting_registration_status_id"))
public class MeetingRegistrationStatus extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return MEETING_REGISTRATION_STATUS;
    }
}
