package no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata;

import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.metadata.VariantFormat;

/**
 * Created by tsodring on 12/03/18.
 */

public interface IVariantFormatService {

    MetadataHateoas createNewVariantFormat(VariantFormat variantFormat);

    MetadataHateoas find(String systemId);

    MetadataHateoas findAll();

    MetadataHateoas findByDescription(String description);

    MetadataHateoas findByCode(String code);

    MetadataHateoas handleUpdate(String systemId, Long version, VariantFormat
            variantFormat);

    VariantFormat generateDefaultVariantFormat();
}
