package no.arkivlab.hioa.nikita.webapp.spring.datasource;

import no.arkivlab.hioa.nikita.webapp.spring.DataSourceConfig;
import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import javax.sql.DataSource;
import java.sql.SQLException;

@Profile("test")
@Configuration
public class TestDataSource extends DataSourceConfig {

    // Note: use the following string when checking database jdbc:h2:mem:n5DemoDb
    @Bean
    public DataSource dataSource() {

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.setType(
                EmbeddedDatabaseType.H2)
                .setName("test")
                .build();

        return db;
    }


    public void setup() {
    }

    // Start WebServer, default access is set to http://localhost:8082
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server startDBManager() throws SQLException {
        return Server.createWebServer();
    }
}