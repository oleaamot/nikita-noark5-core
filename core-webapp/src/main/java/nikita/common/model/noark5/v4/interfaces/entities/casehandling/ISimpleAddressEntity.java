package nikita.common.model.noark5.v4.interfaces.entities.casehandling;

import nikita.common.model.noark5.v4.casehandling.secondary.PostalNumber;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;

/**
 * Created by tsodring on 5/22/17.
 */
public interface ISimpleAddressEntity extends INikitaEntity {

    String getAddressType();

    void setAddressType(String addressType);

    String getAddressLine1();

    void setAddressLine1(String addressLine1);

    String getAddressLine2();

    void setAddressLine2(String addressLine2);

    String getAddressLine3();

    void setAddressLine3(String addressLine3);

    PostalNumber getPostalNumber();

    void setPostalNumber(PostalNumber postalNumber);

    String getPostalTown();

    void setPostalTown(String postalTown);

    String getCountryCode();

    void setCountryCode(String countryCode);

}
