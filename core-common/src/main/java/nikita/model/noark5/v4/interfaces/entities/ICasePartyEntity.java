package nikita.model.noark5.v4.interfaces.entities;

import java.io.Serializable;

/**
 * Created by tsodring on 1/16/17.
 */
 public interface ICasePartyEntity extends Serializable {

     String getCasePartyId();
     void setCasePartyId(String casePartyId);
     void setCasePartyName(String casePartyName);
     String getCasePartyRole();
     void setCasePartyRole(String casePartyRole);
     String getPostalAddress();
     void setPostalAddress(String postalAddress);
     String getPostCode();
     void setPostCode(String postCode);
     String getPostalTown();
     void setPostalTown(String postalTown);
     String getForeignAddress();
     void setForeignAddress(String foreignAddress);
     String getEmailAddress();
     void setEmailAddress(String emailAddress);
     String getTelephoneNumber();
     void setTelephoneNumber(String telephoneNumber);
     String getContactPerson();
     void setContactPerson(String contactPerson);

}
