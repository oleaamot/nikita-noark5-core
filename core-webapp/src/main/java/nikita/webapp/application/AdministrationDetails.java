package nikita.webapp.model.application;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import nikita.webapp.util.serialisers.APIDetailsSerializer;

import static nikita.common.config.Constants.*;
import static nikita.common.config.N5ResourceMappings.*;

@JsonSerialize(using = APIDetailsSerializer.class)
public class AdministrationDetails extends APIDetails {

    public AdministrationDetails(String publicUrlPath) {
        super();
        // Add support for AdministrativeUnit
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH + NOARK_ADMINISTRATION_PATH + SLASH + ADMINISTRATIVE_UNIT,
                NOARK_CONFORMANCE_REL + NOARK_ADMINISTRATION_PATH + SLASH + ADMINISTRATIVE_UNIT + SLASH,
                true
        ));

        // Add support for new AdministrativeUnit
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH + NOARK_ADMINISTRATION_PATH + SLASH + NEW_ADMINISTRATIVE_UNIT,
                NIKITA_CONFORMANCE_REL + NOARK_ADMINISTRATION_PATH + SLASH + NEW_ADMINISTRATIVE_UNIT + SLASH,
                true
        ));

        // Add support for User
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH + NOARK_ADMINISTRATION_PATH + SLASH + USER,
                NIKITA_CONFORMANCE_REL + NOARK_ADMINISTRATION_PATH + SLASH + USER + SLASH,
                true
        ));

        // Add support for new User
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH + NOARK_ADMINISTRATION_PATH + SLASH + NEW_USER,
                NIKITA_CONFORMANCE_REL + NOARK_ADMINISTRATION_PATH + SLASH + NEW_USER + SLASH,
                true
        ));

        // Add support for Right
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH + NOARK_ADMINISTRATION_PATH + SLASH + RIGHT,
                NIKITA_CONFORMANCE_REL + NOARK_ADMINISTRATION_PATH + SLASH + RIGHT + SLASH,
                true
        ));

        // Add support for new Right
        aPIDetails.add(new APIDetail(
                publicUrlPath + SLASH + HATEOAS_API_PATH + SLASH + NOARK_ADMINISTRATION_PATH + SLASH + NEW_RIGHT,
                NIKITA_CONFORMANCE_REL + NOARK_ADMINISTRATION_PATH + SLASH + NEW_RIGHT + SLASH,
                true
        ));
    }
}
