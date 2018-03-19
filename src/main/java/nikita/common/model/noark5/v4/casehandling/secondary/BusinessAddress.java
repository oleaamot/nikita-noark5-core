package nikita.common.model.noark5.v4.casehandling.secondary;

import javax.persistence.*;

/**
 * Created by tsodring on 5/14/17.
 */
@Entity
@Table(name = "business_address")
@AttributeOverride(name = "id", column = @Column(name = "pk_business_address_id"))
public class BusinessAddress extends SimpleAddress {

    @OneToOne(mappedBy = "businessAddress")
    CorrespondencePartUnit correspondencePartUnit;

    public CorrespondencePartUnit getCorrespondencePartUnit() {
        return correspondencePartUnit;
    }

    public void setCorrespondencePartUnit(CorrespondencePartUnit correspondencePartUnit) {
        this.correspondencePartUnit = correspondencePartUnit;
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
