package nikita.webapp.service.interfaces;


import nikita.common.model.noark5.v4.File;
import nikita.common.model.noark5.v4.Series;
import nikita.common.model.noark5.v4.casehandling.CaseFile;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface ISeriesService  {

	// All UPDATE operations
    Series handleUpdate(@NotNull String systemId, @NotNull Long version,
                        @NotNull Series incomingSeries);

	// -- All CREATE operations
	Series save(Series series);
	File createFileAssociatedWithSeries(String seriesSystemId, File file);

    CaseFile createCaseFileAssociatedWithSeries(String seriesSystemId,
                                                CaseFile caseFile);

	// -- All READ operations
    List<Series> findAll();

    List<CaseFile> findAllCaseFileBySeries(String systemId);

	// id
    Optional<Series> findById(Long id);

	// systemId
	Series findBySystemId(String systemId);

	// ownedBy
	List<Series> findByOwnedBy(String ownedBy);

    // All DELETE operations
	void deleteEntity(@NotNull String systemId);
}
