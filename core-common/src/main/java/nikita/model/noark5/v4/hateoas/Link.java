package nikita.model.noark5.v4.hateoas;

import org.apache.commons.lang3.builder.EqualsBuilder;

import javax.validation.constraints.NotNull;

/**
 * Created by tsodring on 12/22/16.
 */
public class Link implements Comparable<Link> {

    private String linkName;
    private String href;
    private String rel;
    private Boolean templated;

    public Link(@NotNull String href, @NotNull String rel, @NotNull Boolean templated) {
        this.href = href;
        this.rel = rel;
        this.templated = templated;
    }

    public Link(String linkName, @NotNull String href, @NotNull String rel, @NotNull Boolean templated) {
        this.linkName = linkName;
        this.href = href;
        this.rel = rel;
        this.templated = templated;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
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

    public void setTemplated(@NotNull Boolean templated) {
        this.templated = templated;
    }

    @Override
    public int hashCode() {
	return rel.hashCode();
    }

    @Override
    public int compareTo(Link other) {
        if (other == null)
	    return -1;
	return rel.compareTo(other.getRel());
    }

    @Override
    public boolean equals(Object other) {
	if (other == this)
	    return true;
	if (!(other instanceof Link))
	    return false;
        Link link = (Link) other;
        return new EqualsBuilder()
	    .append(this.rel, link.getRel())
	    .isEquals();
    }
    
    @Override
    public String toString() {
        return "Link " + linkName + " {" +
                "href='" + href + '\'' +
                ", rel='" + rel + '\'' +
                ", templated=" + templated +
                '}';
    }
}
