package nikita.common.model.noark5.v4.interfaces;

import nikita.common.model.noark5.v4.secondary.Author;

import java.util.List;

/**
 * Created by tsodring on 12/7/16.
 */
public interface IAuthor {
    List<Author> getReferenceAuthor();

    void setReferenceAuthor(List<Author> authors);
}
