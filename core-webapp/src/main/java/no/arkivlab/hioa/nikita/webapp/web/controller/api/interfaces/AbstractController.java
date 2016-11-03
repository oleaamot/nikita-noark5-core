package no.arkivlab.hioa.nikita.webapp.web.controller.api.interfaces;

import no.arkivlab.hioa.nikita.webapp.web.events.AfterResourceCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;

/**
 * Created by tsodring on 6/23/16.
 */
public abstract class AbstractController {


    private ApplicationEventPublisher publisher;

    public AbstractController() {

    }

    protected final void createInternal() {
        publisher.publishEvent(new AfterResourceCreatedEvent(this));
    }
}

