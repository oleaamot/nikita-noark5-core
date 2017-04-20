package no.arkivlab.hioa.nikita.webapp.model.application;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by tsodring on 2/2/17.
 */
public class APIDetails {
    protected Set<APIDetail> aPIDetails = new TreeSet<>();

    public APIDetails() {
    }

    public Set<APIDetail> getApiDetails() {
        return aPIDetails;
    }

    public void setApiDetails(Set<APIDetail> aPIDetails) {
        this.aPIDetails = aPIDetails;
    }
}
