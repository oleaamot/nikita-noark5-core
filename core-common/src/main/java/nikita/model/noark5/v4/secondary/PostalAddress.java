package nikita.model.noark5.v4.secondary;

import nikita.model.noark5.v4.NoarkEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.TreeSet;
import java.util.Set;

import static nikita.config.N5ResourceMappings.POSTAL_ADDRESS;

@Entity
@Table(name = "postal_address")
// Enable soft delete of PostalAddress
@SQLDelete(sql="UPDATE postalAddress SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_postal_address_id"))
public class PostalAddress extends NoarkEntity{

    /**
     * M406 - postadresse (xs:string)
     */
    @Column(name = "postal_address")
    @Audited
    private String postalAddress;

    // Links to CorrespondencePart
    @ManyToMany(mappedBy = "postalAddress")
    private Set<CorrespondencePart> referenceCorrespondencePart = new TreeSet<>();

    public String getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(String postalAddress) {
        this.postalAddress = postalAddress;
    }

    public Set<CorrespondencePart> getReferenceCorrespondencePart() {
        return referenceCorrespondencePart;
    }

    public void setReferenceCorrespondencePart(Set<CorrespondencePart> referenceCorrespondencePart) {
        this.referenceCorrespondencePart = referenceCorrespondencePart;
    }

    @Override
    public String getBaseTypeName() {
        return POSTAL_ADDRESS;
    }

    @Override
    public String toString() {
        return "PostalAddress{" + super.toString() +
                "postalAddress='" + postalAddress + '\'' +
                '}';
    }
}
