package no.arkivlab.hioa.nikita.webapp.model.application;

import javax.validation.constraints.NotNull;

public class FondsStructureDetail {

    private String href;
    private String rel;
    private Boolean templated;
    private Boolean templatedSpecified;

    public FondsStructureDetail(@NotNull String href, @NotNull String rel,
                                @NotNull Boolean templated, @NotNull Boolean templatedSpecified) {
        this.href = href;
        this.rel = rel;
        this.templated = templated;
        this.templatedSpecified = templatedSpecified;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public Boolean getTemplated() {
        return templated;
    }

    public void setTemplated(Boolean templated) {
        this.templated = templated;
    }

    public Boolean getTemplatedSpecified() {
        return templatedSpecified;
    }

    public void setTemplatedSpecified(Boolean templatedSpecified) {
        this.templatedSpecified = templatedSpecified;
    }

    @Override
    public String toString() {
        return "FondsStructureDetail{" +
                "href='" + href + '\'' +
                ", rel='" + rel + '\'' +
                ", templated=" + templated +
                ", templatedSpecified=" + templatedSpecified +
                '}';
    }
}
