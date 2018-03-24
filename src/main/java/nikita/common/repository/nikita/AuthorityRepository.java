package nikita.common.repository.nikita;


import nikita.common.model.noark5.v4.admin.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository
        extends JpaRepository<Authority, String> {
}
