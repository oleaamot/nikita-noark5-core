package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;


import nikita.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;
import nikita.util.exceptions.NikitaException;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.List;

import static nikita.config.Constants.SLASH;

/**
 * Created by tsodring on 2/17/17.
 */
public class NikitaController {


    /**
     * Get the object internally. Might be difficult as we need the various service classes
     *
     * @return
     */
    public List<INoarkGeneralEntity> handleResolutionOfIncomingURLInternal(URL url) {

        return null;
    }

    /**
     * Get the systemId identifying the object requested
     *
     * @return
     */
    public String handleResolutionOfIncomingURLInternalGetSystemId(StringBuffer url) {

        // TODO: Check that this is an internal reference. Check address and contextpath.
        // If it is handle here, if not try to pick it up from the web.
        // Check that the address is OK and check the api path is correct
        // Give back information about problems
        int lastIndexOfSlash = url.lastIndexOf(SLASH);
        if (lastIndexOfSlash <= 0) {
            throw new NikitaException("Error with address of object to reference to. Could not process string. " +
                    "Expecting a ending " + SLASH + " to delimit the systemId. String causing the problem is (" + url
                    + ")");
        }
        return url.substring(lastIndexOfSlash + 1);
    }


    /**
     * Go out on the internet and get the object. Requires handling cookies logging on etc.
     *
     * @return
     */

    public INoarkGeneralEntity handleResolutionOfIncomingURLExternal() {

        return null;
    }

    /**
     * Check that eTag is present in header. If not throw a 400 Bad request error
     * with a description
     */

    protected void checkIncomingPUTRequest(HttpServletRequest request) {


    }
}
