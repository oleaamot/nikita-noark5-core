package nikita.model.noark5.v4.interfaces.entities;

import java.io.Serializable;

/**
 * Created by tsodring on 1/16/17.
 */
public interface ICorrespondencePartEntity extends Serializable {


    String getCorrespondencePartType();

    void setCorrespondencePartType(String correspondencePartType);

    String getCorrespondencePartName();

    void setCorrespondencePartName(String correspondencePartName);

    String getPostalAddress();

    void setPostalAddress(String postalAddress);

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
