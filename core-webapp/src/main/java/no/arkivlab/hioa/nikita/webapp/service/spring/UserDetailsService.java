package no.arkivlab.hioa.nikita.webapp.service.spring;

import no.arkivlab.hioa.nikita.webapp.util.exceptions.UserExistsException;
import no.arkivlab.hioa.nikita.webapp.model.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import no.arkivlab.hioa.nikita.webapp.repository.user.UserRepository;

import java.util.Collection;

@Service
@Transactional
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

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
                user.isEnabled(),
                user.isAccountNonExpired(),
                user.isCredentialsNonExpired(),
                user.isAccountNonLocked(),
                getAuthorities(user));
    }


    public Collection<? extends GrantedAuthority> getAuthorities(User user) {

        String roles = StringUtils.collectionToCommaDelimitedString(user.getAuthorities());
        // TODO: If the user has no role, then what? Probably have to give anonymous role,
        // but this is something that should be logged as ERROR
        //logger.error("User " + user.getUsername() + " has no defined roles.");
        return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
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