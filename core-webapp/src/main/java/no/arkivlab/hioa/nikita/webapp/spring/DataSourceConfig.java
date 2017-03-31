package no.arkivlab.hioa.nikita.webapp.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories({"nikita.repository", "no.arkivlab.hioa.nikita.webapp.security.repository"})
public class DataSourceConfig {


    @Autowired
    private Environment env;

    @Bean
    // This is dealt with by sub-classes
    // You really should throw an exception rather than return null!
    public DataSource dataSource() {
        return null;
    }

    @Bean
    JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        // Scan the Noark domain model from core-common and application domain model
        em.setPackagesToScan("nikita.model", "no.arkivlab.hioa.nikita.webapp.model");
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        return em;
    }

    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();

        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", env.getProperty("spring.jpa.properties.hibernate.cache.use_second_level_cache"));
        hibernateProperties.setProperty("hibernate.cache.use_query_cache", env.getProperty("spring.jpa.properties.hibernate.cache.use_query_cache"));
        hibernateProperties.setProperty("hibernate.generate_statistics", env.getProperty("spring.jpa.properties.hibernate.generate_statistics"));
        //hibernateProperties.setProperty("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("spring.jpa.properties.hibernate.show_sql"));
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.properties.hibernate.hbm2ddl.auto"));
        hibernateProperties.setProperty("hibernate.hbm2ddl.import_files", env.getProperty("spring.jpa.properties.hibernate.hbm2ddl.import_files"));
        //hibernateProperties.setProperty("hibernate.search.default.directory_provider", env.getProperty("spring.jpa.properties.hibernate.search.default.directory_provider"));
        //hibernateProperties.setProperty("hibernate.search.default.indexBase", env.getProperty("spring.jpa.properties.hibernate.search.default.indexBase"));
        // For elasticsearch
        //hibernateProperties.setProperty("hibernate.search.default.indexmanager", env.getProperty("spring.jpa.properties.hibernate.search.default.indexmanager"));
        //hibernateProperties.setProperty("hibernate.search.default.elasticsearch.index_schema_management_strategy", env.getProperty("spring.jpa.properties.hibernate.search.default.elasticsearch.index_schema_management_strategy"));
        //hibernateProperties.setProperty("hibernate.search.default.elasticsearch.host", env.getProperty("spring.jpa.properties.hibernate.search.default.elasticsearch.host"));
        return hibernateProperties;
    }

}
