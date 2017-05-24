package nikita.model.noark5.v4.casehandling.secondary;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by tsodring on 5/14/17.
 */
@Embeddable
public class PostalNumber {

    public PostalNumber() {
    }

    public PostalNumber(String postalNumber) {
        this.postalNumber = postalNumber;
    }

    /**
     * M407 - postnummer (xs:string)
     */
    @Column(name = "postal_number")
    private String postalNumber;

    public String getPostalNumber() {
        return postalNumber;
    }

    public void setPostalNumber(String postalNumber) {
        this.postalNumber = postalNumber;
    }
}
