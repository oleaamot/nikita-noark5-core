package nikita.model.noark5.v4.admin;

import nikita.model.noark5.v4.NoarkEntity;
import nikita.model.noark5.v4.interfaces.entities.admin.IRoleEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;

//@Entity
//@Table(name = "nikita_role")
public class Role extends NoarkEntity implements IRoleEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "role_seq")
    @SequenceGenerator(name = "role_seq", sequenceName = "role_seq", allocationSize = 1)
    private Long id;

    /**
     * M??? - rolle (xs:string)
     */
    @Column(name = "role")
    @Audited
    private String role;

    /**
     * M??? - tilgangskategori (xs:string)
     */
    @Column(name = "access_category")
    @Audited
    private String accesscategory;

    /**
     * M??? - referanseArkivenhet (xs:string)
     */
    @Column(name = "reference_entity")
    @Audited
    private String referenceEntity;

    /**
     * M??? - tilgangsrestriksjon (xs:string)
     */
    @Column(name = "access_restriction")
    @Audited
    private String accessRestriction;

    /**
     * M??? - les (xs:string)
     */
    @Column(name = "read")
    @Audited
    private Boolean read;

    /**
     * M??? - ny (xs:string)
     */
    @Column(name = "create")
    @Audited
    private Boolean create;

    /**
     * M??? - endre (xs:string)
     */
    @Column(name = "update")
    @Audited
    private Boolean update;

    /**
     * M??? - slett (xs:string)
     */
    @Column(name = "delete")
    @Audited
    private Boolean delete;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAccesscategory() {
        return accesscategory;
    }

    public void setAccesscategory(String accesscategory) {
        this.accesscategory = accesscategory;
    }

    public String getReferenceEntity() {
        return referenceEntity;
    }

    public void setReferenceEntity(String referenceEntity) {
        this.referenceEntity = referenceEntity;
    }

    public String getAccessRestriction() {
        return accessRestriction;
    }

    public void setAccessRestriction(String accessRestriction) {
        this.accessRestriction = accessRestriction;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean getCreate() {
        return create;
    }

    public void setCreate(Boolean create) {
        this.create = create;
    }

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    @Override
    public String toString() {
        return "Role{" + super.toString() +
                "role='" + role + '\'' +
                ", referenceEntity='" + referenceEntity + '\'' +
                ", read=" + read +
                ", create=" + create +
                ", update=" + update +
                ", delete=" + delete +
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
        Role rhs = (Role) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(referenceEntity, rhs.referenceEntity)
                .append(read, rhs.read)
                .append(create, rhs.create)
                .append(update, rhs.update)
                .append(delete, rhs.delete)
                .isEquals();
    }
}
