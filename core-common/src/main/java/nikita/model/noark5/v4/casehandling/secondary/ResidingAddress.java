package nikita.model.noark5.v4.casehandling.secondary;

import javax.persistence.*;

/**
 * Created by tsodring on 5/14/17.
 */
@Entity
@Table(name = "residing_address")
@AttributeOverride(name = "id", column = @Column(name = "pk_residing_address_id"))
public class ResidingAddress extends SimpleAddress {

    @OneToOne(mappedBy = "residingAddress")
    CorrespondencePartPerson correspondencePartPerson;

    public CorrespondencePartPerson getCorrespondencePartPerson() {
        return correspondencePartPerson;
    }

    public void setCorrespondencePartPerson(CorrespondencePartPerson correspondencePartPerson) {
        this.correspondencePartPerson = correspondencePartPerson;
    }
}
