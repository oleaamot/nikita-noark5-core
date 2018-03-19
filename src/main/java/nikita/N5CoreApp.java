package nikita;

import nikita.webapp.run.AfterApplicationStartup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;


//@EnableAsync
@SpringBootApplication
public class N5CoreApp {

    private static final Logger logger = LoggerFactory.getLogger(N5CoreApp.class);

    /**
     * Main method, used to run the application.
     *
     * @param args the command line arguments
     * @throws UnknownHostException if the local host name could not be resolved into an address
     */

    public static void main(String[] args) throws UnknownHostException {

        ConfigurableApplicationContext context = SpringApplication.run
                (N5CoreApp.class, args);

        context.getBean(AfterApplicationStartup.class).afterApplicationStarts();
        Environment env = context.getEnvironment();

        String[] activeProfiles = env.getActiveProfiles();
        String profilesAsString = Arrays.toString(activeProfiles);
        logger.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t\thttp://localhost:{}\n\t" +
                        "External: \t\thttp://{}:{}\n\t" +
                        "contextPath: \thttp://{}:{}{} \n\t" +
                        "Application is running with following profile(s): {} \n\t" +
                        "\n----------------------------------------------------------",
                env.getProperty("server.application.name"),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                env.getProperty("server.contextPath"),
                profilesAsString
        );

        String configServerStatus = env.getProperty("configserver.status");
        logger.info("\n----------------------------------------------------------\n\t" +
                        "Config Server: \t{}\n----------------------------------------------------------",
                configServerStatus == null ? "Not found or not setup for this application" : configServerStatus);

        if (env.getProperty("spring.datasource.driver-class-name") != null &&
                env.getProperty("spring.datasource.driver-class-name").equals("org.h2.Driver")) {
            logger.info("\n----------------------------------------------------------\n\t" +
                            "Default profile in use. Using H2: In-memory database ({}). Access is available at." +
                            "http://{}:{}{}{} \n\t. Make sure to use JDBC-string: jdbc:h2:mem:n5DemoDb" +
                            "\n----------------------------------------------------------",
                    env.getProperty("spring.jpa.database"),
                    InetAddress.getLocalHost().getHostAddress(),
                    env.getProperty("server.port"),
                    env.getProperty("server.contextPath"),
                    env.getProperty("spring.h2.console.path")
            );
        }
    }
}
