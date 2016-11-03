package no.arkivlab.hioa.nikita.webapp.util.validation;

import static nikita.config.N5ResourceMappings.DOCUMENT_MEDIUM_ELECTRONIC;
import static nikita.config.N5ResourceMappings.DOCUMENT_MEDIUM_MIXED;
import static nikita.config.N5ResourceMappings.DOCUMENT_MEDIUM_PHYSICAL;

public final class Utils {

    private final static String [] documentMedium = {DOCUMENT_MEDIUM_ELECTRONIC, DOCUMENT_MEDIUM_PHYSICAL, DOCUMENT_MEDIUM_MIXED};

    // You cannot instantiate me!
    private Utils() {}

    public static boolean checkDocumentMediumValid(String documentMediumToCheck) {

        if (documentMediumToCheck != null) {
            for (String s : documentMedium) {
                if (s.equals(documentMediumToCheck)) {
                    return true;
                }
            }
        }
        return false;
    }

//    public final static class FondsValidation {
 //   }
}
