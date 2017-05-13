package nikita.repository.n5v4.secondary;

import nikita.model.noark5.v4.casehandling.Precedence;
import nikita.repository.n5v4.NoarkEntityRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IPrecedenceRepository extends NoarkEntityRepository<Precedence, Long> {
}
