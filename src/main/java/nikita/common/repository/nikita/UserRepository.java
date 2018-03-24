package nikita.common.repository.nikita;

import nikita.common.model.noark5.v4.admin.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
        extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
