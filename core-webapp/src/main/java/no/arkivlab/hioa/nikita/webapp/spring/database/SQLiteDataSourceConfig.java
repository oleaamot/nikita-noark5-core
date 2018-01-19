package no.arkivlab.hioa.nikita.webapp.spring.database;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

import static nikita.config.Constants.SPRING_PROFILE_SQLITE;

@Configuration
@Profile(SPRING_PROFILE_SQLITE)

public class SQLiteDataSourceConfig {
    @Bean
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.sqlite.JDBC");
        dataSourceBuilder.url("jdbc:sqlite:your.db");
        return dataSourceBuilder.build();
    }
}
