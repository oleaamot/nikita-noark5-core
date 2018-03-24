package nikita.webapp.application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tsodring on 2/2/17.
 */
public class APIDetails {
    protected List<APIDetail> aPIDetails = new ArrayList<>();

    public APIDetails() {
    }

    public List<APIDetail> getApiDetails() {
        return aPIDetails;
    }

    public void setApiDetails(List<APIDetail> aPIDetails) {
        this.aPIDetails = aPIDetails;
    }
}
