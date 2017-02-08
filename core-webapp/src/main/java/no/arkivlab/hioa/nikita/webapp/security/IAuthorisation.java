package no.arkivlab.hioa.nikita.webapp.security;

/**
 * Created by tsodring on 2/7/17.
 */
public interface IAuthorisation {

    boolean canCreateFonds();

    boolean canCreateSeries();

}
