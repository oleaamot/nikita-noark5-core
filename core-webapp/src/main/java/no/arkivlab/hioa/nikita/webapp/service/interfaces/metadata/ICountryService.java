package no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata;

import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.metadata.Country;

/**
 * Created by tsodring on 14/03/18.
 */

public interface ICountryService {

    MetadataHateoas createNewCountry(Country country);

    MetadataHateoas find(String systemId);

    MetadataHateoas findAll();

    MetadataHateoas findByDescription(String description);

    MetadataHateoas findByCode(String code);

    MetadataHateoas handleUpdate(String systemId, Long version, Country
            country);

    Country generateDefaultCountry();
}
