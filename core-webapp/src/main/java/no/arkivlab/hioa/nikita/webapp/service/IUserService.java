package no.arkivlab.hioa.nikita.webapp.service;


import no.arkivlab.hioa.nikita.webapp.util.exceptions.UsernameExistsException;
import no.arkivlab.hioa.nikita.webapp.model.user.User;

public interface IUserService {

    User registerNewUser(User user) throws UsernameExistsException;

}
