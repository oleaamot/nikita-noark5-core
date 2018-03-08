package no.arkivlab.hioa.nikita.webapp.spring;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan({"nikita.model", "no.arkivlab.hioa.nikita.webapp.model"})
@EnableJpaRepositories({"nikita.repository", "no.arkivlab.hioa.nikita.webapp.security.repository"})
public class DataSourceConfig {
}
