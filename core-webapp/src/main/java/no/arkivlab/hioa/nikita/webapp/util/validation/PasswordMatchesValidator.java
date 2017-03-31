package no.arkivlab.hioa.nikita.webapp.util.validation;

import no.arkivlab.hioa.nikita.webapp.model.security.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final User user = (User) obj;

        // TODO: IMPORTANT TEMP update ....
        return false; //user.getPassword().equals(user.getPasswordConfirmation());
    }

}
