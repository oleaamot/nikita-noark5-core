package no.arkivlab.hioa.nikita.webapp.service.interfaces;

import nikita.model.noark5.v4.FondsCreator;

public interface IFondsCreatorService {

    // -- All CREATE operations
    FondsCreator createNewFondsCreator(FondsCreator fondsCreator);

    Iterable<FondsCreator> findFondsCreatorByOwnerPaginated(Integer top, Integer skip);

    // -- All READ operations

    Iterable<FondsCreator> findAll();

    // id
    FondsCreator findById(Long id);

    // systemId
    FondsCreator findBySystemId(String systemId);

}
