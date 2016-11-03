package nikita.odata.interfaces;

import nikita.model.noark5.v4.Fonds;
import nikita.odata.SearchCriteria;

import java.util.List;

public interface IFondsDAO {
    List<Fonds> searchFonds(List<SearchCriteria> params);
    void save(Fonds entity);
}
