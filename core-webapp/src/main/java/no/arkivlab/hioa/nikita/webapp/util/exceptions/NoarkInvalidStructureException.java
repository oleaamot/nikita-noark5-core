package no.arkivlab.hioa.nikita.webapp.util.exceptions;

public class NoarkInvalidStructureException extends NikitaException{

    String description;

    public NoarkInvalidStructureException(final String message, String fromObjectName, String toObjectName) {
        super(message);
        description = "There was a problem associating a " + fromObjectName + " with " + toObjectName;

    }
}
