package nikita.model.noark5.v4;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.model.noark5.v4.interfaces.entities.IFondsCreatorEntity;
import nikita.util.deserialisers.FondsCreatorDeserializer;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.TreeSet;
import java.util.Set;

import static nikita.config.N5ResourceMappings.FONDS_CREATOR;

@Entity
@Table(name = "fonds_creator")
// Enable soft delete of Fonds
@SQLDelete(sql = "UPDATE fonds_creator SET deleted = true WHERE id = ?")
@Where(clause = "deleted <> true")
@JsonDeserialize(using = FondsCreatorDeserializer.class)
@AttributeOverride(name = "id", column = @Column(name = "pk_fonds_creator_id"))
public class FondsCreator extends NoarkEntity implements IFondsCreatorEntity {

    /**
     * M006 - arkivskaperID (xs:string)
     */
    @Column(name = "fonds_creator_id")
    @Audited
    private String fondsCreatorId;

    /**
     * M023 - arkivskaperNavn (xs:string)
     */
    @Column(name = "fonds_creator_name")
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
    private Set<Fonds> referenceFonds = new TreeSet<>();

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

    public Set<Fonds> getReferenceFonds() {
        return referenceFonds;
    }

    public void setReferenceFonds(Set<Fonds> referenceFonds) {
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
}
