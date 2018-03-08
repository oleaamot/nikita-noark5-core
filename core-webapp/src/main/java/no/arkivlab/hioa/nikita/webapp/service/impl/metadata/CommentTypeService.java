package no.arkivlab.hioa.nikita.webapp.service.impl.metadata;

import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.model.noark5.v4.metadata.CommentType;
import nikita.repository.n5v4.metadata.ICommentTypeRepository;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.metadata.IMetadataHateoasHandler;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata.ICommentTypeService;
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
import static nikita.config.N5ResourceMappings.COMMENT_TYPE;

/**
 * Created by tsodring on 04/03/18.
 */

@Service
@Transactional
@SuppressWarnings("unchecked")
public class CommentTypeService
        implements ICommentTypeService {

    private static final Logger logger =
            LoggerFactory.getLogger(CommentTypeService.class);

    private ICommentTypeRepository commentTypeRepository;
    private IMetadataHateoasHandler metadataHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public CommentTypeService(
            ICommentTypeRepository
                    commentTypeRepository,
            IMetadataHateoasHandler metadataHateoasHandler,
            ApplicationEventPublisher applicationEventPublisher) {

        this.commentTypeRepository =
                commentTypeRepository;
        this.metadataHateoasHandler = metadataHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // All CREATE operations

    /**
     * Persists a new CommentType object to the database.
     *
     * @param commentType CommentType object with values set
     * @return the newly persisted CommentType object wrapped as a
     * MetadataHateoas object
     */
    @Override
    public MetadataHateoas createNewCommentType(
            CommentType commentType) {

        commentType.setDeleted(false);
        commentType.setOwnedBy(SecurityContextHolder.getContext().
                getAuthentication().getName());

        MetadataHateoas metadataHateoas = new MetadataHateoas(
                commentTypeRepository.save(commentType));
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // All READ operations

    /**
     * Retrieve a list of all CommentType objects
     *
     * @return list of CommentType objects wrapped as a
     * MetadataHateoas object
     */
    @Override
    public MetadataHateoas findAll() {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        commentTypeRepository.findAll(), COMMENT_TYPE);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    // find by systemId

    /**
     * Retrieve a single CommentType object identified by systemId
     *
     * @param systemId systemId of the CommentType you wish to retrieve
     * @return single CommentType object wrapped as a MetadataHateoas object
     */
    @Override
    public MetadataHateoas find(String systemId) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                commentTypeRepository
                        .findBySystemId(systemId));
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * Retrieve all CommentType that have a given description.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param description Description of object you wish to retrieve. The
     *                    whole text, this is an exact search.
     * @return A list of CommentType objects wrapped as a MetadataHateoas
     * object
     */
    @Override
    public MetadataHateoas findByDescription(String description) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        commentTypeRepository
                                .findByDescription(description), COMMENT_TYPE);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * retrieve all CommentType that have a particular code.
     * <br>
     * Note, this will be replaced by OData search.
     *
     * @param code The code of the object you wish to retrieve
     * @return A list of CommentType objects wrapped as a MetadataHateoas
     * object
     */
    @Override
    public MetadataHateoas findByCode(String code) {
        MetadataHateoas metadataHateoas = new MetadataHateoas(
                (List<INikitaEntity>) (List)
                        commentTypeRepository.findByCode(code), COMMENT_TYPE);
        metadataHateoasHandler.addLinks(metadataHateoas, new Authorisation());
        return metadataHateoas;
    }

    /**
     * Generate a default CommentType object
     *
     * @return the CommentType object wrapped as a CommentTypeHateoas object
     */
    @Override
    public CommentType generateDefaultCommentType() {

        CommentType commentType = new CommentType();
        commentType.setCode(TEMPLATE_COMMENT_TYPE_CODE);
        commentType.setDescription(TEMPLATE_COMMENT_TYPE_DESCRIPTION);

        return commentType;
    }

    /**
     * Update a CommentType identified by its systemId
     * <p>
     * Copy the values you are allowed to change, code and description
     *
     * @param systemId    The systemId of the commentType object you wish to
     *                    update
     * @param commentType The updated commentType object. Note the values
     *                    you are allowed to change are copied from this
     *                    object. This object is not persisted.
     * @return the updated commentType
     */
    @Override
    public MetadataHateoas handleUpdate(String systemId, Long
            version, CommentType commentType) {

        CommentType existingCommentType = getCommentTypeOrThrow(systemId);

        // Copy all the values you are allowed to copy ....
        if (null != existingCommentType.getCode()) {
            existingCommentType.setCode(existingCommentType.getCode());
        }
        if (null != existingCommentType.getDescription()) {
            existingCommentType.setDescription(
                    existingCommentType.getDescription());
        }
        // Note this can potentially result in a NoarkConcurrencyException
        // exception
        existingCommentType.setVersion(version);

        MetadataHateoas commentTypeHateoas = new MetadataHateoas(
                commentTypeRepository.save(existingCommentType));

        metadataHateoasHandler.addLinks(commentTypeHateoas,
                new Authorisation());

        applicationEventPublisher.publishEvent(new
                AfterNoarkEntityUpdatedEvent(this,
                existingCommentType));
        return commentTypeHateoas;
    }

    /**
     * Internal helper method. Rather than having a find and try catch in
     * multiple methods, we have it here once. If you call this, be aware
     * that you will only ever get a valid CommentType object back. If there
     * is no CommentType object, a NoarkEntityNotFoundException exception
     * is thrown
     *
     * @param systemId The systemId of the CommentType object to retrieve
     * @return the CommentType object
     */
    private CommentType getCommentTypeOrThrow(@NotNull String systemId) {
        CommentType commentType = commentTypeRepository.findBySystemId(systemId);
        if (commentType == null) {
            String info = INFO_CANNOT_FIND_OBJECT + " CommentType, using " +
                    "systemId " + systemId;
            logger.error(info);
            throw new NoarkEntityNotFoundException(info);
        }
        return commentType;
    }
}
