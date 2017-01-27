package nikita.model.noark5.v4;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "keyword")
// Enable soft delete of Keyword
@SQLDelete(sql="UPDATE keyword SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class Keyword implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_keyword_id", nullable = false, insertable = true, updatable = false)
    protected long id;

    /**
     * M001 - systemID (xs:string)
     */
    @Column(name = "system_id", unique=true)
    @Audited
    protected String systemId;

    /**
     * M022 - noekkelord (xs:string)
     */
    @Column(name = "keyword")
    @Audited
    protected String keyword;

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    // Links to Class
    @ManyToMany(mappedBy = "referenceKeyword")
    protected Set<Class> referenceClass = new HashSet<Class>();

    // Links to File
    @ManyToMany(mappedBy = "referenceKeyword")
    protected Set<File> referenceFile = new HashSet<File>();

    // Links to BasicRecord
    @ManyToMany(mappedBy = "referenceKeyword")
    protected Set<BasicRecord> referenceBasicRecord = new HashSet<BasicRecord>();

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

    public Set<Class> getReferenceClass() {
        return referenceClass;
    }

    public void setReferenceClass(Set<Class> referenceClass) {
        this.referenceClass = referenceClass;
    }

    public Set<File> getReferenceFile() {
        return referenceFile;
    }

    public void setReferenceFile(Set<File> referenceFile) {
        this.referenceFile = referenceFile;
    }

    public Set<BasicRecord> getReferenceBasicRecord() {
        return referenceBasicRecord;
    }

    public void setReferenceBasicRecord(Set<BasicRecord> referenceBasicRecord) {
        this.referenceBasicRecord = referenceBasicRecord;
    }

    @Override
    public String toString() {
        return "Keyword{" +
                "keyword='" + keyword + '\'' +
                ", systemId='" + systemId + '\'' +
                ", id=" + id +
                '}';
    }
}
