package no.arkivlab.hioa.nikita.webapp.security;

/**
 * Created by tsodring on 2/7/17.
 */
public class Authorisation implements IAuthorisation {

    public boolean canCreateFonds() {
        return true;
    }

    public boolean canCreateSeries() {
        return true;
    }
}
