package no.arkivlab.hioa.nikita.webapp.service.interfaces;


import nikita.model.noark5.v4.Fonds;
import nikita.model.noark5.v4.FondsCreator;
import nikita.model.noark5.v4.Series;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public interface IFondsService  {

    // -- All CREATE operations
    Fonds createNewFonds(Fonds fonds);
    Series createSeriesAssociatedWithFonds(String fondsSystemId, Series series);
    Fonds createFondsAssociatedWithFonds(String parentFondsSystemId, Fonds childFonds);

    FondsCreator createFondsCreatorAssociatedWithFonds(String fondsSystemId, FondsCreator fondsCreator);

    // -- All READ operations
    Set<FondsCreator> findFondsCreatorAssociatedWithFonds(String systemId);
    List<Fonds> findFondsByOwnerPaginated(Integer top, Integer skip);


    List<Fonds> findAll();
    List<Fonds> findAll(Sort sort);
    Page<Fonds> findAll(Pageable pageable);

    // id
    Fonds findById(Long id);

    // systemId
    Fonds findBySystemId(String systemId);

    // ownedBy
    List<Fonds> findByOwnedBy(String ownedBy);
    List<Fonds> findByOwnedBy(String ownedBy, Sort sort);
    Page<Fonds> findByOwnedBy(String ownedBy, Pageable pageable);


    // All UPDATE operations
    Fonds handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull Fonds incomingFonds);

    // All DELETE operations
    void deleteEntity(@NotNull String systemId);
}
