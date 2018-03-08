package nikita.model.noark5.v4.interfaces.entities.casehandling;

import nikita.model.noark5.v4.casehandling.RegistryEntry;
import nikita.model.noark5.v4.casehandling.secondary.BusinessAddress;
import nikita.model.noark5.v4.casehandling.secondary.ContactInformation;
import nikita.model.noark5.v4.casehandling.secondary.PostalAddress;

import java.util.List;

/**
 * Created by tsodring on 5/22/17.
 */
public interface ICorrespondencePartUnitEntity extends ICorrespondencePartEntity {

    String getOrganisationNumber();

    void setOrganisationNumber(String organisationNumber);

    String getName();

    void setName(String name);

    ContactInformation getContactInformation();

    void setContactInformation(ContactInformation contactInformation);

    BusinessAddress getBusinessAddress();

    void setBusinessAddress(BusinessAddress businessAddress);

    PostalAddress getPostalAddress();

    void setPostalAddress(PostalAddress postalAddress);

    String getContactPerson();

    void setContactPerson(String contactPerson);

    List<RegistryEntry> getReferenceRegistryEntry();

    void setReferenceRegistryEntry(List<RegistryEntry> referenceRegistryEntry);

}
