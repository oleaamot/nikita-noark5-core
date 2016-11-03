package nikita.model.noark5.v4;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "workflow")
// Enable soft delete of Workflow
@SQLDelete(sql="UPDATE workflow SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class Workflow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_workflow_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M660 flytTil (xs:string)
     */
    @Column(name = "workflow_to")
    @Audited
    protected String workflowTo;

    /**
     * M665 flytFra  (xs:string)
     */
    @Column(name = "workflow_from")
    @Audited
    protected String workflowFrom;

    /**
     * M661 - flytMottattDato (xs:dateTime)
     */
    @Column(name = "workflow_received_date")
    @Audited
    protected Date workflowReceivedDate;

    /**
     * M662 flytSendtDato (xs:dateTime)
     */
    @Column(name = "workflow_sent_date")
    @Audited
    protected Date workflowSentDate;

    /**
     * M663 flytStatus (xs:string)
     */
    @Column(name = "workflow_status")
    @Audited
    protected String workflowStatus;

    /**
     * M664 flytMerknad (xs:string)
     */
    @Column(name = "workflow_comment")
    @Audited
    protected String workflowComment;

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    // Link to Series
    @ManyToOne
    @JoinColumn(name = "work_flow_registry_entry_id", referencedColumnName = "pk_record_id")
    protected RegistryEntry referenceRegistryEntry;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWorkflowTo() {
        return workflowTo;
    }

    public void setWorkflowTo(String workflowTo) {
        this.workflowTo = workflowTo;
    }

    public String getWorkflowFrom() {
        return workflowFrom;
    }

    public void setWorkflowFrom(String workflowFrom) {
        this.workflowFrom = workflowFrom;
    }

    public Date getWorkflowReceivedDate() {
        return workflowReceivedDate;
    }

    public void setWorkflowReceivedDate(Date workflowReceivedDate) {
        this.workflowReceivedDate = workflowReceivedDate;
    }

    public Date getWorkflowSentDate() {
        return workflowSentDate;
    }

    public void setWorkflowSentDate(Date workflowSentDate) {
        this.workflowSentDate = workflowSentDate;
    }

    public String getWorkflowStatus() {
        return workflowStatus;
    }

    public void setWorkflowStatus(String workflowStatus) {
        this.workflowStatus = workflowStatus;
    }

    public String getWorkflowComment() {
        return workflowComment;
    }

    public void setWorkflowComment(String workflowComment) {
        this.workflowComment = workflowComment;
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

    public RegistryEntry getReferenceRegistryEntry() {
        return referenceRegistryEntry;
    }

    public void setReferenceRegistryEntry(RegistryEntry referenceRegistryEntry) {
        this.referenceRegistryEntry = referenceRegistryEntry;
    }

    @Override
    public String toString() {
        return "Workflow{" +
                "workflowComment='" + workflowComment + '\'' +
                ", workflowStatus='" + workflowStatus + '\'' +
                ", workflowSentDate=" + workflowSentDate +
                ", workflowReceivedDate=" + workflowReceivedDate +
                ", workflowFrom='" + workflowFrom + '\'' +
                ", workflowTo='" + workflowTo + '\'' +
                ", id=" + id +
                '}';
    }
}