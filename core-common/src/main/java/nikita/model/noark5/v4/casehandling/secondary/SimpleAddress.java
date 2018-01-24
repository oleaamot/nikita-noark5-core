package nikita.model.noark5.v4.casehandling.secondary;

import nikita.model.noark5.v4.NoarkEntity;
import nikita.model.noark5.v4.interfaces.entities.casehandling.ISimpleAddressEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Created by tsodring on 5/14/17.
 */
@Entity
@Table(name = "simple_address")
@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "pk_simple_address_id"))
public class SimpleAddress extends NoarkEntity implements ISimpleAddressEntity {

    /**
     * M??? - adresselinje1 (xs:string)
     */
    @Column(name = "address_line_1")
    @Audited
    private String addressLine1;

    /**
     * M??? - adresselinje2 (xs:string)
     */
    @Column(name = "address_line_2")
    @Audited
    private String addressLine2;

    /**
     * M??? - adresselinje3 (xs:string)
     */
    @Column(name = "address_line_3")
    @Audited
    private String addressLine3;

    /**
     * M407 - postnummer (xs:string)
     */
    @Embedded
    PostalNumber postalNumber;

    /**
     * M408 - poststed (xs:string)
     */
    @Audited
    @Column(name = "postal_town")
    private String postalTown;

    /**
     * M??? - landKode (xs:string)
     */
    @Audited
    @Column(name = "country_code")
    private String countryCode;

    // Holds if this is a postAddress / residingAddress etc
    @Column(name = "address_type")
    private String addressType;

    @OneToOne(mappedBy = "contactInformation")
    CorrespondencePartPerson correspondencePartPerson;

    @OneToOne(mappedBy = "contactInformation")
    CorrespondencePartUnit correspondencePartUnit;

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public PostalNumber getPostalNumber() {
        return postalNumber;
    }

    public void setPostalNumber(PostalNumber postalNumber) {
        this.postalNumber = postalNumber;
    }

    public String getPostalTown() {
        return postalTown;
    }

    public void setPostalTown(String postalTown) {
        this.postalTown = postalTown;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    @Override
    public String toString() {
        return "SimpleAddress{" + super.toString() +
                "addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", addressLine3='" + addressLine3 + '\'' +
                ", postalNumber=" + postalNumber +
                ", postalTown='" + postalTown + '\'' +
                ", countryCode='" + countryCode + '\'' +
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

        SimpleAddress rhs = (SimpleAddress) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(addressLine1, rhs.addressLine1)
                .append(addressLine2, rhs.addressLine2)
                .append(addressLine3, rhs.addressLine3)
                .append(postalNumber, rhs.postalNumber)
                .append(postalTown, rhs.postalTown)
                .append(countryCode, rhs.countryCode)
                .isEquals();
    }
}
