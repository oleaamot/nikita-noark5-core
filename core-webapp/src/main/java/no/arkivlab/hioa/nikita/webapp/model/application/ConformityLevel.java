package no.arkivlab.hioa.nikita.webapp.model.application;

import javax.validation.constraints.NotNull;

/**
 * Used to identify a single conformity instance with the Noark interface standard
 *
 *
 * The following is an example of this:
 * "href" : "http://localhost:8092/noark5v4/hateoas-api/arkivstruktur",
 *  "rel" : "http://rel.kxml.no/noark5/v4/api/arkivstruktur"
 *
 *
 * Also used to identify conformity with additional interfaces. The example below shows
 * nikitas own GUI being identified as a usable service by the core
 *
 * "href" : "http://localhost:8092/noark5v4/gui",
 * "rel" : "http://nikita.arkivlab.no/noark5/v4/gui"
 */
public class ConformityLevel {
    protected String href;
    protected String rel;

    public ConformityLevel() {
    }

    public String getHref() {
        return href;
    }

    public void setHref(@NotNull String href) {
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(@NotNull String rel) {
        this.rel = rel;
    }
}