package nikita.model.noark5.v4.casehandling.secondary;

import nikita.model.noark5.v4.NoarkEntity;
import nikita.model.noark5.v4.interfaces.entities.casehandling.IContactInformationEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Created by tsodring on 5/14/17.
 */
@Entity
@Table(name = "contact_information")
@AttributeOverride(name = "id", column = @Column(name = "pk_contact_information_id"))
public class ContactInformation extends NoarkEntity implements IContactInformationEntity {

    /**
     * M410 - epostadresse (xs:string)
     */
    @Audited
    @Column(name = "email_address")
    private String emailAddress;

    /**
     * M??? - mobiltelefon (xs:string)
     */
    @Column(name = "mobile_telephone_number")
    @Audited
    private String mobileTelephoneNumber;

    /**
     * M411 - telefonnummer (xs:string)
     */
    @Column(name = "telephone_number")
    @Audited
    private String telephoneNumber;

    @OneToOne(mappedBy = "contactInformation")
    CorrespondencePartPerson correspondencePartPerson;

    @OneToOne(mappedBy = "contactInformation")
    CorrespondencePartUnit correspondencePartUnit;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getMobileTelephoneNumber() {
        return mobileTelephoneNumber;
    }

    public void setMobileTelephoneNumber(String mobileTelephoneNumber) {
        this.mobileTelephoneNumber = mobileTelephoneNumber;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public CorrespondencePartPerson getCorrespondencePartPerson() {
        return correspondencePartPerson;
    }

    public void setCorrespondencePartPerson(CorrespondencePartPerson correspondencePartPerson) {
        this.correspondencePartPerson = correspondencePartPerson;
    }

    public CorrespondencePartUnit getCorrespondencePartUnit() {
        return correspondencePartUnit;
    }

    public void setCorrespondencePartUnit(CorrespondencePartUnit correspondencePartUnit) {
        this.correspondencePartUnit = correspondencePartUnit;
    }

    @Override
    public String toString() {
        return "ContactInformation{" + super.toString() +
                ", emailAddress='" + emailAddress + '\'' +
                ", mobileTelephoneNumber='" + mobileTelephoneNumber + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
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
        ContactInformation rhs = (ContactInformation) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(emailAddress, rhs.emailAddress)
                .append(mobileTelephoneNumber, rhs.mobileTelephoneNumber)
                .append(telephoneNumber, rhs.telephoneNumber)
                .isEquals();
    }
}
