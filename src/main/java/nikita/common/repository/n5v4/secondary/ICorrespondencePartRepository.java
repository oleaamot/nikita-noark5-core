package nikita.common.repository.n5v4.secondary;

import nikita.common.model.noark5.v4.casehandling.secondary.CorrespondencePart;
import nikita.common.repository.n5v4.NoarkEntityRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ICorrespondencePartRepository extends NoarkEntityRepository<CorrespondencePart, Long> {
}
