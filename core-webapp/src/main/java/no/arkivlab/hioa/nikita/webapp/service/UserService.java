package no.arkivlab.hioa.nikita.webapp.service;

import no.arkivlab.hioa.nikita.webapp.model.security.User;
import no.arkivlab.hioa.nikita.webapp.security.repository.UserRepository;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.UsernameExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
class UserService implements IUserService {

    @Autowired
    UserRepository repository;

    @Override
    public User registerNewUser(final User user) throws UsernameExistsException {
        if (emailExist(user.getEmail())) {
            throw new UsernameExistsException("There is an account with that email address: " + user.getEmail());
        }
        return repository.save(user);
    }

    private boolean emailExist(String username) {
        final User user = repository.findByUsername(username);
        return user != null;
    }

}
