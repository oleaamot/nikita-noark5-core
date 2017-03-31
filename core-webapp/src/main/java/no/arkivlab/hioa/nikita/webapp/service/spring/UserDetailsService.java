package no.arkivlab.hioa.nikita.webapp.service.spring;

import no.arkivlab.hioa.nikita.webapp.model.security.Authority;
import no.arkivlab.hioa.nikita.webapp.model.security.User;
import no.arkivlab.hioa.nikita.webapp.security.repository.UserRepository;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.UserExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

//@Service
//@Transactional
public class UserDetailsService {
//implements org.springframework.security.core.userdetails.UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                //              user.isEnabled(),
                //              user.isAccountNonExpired(),
                //              user.isCredentialsNonExpired(),
                //              user.isAccountNonLocked(),
                getAuthorities(user));
    }


    public Collection<? extends GrantedAuthority> getAuthorities(User user) {
        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
        List<Authority> auths = user.getAuthorities();

        for (Authority auth : auths) {
// HERE!!!!            setAuths.add(new SimpleGrantedAuthority(auth.getName()));
        }

        List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);
        return result;
    }

    public User registerNewUser(User user) throws UserExistsException {
        if (userExists(user.getUsername())) {
            throw new UserExistsException("An account with this username already exists: " + user.getUsername());
        }
        return userRepository.save(user);
    }

    private boolean userExists(String username) {
        final User user = userRepository.findByUsername(username);
        return user != null;
    }
}