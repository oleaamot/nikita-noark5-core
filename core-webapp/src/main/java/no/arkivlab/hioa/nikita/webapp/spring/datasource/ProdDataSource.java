package no.arkivlab.hioa.nikita.webapp.spring.datasource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Profile("prod")
@Configuration
public class ProdDataSource {

    private final Logger log = LoggerFactory.getLogger(ProdDataSource.class);

    @Autowired
    private Environment env;

    public ProdDataSource() {
        super();
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driverClassName"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));


        return dataSource;
    }


    public void setup() {
    }
}


