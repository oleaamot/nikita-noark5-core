package nikita.webapp.spring.security.configs;

import nikita.webapp.spring.security.NikitaAuthenticationSuccessHandler;
import nikita.webapp.spring.security.NikitaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * Testing a form based SecurityConfig using the pre-provided users/authorities
 * defined in a sql file in the resources directory
 * <p>
 * This can be tested using:
 * <p>
 * curl -i -X POST -d username=admin -d password=password -c /tmp/cookies.txt
 * http://localhost:8092/noark5v4/login
 * <p>
 * <p>
 * curl -i  -b /tmp/cookies.txt --header Accept:application/vnd.noark5-v4+json
 * -X GET http://localhost:8092/noark5v4/
 */

//@Profile("security-form-authentication")
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true,
        jsr250Enabled = true)
public class FormAuthenticationConfig
        extends WebSecurityConfigurerAdapter {

    @Autowired
    NikitaUserDetailsService userDetailsService;

    @Autowired
    private NikitaAuthenticationSuccessHandler authenticationSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http)
            throws Exception { // @formatter:off
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .and()
                .csrf().disable()
                .formLogin()
                .successHandler(authenticationSuccessHandler)
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
        ;
    }// @formatter:off
}
