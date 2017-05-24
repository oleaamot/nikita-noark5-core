package nikita.model.noark5.v4.casehandling.secondary;

import javax.persistence.*;

/**
 * Created by tsodring on 5/14/17.
 */
@Entity
@Table(name = "postal_address")
@AttributeOverride(name = "id", column = @Column(name = "pk_postal_address_id"))
public class PostalAddress extends SimpleAddress {

    @OneToOne(mappedBy = "postalAddress")
    CorrespondencePartPerson correspondencePartPerson;

    @OneToOne(mappedBy = "postalAddress")
    CorrespondencePartUnit correspondencePartUnit;
}
