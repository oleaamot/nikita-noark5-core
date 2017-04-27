package no.arkivlab.hioa.nikita.webapp.service.interfaces.secondary;


import nikita.model.noark5.v4.Fonds;
import nikita.model.noark5.v4.FondsCreator;
import nikita.model.noark5.v4.Series;
import nikita.model.noark5.v4.secondary.CorrespondencePart;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.INoarkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;

public interface ICorrespondencePartService {

    CorrespondencePart updateCorrespondencePart(String systemId, Long version,
                                                CorrespondencePart incomingCorrespondencePart);

    CorrespondencePart createNewCorrespondencePart(CorrespondencePart entity);

    CorrespondencePart findBySystemId(String correspondencePartSystemId);
}
