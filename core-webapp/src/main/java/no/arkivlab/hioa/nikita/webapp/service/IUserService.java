package no.arkivlab.hioa.nikita.webapp.service;

import nikita.model.noark5.v4.admin.User;
import no.arkivlab.hioa.nikita.webapp.util.exceptions.UsernameExistsException;

public interface IUserService {

    User registerNewUser(User user) throws UsernameExistsException;

}
