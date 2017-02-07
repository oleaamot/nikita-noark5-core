package no.arkivlab.hioa.nikita.webapp.service.interfaces;

import nikita.model.noark5.v4.BasicRecord;

import java.util.List;

public interface IBasicRecordService extends IRecordService {

    List<BasicRecord> findBasicRecordByOwnerPaginated(Integer top, Integer skip);
}