package nikita.common.model.noark5.v4.secondary;

import nikita.common.config.N5ResourceMappings;
import nikita.common.model.noark5.v4.NoarkEntity;
import nikita.common.model.noark5.v4.casehandling.RegistryEntry;
import nikita.common.model.noark5.v4.casehandling.secondary.CorrespondencePart;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "sign_off")
// Enable soft delete of SignOff
// @SQLDelete(sql="UPDATE sign_off SET deleted = true WHERE pk_sign_off_id = ? and version = ?")
// @Where(clause="deleted <> true")
@AttributeOverride(name = "id", column = @Column(name = "pk_sign_off_id"))
public class SignOff extends NoarkEntity {

    /**
     * M617 - avskrivningsdato
     */
    @Column(name = "sign_off_date")
    @Audited
    private Date signOffDate;

    /**
     * M618 - avskrevetAv
     */
    @Column(name = "sign_off_name")
    @Audited
    private String signOffBy;

    /**
     * M619 - avskrivningsmaate
     */
    @Column(name = "sign_off_method")
    @Audited
    private String signOffMethod;

    /**
     * M215 referanseAvskrivesAvJournalpost
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_record_id")

    private RegistryEntry referenceSignedOffRecord;
    /**
     * M??? - referanseAvskrivesAvKorrespondansepart
     * Note this is new to v4, I think. Missing Metatdata number
     */

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pk_correspondence_part_id")
    private CorrespondencePart referenceSignedOffCorrespondencePart;

    // Links to RegistryEnty
    @ManyToMany(mappedBy = "referenceSignOff")
    private List<RegistryEntry> referenceRecord = new ArrayList<>();

    public Date getSignOffDate() {
        return signOffDate;
    }

    public void setSignOffDate(Date signOffDate) {
        this.signOffDate = signOffDate;
    }

    public String getSignOffBy() {
        return signOffBy;
    }

    public void setSignOffBy(String signOffBy) {
        this.signOffBy = signOffBy;
    }

    public String getSignOffMethod() {
        return signOffMethod;
    }

    public void setSignOffMethod(String signOffMethod) {
        this.signOffMethod = signOffMethod;
    }

    @Override
    public String getBaseTypeName() {
        return N5ResourceMappings.SIGN_OFF;
    }

    public RegistryEntry getReferenceSignedOffRecord() {
        return referenceSignedOffRecord;
    }

    public void setReferenceSignedOffRecord(RegistryEntry referenceSignedOffRecord) {
        this.referenceSignedOffRecord = referenceSignedOffRecord;
    }

    public CorrespondencePart getReferenceSignedOffCorrespondencePart() {
        return referenceSignedOffCorrespondencePart;
    }

    public void setReferenceSignedOffCorrespondencePart(CorrespondencePart referenceSignedOffCorrespondencePart) {
        this.referenceSignedOffCorrespondencePart = referenceSignedOffCorrespondencePart;
    }

    public List<RegistryEntry> getReferenceRecord() {
        return referenceRecord;
    }

    public void setReferenceRecord(List<RegistryEntry> referenceRecord) {
        this.referenceRecord = referenceRecord;
    }

    @Override
    public String toString() {
        return "SignOff{" + super.toString() +
                "signOffMethod='" + signOffMethod + '\'' +
                ", signOffBy='" + signOffBy + '\'' +
                ", signOffDate=" + signOffDate +
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
        SignOff rhs = (SignOff) other;
        return new EqualsBuilder()
                .appendSuper(super.equals(other))
                .append(signOffMethod, rhs.signOffMethod)
                .append(signOffBy, rhs.signOffBy)
                .append(signOffDate, rhs.signOffDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(signOffMethod)
                .append(signOffBy)
                .append(signOffDate)
                .toHashCode();
    }
}
