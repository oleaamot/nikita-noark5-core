package nikita.repository.n5v4;

import org.springframework.data.repository.CrudRepository;
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
public interface NoarkEntityRepository<INoarkSystemIdEntity, ID extends Serializable> extends
        PagingAndSortingRepository<INoarkSystemIdEntity, Long> {

    List<INoarkSystemIdEntity> findAll();

    INoarkSystemIdEntity findBySystemId(String systemId);

}
