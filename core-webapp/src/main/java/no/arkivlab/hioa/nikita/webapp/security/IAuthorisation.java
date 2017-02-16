package no.arkivlab.hioa.nikita.webapp.security;

/**
 * Created by tsodring on 2/7/17.
 */
public interface IAuthorisation {

    boolean canCreateFonds();
    boolean canCreateSeries();

    boolean canCreateClassifcationSystem();

    boolean canCreateClassifcationSystemAttachedToSeries();

    boolean canCreateRegistrationAttachedToSeries();

    boolean canCreateRegistrationAttachedToFile();

    boolean canCreateFileAttachedToSeries();

}
