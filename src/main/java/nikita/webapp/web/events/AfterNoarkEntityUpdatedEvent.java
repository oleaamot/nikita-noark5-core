package nikita.webapp.web.events;


import nikita.common.model.noark5.v4.interfaces.entities.INikitaEntity;

import javax.validation.constraints.NotNull;

public class AfterNoarkEntityUpdatedEvent extends AfterNoarkEntityEvent {

    public AfterNoarkEntityUpdatedEvent(Object source, @NotNull INikitaEntity entity) {
        super(source, entity);
    }
}
