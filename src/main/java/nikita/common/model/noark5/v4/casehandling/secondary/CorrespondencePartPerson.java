package nikita.common.model.noark5.v4.casehandling.secondary;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.common.config.Constants;
import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.NoarkEntity;
import nikita.common.model.noark5.v4.interfaces.entities.casehandling.ICorrespondencePartPersonEntity;
import nikita.common.model.noark5.v4.metadata.CorrespondencePartType;
import nikita.common.util.deserialisers.casehandling.CorrespondencePartPersonDeserializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "correspondence_part_person")
@JsonDeserialize(using = CorrespondencePartPersonDeserializer.class)
@AttributeOverride(name = "id",
        column = @Column(name = "pk_correspondence_part_person_id"))
public class CorrespondencePartPerson
        extends NoarkEntity
        implements ICorrespondencePartPersonEntity {


    @Override
    public CorrespondencePartType getCorrespondencePartType() {
        return null;
    }

    @Override
    public void setCorrespondencePartType(CorrespondencePartType correspondencePartType) {

    }

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

    /*
    TODO: Temp disabled!
    // Links to RegistryEntry
    @ManyToMany(mappedBy = "referenceCorrespondencePartPerson")
    private List<RegistryEntry> referenceRegistryEntry = new ArrayList<>();
*/
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

    //@Override
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
        return N5ResourceMappings.CORRESPONDENCE_PART_PERSON;
    }

    @Override
    public String getFunctionalTypeName() {
        return Constants.NOARK_CASE_HANDLING_PATH;
    }

    /*

    TODO: Temp disabled!
        public List<RegistryEntry> getReferenceRegistryEntry() {
            return referenceRegistryEntry;
        }

        public void setReferenceRegistryEntry(List<RegistryEntry> referenceRegistryEntry) {
            this.referenceRegistryEntry = referenceRegistryEntry;
        }
    */
    @Override
    public String toString() {
        return "CorrespondencePartPerson{" + super.toString() +
                "socialSecurityNumber='" + socialSecurityNumber + '\'' +
                ", dNumber='" + dNumber + '\'' +
                ", name='" + name + '\'' +
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
        CorrespondencePartPerson rhs = (CorrespondencePartPerson) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(socialSecurityNumber, rhs.socialSecurityNumber)
                .append(dNumber, rhs.dNumber)
                .append(name, rhs.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(socialSecurityNumber)
                .append(dNumber)
                .append(name)
                .toHashCode();
    }
}
