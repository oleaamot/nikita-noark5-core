package nikita.model.noark5.v4;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fonds_creator")
// Enable soft delete of Fonds
@SQLDelete(sql="UPDATE fonds_creator SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class FondsCreator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_fonds_creator_id", nullable = false, insertable = true, updatable = false)
    protected long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id")
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

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    // Links to Fonds
    @ManyToMany(mappedBy = "referenceFondsCreator")
    protected Set<Fonds> referenceFonds = new HashSet<Fonds>();

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

    public Set<Fonds> getReferenceFonds() {
        return referenceFonds;
    }

    public void setReferenceFonds(Set<Fonds> referenceFonds) {
        this.referenceFonds = referenceFonds;
    }

    @Override
    public String toString() {
        return "FondsCreator{" +
                "description='" + description + '\'' +
                ", fondsCreatorName='" + fondsCreatorName + '\'' +
                ", fondsCreatorId='" + fondsCreatorId + '\'' +
                ", systemId='" + systemId + '\'' +
                ", id=" + id +
                '}';
    }
}
