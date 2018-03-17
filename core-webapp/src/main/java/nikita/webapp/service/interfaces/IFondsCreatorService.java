package nikita.webapp.service.interfaces;

import nikita.common.model.noark5.v4.Fonds;
import nikita.common.model.noark5.v4.FondsCreator;

import javax.validation.constraints.NotNull;
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

    // All UPDATE operations
    FondsCreator handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull FondsCreator fondsCreator);

    // All DELETE operations
    void deleteEntity(@NotNull String systemId);
}
