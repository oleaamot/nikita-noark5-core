package no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata;

import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.metadata.CaseStatus;

/**
 * Created by tsodring on 13/03/18.
 */

public interface ICaseStatusService {

    MetadataHateoas createNewCaseStatus(CaseStatus caseStatus);

    MetadataHateoas find(String systemId);

    MetadataHateoas findAll();

    MetadataHateoas findByDescription(String description);

    MetadataHateoas findByCode(String code);

    MetadataHateoas handleUpdate(String systemId, Long version, CaseStatus
            caseStatus);

    CaseStatus generateDefaultCaseStatus();
}
