package nikita.model.noark5.v4.interfaces;

import nikita.model.noark5.v4.secondary.Comment;

import java.util.List;

/**
 * Created by tsodring on 12/7/16.
 */
public interface IComment {
    List<Comment> getReferenceComment();

    void setReferenceComment(List<Comment> comments);
}
