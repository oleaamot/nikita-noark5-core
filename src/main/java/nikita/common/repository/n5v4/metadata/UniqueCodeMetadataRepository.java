package nikita.common.repository.n5v4.metadata;

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
public interface UniqueCodeMetadataRepository<INikitaEntity, ID extends Serializable> extends
        CrudRepository<INikitaEntity, Long> {

    List<INikitaEntity> findAll();

    INikitaEntity findBySystemId(String systemId);

    List<INikitaEntity> findByDescription(String description);

    INikitaEntity findByCode(String code);
}
