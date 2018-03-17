package nikita.webapp.service.impl.metadata;

import nikita.common.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.common.model.noark5.v4.metadata.PostalCode;
import nikita.common.repository.n5v4.metadata.IPostalCodeRepository;
import nikita.common.util.exceptions.NoarkEntityNotFoundException;
import nikita.webapp.hateoas.interfaces.metadata.IMetadataHateoasHandler;
import nikita.webapp.security.Authorisation;
import nikita.webapp.service.interfaces.metadata.IPostalCodeService;
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
import static nikita.common.config.N5ResourceMappings.POST_CODE;

/**
 * Created by tsodring on 14/03/18.
 */

@Service
@Transactional
@SuppressWarnings("unchecked")
public class PostalCodeService
        implements IPostalCodeService {

    private static final Logger logger =
            LoggerFactory.getLogger(PostalCodeService.class);

    private IPostalCodeRepository postalCodeRepository;
    private IMetadataHateoasHandler metadataHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public PostalCodeService(
            IPostalCodeRepository
                    postalCodeRepository,
            IMetadataHateoasHandler metadataHateoasHandler,
            ApplicationEventPublisher applicationEventPublisher) {

        this.postalCodeRepository =
                postalCodeRepository;
        this.metadataHateoasHandler = metadataHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // All CREATE operations

    /**
     * Persists a new PostalCode object to the database.
     *
     * @param postalCode PostalCode object with values set
     * @return the newly persisted PostalCode object wrapped as a
     * MetadataHateoas object
     */
    @Override
    public MetadataHateoas createNewPostalCode(
            PostalCode postalCode) {

        postalCode.setDeleted(false);
        postalCode.setOwnedBy(SecurityContextHolder.getContext().
                getAuthentication().getName());

        MetadataHateoas metadataHateoas = new MetadataHateoas(
                postalCodeRepository.save(postalCode));
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // All READ operations

    /**
     * Retrieve a list of all PostalCode objects
     *
     * @return list of PostalCode objects wrapped as a
     * MetadataHateoas object
     */
    @Override
    public MetadataHateoas findAll() {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        postalCodeRepository.findAll(), POST_CODE);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // find by systemId

    /**
     * Retrieve a single PostalCode object identified by systemId
     *
     * @param systemId systemId of the PostalCode you wish to retrieve
     * @return single PostalCode object wrapped as a MetadataHateoas object
     */
    @Override
    public MetadataHateoas find(String systemId) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                postalCodeRepository
                        .findBySystemId(systemId));
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * Retrieve all PostalCode that have a given description.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param description Description of object you wish to retrieve. The
     *                    whole text, this is an exact search.
     * @return A list of PostalCode objects wrapped as a MetadataHateoas
     * object
     */
    @Override
    public MetadataHateoas findByDescription(String description) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        postalCodeRepository
                                .findByDescription(description), POST_CODE);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * retrieve all PostalCode that have a particular code.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param code The code of the object you wish to retrieve
     * @return A list of PostalCode objects wrapped as a MetadataHateoas
     * object
     */
    @Override
    public MetadataHateoas findByCode(String code) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        postalCodeRepository.findByCode(code), POST_CODE);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * Generate a default PostalCode object
     *
     * @return the PostalCode object wrapped as a PostalCodeHateoas object
     */
    @Override
    public PostalCode generateDefaultPostalCode() {

        PostalCode postalCode = new PostalCode();
        postalCode.setCode(TEMPLATE_POST_CODE_CODE);
        postalCode.setDescription(TEMPLATE_POST_CODE_DESCRIPTION);

        return postalCode;
    }

    /**
     * Update a PostalCode identified by its systemId
     * <p>
     * Copy the values you are allowed to change, code and description
     *
     * @param systemId   The systemId of the postalCode object you wish to
     *                   update
     * @param postalCode The updated postalCode object. Note the values
     *                   you are allowed to change are copied from this
     *                   object. This object is not persisted.
     * @return the updated postalCode
     */
    @Override
    public MetadataHateoas handleUpdate(String systemId, Long
            version, PostalCode postalCode) {

        PostalCode existingPostalCode = getPostalCodeOrThrow(systemId);

        // Copy all the values you are allowed to copy ....
        if (null != existingPostalCode.getCode()) {
            existingPostalCode.setCode(existingPostalCode.getCode());
        }
        if (null != existingPostalCode.getDescription()) {
            existingPostalCode.setDescription(
                    existingPostalCode.getDescription());
        }
        // Note this can potentially result in a NoarkConcurrencyException
        // exception
        existingPostalCode.setVersion(version);

        MetadataHateoas postalCodeHateoas = new MetadataHateoas(
                postalCodeRepository.save(existingPostalCode));

        metadataHateoasHandler.addLinks(postalCodeHateoas,
                new Authorisation());

        applicationEventPublisher.publishEvent(new
                AfterNoarkEntityUpdatedEvent(this,
                existingPostalCode));
        return postalCodeHateoas;
    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid PostalCode object back. If there
     * is no PostalCode object, a NoarkEntityNotFoundException exception
     * is thrown
     *
     * @param systemId The systemId of the PostalCode object to retrieve
     * @return the PostalCode object
     */
    private PostalCode getPostalCodeOrThrow(@NotNull String systemId) {
        PostalCode postalCode = postalCodeRepository.
                findBySystemId(systemId);
        if (postalCode == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " PostalCode, using " +
                    "systemId " + systemId;
            logger.error(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return postalCode;
    }
}
