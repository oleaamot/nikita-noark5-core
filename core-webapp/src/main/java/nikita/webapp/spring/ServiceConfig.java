package nikita.webapp.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan({"nikita.webapp.service"})
public class ServiceConfig {

    public ServiceConfig() {
        super();
    }

    // beans

}
