package nikita.webapp.web.events;

import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;

import javax.validation.constraints.NotNull;

public class AfterNoarkEntityCreatedEvent extends AfterNoarkEntityEvent {

    public AfterNoarkEntityCreatedEvent(Object source, @NotNull INikitaEntity entity) {
        super(source, entity);
    }
}
