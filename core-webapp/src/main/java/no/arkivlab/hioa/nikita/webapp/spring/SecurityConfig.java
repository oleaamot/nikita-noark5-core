package no.arkivlab.hioa.nikita.webapp.spring;

import no.arkivlab.hioa.nikita.webapp.security.JwtAuthenticationEntryPoint;
import no.arkivlab.hioa.nikita.webapp.security.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static nikita.config.Constants.ROLE_RECORDS_MANAGER;
import static nikita.config.Constants.SLASH;
import static nikita.config.N5ResourceMappings.FONDS;
import static nikita.config.PATHPatterns.PATTERN_METADATA_PATH;
import static nikita.config.PATHPatterns.PATTERN_NEW_FONDS_STRUCTURE_ALL;

@Profile("!nosecurity")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception { // @formatter:off
        httpSecurity
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()

                // don't create session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                // GET [api]/metadata/**, public to read basic structure
                .antMatchers(HttpMethod.GET, PATTERN_METADATA_PATH).permitAll()
                // Allow OPTIONS command on root
                .antMatchers(HttpMethod.OPTIONS, "/").permitAll()
                // Allow GET on root of application
                .antMatchers(HttpMethod.GET, "/").permitAll()
                // The following will like be removed soon ...
                .antMatchers("/signup", "/user/register", "/webapp/login/**").permitAll()
                // The metrics configuration is visible to all
                .antMatchers("/management/**").permitAll()
                // The swaggerUI related stuff. No authorisation required to see it.
                .antMatchers("/v2/api-docs/**", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**").permitAll()
                // besides the above all request MUST be authenticated
                // POST GET [api]/arkivstruktur/ny-*, need admin
                .antMatchers(HttpMethod.POST, PATTERN_NEW_FONDS_STRUCTURE_ALL).hasAuthority(ROLE_RECORDS_MANAGER)
                .antMatchers(HttpMethod.GET, PATTERN_NEW_FONDS_STRUCTURE_ALL).hasAuthority(ROLE_RECORDS_MANAGER)
                // POST PUT PATCH [api]/arkivstruktur/**, need admin
                .antMatchers(HttpMethod.PUT, FONDS + SLASH + "**").hasAuthority(ROLE_RECORDS_MANAGER)
                .antMatchers(HttpMethod.PATCH, FONDS + SLASH + "**").hasAuthority(ROLE_RECORDS_MANAGER)
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
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
        ;

        // Custom JWT based security filter
        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        httpSecurity.headers().cacheControl();
    } // @formatter:on
}
