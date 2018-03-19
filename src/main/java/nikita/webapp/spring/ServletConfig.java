package nikita.webapp.spring;

import org.springframework.web.servlet.DispatcherServlet;

//@Configuration
public class ServletConfig {

    public ServletConfig() {
        super();
    }

    //  @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

}

