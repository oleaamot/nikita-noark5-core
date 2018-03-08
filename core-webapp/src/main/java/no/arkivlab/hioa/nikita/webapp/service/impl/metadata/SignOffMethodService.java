package no.arkivlab.hioa.nikita.webapp.service.impl.metadata;

import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.model.noark5.v4.metadata.SignOffMethod;
import nikita.repository.n5v4.metadata.ISignOffMethodRepository;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.metadata.IMetadataHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.ISignOffMethodService;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityUpdatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.SIGN_OFF_METHOD;

/**
 * Created by tsodring on 13/02/18.
 */


@Service
@Transactional
public class SignOffMethodService
        implements ISignOffMethodService {

    private static final Logger logger =
            LoggerFactory.getLogger(SignOffMethodService.class);

    private ISignOffMethodRepository SignOffMethodRepository;
    private IMetadataHateoasHandler metadataHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public SignOffMethodService(ISignOffMethodRepository SignOffMethodRepository,
                                IMetadataHateoasHandler metadataHateoasHandler,
                                ApplicationEventPublisher applicationEventPublisher) {
        this.SignOffMethodRepository = SignOffMethodRepository;
        this.metadataHateoasHandler = metadataHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // All CREATE operations

    /**
     * Persists a new SignOffMethod object to the database.
     *
     * @param SignOffMethod SignOffMethod object with values set
     * @return the newly persisted SignOffMethod object wrapped as a
     * MetadataHateoas object
     */
    @Override
    public MetadataHateoas createNewSignOffMethod(
            SignOffMethod SignOffMethod) {

        SignOffMethod.setDeleted(false);
        SignOffMethod.setOwnedBy(
                SecurityContextHolder.getContext().
                        getAuthentication().getName());

        MetadataHateoas metadataHateoas = new MetadataHateoas(
                SignOffMethodRepository.save(SignOffMethod));
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // All READ operations

    /**
     * Retrieve a list of all SignOffMethod objects
     *
     * @return list of SignOffMethod objects wrapped as a MetadataHateoas object
     */
    @Override
    public MetadataHateoas findAll() {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        SignOffMethodRepository.findAll(), SIGN_OFF_METHOD);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // find by systemId

    /**
     * Retrieve a single SignOffMethod object identified by systemId
     *
     * @param systemId
     * @return single SignOffMethod object wrapped as a MetadataHateoas object
     */
    @Override
    public MetadataHateoas find(String systemId) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                SignOffMethodRepository.save(
                        SignOffMethodRepository.findBySystemId(systemId)));
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * Retrieve all SignOffMethod that have a given description.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param description
     * @return A list of SignOffMethod objects wrapped as a MetadataHateoas
     * object
     */
    @Override
    public MetadataHateoas findByDescription(String description) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        SignOffMethodRepository.findByDescription(description),
                SIGN_OFF_METHOD);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * retrieve all SignOffMethod that have a particular code.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param code
     * @return A list of SignOffMethod objects wrapped as a MetadataHateoas
     * object
     */
    @Override
    public MetadataHateoas findByCode(String code) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        SignOffMethodRepository.findByCode(code),
                SIGN_OFF_METHOD);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * Generate a default SignOffMethod object
     *
     * @return the SignOffMethod object wrapped as a SignOffMethodHateoas object
     */
    @Override
    public SignOffMethod generateDefaultSignOffMethod() {

        SignOffMethod SignOffMethod = new SignOffMethod();
        SignOffMethod.setCode(TEMPLATE_SIGN_OFF_METHOD_CODE);
        SignOffMethod.setDescription(TEMPLATE_SIGN_OFF_METHOD_DESCRIPTION);

        return SignOffMethod;
    }

    /**
     * Update a SignOffMethod identified by its systemId
     * <p>
     * Copy the values you are allowed to change, code and description
     *
     * @param SignOffMethod
     * @return the updated SignOffMethod
     */
    @Override
    public MetadataHateoas handleUpdate(String systemId, Long
            version, SignOffMethod SignOffMethod) {

        SignOffMethod existingSignOffMethod = getSignOffMethodOrThrow(systemId);

        // Copy all the values you are allowed to copy ....
        if (null != existingSignOffMethod.getCode()) {
            existingSignOffMethod.setCode(existingSignOffMethod.getCode());
        }
        if (null != existingSignOffMethod.getDescription()) {
            existingSignOffMethod.setDescription(existingSignOffMethod.
                    getDescription());
        }
        // Note this can potentially result in a NoarkConcurrencyException
        // exception
        existingSignOffMethod.setVersion(version);

        MetadataHateoas SignOffMethodHateoas = new MetadataHateoas(
                SignOffMethodRepository.save(existingSignOffMethod));
        metadataHateoasHandler.addLinks(SignOffMethodHateoas,
                new Authorisation());
        applicationEventPublisher.publishEvent(
                new AfterNoarkEntityUpdatedEvent(this,
                        existingSignOffMethod));
        return SignOffMethodHateoas;
    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid SignOffMethod object back. If there
     * is no SignOffMethod object, a NoarkEntityNotFoundException exception
     * is thrown
     *
     * @param systemId The systemId of the SignOffMethod object to retrieve
     * @return the SignOffMethod object
     */
    private SignOffMethod getSignOffMethodOrThrow(@NotNull String systemId) {
        SignOffMethod SignOffMethod = SignOffMethodRepository.findBySystemId
                (systemId);
        if (SignOffMethod == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " SignOffMethod, using " +
                    "systemId " + systemId;
            logger.error(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return SignOffMethod;
    }

}
