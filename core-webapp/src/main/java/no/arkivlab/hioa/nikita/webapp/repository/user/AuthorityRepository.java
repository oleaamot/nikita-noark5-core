package no.arkivlab.hioa.nikita.webapp.repository.user;


import no.arkivlab.hioa.nikita.webapp.model.user.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
