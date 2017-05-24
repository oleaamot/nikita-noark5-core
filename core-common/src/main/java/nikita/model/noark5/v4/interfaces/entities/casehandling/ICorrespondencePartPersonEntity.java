package nikita.model.noark5.v4.interfaces.entities.casehandling;

import nikita.model.noark5.v4.casehandling.secondary.ContactInformation;
import nikita.model.noark5.v4.casehandling.secondary.PostalAddress;
import nikita.model.noark5.v4.casehandling.secondary.ResidingAddress;

/**
 * Created by tsodring on 5/22/17.
 */
public interface ICorrespondencePartPersonEntity extends ICorrespondencePartEntity {

    String getSocialSecurityNumber();

    void setSocialSecurityNumber(String socialSecurityNumber);

    String getdNumber();

    void setdNumber(String dNumber);

    String getName();

    void setName(String name);

    PostalAddress getPostalAddress();

    void setPostalAddress(PostalAddress postalAddress);

    ResidingAddress getResidingAddress();

    void setResidingAddress(ResidingAddress residingAddress);

    ContactInformation getContactInformation();

    void setContactInformation(ContactInformation contactInformation);
}
