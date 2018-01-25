package nikita.model.noark5.v4.casehandling;

import nikita.model.noark5.v4.NoarkEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;

import static nikita.config.Constants.NOARK_CASE_HANDLING_PATH;
import static nikita.config.N5ResourceMappings.DOCUMENT_FLOW;

@Entity
@Table(name = "document_flow")
// Enable soft delete of DocumentFlow
// @SQLDelete(sql="UPDATE document_flow SET deleted = true WHERE pk_flow_id = ? and version = ?")
// @Where(clause="deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_flow_id"))
public class DocumentFlow extends NoarkEntity {

    /**
     * M660 flytTil (xs:string)
     */
    @Column(name = "flow_to")
    @Audited
    private String flowTo;

    /**
     * M665 flytFra  (xs:string)
     */
    @Column(name = "flow_from")
    @Audited
    private String flowFrom;

    /**
     * M661 - flytMottattDato (xs:dateTime)
     */
    @Column(name = "flow_received_date")
    @Audited
    private Date flowReceivedDate;

    /**
     * M662 flytSendtDato (xs:dateTime)
     */
    @Column(name = "flow_sent_date")
    @Audited
    private Date flowSentDate;

    /**
     * M663 flytStatus (xs:string)
     */
    @Column(name = "flow_status")
    @Audited
    private String flowStatus;

    /**
     * M664 flytMerknad (xs:string)
     */
    @Column(name = "flow_comment")
    @Audited
    private String flowComment;

    // Link to Series
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_flow_registry_entry_id", referencedColumnName = "pk_record_id")
    private RegistryEntry referenceRegistryEntry;

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

    @Override
    public String getBaseTypeName() {
        return DOCUMENT_FLOW;
    }

    @Override
    public String getFunctionalTypeName() {
        return NOARK_CASE_HANDLING_PATH;
    }

    public RegistryEntry getReferenceRegistryEntry() {
        return referenceRegistryEntry;
    }

    public void setReferenceRegistryEntry(RegistryEntry referenceRegistryEntry) {
        this.referenceRegistryEntry = referenceRegistryEntry;
    }

    @Override
    public String toString() {
        return "Flow{" + super.toString() + 
                ", flowComment='" + flowComment + '\'' +
                ", flowStatus='" + flowStatus + '\'' +
                ", flowSentDate=" + flowSentDate +
                ", flowReceivedDate=" + flowReceivedDate +
                ", flowFrom='" + flowFrom + '\'' +
                ", flowTo='" + flowTo + '\'' +
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
        DocumentFlow rhs = (DocumentFlow) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(flowComment, rhs.flowComment)
                .append(flowStatus, rhs.flowStatus)
                .append(flowSentDate, rhs.flowSentDate)
                .append(flowReceivedDate, rhs.flowReceivedDate)
                .append(flowFrom, rhs.flowFrom)
                .append(flowTo, rhs.flowTo)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(flowComment)
                .append(flowStatus)
                .append(flowSentDate)
                .append(flowReceivedDate)
                .append(flowFrom)
                .append(flowTo)
                .toHashCode();
    }
}
