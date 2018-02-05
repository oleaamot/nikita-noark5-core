package nikita.model.noark5.v4;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.model.noark5.v4.interfaces.entities.IFondsCreatorEntity;
import nikita.util.deserialisers.FondsCreatorDeserializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static nikita.config.N5ResourceMappings.FONDS_CREATOR;

@Entity
@Table(name = "fonds_creator")
// Enable soft delete of Fonds
// @SQLDelete(sql = "UPDATE fonds_creator SET deleted = true WHERE pk_fonds_creator_id = ? and version = ?")
// @Where(clause = "deleted <> true")
@JsonDeserialize(using = FondsCreatorDeserializer.class)
@AttributeOverride(name = "id", column = @Column(name = "pk_fonds_creator_id"))
public class FondsCreator extends NoarkEntity implements IFondsCreatorEntity {

    /**
     * M006 - arkivskaperID (xs:string)
     */
    @NotNull
    @Column(name = "fonds_creator_id", nullable = false)
    @Audited
    private String fondsCreatorId;

    /**
     * M023 - arkivskaperNavn (xs:string)
     */
    @NotNull
    @Column(name = "fonds_creator_name", nullable = false)
    @Audited
    private String fondsCreatorName;

    /**
     * M021 - beskrivelse (xs:string)
     */
    @Column(name = "description")
    @Audited
    private String description;

    // Links to Fonds
    @ManyToMany(mappedBy = "referenceFondsCreator")
    private List<Fonds> referenceFonds = new ArrayList<>();

    public String getFondsCreatorId() {
        return fondsCreatorId;
    }

    public void setFondsCreatorId(String fondsCreatorId) {
        this.fondsCreatorId = fondsCreatorId;
    }

    public String getFondsCreatorName() {
        return fondsCreatorName;
    }

    public void setFondsCreatorName(String fondsCreatorName) {
        this.fondsCreatorName = fondsCreatorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getBaseTypeName() {
        return FONDS_CREATOR;
    }

    public List<Fonds> getReferenceFonds() {
        return referenceFonds;
    }

    public void setReferenceFonds(List<Fonds> referenceFonds) {
        this.referenceFonds = referenceFonds;
    }

    public void addFonds(Fonds fonds) {
        referenceFonds.add(fonds);
    }

    @Override
    public String toString() {
        return "FondsCreator{" + super.toString() +
                ", fondsCreatorId='" + fondsCreatorId + '\'' +
                ", fondsCreatorName='" + fondsCreatorName + '\'' +
                ", description='" + description + '\'' +
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
        FondsCreator rhs = (FondsCreator) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(fondsCreatorId, rhs.fondsCreatorId)
                .append(fondsCreatorName, rhs.fondsCreatorName)
                .append(description, rhs.description)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(fondsCreatorId)
                .append(fondsCreatorName)
                .append(description)
                .toHashCode();
    }
}
