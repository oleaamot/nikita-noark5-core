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
import static nikita.config.N5ResourceMappings.SYSTEM_ID;

@RestController
@RequestMapping(value = Constants.HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + BASIC_RECORD + SLASH,
        produces = {NOARK5_V4_CONTENT_TYPE})
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
    @RequestMapping(value = LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS, method = RequestMethod.GET)
    public ResponseEntity<BasicRecordHateoas> findOneBasicRecordBySystemId(
            @ApiParam(name = "systemID",
                    value = "systemID of the basicRecord to retrieve",
                    required = true)
            @PathVariable("systemID") final String basicRecordSystemId) {
        BasicRecordHateoas basicRecordHateoas = new
                BasicRecordHateoas((BasicRecord)basicRecordService.findBySystemId(basicRecordSystemId));
        return new ResponseEntity<>(basicRecordHateoas, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Retrieves multiple BasicRecord entities limited by ownership rights", notes = "The field skip" +
            "tells how many BasicRecord rows of the result set to ignore (starting at 0), while  top tells how many rows" +
            " after skip to return. Note if the value of top is greater than system value " +
            " nikita-noark5-core.pagination.maxPageSize, then nikita-noark5-core.pagination.maxPageSize is used. ",
            response = BasicRecordHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "BasicRecord list found",
                    response = BasicRecordHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<BasicRecordHateoas> findAllBasicRecord(
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {

        BasicRecordHateoas basicRecordHateoas = new
                BasicRecordHateoas(basicRecordService.findBasicRecordByOwnerPaginated(top, skip));
        return new ResponseEntity<>(basicRecordHateoas, HttpStatus.OK);
    }
}
