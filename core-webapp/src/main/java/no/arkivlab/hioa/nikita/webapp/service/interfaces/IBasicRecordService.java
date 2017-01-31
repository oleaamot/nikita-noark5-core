package no.arkivlab.hioa.nikita.webapp.service.interfaces;

import nikita.model.noark5.v4.BasicRecord;

public interface IBasicRecordService extends IRecordService {

    Iterable<BasicRecord> findBasicRecordByOwnerPaginated(Integer top, Integer skip);
}