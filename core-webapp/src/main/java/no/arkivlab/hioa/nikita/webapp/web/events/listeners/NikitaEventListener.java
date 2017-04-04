package no.arkivlab.hioa.nikita.webapp.web.events.listeners;

import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityCreatedEvent;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityDeletedEvent;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityUpdatedEvent;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by tsodring on 4/4/17.
 * <p>
 * Event listeners to be able to respond when CUD operations have occurred. Currently the plan is to use
 * them for blockchain integration or to republish information in another Noark core.
 */

@Async
@Component
public class NikitaEventListener {

    private static final Log logger = LogFactory.getLog("NikitaEventListener");

    @EventListener
    public void handleCreationEvent(AfterNoarkEntityCreatedEvent event) {
        logger.info("Nikita created an object of type [" + event.getEntity().getClass().getSimpleName() + "], " + event.toString());
    }

    @EventListener
    public void handleUpdateEvent(AfterNoarkEntityUpdatedEvent event) {
        logger.info("Nikita updated an object of type [" + event.getEntity().getClass().getSimpleName() + "], " + event.toString());
    }

    @EventListener
    public void handleDeletionEvent(AfterNoarkEntityDeletedEvent event) {
        logger.info("Nikita deleted an object of type [" + event.getEntity().getClass().getSimpleName() + "], " + event.toString());
    }
}
