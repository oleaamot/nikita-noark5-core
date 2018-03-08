package no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata;

import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.metadata.CommentType;

/**
 * Created by tsodring on 04/03/18.
 */

public interface ICommentTypeService {

    MetadataHateoas createNewCommentType(CommentType commentType);

    MetadataHateoas find(String systemId);

    MetadataHateoas findAll();

    MetadataHateoas findByDescription(String description);

    MetadataHateoas findByCode(String code);

    MetadataHateoas handleUpdate(String systemId, Long version, CommentType
            commentType);

    CommentType generateDefaultCommentType();
}
