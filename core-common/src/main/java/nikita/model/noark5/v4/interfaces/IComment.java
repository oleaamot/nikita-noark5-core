package nikita.model.noark5.v4.interfaces;

import nikita.model.noark5.v4.Comment;

import java.util.Set;

/**
 * Created by tsodring on 12/7/16.
 */
public interface IComment {
    Set<Comment> getReferenceComment();

    void setReferenceComment(Set<Comment> comments);
}
