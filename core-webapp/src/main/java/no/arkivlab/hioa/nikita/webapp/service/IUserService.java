package no.arkivlab.hioa.nikita.webapp.service;

import no.arkivlab.hioa.nikita.webapp.model.security.User;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.UsernameExistsException;

public interface IUserService {

    User registerNewUser(User user) throws UsernameExistsException;

}
