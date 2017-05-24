package no.arkivlab.hioa.nikita.webapp.security.repository;

import nikita.model.noark5.v4.admin.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
