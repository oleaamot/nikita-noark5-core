package no.arkivlab.hioa.nikita.webapp.service.interfaces;

import nikita.model.noark5.v4.BasicRecord;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IBasicRecordService {
    List<BasicRecord> findBasicRecordByOwnerPaginated(Integer top, Integer skip);

    BasicRecord findBySystemIdOrderBySystemId(String basicRecordSystemId);

    // All UPDATE operations
    BasicRecord handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull BasicRecord incomingBasicRecord);

    // All DELETE operations
    void deleteEntity(@NotNull String systemId);
}
