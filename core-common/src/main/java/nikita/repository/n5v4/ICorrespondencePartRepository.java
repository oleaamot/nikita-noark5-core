package nikita.repository.n5v4;

import nikita.model.noark5.v4.CorrespondencePart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ICorrespondencePartRepository extends PagingAndSortingRepository<CorrespondencePart, Long> {


    // -- All SAVE operations
    @Override
    CorrespondencePart save(CorrespondencePart correspondencePart);

    // -- All READ operations
    @Override
    List<CorrespondencePart> findAll();

    @Override
    List<CorrespondencePart> findAll(Sort sort);

    @Override
    Page<CorrespondencePart> findAll(Pageable pageable);

    // id
    CorrespondencePart findById(Long id);

    // systemId
    CorrespondencePart findBySystemId(String systemId);
}
