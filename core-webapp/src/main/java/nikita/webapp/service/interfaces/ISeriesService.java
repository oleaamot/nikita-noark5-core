package nikita.webapp.service.interfaces;


import nikita.common.model.noark5.v4.File;
import nikita.common.model.noark5.v4.Series;
import nikita.common.model.noark5.v4.casehandling.CaseFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ISeriesService  {

	// All UPDATE operations
	Series handleUpdate(@NotNull String systemId, @NotNull Long version, @NotNull Series incomingSeries);

	// -- All CREATE operations
	Series save(Series series);
	File createFileAssociatedWithSeries(String seriesSystemId, File file);
	CaseFile createCaseFileAssociatedWithSeries(String seriesSystemId, CaseFile caseFile);

	// -- All READ operations
    List<Series> findAll();

    List<CaseFile> findAllCaseFileBySeries(String systemId);

    List<Series> findAll(Sort sort);

    Page<Series> findAll(Pageable pageable);

	// id
	Series findById(Long id);

	// systemId
	Series findBySystemId(String systemId);

	// ownedBy
	List<Series> findByOwnedBy(String ownedBy);
	List<Series> findByOwnedBy(String ownedBy, Sort sort);

    List<Series> findSeriesByOwnerPaginated(Integer top, Integer skip);

    // All DELETE operations
	void deleteEntity(@NotNull String systemId);
}
