package nikita.model.noark5.v4.interfaces;

import nikita.model.noark5.v4.casehandling.DocumentFlow;

import java.util.List;

/**
 *Created by tsodring on 12/7/16.
 */
public interface IDocumentFlow {
    List<DocumentFlow> getReferenceDocumentFlow();

    void setReferenceDocumentFlow(List<DocumentFlow> documentFlow);
}
