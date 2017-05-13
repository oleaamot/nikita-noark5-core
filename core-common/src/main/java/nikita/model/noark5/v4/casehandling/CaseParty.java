package nikita.model.noark5.v4.casehandling;

import nikita.model.noark5.v4.NoarkGeneralEntity;
import nikita.model.noark5.v4.interfaces.entities.ICasePartyEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

import static nikita.config.Constants.NOARK_CASE_HANDLING_PATH;
import static nikita.config.N5ResourceMappings.CASE_PARTY;

/**
 * Created by tsodring on 4/10/16.
 */

@Entity
@Table(name = "case_party")
// Enable soft delete of CaseParty
// @SQLDelete(sql="UPDATE case_party SET deleted = true WHERE pk_case_party_id = ? and version = ?")
// @Where(clause="deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_case_party_id"))
public class CaseParty extends NoarkGeneralEntity implements ICasePartyEntity {

    /**
     * M010 - sakspartID (xs:string)
     */
    @Column(name = "case_party_id")
    @Audited
    private String casePartyId;

    /**
     * M302 - sakspartNavn (xs:string)
     */
    @Column(name = "case_party_name")
    @Audited
    private String casePartyName;

    /**
     * M303 - sakspartRolle (xs:string)
     */
    @Column(name = "case_party_role")
    @Audited
    private String casePartyRole;

    /**
     * M406 - postadresse (xs:string)
     */
    @Column(name = "postal_address")
    @Audited
    private String postalAddress;

    /**
     * M407 - postnummer (xs:string)
     */
    @Column(name = "postal_code")
    @Audited
    private String postCode;

    /**
     * M408 - poststed (xs:string)
     */
    @Column(name = "postal_town")
    @Audited
    private String postalTown;

    /**
     * M409 - utenlandsadresse (xs:string)
     */
    @Column(name = "foreign_address")
    @Audited
    private String foreignAddress;

    /**
     * M410 - epostadresse (xs:string)
     */
    @Column(name = "email_address")
    @Audited
    private String emailAddress;

    /**
     * M411 - telefonnummer (xs:string)
     * TODO: This is a multi-value attributte, that needs to implemented properly
     */
    @Column(name = "telephone_number")
    @Audited
    private String telephoneNumber;

    /**
     * M412 - kontaktperson (xs:string)
     */
    @Column(name = "contact_person")
    @Audited
    private String contactPerson;

    // Links to CaseFiles
    @ManyToMany(mappedBy = "referenceCaseParty")
    private Set<CaseFile> referenceCaseFile = new TreeSet<>();

    public String getCasePartyId() {
        return casePartyId;
    }

    public void setCasePartyId(String casePartyId) {
        this.casePartyId = casePartyId;
    }

    public String getCasePartyName() {
        return casePartyName;
    }

    public void setCasePartyName(String casePartyName) {
        this.casePartyName = casePartyName;
    }

    public String getCasePartyRole() {
        return casePartyRole;
    }

    public void setCasePartyRole(String casePartyRole) {
        this.casePartyRole = casePartyRole;
    }

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
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

    public String getForeignAddress() {
        return foreignAddress;
    }

    public void setForeignAddress(String foreignAddress) {
        this.foreignAddress = foreignAddress;
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

    @Override
    public String getBaseTypeName() {
        return CASE_PARTY;
    }

    @Override
    public String getFunctionalTypeName() {
        return NOARK_CASE_HANDLING_PATH;
    }

    public Set<CaseFile> getReferenceCaseFile() {
        return referenceCaseFile;
    }

    public void setReferenceCaseFile(Set<CaseFile> referenceCaseFile) {
        this.referenceCaseFile = referenceCaseFile;
    }

    @Override
    public String toString() {
        return "CaseParty{" + super.toString() +
                ", casePartyId='" + casePartyId + '\'' +
                ", casePartyName='" + casePartyName + '\'' +
                ", casePartyRole='" + casePartyRole + '\'' +
                ", postalAddress='" + postalAddress + '\'' +
                ", postCode='" + postCode + '\'' +
                ", postalTown='" + postalTown + '\'' +
                ", foreignAddress='" + foreignAddress + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                '}';
    }
}
