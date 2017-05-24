package nikita.model.noark5.v4.casehandling.secondary;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.model.noark5.v4.interfaces.entities.casehandling.ICorrespondencePartPersonEntity;
import nikita.util.deserialisers.casehandling.CorrespondencePartPersonDeserializer;
import org.hibernate.envers.Audited;

import javax.persistence.*;

import static nikita.config.Constants.NOARK_CASE_HANDLING_PATH;
import static nikita.config.N5ResourceMappings.CORRESPONDENCE_PART_PERSON;

@Entity
@Table(name = "correspondence_part_person")
@JsonDeserialize(using = CorrespondencePartPersonDeserializer.class)
@AttributeOverride(name = "id", column = @Column(name = "pk_correspondence_part_person_id"))
public class CorrespondencePartPerson extends CorrespondencePart implements ICorrespondencePartPersonEntity {

    /**
     * M??? - fødselsnummer (xs:string)
     */
    @Column(name = "social_security_number")
    @Audited
    private String socialSecurityNumber;

    /**
     * M??? - DNummer (xs:string)
     */
    @Column(name = "d_number")
    @Audited
    private String dNumber;

    /**
     * M400 - korrespondansepartNavn (xs:string)
     * Interface standard lists this as name. Using name until clarification is provided
     */
    @Audited
    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_contact_information_id")
    private ContactInformation contactInformation;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_residing_address_id")
    private ResidingAddress residingAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_postal_address_id")
    private PostalAddress postalAddress;

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public String getdNumber() {
        return dNumber;
    }

    public void setdNumber(String dNumber) {
        this.dNumber = dNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public PostalAddress getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
    }

    public ResidingAddress getResidingAddress() {
        return residingAddress;
    }

    public void setResidingAddress(ResidingAddress residingAddress) {
        this.residingAddress = residingAddress;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    @Override
    public String getBaseTypeName() {
        return CORRESPONDENCE_PART_PERSON;
    }

    @Override
    public String getFunctionalTypeName() {
        return NOARK_CASE_HANDLING_PATH;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", socialSecurityNumber='" + socialSecurityNumber + '\'' +
                ", dNumber='" + dNumber + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
