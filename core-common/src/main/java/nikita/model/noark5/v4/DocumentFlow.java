package nikita.model.noark5.v4;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "documentflow")
// Enable soft delete of DocumentFlow
@SQLDelete(sql="UPDATE documentflow SET deleted = true WHERE id = ?")
@Where(clause="deleted <> true")
public class DocumentFlow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pk_flow_id", nullable = false, insertable = true, updatable = false)
    protected Long id;

    /**
     * M660 flytTil (xs:string)
     */
    @Column(name = "flow_to")
    @Audited
    protected String flowTo;

    /**
     * M665 flytFra  (xs:string)
     */
    @Column(name = "flow_from")
    @Audited
    protected String flowFrom;

    /**
     * M661 - flytMottattDato (xs:dateTime)
     */
    @Column(name = "flow_received_date")
    @Audited
    protected Date flowReceivedDate;

    /**
     * M662 flytSendtDato (xs:dateTime)
     */
    @Column(name = "flow_sent_date")
    @Audited
    protected Date flowSentDate;

    /**
     * M663 flytStatus (xs:string)
     */
    @Column(name = "flow_status")
    @Audited
    protected String flowStatus;

    /**
     * M664 flytMerknad (xs:string)
     */
    @Column(name = "flow_comment")
    @Audited
    protected String flowComment;

    // Used for soft delete.
    @Column(name = "deleted")
    @Audited
    private Boolean deleted;

    @Column(name = "owned_by")
    @Audited
    protected String ownedBy;

    // Link to Series
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_flow_registry_entry_id", referencedColumnName = "pk_record_id")
    protected RegistryEntry referenceRegistryEntry;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlowTo() {
        return flowTo;
    }

    public void setFlowTo(String flowTo) {
        this.flowTo = flowTo;
    }

    public String getFlowFrom() {
        return flowFrom;
    }

    public void setFlowFrom(String flowFrom) {
        this.flowFrom = flowFrom;
    }

    public Date getFlowReceivedDate() {
        return flowReceivedDate;
    }

    public void setFlowReceivedDate(Date flowReceivedDate) {
        this.flowReceivedDate = flowReceivedDate;
    }

    public Date getFlowSentDate() {
        return flowSentDate;
    }

    public void setFlowSentDate(Date flowSentDate) {
        this.flowSentDate = flowSentDate;
    }

    public String getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(String flowStatus) {
        this.flowStatus = flowStatus;
    }

    public String getFlowComment() {
        return flowComment;
    }

    public void setFlowComment(String flowComment) {
        this.flowComment = flowComment;
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
        return "Flow{" +
                "flowComment='" + flowComment + '\'' +
                ", flowStatus='" + flowStatus + '\'' +
                ", flowSentDate=" + flowSentDate +
                ", flowReceivedDate=" + flowReceivedDate +
                ", flowFrom='" + flowFrom + '\'' +
                ", flowTo='" + flowTo + '\'' +
                ", id=" + id +
                '}';
    }
}