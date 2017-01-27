package nikita.repository.n5v4;

import nikita.model.noark5.v4.CaseFile;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICaseFileRepository extends PagingAndSortingRepository<CaseFile, Long>  {

    // -- All SAVE operations
    CaseFile save(CaseFile caseFile);

    // -- All READ operations
    // systemId
    CaseFile findBySystemId(String systemId);
}
