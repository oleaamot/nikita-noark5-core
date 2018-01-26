package nikita.model.noark5.v4.casehandling.secondary;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by tsodring on 5/14/17.
 */
@Embeddable
public class PostalNumber {

    /**
     * M407 - postnummer (xs:string)
     */
    @Column(name = "postal_number")
    private String postalNumber;

    public PostalNumber() {
    }

    public PostalNumber(String postalNumber) {
        this.postalNumber = postalNumber;
    }

    public String getPostalNumber() {
        return postalNumber;
    }

    public void setPostalNumber(String postalNumber) {
        this.postalNumber = postalNumber;
    }

    @Override
    public String toString() {
        return "PostalNumber{" +
                "postalNumber='" + postalNumber + '\'' +
                '}';
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
        PostalNumber rhs = (PostalNumber) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(postalNumber, rhs.postalNumber)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(postalNumber)
                .toHashCode();
    }
}
