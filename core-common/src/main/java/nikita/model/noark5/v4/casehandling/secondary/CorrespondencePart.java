package nikita.model.noark5.v4.casehandling.secondary;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nikita.model.noark5.v4.NoarkEntity;
import nikita.model.noark5.v4.interfaces.entities.casehandling.ICorrespondencePartEntity;
import nikita.model.noark5.v4.metadata.CorrespondencePartType;
import nikita.util.deserialisers.casehandling.CorrespondencePartUnitDeserializer;
import org.hibernate.envers.Audited;

import javax.persistence.*;

import static nikita.config.Constants.NOARK_CASE_HANDLING_PATH;

@Entity
@Table(name = "correspondence_part")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonDeserialize(using = CorrespondencePartUnitDeserializer.class)
@AttributeOverride(name = "id", column = @Column(name = "pk_correspondence_part_id"))
// Adding @Audited as the it's required as the base class requires it
@Audited
public class CorrespondencePart extends NoarkEntity implements ICorrespondencePartEntity {

    /**
     * M087 - korrespondanseparttype (xs:string)
     */
    // Link to CorrespondencePartType
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "correspondence_part_correspondence_part_type_id",
            referencedColumnName = "pk_correspondence_part_type_id")
    private CorrespondencePartType referenceCorrespondencePartType;


    @Override
    public CorrespondencePartType getCorrespondencePartType() {
        return referenceCorrespondencePartType;
    }

    @Override
    public void setCorrespondencePartType(CorrespondencePartType referenceCorrespondencePartType) {
        this.referenceCorrespondencePartType = referenceCorrespondencePartType;
    }

    @Override
    public String getFunctionalTypeName() {
        return NOARK_CASE_HANDLING_PATH;
    }
}
