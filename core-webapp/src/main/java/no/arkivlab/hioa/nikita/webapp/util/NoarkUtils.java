package no.arkivlab.hioa.nikita.webapp.util;


import nikita.model.noark5.v4.interfaces.IDocumentMedium;
import nikita.model.noark5.v4.interfaces.entities.*;
import nikita.util.exceptions.NikitaException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.UUID;

import static nikita.config.N5ResourceMappings.*;

public final class NoarkUtils {

    private final static String[] documentMedium = {DOCUMENT_MEDIUM_ELECTRONIC, DOCUMENT_MEDIUM_PHYSICAL,
            DOCUMENT_MEDIUM_MIXED};

    // You shall not instantiate me!
    private NoarkUtils() {
    }


    public static final class NoarkEntity {

        public static final class Create {
            /**
             *
             * @param noarkEntity
             *
             * A helper method to set default values for most noark entities. This reduces *a lot* of redundant code
             */

            public static void setNoarkEntityValues(INoarkGeneralEntity noarkEntity) {
                // Required Noark values
                setSystemIdEntityValues(noarkEntity);
                setCreateEntityValues(noarkEntity);
                // Nikita values
                setNikitaEntityValues(noarkEntity);
            }

            public static void setNikitaEntityValues(INikitaEntity nikitaEntity) {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                // Nikita values
                nikitaEntity.setOwnedBy(username);
                nikitaEntity.setDeleted(false);
            }

            public static void setSystemIdEntityValues(INoarkSystemIdEntity systemIdEntity) {
                // Required Noark values
                systemIdEntity.setSystemId(UUID.randomUUID().toString());
            }


            public static void setCreateEntityValues(INoarkCreateEntity createEntity) {
                String username = SecurityContextHolder.getContext().getAuthentication().getName();
                if (username == null) {
                    throw new NikitaException("Security context problem. username is null! Cannot continue with " +
                            "this request!");
                }
                createEntity.setCreatedDate(new Date());
                createEntity.setCreatedBy(username);
            }

            public static void setFinaliseEntityValues(INoarkFinaliseEntity finaliseEntity) {
                finaliseEntity.setFinalisedDate(null);
                finaliseEntity.setFinalisedBy(null);
            }


            public static boolean checkDocumentMediumValid(IDocumentMedium documentMediumEntity) {
                // Assume there will not be a valid value
                boolean invalidValueFound = false;

                if (documentMediumEntity != null && documentMediumEntity.getDocumentMedium() != null) {
                    for (String s : documentMedium) {
                        // if a valid value is found, then all is OK
                        if (s.equals(documentMediumEntity.getDocumentMedium())) {
                            invalidValueFound = true;
                        }
                    }
                }
                // null is also a valid value
                else {
                    invalidValueFound = true;
                }
                // No valid value for documentMedium was found this is a problem
                // throw an exception back to the caller
                if (!invalidValueFound) {
                    throw new NikitaException("Document medium not a valid document medium. Object causing this is " +
                            documentMediumEntity.toString());
                }
                return invalidValueFound;
            }
        }


    }

}
