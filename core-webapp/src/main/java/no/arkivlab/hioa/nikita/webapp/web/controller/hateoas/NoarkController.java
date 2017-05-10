package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;


import nikita.model.noark5.v4.FondsCreator;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;
import nikita.util.exceptions.NikitaException;
import nikita.util.exceptions.NikitaMalformedHeaderException;
import nikita.util.exceptions.NikitaMalformedInputDataException;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.List;

import static nikita.config.Constants.ONLY_WHITESPACE;
import static nikita.config.Constants.SLASH;

/**
 * Created by tsodring on 2/17/17.
 */
public class NoarkController {


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

    protected boolean validateForCreate(INoarkGeneralEntity noarkGeneralEntity) {
        if (noarkGeneralEntity.getTitle() == null) {
            throw new NikitaMalformedInputDataException("The " + noarkGeneralEntity.getBaseTypeName() + " you tried " +
                    "to create is malformed. The tittel field is mandatory, and you have submitted an empty value.");
        }
        return true;
    }

    protected boolean validateForUpdate(INoarkGeneralEntity noarkGeneralEntity) {
        return true;
    }

    /**
     * This method is a bit messy as it has to figure what is the actual type of class before being able to check for
     * validity.
     *
     * @param noarkEntity
     * @return true if the object is value
     */
    protected boolean validateForCreate(INikitaEntity noarkEntity) {
        if (noarkEntity instanceof FondsCreator) {
            if (((FondsCreator)noarkEntity).getFondsCreatorId() == null) {
                throw new NikitaMalformedInputDataException("The arkivskaper you tried to create is malformed. The "
                        + "arkivskaperID field is mandatory, and you have submitted an empty value.");
            }
            if (((FondsCreator)noarkEntity).getFondsCreatorName() == null) {
                throw new NikitaMalformedInputDataException("The arkivskaper you tried to create is malformed. The "
                        + "arkivskaperNavn field is mandatory, and you have submitted an empty value.");
            }
        }

        return true;
    }

    protected boolean validateForUpdate(INikitaEntity noarkEntity) {
        //rejectIfEmptyOrWhitespace(ONLY_WHITESPACE);
        return true;
    }

    private boolean rejectIfEmptyOrWhitespace(String stringToCheck) {
        return true;
    }

    public Long parseETAG(String quotedETAG) {
        Long etagVal = new Long(-1L);
        if (quotedETAG != null) {
            try {
                etagVal = Long.parseLong(quotedETAG.replaceAll("^\"|\"$", ""));
            }
            catch (NumberFormatException nfe) {
                throw new NikitaMalformedHeaderException("eTag value is not numeric. Nikita  uses numeric ETAG " +
                        "values >= 0.");
            }
        }
        if (etagVal < 0) {
            throw new NikitaMalformedHeaderException("eTag value is less than 0. This is illegal" +
                    "as ETAG values show version of an entity in the database and start at 0");
        }
        return etagVal;
    }

}
