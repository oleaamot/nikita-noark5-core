package no.arkivlab.hioa.nikita.webapp.model.application;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import no.arkivlab.hioa.nikita.webapp.util.serialisers.APIDetailsSerializer;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;

@JsonSerialize(using = APIDetailsSerializer.class)
public class AdministrationDetails extends APIDetails {

    public AdministrationDetails() {
        super();
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();

        // Add support for AdministrativeUnit
        aPIDetails.add(new APIDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_ADMINISTRATION_PATH + SLASH + ADMINISTRATIVE_UNIT,
                NOARK_CONFORMANCE_REL + NOARK_ADMINISTRATION_PATH + SLASH + ADMINISTRATIVE_UNIT + SLASH,
                true
        ));

        // Add support for new AdministrativeUnit
        aPIDetails.add(new APIDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_ADMINISTRATION_PATH + SLASH + NEW_ADMINISTRATIVE_UNIT,
                NIKITA_CONFORMANCE_REL + NOARK_ADMINISTRATION_PATH + SLASH + NEW_ADMINISTRATIVE_UNIT + SLASH,
                true
        ));

        // Add support for User
        aPIDetails.add(new APIDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_ADMINISTRATION_PATH + SLASH + USER,
                NIKITA_CONFORMANCE_REL + NOARK_ADMINISTRATION_PATH + SLASH + USER + SLASH,
                true
        ));

        // Add support for new User
        aPIDetails.add(new APIDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_ADMINISTRATION_PATH + SLASH + NEW_USER,
                NIKITA_CONFORMANCE_REL + NOARK_ADMINISTRATION_PATH + SLASH + NEW_USER + SLASH,
                true
        ));

        // Add support for Right
        aPIDetails.add(new APIDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_ADMINISTRATION_PATH + SLASH + RIGHT,
                NIKITA_CONFORMANCE_REL + NOARK_ADMINISTRATION_PATH + SLASH + RIGHT + SLASH,
                true
        ));

        // Add support for new Right
        aPIDetails.add(new APIDetail(
                uri + SLASH + HATEOAS_API_PATH + SLASH + NOARK_ADMINISTRATION_PATH + SLASH + NEW_RIGHT,
                NIKITA_CONFORMANCE_REL + NOARK_ADMINISTRATION_PATH + SLASH + NEW_RIGHT + SLASH,
                true
        ));
    }
}
