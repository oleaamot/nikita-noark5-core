package nikita.webapp.service.interfaces.imprt;

import nikita.common.model.noark5.v4.Fonds;
import nikita.common.model.noark5.v4.Series;

public interface IFondsImportService {
    // -- All CREATE operations
    Fonds createNewFonds(Fonds fonds);
    Series createSeriesAssociatedWithFonds(String fondsSystemId, Series series);
    Fonds createFondsAssociatedWithFonds(String parentFondsSystemId, Fonds childFonds);
}
