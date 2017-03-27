package no.arkivlab.hioa.nikita.webapp.model.application;

import javax.validation.constraints.NotNull;

public class APIDetail {

    private String href;
    private String rel;
    private Boolean templated;

    public APIDetail(@NotNull String href, @NotNull String rel,
                     @NotNull Boolean templated) {
        this.href = href;
        this.rel = rel;
        this.templated = templated;
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

    @Override
    public String toString() {
        return "FondsStructureDetail{" +
                "href='" + href + '\'' +
                ", rel='" + rel + '\'' +
                ", templated=" + templated +
                '}';
    }
}
