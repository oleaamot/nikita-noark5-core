package no.arkivlab.hioa.nikita.webapp.spring.datasource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Profile({"prod", "dev-db"})
//@Configuration
//@EnableTransactionManagement
//@ComponentScan("no.arkivlab.hioa.nikita.webapp.persistence")
//@EnableJpaRepositories(basePackages = {"no.arkivlab.hioa.nikita.webapp.persistence.dao", "no.arkivlab.hioa.nikita.client.repository"})
public class ProdDataSource {

    private final Logger log = LoggerFactory.getLogger(ProdDataSource.class);

    @Autowired
    private Environment env;

    public ProdDataSource() {
        super();
    }

    // beans
    /*
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        //em.setPackagesToScan("no.arkivlab.hioa.nikita.client.persistence.model");
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        return em;
    }*/

    @Bean(name="dataSource")
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));


        return dataSource;
    }

    /*
        @Bean
        public JpaTransactionManager transactionManager() {
            final JpaTransactionManager transactionManager = new JpaTransactionManager();
            transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
            return transactionManager;
        }

        @Bean
        public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
            return new PersistenceExceptionTranslationPostProcessor();
        }

        final Properties additionalProperties() {
            final Properties hibernateProperties = new Properties();

            hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.properties.hibernate.hbm2ddl.auto"));

            hibernateProperties.setProperty("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
            // TODO, you should be able to remove this. It was added because we were getting an exception on cache not being
            //found and I needed to move forward
            hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", "");

            // setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
            // setProperty("hibernate.ejb.naming_strategy", org.hibernate.cfg.ImprovedNamingStrategy.class.getName());
            return hibernateProperties;
        }
    */
    public void setup() {
    }
}


