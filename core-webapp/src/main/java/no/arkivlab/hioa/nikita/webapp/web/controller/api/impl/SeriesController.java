package no.arkivlab.hioa.nikita.webapp.web.controller.api.impl;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.config.N5ResourceMappings;
import nikita.model.noark5.v4.Series;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.ISeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by tsodring on 6/24/16.
 *
 * @formatter:off
 *  The following requests related to series are supported:
 ------------------------------------------------------------------------------------------------------------------------------------------
 |    |                RequestMapping                                    | Description                                                    |
 |----|------------------------------------------------------------------|----------------------------------------------------------------|
 |GET Requests (Corresponds to READ in CRUD commands)                    |                                                                |
 |----|------------------------------------------------------------------|----------------------------------------------------------------|
 |  1 | /arkiv                                                           | Get all arkiv entities                                         |
 |  2 | /arkiv/{seriesId}                                                 | Get one arkiv entity given seriesId                             |
 |  3 | /arkiv/{seriesId}/arkivskaper                                     | Get all arkivskaper associated with an arkiv                   |
 |  4 | /arkiv/{seriesId}/arkivskaper{seriesCreatorId}                     | Get one arkivskaper associated with an arkiv                   |
 |  5 | /arkiv/{seriesId}/arkivdel                                        | Get all arkivdel associated with an arkiv                      |
 |  6 | /arkiv/{seriesId}/arkivdel/{seriesId}  -- utgår i SERIEScontroler | Get one arkivdel associated with an arkiv                      |
 |  7 | /arkiv/{seriesId}/arkivstatus                                     | Get status of one arkiv                                        |
 |  8 | /arkiv/{seriesId}/arkiv                                           | Get all underarkiv                                             |
 |  9 | /arkiv/{seriesId}/dokumentmedium                                  |                                                                |
 | 10 | /arkiv/{seriesId}/arkivstatus                                     |                                                                |
 | 10 | /arkiv/{seriesId}/oppbevarinsystegsted                            |                                                                |
 | 10 | /arkiv/{seriesId}/oppbevaringsted{storageLocationId}              |                                                                |
 You will also have bevaring / kassasjon / merknad? / sikkerhet instruks osv.
 |----|------------------------------------------------------------------|----------------------------------------------------------------|
 |POST (Corresponds to CREATE in CRUD commands)                                                                                           |                                                              |
 |----|------------------------------------------------------------------|-----------------------------------------------------------------
 | 11 | /arkiv/                                                          | Create a new series entity                                      |
 | 12 | /arkiv/{seriesId}/arkivskaper                                     | Create a new seriesCreator entity associated with a given series |
 | 13 | /arkiv/{seriesId}/arkiv                                           | Create a new (sub)series entity associated with a given series   |
 | 14 | /arkiv/{seriesId}/arkivdel                                        | Create a new series entity associated with a given series       |
 |----|------------------------------------------------------------------|----------------------------------------------------------------|
 |PUT (Corresponds to UPDATE in CRUD commands)                                                                                            |
 |----|------------------------------------------------------------------|----------------------------------------------------------------|
 | 11 | /arkiv/{seriesId}                                                 |                                                                |
 | 11 | /arkiv/{seriesId}/arkivskaper{seriesCreatorId}                     |                                                                |
 | 11 | /arkiv/{seriesId}/oppbevaringsted{storageLocationId}              |                                                                |
 | 11 | /arkiv/{seriesId}/arkivstatus                                     |                                                                |

 |----|------------------------------------------------------------------|----------------------------------------------------------------|
 |DELETE (Corresponds to DELETE in CRUD commands)                                                                                         |
 |----|------------------------------------------------------------------|----------------------------------------------------------------|
 | 11 | /arkiv/{seriesId}/arkiv                                           | Delete all subseries                                            |
 | 11 | /arkiv/{seriesId}                                                 | Delete one series (could be subseries)                           |
 | 11 | /arkiv/{seriesId}/arkivdel                                        |                                                                |
 |  6 | /arkiv/{seriesId}/arkivdel/{seriesId}                             | Delete one arkivdel associated with an arkiv                   |
 | 11 | /arkiv/{seriesId}/arkivskaper                                     |                                                                |
 | 11 | /arkiv/{seriesId}/arkivskaper{seriesCreatorId}                     |                                                                |
 | 10 | /arkiv/{seriesId}/oppbevaringsted                                 |                                                                |
 | 10 | /arkiv/{seriesId}/oppbevaringsted{storageLocationId}              |                                                                |
 |----|------------------------------------------------------------------|----------------------------------------------------------------|

 @formatter:on



 204 No Content - for PUT, PATCH, and DELETE


 */

@RestController
@RequestMapping(value = Constants.API_PATH + "/" + N5ResourceMappings.SERIES)
@Api(value = "Series", description = "CRUD operations on series")
public class SeriesController {

    @Autowired
    ISeriesService seriesService;

    // API - All POST Requests (CRUD - CREATE) {"title": "Test tittel", "description": "Test description", "documentMedium":"Elektronisk arkiv"}
    @ApiOperation(value = "Returns user details", notes = "Returns a complete list of users details with a date of last modification.", response = Series.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Series object successfully created", response = Series.class),
            @ApiResponse(code = 500, message = "Internal server error")}
    )

    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST)
    public Series save(/* @ApiParam(name = "userName", value = "Alphanumeric login to the application", required = true) */@RequestBody Series series) {
        return seriesService.save(series);
    }

    // API - All GET Requests (CRUD - READ)
    @Counted
    @Timed
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Series findOne(@PathVariable("id") final Long id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        return seriesService.findById(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Series> findAll(final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response) {
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Iterable<Series> series = seriesService.findByOwnedBy(loggedInUser);
        return series;
    }
}