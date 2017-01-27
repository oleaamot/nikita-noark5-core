package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import nikita.config.Constants;
import nikita.model.noark5.v4.BasicRecord;
import nikita.model.noark5.v4.hateoas.BasicRecordHateoas;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.IBasicRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.BASIC_RECORD;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + BASIC_RECORD)
public class BasicRecordHateoasController {

    @Autowired
    IBasicRecordService basicRecordService;

    // API - All GET Requests (CRUD - READ)

    @ApiOperation(value = "Retrieves a single BasicRecord entity given a systemId", response = BasicRecord.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "BasicRecord returned", response = BasicRecord.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = "/{systemID}", method = RequestMethod.GET)
    public ResponseEntity<BasicRecordHateoas> findOneBasicRecordBySystemId(
            @ApiParam(name = "systemID",
                    value = "systemID of the basicRecord to retrieve",
                    required = true)
            @PathVariable("systemID") final String basicRecordSystemId) {
        BasicRecordHateoas basicRecordHateoas = new
                BasicRecordHateoas((BasicRecord)basicRecordService.findBySystemId(basicRecordSystemId));
        return new ResponseEntity<>(basicRecordHateoas, HttpStatus.CREATED);
    }
}
