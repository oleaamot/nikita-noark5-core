package nikita.model.noark5.v4.interfaces;

import nikita.model.noark5.v4.DocumentFlow;

import java.util.Set;

/**
 *Created by tsodring on 12/7/16.
 */
public interface IDocumentFlow {
    void setReferenceDocumentFlow(Set<DocumentFlow> documentFlow);
    Set<DocumentFlow> getReferenceDocumentFlow();
}
