package nikita.model.noark5.v4.secondary;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.model.noark5.v4.NoarkEntity;
import nikita.model.noark5.v4.RegistryEntry;
import nikita.model.noark5.v4.interfaces.entities.ICorrespondencePartEntity;
import nikita.model.noark5.v4.metadata.CorrespondencePartType;
import nikita.util.deserialisers.CorrespondencePartDeserializer;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

import static nikita.config.N5ResourceMappings.CORRESPONDENCE_PART;

@Entity
@Table(name = "correspondence_part")
// Enable soft delete of CorrespondencePart
// @SQLDelete(sql="UPDATE correspondence_part SET deleted = true WHERE pk_correspondence_part_id = ? and version = ?")
// @Where(clause="deleted <> true")
@JsonDeserialize(using = CorrespondencePartDeserializer.class)
@AttributeOverride(name = "id", column = @Column(name = "pk_correspondence_part_id"))
public class CorrespondencePart extends NoarkEntity implements ICorrespondencePartEntity {

    /** M400 - korrespondansepartNavn (xs:string) */
    @Audited
    @Column(name = "correspondence_part_name")
    private String correspondencePartName;

    /** M407 - postnummer (xs:string) */
    @Audited
    @Column(name = "post_code")
    private String postCode;

    /** M408 - poststed (xs:string) */
    @Audited
    @Column(name = "postal_town")
    private String postalTown;

    /** M409 - land (xs:string) */
    @Audited
    @Column(name = "country")
    private String country;

    /** M410 - epostadresse (xs:string) */
    @Audited
    @Column(name = "email_address")
    private String emailAddress;

    /** M411 - telefonnummer (xs:string) */
    @Column(name = "telephone_number")
    @Audited
    private String telephoneNumber;

    /** M412 - kontaktperson (xs:string) */
    @Column(name = "contact_person")
    @Audited
    private String contactPerson;

    /** M305 - administrativEnhet (xs:string) */
    @Column(name = "administrative_unit")
    @Audited
    private String administrativeUnit;

    /** M307 - saksbehandler (xs:string) */
    @Column(name = "case_handler")
    @Audited
    private String caseHandler;

    /**
     * M087 - korrespondanseparttype (xs:string)
     */
    // Link to precursor CorrespondencePartType
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "correspondence_part_correspondence_part_type_id",
            referencedColumnName = "pk_correspondence_part_type_id")
    private CorrespondencePartType referenceCorrespondencePartType;

    // Links to PostalAddress
    @ManyToMany(cascade=CascadeType.PERSIST)
    private Set<PostalAddress> postalAddress = new TreeSet<>();

    // Links to Record
    @ManyToMany(mappedBy = "referenceCorrespondencePart")
    private Set<RegistryEntry> referenceRegistryEntry = new TreeSet<>();

    public CorrespondencePartType getCorrespondencePartType() {
        return referenceCorrespondencePartType;
    }

    public void setCorrespondencePartType(CorrespondencePartType referenceCorrespondencePartType) {
        this.referenceCorrespondencePartType = referenceCorrespondencePartType;
    }

    public String getCorrespondencePartName() {
        return correspondencePartName;
    }

    public void setCorrespondencePartName(String correspondencePartName) {
        this.correspondencePartName = correspondencePartName;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostalTown() {
        return postalTown;
    }

    public void setPostalTown(String postalTown) {
        this.postalTown = postalTown;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
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

    @Override
    public Set<PostalAddress> getPostalAddress() {
        return postalAddress;
    }

    @Override
    public void setPostalAddress(Set<PostalAddress> postalAddress) {
        this.postalAddress = postalAddress;
    }

    @Override
    public void addPostalAddress(String singlePostalAddress) {
        PostalAddress postalAddress = new PostalAddress();
        postalAddress.setPostalAddress(singlePostalAddress);
        this.postalAddress.add(postalAddress);
    }

    @Override
    public String getBaseTypeName() {
        return CORRESPONDENCE_PART;
    }

    public Set<RegistryEntry> getReferenceRegistryEntry() {
        return referenceRegistryEntry;
    }

    public void setReferenceRegistryEntry(Set<RegistryEntry> referenceRegistryEntry) {
        this.referenceRegistryEntry = referenceRegistryEntry;
    }

    @Override
    public String toString() {
        return "CorrespondencePart{" + super.toString() +
                "caseHandler='" + caseHandler + '\'' +
                ", administrativeUnit='" + administrativeUnit + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", country='" + country + '\'' +
                ", postalTown='" + postalTown + '\'' +
                ", postCode='" + postCode + '\'' +
                ", correspondencePartName='" + correspondencePartName + '\'' +
                '}';
    }
}
