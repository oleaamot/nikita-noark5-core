package nikita.model.noark5.v4.interfaces;

import nikita.model.noark5.v4.Author;

import java.util.Set;

/**
 * Created by tsodring on 12/7/16.
 */
public interface IAuthor {
    Set<Author> getReferenceAuthor();
    void setReferenceAuthor(Set<Author> authors);
}
