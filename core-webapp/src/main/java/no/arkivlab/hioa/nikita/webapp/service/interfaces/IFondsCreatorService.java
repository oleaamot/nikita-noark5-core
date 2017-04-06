package no.arkivlab.hioa.nikita.webapp.service.interfaces;

import nikita.model.noark5.v4.Fonds;
import nikita.model.noark5.v4.FondsCreator;

import java.util.List;

public interface IFondsCreatorService {

    // -- All CREATE operations
    FondsCreator createNewFondsCreator(FondsCreator fondsCreator);

    Fonds createFondsAssociatedWithFondsCreator(String fondsCreatorSystemId, Fonds fonds);

    List<FondsCreator> findFondsCreatorByOwnerPaginated(Integer top, Integer skip);

    // -- All READ operations

    Iterable<FondsCreator> findAll();

    // id
    FondsCreator findById(Long id);

    // systemId
    FondsCreator findBySystemId(String systemId);

    // -- All UPDATE  operations
    FondsCreator updateFondsCreator(FondsCreator fondsCreator);
}
