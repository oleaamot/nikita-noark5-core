package nikita.common.model.noark5.v4.casehandling.secondary;

import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.interfaces.entities.casehandling.ICorrespondencePartUnitEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "correspondence_part_unit")
//@JsonDeserialize(using = CorrespondencePartUnitDeserializer.class)
@AttributeOverride(name = "id", column = @Column(name = "pk_correspondence_part_unit_id"))
public class CorrespondencePartUnit extends CorrespondencePart implements ICorrespondencePartUnitEntity {


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_contact_information_id")
    ContactInformation contactInformation;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_business_address_id")
    BusinessAddress businessAddress;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_postal_link_id")
    PostalAddress postalAddress;
    /**
     * M??? - organisasjonsnummer (xs:string)
     */
    @Column(name = "organisation_number")
    @Audited
    private String organisationNumber;
    /**
     * M??? - navn (xs:string)
     */
    @Column(name = "name")
    @Audited
    private String name;
    /**
     * M412 - kontaktperson  (xs:string)
     */
    @Column(name = "contact_person")
    @Audited
    private String contactPerson;

    /*
  TODO: Temp disabled!

    // Links to RegistryEntry
    @ManyToMany(mappedBy = "referenceCorrespondencePartUnit")
    private List<RegistryEntry> referenceRegistryEntry = new ArrayList<>();
*/

    public String getOrganisationNumber() {
        return organisationNumber;
    }

    public void setOrganisationNumber(String organisationNumber) {
        this.organisationNumber = organisationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    public BusinessAddress getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(BusinessAddress businessAddress) {
        this.businessAddress = businessAddress;
    }

    public PostalAddress getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.CORRESPONDENCE_PART_UNIT;
    }

    /*
      TODO: Temp disabled!

        @Override
        public List<RegistryEntry> getReferenceRegistryEntry() {
            return referenceRegistryEntry;
        }

        @Override
        public void setReferenceRegistryEntry(List<RegistryEntry> referenceRegistryEntry) {
            this.referenceRegistryEntry = referenceRegistryEntry;
        }
    */
    @Override
    public String toString() {
        return super.toString() +
                ", organisationNumber='" + organisationNumber + '\'' +
                ", name='" + name + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
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
        CorrespondencePartUnit rhs = (CorrespondencePartUnit) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(organisationNumber, rhs.organisationNumber)
                .append(name, rhs.name)
                .append(contactPerson, rhs.contactPerson)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(organisationNumber)
                .append(name)
                .append(contactPerson)
                .toHashCode();
    }
}
