package nikita.webapp.service.impl.metadata;

import nikita.common.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.model.noark5.v4.metadata.VariantFormat;
import nikita.common.repository.n5v4.metadata.IVariantFormatRepository;
import nikita.common.util.exceptions.NoarkEntityNotFoundException;
import nikita.webapp.hateoas.interfaces.metadata.IMetadataHateoasHandler;
import nikita.webapp.security.Authorisation;
import nikita.webapp.service.interfaces.metadata.IVariantFormatService;
import nikita.webapp.web.events.AfterNoarkEntityUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

import static nikita.common.config.Constants.*;
import static nikita.common.config.N5ResourceMappings.VARIANT_FORMAT;

/**
 * Created by tsodring on 12/03/18.
 */

@Service
@Transactional
@SuppressWarnings("unchecked")
public class VariantFormatService
        implements IVariantFormatService {

    private static final Logger logger =
            LoggerFactory.getLogger(VariantFormatService.class);

    private IVariantFormatRepository variantFormatRepository;
    private IMetadataHateoasHandler metadataHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public VariantFormatService(
            IVariantFormatRepository
                    variantFormatRepository,
            IMetadataHateoasHandler metadataHateoasHandler,
            ApplicationEventPublisher applicationEventPublisher) {

        this.variantFormatRepository =
                variantFormatRepository;
        this.metadataHateoasHandler = metadataHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // All CREATE operations

    /**
     * Persists a new VariantFormat object to the database.
     *
     * @param variantFormat VariantFormat object with values set
     * @return the newly persisted VariantFormat object wrapped as a
     * MetadataHateoas object
     */
    @Override
    public MetadataHateoas createNewVariantFormat(
            VariantFormat variantFormat) {

        variantFormat.setDeleted(false);
        variantFormat.setOwnedBy(SecurityContextHolder.getContext().
                getAuthentication().getName());

        MetadataHateoas metadataHateoas = new MetadataHateoas(
                variantFormatRepository.save(variantFormat));
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // All READ operations

    /**
     * Retrieve a list of all VariantFormat objects
     *
     * @return list of VariantFormat objects wrapped as a
     * MetadataHateoas object
     */
    @Override
    public MetadataHateoas findAll() {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        variantFormatRepository.findAll(), VARIANT_FORMAT);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // find by systemId

    /**
     * Retrieve a single VariantFormat object identified by systemId
     *
     * @param systemId systemId of the VariantFormat you wish to retrieve
     * @return single VariantFormat object wrapped as a MetadataHateoas object
     */
    @Override
    public MetadataHateoas find(String systemId) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                variantFormatRepository
                        .findBySystemId(systemId));
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * Retrieve all VariantFormat that have a given description.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param description Description of object you wish to retrieve. The
     *                    whole text, this is an exact search.
     * @return A list of VariantFormat objects wrapped as a MetadataHateoas
     * object
     */
    @Override
    public MetadataHateoas findByDescription(String description) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        variantFormatRepository
                                .findByDescription(description), VARIANT_FORMAT);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * retrieve all VariantFormat that have a particular code.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param code The code of the object you wish to retrieve
     * @return A list of VariantFormat objects wrapped as a MetadataHateoas
     * object
     */
    @Override
    public MetadataHateoas findByCode(String code) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        variantFormatRepository.findByCode(code), VARIANT_FORMAT);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * Generate a default VariantFormat object
     *
     * @return the VariantFormat object wrapped as a VariantFormatHateoas object
     */
    @Override
    public VariantFormat generateDefaultVariantFormat() {

        VariantFormat variantFormat = new VariantFormat();
        variantFormat.setCode(TEMPLATE_VARIANT_FORMAT_CODE);
        variantFormat.setDescription(TEMPLATE_VARIANT_FORMAT_DESCRIPTION);

        return variantFormat;
    }

    /**
     * Update a VariantFormat identified by its systemId
     * <p>
     * Copy the values you are allowed to change, code and description
     *
     * @param systemId      The systemId of the variantFormat object you wish to
     *                      update
     * @param variantFormat The updated variantFormat object. Note the values
     *                      you are allowed to change are copied from this
     *                      object. This object is not persisted.
     * @return the updated variantFormat
     */
    @Override
    public MetadataHateoas handleUpdate(String systemId, Long
            version, VariantFormat variantFormat) {

        VariantFormat existingVariantFormat = getVariantFormatOrThrow(systemId);

        // Copy all the values you are allowed to copy ....
        if (null != existingVariantFormat.getCode()) {
            existingVariantFormat.setCode(existingVariantFormat.getCode());
        }
        if (null != existingVariantFormat.getDescription()) {
            existingVariantFormat.setDescription(
                    existingVariantFormat.getDescription());
        }
        // Note this can potentially result in a NoarkConcurrencyException
        // exception
        existingVariantFormat.setVersion(version);

        MetadataHateoas variantFormatHateoas = new MetadataHateoas(
                variantFormatRepository.save(existingVariantFormat));

        metadataHateoasHandler.addLinks(variantFormatHateoas,
                new Authorisation());

        applicationEventPublisher.publishEvent(new
                AfterNoarkEntityUpdatedEvent(this,
                existingVariantFormat));
        return variantFormatHateoas;
    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid VariantFormat object back. If there
     * is no VariantFormat object, a NoarkEntityNotFoundException exception
     * is thrown
     *
     * @param systemId The systemId of the VariantFormat object to retrieve
     * @return the VariantFormat object
     */
    private VariantFormat getVariantFormatOrThrow(@NotNull String systemId) {
        VariantFormat variantFormat = variantFormatRepository.
                findBySystemId(systemId);
        if (variantFormat == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " VariantFormat, using " +
                    "systemId " + systemId;
            logger.error(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return variantFormat;
    }
}
