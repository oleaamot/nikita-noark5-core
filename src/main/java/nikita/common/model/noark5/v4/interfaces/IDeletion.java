package nikita.common.model.noark5.v4.interfaces;

import nikita.common.model.noark5.v4.secondary.Deletion;

/**
 * Created by tsodring on 12/7/16.
 */
public interface IDeletion {
    Deletion getReferenceDeletion();

    void setReferenceDeletion(Deletion deletion);
}
