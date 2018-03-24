package nikita.common.model.noark5.v4.metadata;

import nikita.common.config.N5ResourceMappings;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

// Noark 5v4 postnummer
@Entity
@Table(name = "postal_code")

@AttributeOverride(name = "id", column = @Column(name = "pk_postal_code_id"))
public class PostalCode extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    /**
     * Kommunenummer (xs:string)
     */
    @Column(name = "municipality_number")
    @Audited
    protected String municipalitynumber;

    /**
     * Kommunenavn (xs:string)
     */
    @Column(name = "municipality_name")
    @Audited
    protected String municipalityname;

    /**
     * kategori  (xs:string)
     */
    @Column(name = "category")
    @Audited
    protected String category;

    public String getMunicipalitynumber() {
        return municipalitynumber;
    }

    public void setMunicipalitynumber(String municipalitynumber) {
        this.municipalitynumber = municipalitynumber;
    }

    public String getMunicipalityname() {
        return municipalityname;
    }

    public void setMunicipalityname(String municipalityname) {
        this.municipalityname = municipalityname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.POSTAL_NUMBER;
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
        PostalCode rhs = (PostalCode) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(municipalitynumber, rhs.municipalitynumber)
                .append(municipalityname, rhs.municipalityname)
                .append(category, rhs.category)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(municipalityname)
                .append(municipalitynumber)
                .append(category)
                .toHashCode();
    }
}
