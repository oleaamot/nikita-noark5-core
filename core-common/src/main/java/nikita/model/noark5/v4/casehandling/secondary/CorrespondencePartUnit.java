package nikita.model.noark5.v4.casehandling.secondary;

import nikita.model.noark5.v4.casehandling.RegistryEntry;
import nikita.model.noark5.v4.interfaces.entities.casehandling.ICorrespondencePartUnitEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

import static nikita.config.N5ResourceMappings.CORRESPONDENCE_PART_UNIT;

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

    // Links to RegistryEntry
    @ManyToMany(mappedBy = "referenceCorrespondencePartUnit")
    private Set<RegistryEntry> referenceRegistryEntry = new TreeSet<>();


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
        return CORRESPONDENCE_PART_UNIT;
    }

    @Override
    public Set<RegistryEntry> getReferenceRegistryEntry() {
        return referenceRegistryEntry;
    }

    @Override
    public void setReferenceRegistryEntry(Set<RegistryEntry> referenceRegistryEntry) {
        this.referenceRegistryEntry = referenceRegistryEntry;
    }

    @Override
    public String toString() {
        return super.toString() +
                ", organisationNumber='" + organisationNumber + '\'' +
                ", name='" + name + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                '}';
    }
}
