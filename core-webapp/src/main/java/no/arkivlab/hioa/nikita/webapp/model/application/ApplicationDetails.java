package no.arkivlab.hioa.nikita.webapp.model.application;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import no.arkivlab.hioa.nikita.webapp.util.serialisers.ApplicationDetailsSerializer;
import java.util.List;
import javax.validation.constraints.NotNull;

@JsonSerialize(using = ApplicationDetailsSerializer.class)
public class ApplicationDetails {


    protected List<ConformityLevel> conformityLevels;
    
    public ApplicationDetails(@NotNull List<ConformityLevel> conformityLevels) {
        this.conformityLevels =  conformityLevels;
    }

    public List<ConformityLevel> getConformityLevels() {
        return conformityLevels;
    }

    public void setConformityLevels(List<ConformityLevel> conformanceLevel) {
        this.conformityLevels = conformanceLevel;
    }

}
