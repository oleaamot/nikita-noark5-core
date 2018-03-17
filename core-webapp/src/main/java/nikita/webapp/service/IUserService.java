package nikita.webapp.service;

import nikita.common.model.noark5.v4.admin.User;
import nikita.webapp.util.exceptions.UsernameExistsException;

public interface IUserService {

    User registerNewUser(User user) throws UsernameExistsException;

}
