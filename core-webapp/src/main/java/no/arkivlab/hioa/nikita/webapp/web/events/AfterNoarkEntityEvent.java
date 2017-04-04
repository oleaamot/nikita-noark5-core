package no.arkivlab.hioa.nikita.webapp.web.events;


import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import org.springframework.context.ApplicationEvent;

import javax.validation.constraints.NotNull;

public class AfterNoarkEntityEvent extends ApplicationEvent {

    INikitaEntity entity;

    public AfterNoarkEntityEvent(Object source, @NotNull INikitaEntity entity) {
        super(source);
        this.entity = entity;
    }

    public String toString() {
        return entity.toString();
    }

    public INikitaEntity getEntity() {
        return entity;
    }
}
