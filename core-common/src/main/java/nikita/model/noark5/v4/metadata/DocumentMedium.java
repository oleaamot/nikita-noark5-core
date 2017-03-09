package nikita.model.noark5.v4.metadata;


import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.model.noark5.v4.interfaces.entities.INoarkSystemIdEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Field;

import javax.persistence.*;

@Entity
@Table(name = "document_medium")
// Enable soft delete of DocumentMedium
@SQLDelete(sql="UPDATE document_medium SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class DocumentMedium implements INikitaEntity, INoarkSystemIdEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_document_medium_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique = true)
    @Audited
    @Field
    protected String systemId;

    /**
     * M -  (xs:string)
     */
    @Column(name = "code")
    @Audited
    protected String code;

    /**
     * M021 - beskrivelse (xs:string)
     */
    @Column(name = "description")
    @Audited
    protected String description;
    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;
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

    @Override
    public String getSystemId() {
        return systemId;
    }

    @Override
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Boolean getDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String getOwnedBy() {
        return ownedBy;
    }

    @Override
    public void setOwnedBy(String ownedBy) {
        this.ownedBy = ownedBy;
    }

    @Override
    public String toString() {
        return "DocumentMedium{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", deleted=" + deleted +
                ", ownedBy='" + ownedBy + '\'' +
                '}';
    }
}
