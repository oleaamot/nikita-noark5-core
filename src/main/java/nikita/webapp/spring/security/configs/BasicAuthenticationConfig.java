package nikita.webapp.spring.security.configs;

import nikita.webapp.spring.security.NikitaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static nikita.common.config.Constants.ROLE_RECORDS_MANAGER;
import static nikita.common.config.Constants.SLASH;
import static nikita.common.config.N5ResourceMappings.FONDS;
import static nikita.common.config.PATHPatterns.PATTERN_METADATA_PATH;
import static nikita.common.config.PATHPatterns.PATTERN_NEW_FONDS_STRUCTURE_ALL;

/**
 * Testing a http-basic-authentication SecurityConfig with a single account
 * and a BCyrpt encoded password.
 * <p>
 * This can be tested using:
 * curl -i --user admin:password http://localhost:8092/noark5v4/
 */

@Profile("security-http-basic-authentication")
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true,
        jsr250Enabled = true)
public class BasicAuthenticationConfig
        extends WebSecurityConfigurerAdapter {

    @Autowired
    NikitaUserDetailsService userDetailsService;

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
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("*").permitAll()
                // GET [api]/metadata/**, public to read basic structure
                .antMatchers(HttpMethod.GET, PATTERN_METADATA_PATH).permitAll()
                // Allow OPTIONS command on everything root
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // Allow GET on root of application
                .antMatchers(HttpMethod.GET, "/").permitAll()
                // The metrics configuration is visible to all
                .antMatchers("/management/**").permitAll()
                // The swaggerUI related stuff. No authorisation required to see it.
                .antMatchers("/v2/api-docs/**", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**").permitAll()
                // besides the above all request MUST be authenticated
                // POST GET [api]/arkivstruktur/ny-*, need admin
                .antMatchers(HttpMethod.POST, PATTERN_NEW_FONDS_STRUCTURE_ALL).hasAuthority(ROLE_RECORDS_MANAGER)
                .antMatchers(HttpMethod.GET, PATTERN_NEW_FONDS_STRUCTURE_ALL).hasAuthority(ROLE_RECORDS_MANAGER)
                .antMatchers(HttpMethod.OPTIONS, "/noark5v4/hateoas-api/arkivstruktur/ny-arkivskaper").permitAll()
                // POST PUT PATCH [api]/arkivstruktur/**, need admin
                .antMatchers(HttpMethod.PUT, FONDS + SLASH + "**").hasAuthority(ROLE_RECORDS_MANAGER)
                .antMatchers(HttpMethod.PATCH, FONDS + SLASH + "**").hasAuthority(ROLE_RECORDS_MANAGER)
                .antMatchers(HttpMethod.DELETE, "/**").hasAuthority(ROLE_RECORDS_MANAGER)
                // POST PUT PATCH DELETE [api]/metadata/**, need admin
                .antMatchers(HttpMethod.PATCH, PATTERN_METADATA_PATH).hasAuthority(ROLE_RECORDS_MANAGER)
                .antMatchers(HttpMethod.PUT, PATTERN_METADATA_PATH).hasAuthority(ROLE_RECORDS_MANAGER)
                .antMatchers(HttpMethod.POST, PATTERN_METADATA_PATH).hasAuthority(ROLE_RECORDS_MANAGER)
                .antMatchers(HttpMethod.DELETE, PATTERN_METADATA_PATH).hasAuthority(ROLE_RECORDS_MANAGER)
                // allow anonymous resource requests
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .antMatchers(
                        "/console/**"
                ).permitAll()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .httpBasic()
                .realmName("nikita-noark5")
                .and()
        ;
    }// @formatter:off

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
