package no.arkivlab.hioa.nikita.webapp.model.application;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tsodring on 2/2/17.
 */
public class APIDetails {
    protected Set<APIDetail> aPIDetails = new HashSet<>();

    public APIDetails() {
    }

    public Set<APIDetail> getApiDetails() {
        return aPIDetails;
    }

    public void setApiDetails(Set<APIDetail> aPIDetails) {
        this.aPIDetails = aPIDetails;
    }
}
