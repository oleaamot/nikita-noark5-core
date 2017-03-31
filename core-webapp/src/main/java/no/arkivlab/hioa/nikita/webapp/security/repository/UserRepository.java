package no.arkivlab.hioa.nikita.webapp.security.repository;

import no.arkivlab.hioa.nikita.webapp.model.security.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}