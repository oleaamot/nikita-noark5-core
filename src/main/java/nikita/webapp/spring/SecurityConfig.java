package nikita.webapp.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
*/
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void globalUserDetails(final AuthenticationManagerBuilder auth)
            throws Exception {// @formatter:off
        auth.inMemoryAuthentication()
                .withUser("john").password("123").roles("USER").and()
                .withUser("admin").password("password").roles("RECORDS_MANAGER");
    }// @formatter:on

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception { // @formatter:off
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("**").permitAll()
                .antMatchers("/oauth/**").permitAll()
                .anyRequest().authenticated()
        ;

        // disable page caching
        httpSecurity.headers().cacheControl();
    } // @formatter:on


    @Override
    public void configure(WebSecurity httpSecurity) { //
        // @formatter:off
        //
        httpSecurity.ignoring()
                .antMatchers("**")
                .antMatchers("/oauth/**")
        ;
    } // @formatter:on
}
