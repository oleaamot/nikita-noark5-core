package nikita.webapp.service.interfaces.metadata;

import nikita.common.model.noark5.v4.hateoas.metadata.MetadataHateoas;
import nikita.common.model.noark5.v4.metadata.FlowStatus;

/**
 * Created by tsodring on 17/02/18.
 */

public interface IFlowStatusService {

    MetadataHateoas createNewFlowStatus(FlowStatus FlowStatus);

    MetadataHateoas find(String systemId);

    MetadataHateoas findAll();

    MetadataHateoas findByDescription(String description);

    MetadataHateoas findByCode(String code);

    MetadataHateoas handleUpdate(String systemId, Long version, FlowStatus FlowStatus);

    FlowStatus generateDefaultFlowStatus();
}
