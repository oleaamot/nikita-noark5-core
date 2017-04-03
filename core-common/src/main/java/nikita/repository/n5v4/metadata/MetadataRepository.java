package nikita.repository.n5v4.metadata;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tsodring on 4/2/17.
 */

@Repository
@NoRepositoryBean
public interface MetadataRepository<INoarkSystemIdEntity, ID extends Serializable> extends
        CrudRepository<INoarkSystemIdEntity, Long> {

    List<INoarkSystemIdEntity> findAll();

    INoarkSystemIdEntity findBySystemId(String systemId);

    List<INoarkSystemIdEntity> findByDescription(String description);

    List<INoarkSystemIdEntity> findByCode(String code);
}
