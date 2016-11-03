package no.arkivlab.hioa.nikita.webapp.web.events;


import org.springframework.context.ApplicationEvent;

public class AfterResourceCreatedEvent { //extends ApplicationEvent {

    public AfterResourceCreatedEvent(Object source) {
    //    super(source);
    }

    public String toString(){
        return "A new resource was created";
    }
}
