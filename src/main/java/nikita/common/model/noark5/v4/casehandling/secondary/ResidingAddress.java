package nikita.common.model.noark5.v4.casehandling.secondary;

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

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
