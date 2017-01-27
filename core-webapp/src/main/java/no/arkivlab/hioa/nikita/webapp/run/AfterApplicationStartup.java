package no.arkivlab.hioa.nikita.webapp.run;

import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFondsService;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.ISeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Create som basic data if application is in demo mode
 */
@Component
public class AfterApplicationStartup {

    @Autowired
    private Environment environment;

    @Autowired
    IFondsService fondsService;

    @Autowired
    ISeriesService seriesService;

    public void afterApplicationStarts() {

        String [] activeProfiles = environment.getActiveProfiles();

        if (Arrays.asList(activeProfiles).contains("demo") == true){
            // Create a fonds object to be persisted to the database via fondsService
/*            Fonds fonds = new Fonds();
            fonds.setTitle(Constants.TEST_TITLE);
            fonds.setDocumentMedium(N5ResourceMappings.DOCUMENT_MEDIUM_ELECTRONIC);
            Fonds persistedFonds = fondsService.saveWithOwner(fonds, "admin");

            // Create a series object to be persisted to the database via seriesService
            Series series = new Series();
            series.setTitle(Constants.TEST_TITLE);
            series.setDocumentMedium(N5ResourceMappings.DOCUMENT_MEDIUM_ELECTRONIC);
            series.setReferenceFonds(persistedFonds);
            seriesService.saveWithOwner(series, "admin");
            */
        }
    }
}