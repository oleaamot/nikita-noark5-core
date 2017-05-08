package nikita.model.noark5.v4.interfaces.entities;

import nikita.model.noark5.v4.secondary.PostalAddress;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by tsodring on 1/16/17.
 */
public interface ICorrespondencePartEntity extends INikitaEntity, Serializable {


    String getCorrespondencePartType();

    void setCorrespondencePartType(String correspondencePartType);

    String getCorrespondencePartName();

    void setCorrespondencePartName(String correspondencePartName);

    Set<PostalAddress> getPostalAddress();

    void setPostalAddress(Set<PostalAddress> postalAddress);

    void addPostalAddress(String postalAddress);

    String getPostCode();

    void setPostCode(String postCode);

    String getPostalTown();

    void setPostalTown(String postalTown);

    String getCountry();

    void setCountry(String country);

    String getEmailAddress();

    void setEmailAddress(String emailAddress);

    String getTelephoneNumber();

    void setTelephoneNumber(String telephoneNumber);

    String getContactPerson();

    void setContactPerson(String contactPerson);

    String getAdministrativeUnit();

    void setAdministrativeUnit(String administrativeUnit);

    String getCaseHandler();

    void setCaseHandler(String caseHandler);
}
