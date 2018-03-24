package nikita.webapp.spring.security;

import nikita.common.model.nikita.NikitaUserPrincipal;
import nikita.common.model.noark5.v4.admin.User;
import nikita.common.repository.nikita.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class NikitaUserDetailsService
        implements UserDetailsService {

    private UserRepository userRepository;

    public NikitaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with this " +
                    "username: " + username);
        }
        return new NikitaUserPrincipal(user);
    }
}
