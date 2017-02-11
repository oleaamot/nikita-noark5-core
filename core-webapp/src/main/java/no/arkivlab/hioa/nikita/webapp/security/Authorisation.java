package no.arkivlab.hioa.nikita.webapp.security;

/**
 * Created by tsodring on 2/7/17.
 *
 * Currently just yes on everything but when roles and security implemented properly, class will be instantiated
 * with details about user and what they can do. At simplest for it will latch on to roles, but finer grained control
 * can be done with e.g. LDAP integration to groups-
 *
 */
public class Authorisation implements IAuthorisation {

    @Override
    public boolean canCreateFonds() {
        return true;
    }

    @Override
    public boolean canCreateSeries() {
        return true;
    }

    @Override
    public boolean canCreateClassifcationSystem() {
        return false;
    }

    @Override
    public boolean canCreateClassifcationSystemAttachedToSeries() {
        return true;
    }

    @Override
    public boolean canCreateRegistrationAttachedToSeries() {
        return true;
    }

    @Override
    public boolean canCreateRegistrationAttachedToFile() {
        return true;
    }

    @Override
    public boolean canCreateFileAttachedToSeries() {
        return true;
    }
}
