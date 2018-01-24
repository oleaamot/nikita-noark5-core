package nikita.model.noark5.v4.casehandling.secondary;

import nikita.model.noark5.v4.admin.AdministrativeUnit;
import nikita.model.noark5.v4.admin.User;
import nikita.model.noark5.v4.casehandling.RegistryEntry;
import nikita.model.noark5.v4.interfaces.entities.casehandling.ICorrespondencePartInternalEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Set;
import java.util.TreeSet;

import static nikita.config.N5ResourceMappings.CORRESPONDENCE_PART_UNIT;

@Entity
@Table(name = "correspondence_part_internal")
//@JsonDeserialize(using = CorrespondencePartPersonInternal.class)
@AttributeOverride(name = "id", column = @Column(name = "pk_correspondence_part_person_id"))
public class CorrespondencePartInternal extends CorrespondencePart implements ICorrespondencePartInternalEntity {


    /**
     * M305 - administrativEnhet (xs:string)
     */
    @Column(name = "administrative_unit")
    @Audited
    private String administrativeUnit;

    @ManyToOne
    private AdministrativeUnit referenceAdministrativeUnit;

    /**
     * M307 - saksbehandler (xs:string)
     */
    @Column(name = "case_handler")
    @Audited
    private String caseHandler;

    @ManyToOne
    private User referenceCaseHandler;

    // Links to RegistryEntry
    @ManyToMany(mappedBy = "referenceCorrespondencePartInternal")
    private Set<RegistryEntry> referenceRegistryEntry = new TreeSet<>();

    public String getAdministrativeUnit() {
        return administrativeUnit;
    }

    public void setAdministrativeUnit(String administrativeUnit) {
        this.administrativeUnit = administrativeUnit;
    }

    public AdministrativeUnit getReferenceAdministrativeUnit() {
        return referenceAdministrativeUnit;
    }

    public void setReferenceAdministrativeUnit(AdministrativeUnit referenceAdministrativeUnit) {
        this.referenceAdministrativeUnit = referenceAdministrativeUnit;
    }

    public String getCaseHandler() {
        return caseHandler;
    }

    public void setCaseHandler(String caseHandler) {
        this.caseHandler = caseHandler;
    }

    public User getReferenceCaseHandler() {
        return referenceCaseHandler;
    }

    public void setReferenceCaseHandler(User referenceCaseHandler) {
        this.referenceCaseHandler = referenceCaseHandler;
    }

    @Override
    public String getBaseTypeName() {
        return CORRESPONDENCE_PART_UNIT;
    }

    @Override
    public Set<RegistryEntry> getReferenceRegistryEntry() {
        return referenceRegistryEntry;
    }

    @Override
    public void setReferenceRegistryEntry(Set<RegistryEntry> referenceRegistryEntry) {
        this.referenceRegistryEntry = referenceRegistryEntry;
    }

    @Override
    public String toString() {
        return "CorrespondencePartInternal{" + super.toString() +
                "administrativeUnit='" + administrativeUnit + '\'' +
                ", caseHandler='" + caseHandler + '\'' +
                '}';
    }

}
