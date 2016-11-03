package no.arkivlab.hioa.nikita.webapp.web.controller.api.impl;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.config.N5ResourceMappings;
import nikita.model.noark5.v4.Fonds;
import nikita.model.noark5.v4.FondsCreator;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IFondsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * Created by tsodring on 6/24/16.
 *
 * @formatter:off
 *  The following requests related to fonds are supported:
 ------------------------------------------------------------------------------------------------------------------------------------------
 |    |                RequestMapping                                    | Description                                                    |
 |----|------------------------------------------------------------------|----------------------------------------------------------------|
 |GET Requests (Corresponds to READ in CRUD commands)                    |                                                                |
 |----|------------------------------------------------------------------|----------------------------------------------------------------|
 |  1 | /arkiv                                                           | Get all arkiv entities                                         |
 |  2 | /arkiv/{fondsId}                                                 | Get one arkiv entity given fondsId                             |
 |  3 | /arkiv/{fondsId}/arkivskaper                                     | Get all arkivskaper associated with an arkiv                   |
 |  4 | /arkiv/{fondsId}/arkivskaper{fondsCreatorId}                     | Get one arkivskaper associated with an arkiv                   |
 |  5 | /arkiv/{fondsId}/arkivdel                                        | Get all arkivdel associated with an arkiv                      |
 |  6 | /arkiv/{fondsId}/arkivdel/{seriesId}  -- utgår i SERIEScontroler | Get one arkivdel associated with an arkiv                      |
 |  7 | /arkiv/{fondsId}/arkivstatus                                     | Get status of one arkiv                                        |
 |  8 | /arkiv/{fondsId}/arkiv                                           | Get all underarkiv                                             |
 |  9 | /arkiv/{fondsId}/dokumentmedium                                  |                                                                |
 | 10 | /arkiv/{fondsId}/arkivstatus                                     |                                                                |
 | 10 | /arkiv/{fondsId}/oppbevarinsystegsted                            |                                                                |
 | 10 | /arkiv/{fondsId}/oppbevaringsted{storageLocationId}              |                                                                |
 You will also have bevaring / kassasjon / merknad? / sikkerhet instruks osv.
 |----|------------------------------------------------------------------|----------------------------------------------------------------|
 |POST (Corresponds to CREATE in CRUD commands)                                                                                           |                                                              |
 |----|------------------------------------------------------------------|-----------------------------------------------------------------
 | 11 | /arkiv/                                                          | Create a new fonds entity                                      |
 | 12 | /arkiv/{fondsId}/arkivskaper                                     | Create a new fondsCreator entity associated with a given fonds |
 | 13 | /arkiv/{fondsId}/arkiv                                           | Create a new (sub)fonds entity associated with a given fonds   |
 | 14 | /arkiv/{fondsId}/arkivdel                                        | Create a new series entity associated with a given fonds       |
 |----|------------------------------------------------------------------|----------------------------------------------------------------|
 |PUT (Corresponds to UPDATE in CRUD commands)                                                                                            |
 |----|------------------------------------------------------------------|----------------------------------------------------------------|
 | 11 | /arkiv/{fondsId}                                                 |                                                                |
 | 11 | /arkiv/{fondsId}/arkivskaper{fondsCreatorId}                     |                                                                |
 | 11 | /arkiv/{fondsId}/oppbevaringsted{storageLocationId}              |                                                                |
 | 11 | /arkiv/{fondsId}/arkivstatus                                     |                                                                |

 |----|------------------------------------------------------------------|----------------------------------------------------------------|
 |DELETE (Corresponds to DELETE in CRUD commands)                                                                                         |
 |----|------------------------------------------------------------------|----------------------------------------------------------------|
 | 11 | /arkiv/{fondsId}/arkiv                                           | Delete all subfonds                                            |
 | 11 | /arkiv/{fondsId}                                                 | Delete one fonds (could be subfonds)                           |
 | 11 | /arkiv/{fondsId}/arkivdel                                        |                                                                |
 |  6 | /arkiv/{fondsId}/arkivdel/{seriesId}                             | Delete one arkivdel associated with an arkiv                   |
 | 11 | /arkiv/{fondsId}/arkivskaper                                     |                                                                |
 | 11 | /arkiv/{fondsId}/arkivskaper{fondsCreatorId}                     |                                                                |
 | 10 | /arkiv/{fondsId}/oppbevaringsted                                 |                                                                |
 | 10 | /arkiv/{fondsId}/oppbevaringsted{storageLocationId}              |                                                                |
 |----|------------------------------------------------------------------|----------------------------------------------------------------|

 @formatter:on



 204 No Content - for PUT, PATCH, and DELETE


 */

@RestController
@RequestMapping(value = Constants.API_PATH + "/" + N5ResourceMappings.FONDS)
@Api(value = "Fonds", description = "CRUD operations on fonds")
public class FondsController {

    @Autowired
    IFondsService fondsService;

    // API - All POST Requests (CRUD - CREATE) {"title": "Test tittel", "description": "Test description", "documentMedium":"Elektronisk arkiv"}
    @ApiOperation(value = "Returns user details", notes = "Returns a complete list of users details with a date of last modification.", response = Fonds.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Fonds object successfully created", response = Fonds.class),
            @ApiResponse(code = 500, message = "Internal server error")}
    )

    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST)
    public Fonds save(/* @ApiParam(name = "userName", value = "Alphanumeric login to the application", required = true) */@RequestBody Fonds fonds) {
        SecurityContext securityContext  = SecurityContextHolder.getContext();
        if (securityContext == null)
            System.out.println("hello");

        Authentication authentication = securityContext.getAuthentication();
        return fondsService.save(fonds);
    }

    // API - All GET Requests (CRUD - READ)
    @Counted
    @Timed
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Fonds findOne(@PathVariable("id") final Long id, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
        Fonds fonds = fondsService.findById(id);
        return fonds;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Fonds> findAll(final UriComponentsBuilder uriBuilder, HttpServletRequest request, final HttpServletResponse response) {
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Iterable<Fonds> fonds = fondsService.findByOwnedBy(loggedInUser);
        return fonds;
    }

    @RequestMapping(value = "/{fondsId}/" + N5ResourceMappings.FONDS, method = RequestMethod.GET)
    public List<Fonds> findAllSubFonds(@PathVariable("fondsId") final Long fondsId,
                                              final UriComponentsBuilder uriBuilder,
                                              HttpServletRequest request, final HttpServletResponse response) {
        return null;
    }

    @RequestMapping(value = "/{fondsId}/" + N5ResourceMappings.FONDS_CREATOR, method = RequestMethod.GET)
    public List<FondsCreator> findAllFondsCreator(@PathVariable("fondsId") final Long id,
                                                  final UriComponentsBuilder uriBuilder,
                                                  HttpServletRequest request, final HttpServletResponse response) {
        return null;
    }


}
