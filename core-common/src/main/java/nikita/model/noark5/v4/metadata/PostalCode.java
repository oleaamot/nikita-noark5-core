package nikita.model.noark5.v4.metadata;

import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Field;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.POSTAL_NUMBER;

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
    @Field
    protected String municipalitynumber;

    /**
     * Kommunenavn (xs:string)
     */
    @Column(name = "municipality_name")
    @Audited
    @Field
    protected String municipalityname;

    /**
     * kategori  (xs:string)
     */
    @Column(name = "category")
    @Audited
    @Field
    protected String category;

    @Override
    public String getBaseTypeName() {
        return POSTAL_NUMBER;
    }
}
