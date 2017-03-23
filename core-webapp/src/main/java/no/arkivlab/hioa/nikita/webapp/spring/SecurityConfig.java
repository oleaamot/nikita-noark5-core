package no.arkivlab.hioa.nikita.webapp.spring;

import no.arkivlab.hioa.nikita.webapp.spring.security.AppAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import static nikita.config.Constants.ROLE_RECORDS_MANAGER;
import static nikita.config.Constants.SLASH;
import static nikita.config.N5ResourceMappings.FONDS;
import static nikita.config.PATHPatterns.PATTERN_NEW_FONDS_STRUCTURE_ALL;

@Profile("!nosecurity")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AppAuthenticationSuccessHandler appAuthenticationSuccessHandler;
    @Autowired
    private UserDetailsService userDetailsService;

    public SecurityConfig() {
        super();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off
        http
                .authorizeRequests()
                .antMatchers("/").permitAll() // allow access to conformity details
                .antMatchers("/signup", "/user/register", "/webapp/login/**").permitAll()
                // The metrics configuration is visible to all
                .antMatchers("/management/**").permitAll()
                // The swaggerUI related stuff. No authorisation required to see it.
                .antMatchers("/v2/api-docs/**", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**").permitAll()
                // besides the above all request MUST be authenticated
                // filters on role access for arkiv
                .antMatchers(HttpMethod.POST, PATTERN_NEW_FONDS_STRUCTURE_ALL).hasAuthority(ROLE_RECORDS_MANAGER)
                .antMatchers(HttpMethod.GET, PATTERN_NEW_FONDS_STRUCTURE_ALL).hasAuthority(ROLE_RECORDS_MANAGER)
                .antMatchers(HttpMethod.PUT, FONDS + SLASH + "**").hasAuthority(ROLE_RECORDS_MANAGER)
                .antMatchers(HttpMethod.PATCH, FONDS + SLASH + "**").hasAuthority(ROLE_RECORDS_MANAGER)
                .anyRequest().authenticated()
        .and()
        .formLogin().
            loginPage("/login").permitAll()
                .successHandler(appAuthenticationSuccessHandler)
                //.defaultSuccessUrl("/user", true).
                .loginProcessingUrl("/doLogin")

        .and()
        .logout().permitAll().logoutUrl("/logout")

        .and()
        .csrf().disable()
        ;
    } // @formatter:on

}
/*

    protected void configure(HttpSecurity http) throws Exception { // @formatter:off
        http
                .authorizeRequests()
                .antMatchers("/").permitAll() // allow access to conformity details
                .antMatchers("/signup", "/user/register", "/webapp/login/**").permitAll()
                // filters on role access for arkiv
                /*.antMatchers(HttpMethod.POST, FONDS + SLASH + "**").hasAuthority(ROLE_RECORDS_MANAGER)
                .antMatchers(HttpMethod.PUT, FONDS + SLASH + "**").hasAuthority(ROLE_RECORDS_MANAGER)
                .antMatchers(HttpMethod.PATCH, FONDS + SLASH + "**").hasAuthority(ROLE_RECORDS_MANAGER)
                .antMatchers(HttpMethod.GET, FONDS + SLASH + "**").hasAnyAuthority()
                        .formLogin().
                        loginPage("/login").permitAll()
                        .defaultSuccessUrl("/user", true)
                        //   .successHandler(appAuthenticationSuccessHandler)
                        .loginProcessingUrl("/doLogin")

                        //authentication-success-handler-ref="myAuthenticationSuccessHandler"/>

 */