package no.arkivlab.hioa.nikita.webapp.service.impl;

import no.arkivlab.hioa.nikita.webapp.service.interfaces.IBasicRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BasicRecordService extends RecordService implements IBasicRecordService {

    private static final Logger logger = LoggerFactory.getLogger(BasicRecordService.class);

}
