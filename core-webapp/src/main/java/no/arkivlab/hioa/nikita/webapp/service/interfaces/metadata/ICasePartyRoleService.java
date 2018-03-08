package no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata;

import nikita.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.model.noark5.v4.metadata.CasePartyRole;

/**
 * Created by tsodring on 21/02/18.
 */

public interface ICasePartyRoleService {

    MetadataHateoas createNewCasePartyRole(CasePartyRole casePartyRole);

    MetadataHateoas find(String systemId);

    MetadataHateoas findAll();

    MetadataHateoas findByDescription(String description);

    MetadataHateoas findByCode(String code);

    MetadataHateoas handleUpdate(String systemId, Long version, CasePartyRole
            casePartyRole);

    CasePartyRole generateDefaultCasePartyRole();
}
