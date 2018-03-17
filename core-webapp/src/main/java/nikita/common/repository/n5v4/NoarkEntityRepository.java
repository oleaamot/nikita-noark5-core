package nikita.common.repository.n5v4;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tsodring on 4/2/17.
 */

@Repository
@NoRepositoryBean
public interface NoarkEntityRepository<INikitaEntity, ID extends Serializable> extends
        PagingAndSortingRepository<INikitaEntity, Long> {

    List<INikitaEntity> findAll();

    INikitaEntity findBySystemId(String systemId);
}
