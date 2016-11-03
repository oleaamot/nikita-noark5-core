package no.arkivlab.hioa.nikita.webapp.spring;

import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Profile({"nosecurity"})
@EnableWebSecurity
public class SecurityOffConfig extends WebSecurityConfigurerAdapter {

    public SecurityOffConfig() {
        super();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // @formatter:off
        http
            .authorizeRequests()
            .antMatchers("/**").permitAll()
        .and()
            .csrf().disable();

    } // @formatter:on
}
