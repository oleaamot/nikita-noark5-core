package no.arkivlab.hioa.nikita.webapp.model.application;

import javax.validation.constraints.NotNull;

public class APIDetail implements Comparable <APIDetail>{

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

    public void setHref(@NotNull String href) {
        this.href = href;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(@NotNull String rel) {
        this.rel = rel;
    }

    public Boolean getTemplated() {
        return templated;
    }

    public void setTemplated(Boolean templated) {
        this.templated = templated;
    }

    public int compareTo(APIDetail otherDetail) {
        if (otherDetail != null) {
            return rel.compareTo(otherDetail.getRel());
        }
        return -1;
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
