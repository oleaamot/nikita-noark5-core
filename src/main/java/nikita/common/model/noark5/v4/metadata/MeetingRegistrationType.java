package nikita.common.model.noark5.v4.metadata;

import nikita.common.config.N5ResourceMappings;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// Noark 5v4 Møteregistreringstype
@Entity
@Table(name = "meeting_registration_type")
// Enable soft delete
// @SQLDelete(sql = "UPDATE meeting_registration_type SET deleted = true WHERE pk_meeting_registration_type_id = ? and version = ?")
// @Where(clause = "deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_meeting_registration_type_id"))
public class MeetingRegistrationType extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.MEETING_REGISTRATION_TYPE;
    }
}
