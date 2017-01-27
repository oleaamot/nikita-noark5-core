package no.arkivlab.hioa.nikita.webapp.service.impl.imprt;

import nikita.config.Constants;
import nikita.model.noark5.v4.interfaces.IDocumentMedium;
import nikita.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.imprt.INoarkImportService;
import no.arkivlab.hioa.nikita.webapp.util.NoarkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.UUID;

/**
 * Created by tsodring on 12/7/16.
 *
 * This class provides basic functionality when importing Noark 5 objects implementing
 *  INoarkGeneralEntity
 *
 * Takes care
 */
public class NoarkImportService implements INoarkImportService {

    // Not declaring it as static, gonna be a performance cost with this, but it should
    // at least associate the logger messages with the right subclass
    private final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     *
     * Check a Noark entity (fonds, series, file, etc) for valid properties and set any values that have not been set
     * that should be set with default values.
     *
     * @param entity
     * @throws Exception
     */
    public void checkAndSetDefaultValues(INoarkGeneralEntity entity) throws Exception {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Not all Noark objects, i.e DocumentDescription and DocumentObject have a documentMedium property
        if (entity instanceof IDocumentMedium) {
            if (!NoarkUtils.NoarkEntity.Create.checkDocumentMediumValid(((IDocumentMedium)entity))) {
                logger.error("Document medium not a valid document medium. Assigned value is " +
                        ((IDocumentMedium)entity).getDocumentMedium());
                // An exception will be thrown  by checkDocumentMediumValid if not valid
                //TODO: Look at the logic here, as a change checkDocumentMediumValid kinda makes
                // the if redundant
            }
        }
        // Expecting a standard UUID of length 32.
        // The systemId column in the database should be indexed as unique, so this should guarantee uniqueness within
        // the table, but not within the database. Potential problem for extractions if the importer doesn't ensure
        // uniqueness via systemId
        if (entity.getSystemId() == null) {
            entity.setSystemId(UUID.randomUUID().toString());
        } else if (entity.getSystemId().length() != Constants.UUIDLength) {
            String randomUUID = UUID.randomUUID().toString();
            logger.info("Noark object of type [ " + entity.getClass().getName() +
                    "] does not have a valid UUID. Original value [" + entity.getSystemId()
                    + entity.toString() + "] is being changed and assigned with [" + randomUUID + "]");
            entity.setSystemId(randomUUID);
        }

        if (entity.getCreatedDate() == null) {
            logger.info("Noark object of type [ " + entity.getClass().getName() +
                    "] does not have a createdDate value. createdDate is being set to [" + new Date().toString() + "]");
            entity.setCreatedDate(new Date());
        }

        if (entity.getCreatedBy() == null) {
            logger.info("Noark object of type [ " + entity.getClass().getName() +
                    "] does not have a createdBy value. createdBy is being set to [" + username + "]");
            entity.setCreatedBy(username);
        }

        if (entity.getOwnedBy() == null) {
            logger.info("Noark object of type [ " + entity.getClass().getName() +
                    "] does not have a createdBy value. createdBy is being set to [" + username + "]");
            entity.setOwnedBy(username);
        }

        entity.setDeleted(false);
    }
}
