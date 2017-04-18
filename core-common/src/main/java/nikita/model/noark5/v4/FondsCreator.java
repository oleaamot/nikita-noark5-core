package nikita.model.noark5.v4;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.model.noark5.v4.interfaces.entities.IFondsCreatorEntity;
import nikita.util.deserialisers.FondsCreatorDeserializer;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static nikita.config.N5ResourceMappings.FONDS_CREATOR;

@Entity
@Table(name = "fonds_creator")
// Enable soft delete of Fonds
@SQLDelete(sql = "UPDATE fonds_creator SET deleted = true WHERE id = ?")
@Where(clause = "deleted <> true")
@JsonDeserialize(using = FondsCreatorDeserializer.class)
public class FondsCreator implements IFondsCreatorEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_fonds_creator_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique = true)
    @Audited
    protected String systemId;

    /**
     * M006 - arkivskaperID (xs:string)
     */
    @Column(name = "fonds_creator_id")
    @Audited
    protected String fondsCreatorId;

    /**
     * M023 - arkivskaperNavn (xs:string)
     */
    @Column(name = "fonds_creator_name")
    @Audited
    protected String fondsCreatorName;

    /**
     * M021 - beskrivelse (xs:string)
     */
    @Column(name = "description")
    @Audited
    protected String description;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    @Version
    @Column(name = "version")
    protected Long version;
    // Links to Fonds
    @ManyToMany(mappedBy = "referenceFondsCreator")
    protected Set<Fonds> referenceFonds = new HashSet<Fonds>();
    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

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

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
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
        return "FondsCreator{" +
                "description='" + description + '\'' +
                ", fondsCreatorName='" + fondsCreatorName + '\'' +
                ", fondsCreatorId='" + fondsCreatorId + '\'' +
                ", systemId='" + systemId + '\'' +
                ", version='" + version + '\'' +
                ", id=" + id +
                '}';
    }
}
