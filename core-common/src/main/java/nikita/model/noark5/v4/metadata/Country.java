package nikita.model.noark5.v4.metadata;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import static nikita.config.N5ResourceMappings.COUNTRY;

// Noark 5v4 Land
@Entity
@Table(name = "country")
@AttributeOverride(name = "id", column = @Column(name = "pk_country_id"))
public class Country extends MetadataSuperClass {
    private static final long serialVersionUID = 1L;

    @Override
    public String getBaseTypeName() {
        return COUNTRY;
    }
}
