package nikita.model.noark5.v4;

import nikita.model.noark5.v4.interfaces.entities.ICasePartyEntity;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static nikita.config.N5ResourceMappings.CASE_PARTY;

/**
 * Created by tsodring on 4/10/16.
 */

@Entity
@Table(name = "case_party")
// Enable soft delete of CaseParty
@SQLDelete(sql="UPDATE case_party SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class CaseParty implements ICasePartyEntity, INoarkSystemIdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_case_party_id", nullable = false, insertable = true, updatable = false)
    protected long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique = true)
    @Audited
    protected String systemId;

    /**
     * M010 - sakspartID (xs:string)
     */
    @Column(name = "case_party_id")
    @Audited
    protected String casePartyId;

    /**
     * M302 - sakspartNavn (xs:string)
     */
    @Column(name = "case_party_name")
    @Audited
    protected String casePartyName;

    /**
     * M303 - sakspartRolle (xs:string)
     */
    @Column(name = "case_party_role")
    @Audited
    protected String casePartyRole;

    /**
     * M406 - postadresse (xs:string)
     */
    @Column(name = "postal_address")
    @Audited
    protected String postalAddress;


    /**
     * M407 - postnummer (xs:string)
     */
    @Column(name = "postal_code")
    @Audited
    protected String postCode;

    /**
     * M408 - poststed (xs:string)
     */
    @Column(name = "postal_town")
    @Audited
    protected String postalTown;

    /**
     * M409 - utenlandsadresse (xs:string)
     */
    @Column(name = "foreign_address")
    @Audited
    protected String foreignAddress;

    /**
     * M410 - epostadresse (xs:string)
     */
    @Column(name = "email_address")
    @Audited
    protected String emailAddress;

    /**
     * M411 - telefonnummer (xs:string)
     * TODO: This is a multi-value attributte, that needs to implemented properly
     */
    @Column(name = "telephone_number")
    @Audited
    protected String telephoneNumber;

    /**
     * M412 - kontaktperson (xs:string)
     */
    @Column(name = "contact_person")
    @Audited
    protected String contactPerson;
    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;
    @Version
    @Column(name = "version")
    protected Long version;
    // Links to CaseFiles
    @ManyToMany(mappedBy = "referenceCaseParty")
    protected Set<CaseFile> referenceCaseFile = new HashSet<CaseFile>();
    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String getBaseTypeName() {
        return CASE_PARTY;
    }

    public Set<CaseFile> getReferenceCaseFile() {
        return referenceCaseFile;
    }

    public void setReferenceCaseFile(Set<CaseFile> referenceCaseFile) {
        this.referenceCaseFile = referenceCaseFile;
    }

    @Override
    public String toString() {
        return "CaseParty{" +
                "id=" + id +
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
                ", version='" + version + '\'' +
                '}';
    }
}