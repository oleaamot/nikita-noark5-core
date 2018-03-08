package no.arkivlab.hioa.nikita.webapp.service.interfaces.metadata;

import nikita.model.noark5.v4.metadata.CorrespondencePartType;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by tsodring on 3/9/17.
 */
public interface ICorrespondencePartTypeService {

    CorrespondencePartType createNewCorrespondencePartType(CorrespondencePartType correspondencePartType);

    Iterable<CorrespondencePartType> findAll();

    List<CorrespondencePartType> findAllAsList();

    CorrespondencePartType findBySystemId(String systemId);

    CorrespondencePartType update(CorrespondencePartType correspondencePartType);

    List<CorrespondencePartType> findByDescription(String description);

    CorrespondencePartType findByCode(String code);

    void deleteEntity(@NotNull String correspondencePartTypeCode);
}
