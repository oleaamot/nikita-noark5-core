package nikita.model.noark5.v4;

import com.fasterxml.jackson.annotation.JsonProperty;
import nikita.model.noark5.v4.interfaces.entities.INoarkGeneralEntity;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.envers.Audited;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.Field;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by tsodring on 5/8/17.
 */
@MappedSuperclass
public class NoarkGeneralEntity extends NoarkEntity implements INoarkGeneralEntity {

    /**
     * M020 - tittel (xs:string)
     */
    @Column(name = "title")
    @Audited
    @Field
    @Boost(1.3f)
    @JsonProperty("tittel")
    private String title;

    /**
     * M021 - beskrivelse (xs:string)
     */
    @Column(name = "description")
    @Audited
    @Field
    @Boost (1.2f)
    private String description;


    /**
     * M600 - opprettetDato (xs:dateTime)
     */
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Audited
    private Date createdDate;

    /**
     * M601 - opprettetAv (xs:string)
     */
    @Column(name = "created_by")
    @Audited
    private String createdBy;

    /**
     * M602 - avsluttetDato (xs:dateTime)
     */
    @Column(name = "finalised_date")
    @Temporal(TemporalType.TIMESTAMP)
    @Audited
    private Date finalisedDate;

    /**
     * M603 - avsluttetAv (xs:string)
     */
    @Column(name = "finalised_by")
    @Audited
    private String finalisedBy;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Date getFinalisedDate() {
        return finalisedDate;
    }

    @Override
    public void setFinalisedDate(Date finalisedDate) {
        this.finalisedDate = finalisedDate;
    }

    @Override
    public String getFinalisedBy() {
        return finalisedBy;
    }

    @Override
    public void setFinalisedBy(String finalisedBy) {
        this.finalisedBy = finalisedBy;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(createdDate)
                .toHashCode();
    }

    @Override
    public int compareTo(NoarkEntity otherEntity) {
        if (null == otherEntity) {
            return -1;
        }
        return new CompareToBuilder()
                .append(this.createdDate, ((NoarkGeneralEntity) otherEntity).createdDate)
                .toComparison();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof NoarkGeneralEntity))
            return false;
        NoarkGeneralEntity noarkGeneralEntity = (NoarkGeneralEntity)other;
        return new EqualsBuilder()
                .append(this.createdDate, noarkGeneralEntity.createdDate)
                .isEquals();
    }


    @Override
    public String toString() {

        return super.toString() +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                ", finalisedDate=" + finalisedDate +
                ", finalisedBy='" + finalisedBy + '\'';
    }
}
