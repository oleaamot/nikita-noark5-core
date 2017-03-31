package no.arkivlab.hioa.nikita.webapp.security.repository;


import no.arkivlab.hioa.nikita.webapp.model.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {}
