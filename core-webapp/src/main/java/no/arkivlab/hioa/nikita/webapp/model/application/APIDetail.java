package no.arkivlab.hioa.nikita.webapp.model.application;

import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.validation.constraints.NotNull;
import java.util.Objects;


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

    @Override
    public int hashCode() {
        return Objects.hash(href, rel, templated);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != getClass()) {
            return false;
        }
        APIDetail rhs = (APIDetail) other;
        return new EqualsBuilder()
                .append(href, rhs.href)
                .append(rel, rhs.rel)
                .append(templated, rhs.templated)
                .isEquals();
    }

    @Override
    public int compareTo(APIDetail otherDetail) {
        if (otherDetail != null) {
            return rel.compareTo(otherDetail.getRel());
        }
        return -1;
    }

    @Override
    public String toString() {
        return "APIDetail{" +
                "href='" + href + '\'' +
                ", rel='" + rel + '\'' +
                ", templated=" + templated +
                '}';
    }
}
