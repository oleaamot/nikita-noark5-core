package no.arkivlab.hioa.nikita.webapp.web.controller.hateoas;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.*;
import nikita.model.noark5.v4.*;
import nikita.model.noark5.v4.casehandling.CaseFile;
import nikita.model.noark5.v4.hateoas.*;
import nikita.model.noark5.v4.hateoas.casehandling.CaseFileHateoas;
import nikita.model.noark5.v4.interfaces.entities.INikitaEntity;
import nikita.util.CommonUtils;
import nikita.util.exceptions.NikitaException;
import nikita.util.exceptions.NoarkEntityNotFoundException;
import no.arkivlab.hioa.nikita.webapp.handlers.hateoas.interfaces.*;
import no.arkivlab.hioa.nikita.webapp.security.Authorisation;
import no.arkivlab.hioa.nikita.webapp.service.interfaces.ISeriesService;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityCreatedEvent;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityDeletedEvent;
import no.arkivlab.hioa.nikita.webapp.web.events.AfterNoarkEntityUpdatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

import static nikita.config.Constants.*;
import static nikita.config.N5ResourceMappings.*;
import static org.springframework.http.HttpHeaders.ETAG;

@RestController
@RequestMapping(value = HATEOAS_API_PATH + SLASH + NOARK_FONDS_STRUCTURE_PATH + SLASH + SERIES,
        produces = {NOARK5_V4_CONTENT_TYPE_JSON, NOARK5_V4_CONTENT_TYPE_JSON_XML})
@Api(value = "SeriesController", description = "Contains CRUD operations for Series. Create operations are only for " +
        "entities that can be associated with a series e.g. File / ClassificationSystem. Update and delete operations" +
        " are on individual series entities identified by systemId. Read operations are either on individual series" +
        "entities or pageable iterable sets of series")
public class SeriesHateoasController extends NoarkController {

    private ISeriesService seriesService;
    private IFondsHateoasHandler fondsHateoasHandler;
    private ISeriesHateoasHandler seriesHateoasHandler;
    private IRecordHateoasHandler recordHateoasHandler;
    private ICaseFileHateoasHandler caseFileHateoasHandler;
    private IFileHateoasHandler fileHateoasHandler;
    private ApplicationEventPublisher applicationEventPublisher;

    public SeriesHateoasController(ISeriesService seriesService,
                                   IFondsHateoasHandler fondsHateoasHandler,
                                   ISeriesHateoasHandler seriesHateoasHandler,
                                   IRecordHateoasHandler recordHateoasHandler,
                                   ICaseFileHateoasHandler caseFileHateoasHandler,
                                   IFileHateoasHandler fileHateoasHandler,
                                   ApplicationEventPublisher applicationEventPublisher) {

        this.seriesService = seriesService;
        this.fondsHateoasHandler = fondsHateoasHandler;
        this.seriesHateoasHandler = seriesHateoasHandler;
        this.recordHateoasHandler = recordHateoasHandler;
        this.caseFileHateoasHandler = caseFileHateoasHandler;
        this.fileHateoasHandler = fileHateoasHandler;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    // API - All POST Requests (CRUD - CREATE)

    // Create a new file
    // POST [contextPath][api]/arkivstruktur/arkivdel/ny-mappe/
    @ApiOperation(value = "Persists a File object associated with the given Series systemId", notes = "Returns the " +
            "newly created file object after it was associated with a Series object and persisted to the database",
            response = FileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = FileHateoas.class),
            @ApiResponse(code = 201, message = "File " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = FileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type File"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS +
            SLASH + NEW_FILE, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<FileHateoas> createFileAssociatedWithSeries(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemId of series to associate the caseFile with",
                    required = true)
            @PathVariable String systemID,
            @ApiParam(name = "File",
                    value = "Incoming file object",
                    required = true)
            @RequestBody File file) throws NikitaException {
        validateForCreate(file);
        File createdFile = seriesService.createFileAssociatedWithSeries(systemID, file);
        FileHateoas fileHateoas = new FileHateoas(createdFile);
        fileHateoasHandler.addLinks(fileHateoas, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdFile));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(createdFile.getVersion().toString())
                .body(fileHateoas);
    }

    // Create a new casefile
    // POST [contextPath][api]/arkivstruktur/arkivdel/{systemId}/ny-saksmappe/
    // This currently is not supported in the standard, but probably will be later
    @ApiOperation(value = "Persists a CaseFile object associated with the given Series systemId", notes = "Returns " +
            "the newly created caseFile object after it was associated with a Series object and persisted to " +
            "the database", response = CaseFileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = CaseFileHateoas.class),
            @ApiResponse(code = 201, message = "File " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = CaseFileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type CaseFile"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH
            + NEW_CASE_FILE, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<CaseFileHateoas> createCaseFileAssociatedWithSeries(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemId of series to associate the caseFile with",
                    required = true)
            @PathVariable String systemID,
            @ApiParam(name = "caseFile",
                    value = "Incoming caseFile object",
                    required = true)
            @RequestBody CaseFile caseFile) throws NikitaException {
        validateForCreate(caseFile);
        CaseFile createdCaseFile = seriesService.createCaseFileAssociatedWithSeries(systemID, caseFile);
        CaseFileHateoas caseFileHateoas = new CaseFileHateoas(createdCaseFile);
        caseFileHateoasHandler.addLinks(caseFileHateoas, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, createdCaseFile));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(createdCaseFile.getVersion().toString())
                .body(caseFileHateoas);
    }

    // Create a new record
    // POST [contextPath][api]/arkivstruktur/arkivdel/ny-registrering/
    @ApiOperation(value = "Persists a Record object associated with the given Series systemId", notes = "Returns the " +
            "newly created record object after it was associated with a Series object and persisted to the database",
            response = RecordHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Record " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = RecordHateoas.class),
            @ApiResponse(code = 201, message = "Record " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = RecordHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type Record"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.POST, value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS +
            SLASH + NEW_RECORD, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<String> createRecordAssociatedWithSeries(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemId of series to associate the record with",
                    required = true)
            @PathVariable String systemID,
            @ApiParam(name = "Record",
                    value = "Incoming record object",
                    required = true)
            @RequestBody Record record) throws NikitaException {
        //  validateForCreate(record);
        //RecordHateoas recordHateoas = new RecordHateoas(seriesService.createRecordAssociatedWithSeries(systemID, record));
        //recordHateoasHandler.addLinks(recordHateoas, new Authorisation());
        // applicationEventPublisher.publishEvent(new AfterNoarkEntityCreatedEvent(this, ));
        //  return ResponseEntity.status(HttpStatus.CREATED)
        //.eTag(createdRecord.getVersion().toString())
        //.body(recordHateoas);
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // API - All PUT Requests (CRUD - UPDATE)

    // Associate Series with reference to successor
    // PUT [contextPath][api]/arkivstruktur/arkivdel/{systemIdArvtager}/referanseArvtager/
    @ApiOperation(value = "Associates a Series object (successor) as a successor to another Series object (precursor)" +
            "identified by systemID. ", notes = "Automatically sets the reverse relationship. " +
            "Associates a precursor relationship as the same time. Returns both the successor as well as the precursor",
            response = SeriesHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Series " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = SeriesHateoas.class),
            @ApiResponse(code = 201, message = "Series " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = SeriesHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type Series"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.PUT, value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
            RIGHT_PARENTHESIS + SLASH + SERIES_ASSOCIATE_AS_SUCCESSOR, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<String> associateSeriesWithSeriesPrecursor(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "The systemId of the Series identified as a precursor",
                    required = true)
            @PathVariable String systemID,
            @ApiParam(name = "id",
                    value = "Address of the Series identified as a successor",
                    required = true)
            @RequestParam StringBuffer id) throws NikitaException {

        //String systemID_1 = handleResolutionOfIncomingURLInternalGetSystemId
        //      (id);
//        SeriesHateoas seriesHateoas = new
//                SeriesHateoas(seriesService.associateSeriesWithSeriesSuccessor(seriesPrecursorSys temId, urlToSeriesSuccessor));
//        seriesHateoasHandler.addLinks(seriesHateoas, new Authorisation());
        //applicationEventPublisher.publishEvent(new AfterNoarkEntityUpdtedEvent(this, ));
//   return ResponseEntity.status(HttpStatus.CREATED)
//                .eTag(series.getVersion().toString())
//                .body(seriesHateoas);
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Associate Series with reference to precursor
    // PUT [contextPath][api]/arkivstruktur/arkivdel/{systemIdForloeper}/referanseForloeper/
    @ApiOperation(value = "Associates a Series object (precursor) as precursor to a Series object (successor)" +
            "identified by systemID. ", notes = "Automatically sets the reverse relationship. " +
            "Associates a successor relationship as the same time. Returns both the successor as well as the precursor",
            response = SeriesHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Series " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = SeriesHateoas.class),
            @ApiResponse(code = 201, message = "File " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = SeriesHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type Series"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.PUT, value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
            RIGHT_PARENTHESIS + SLASH + SERIES_ASSOCIATE_AS_PRECURSOR, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<String> associateSeriesWithSeriesSuccessor(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "The systemId of the Series identified as a successor",
                    required = true)
            @PathVariable String systemID,
            @ApiParam(name = "id",
                    value = "Address of the Series identified as a precursor",
                    required = true)
            @RequestParam StringBuffer id) throws NikitaException {
        //String systemID_2 = handleResolutionOfIncomingURLInternalGetSystemId
        //            (id);
//        SeriesHateoas seriesHateoas = new
//                SeriesHateoas(seriesService.associateSeriesWithSeriesSuccessor(systemID, caseFile));
//        seriesHateoasHandler.addLinks(seriesHateoas, new Authorisation());
//        applicationEventPublisher.publishEvent(new AfterNoarkEntityUpdatedEvent(this, ));
//   return ResponseEntity.status(HttpStatus.CREATED)
//                .eTag(series.getVersion().toString())
//                .body(seriesHateoas);
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }


    // Associate ClassificationSystem to identified Series
    // PUT [contextPath][api]/arkivstruktur/arkivdel/{systemId}/ny-klassifikasjonssystem/
    @ApiOperation(value = "Associates a ClassificationSystem with a Series", notes = "Association can only occur if "
            + "nothing (record, file) has been associated with the Series", response = ClassificationSystemHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ClassificationSystem " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = ClassificationSystemHateoas.class),
            @ApiResponse(code = 201, message = "ClassificationSystem " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = ClassificationSystemHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type ClassificationSystem"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.PUT, value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
            RIGHT_PARENTHESIS + SLASH + NEW_CLASSIFICATION_SYSTEM, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<String> associateSeriesWithClassificationSystem(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "The systemId of the Series",
                    required = true)
            @PathVariable String classificationSystemSuccessorSystemId,
            @ApiParam(name = "id",
                    value = "Address of the ClassificationSystem to associate",
                    required = true)
            @RequestParam StringBuffer id) throws NikitaException {
        //String classificationSystemPrecursorSystemId =
        //      handleResolutionOfIncomingURLInternalGetSystemId(id);
//        ClassificationSystemHateoas classificationSystemHateoas = new
//                ClassificationSystemHateoas(classificationSystemService.associateClassificationSystemWithClassificationSystemSuccessor(classificationSystemSystemId, caseFile));
//        classificationSystemHateoasHandler.addLinks(classificationSystemHateoas, new Authorisation());
//        applicationEventPublisher.publishEvent(new AfterNoarkEntityUpdatedEvent(this, ));
//   return ResponseEntity.status(HttpStatus.CREATED)
//                .eTag(classificationSystem.getVersion().toString())
//                .body(classificationSystemHateoas);
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }


    // Update an identified Series
    // PUT [contextPath][api]/arkivstruktur/arkivdel/{systemId}
    @ApiOperation(value = "Updates a Series object", notes = "Returns the newly" +
            " update Series object after it is persisted to the database", response = SeriesHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Series " + API_MESSAGE_OBJECT_ALREADY_PERSISTED,
                    response = SeriesHateoas.class),
            @ApiResponse(code = 201, message = "Series " + API_MESSAGE_OBJECT_SUCCESSFULLY_CREATED,
                    response = SeriesHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 404, message = API_MESSAGE_PARENT_DOES_NOT_EXIST + " of type ClassificationSystem"),
            @ApiResponse(code = 409, message = API_MESSAGE_CONFLICT),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.PUT, value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID +
            RIGHT_PARENTHESIS, consumes = {NOARK5_V4_CONTENT_TYPE_JSON})
    public ResponseEntity<SeriesHateoas> updateSeries(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemId of fonds to update.",
                    required = true)
                @PathVariable("systemID") String systemID,
            @ApiParam(name = "series",
                    value = "Incoming series object",
                    required = true)
                @RequestBody Series series) throws NikitaException {
        validateForUpdate(series);
        Series updatedSeries = seriesService.handleUpdate(systemID, parseETAG(request.getHeader(ETAG)), series);
        SeriesHateoas seriesHateoas = new SeriesHateoas(updatedSeries);
        seriesHateoasHandler.addLinks(seriesHateoas, new Authorisation());
        applicationEventPublisher.publishEvent(new AfterNoarkEntityUpdatedEvent(this, updatedSeries));
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(updatedSeries.getVersion().toString())
                .body(seriesHateoas);
    }
    // API - All GET Requests (CRUD - READ)

    // Retrieve a Series given a systemId
    // GET [contextPath][api]/arkivstruktur/arkivdel/{systemId}/
    @ApiOperation(value = "Retrieves a single Series entity given a systemId", response = Series.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Series returned", response = Series.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.GET)
    public ResponseEntity<SeriesHateoas> findOneSeriesbySystemId(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemID of the series to retrieve",
                    required = true)
            @PathVariable("systemID") final String systemID) {
        Series series = seriesService.findBySystemId(systemID);
        if (series == null) {
            throw new NoarkEntityNotFoundException("Could not find series object with systemID " + systemID);
        }
        SeriesHateoas seriesHateoas = new
                SeriesHateoas(series);
        seriesHateoasHandler.addLinks(seriesHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.CREATED)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(series.getVersion().toString())
                .body(seriesHateoas);
    }

    private File setFileDefaults(File defaultFile)
    {
        defaultFile.setFileId("2017/01");
        defaultFile.setDocumentMedium(DOCUMENT_MEDIUM_ELECTRONIC);
        return defaultFile;
    }
    private CaseFile setCaseFileDefaults(CaseFile defaultCaseFile)
    {
        defaultCaseFile = (CaseFile)setFileDefaults(defaultCaseFile);
        defaultCaseFile.setCaseResponsible(TEST_USER_CASE_HANDLER_1);
        defaultCaseFile.setAdministrativeUnit(TEST_ADMINISTRATIVE_UNIT);
        defaultCaseFile.setCaseDate(new Date());
        defaultCaseFile.setCaseStatus(STATUS_OPEN);
        return defaultCaseFile;
    }

    // Create a File object with default values
    // GET [contextPath][api]/arkivstruktur/arkivdel/{systemId}/ny-mappe/
    @ApiOperation(value = "Create a File with default values", response = File.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File returned", response = File.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + NEW_FILE,
            method = RequestMethod.GET)
    public ResponseEntity<FileHateoas> createDefaultFile(
            HttpServletRequest request, final HttpServletResponse response) {

        File defaultFile = new File();
        defaultFile = setFileDefaults(defaultFile);
        FileHateoas fileHateoas = new
                FileHateoas(defaultFile);
        fileHateoasHandler.addLinksOnNew(fileHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(fileHateoas);
    }

    // Create a CaseFile object with default values
    // GET [contextPath][api]/arkivstruktur/arkivdel/{systemId}/ny-saksmappe/
    @ApiOperation(value = "Create a CaseFile with default values", response = CaseFile.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CaseFile returned", response = CaseFile.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + NEW_CASE_FILE,
            method = RequestMethod.GET)
    public ResponseEntity<CaseFileHateoas> createDefaultCaseFile(
            HttpServletRequest request, final HttpServletResponse response) {

        CaseFile defaultCaseFile = new CaseFile();
        defaultCaseFile = setCaseFileDefaults(defaultCaseFile);
        CaseFileHateoas caseFileHateoas = new
                CaseFileHateoas(defaultCaseFile);
        caseFileHateoasHandler.addLinksOnNew(caseFileHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(caseFileHateoas);
    }

    // Retrieve the precursor to a Series given a systemId
    // GET [contextPath][api]/arkivstruktur/arkivdel/{systemId}/forloeper/
    @ApiOperation(value = "Retrieves a Series that is the precursor to the series identified by systemID",
            response = Series.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Series returned", response = Series.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + SERIES_PRECURSOR,
            method = RequestMethod.GET)
    public ResponseEntity<String> findPrecursorToSeriesBySystemId(
            HttpServletRequest request,
            @ApiParam(name = " systemID",
                    value = "systemId of the series to retrieve",
                    required = true)
            @PathVariable("systemID") final String systemID) {
        /*Series series = seriesService.findBySystemId(systemID);
        if (series == null) {
            throw new NoarkEntityNotFoundException("Could not find series object with systemID " + systemID);
        }
        SeriesHateoas seriesHateoas = new
                SeriesHateoas(series);
        seriesHateoasHandler.addLinks(seriesHateoas, new Authorisation());*/
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .eTag(series.getVersion().toString())
//                .body(seriesHateoas);
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Retrieve the successor to a Series given a systemId
    // GET [contextPath][api]/arkivstruktur/arkivdel/{systemId}/arvtager/
    @ApiOperation(value = "Retrieves a Series that is the successor to the series identified by systemID",
            response = Series.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Series returned", response = Series.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + SERIES_SUCCESSOR,
            method = RequestMethod.GET)
    public ResponseEntity<String> findSuccessorToSeriesBySystemId(
            HttpServletRequest request,
            @ApiParam(name = " systemID",
                    value = "systemId of the series to retrieve",
                    required = true)
            @PathVariable("systemID") final String systemID) {
        /*Series series = seriesService.findBySystemId(systemID);
        if (series == null) {
            throw new NoarkEntityNotFoundException("Could not find series object with systemID " + systemID);
        }
        SeriesHateoas seriesHateoas = new
                SeriesHateoas(series);
        seriesHateoasHandler.addLinks(seriesHateoas, new Authorisation());*/
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .eTag(series.getVersion().toString())
//                .body(seriesHateoas);
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Retrieve the Fonds associated with a Series given a systemId of the Series
    // GET [contextPath][api]/arkivstruktur/arkivdel/{systemId}/arkiv/
    @ApiOperation(value = "Retrieves the Fonds associated wth a Series given a systemId of the Series",
            response = Fonds.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Fonds returned", response = Fonds.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + FONDS,
            method = RequestMethod.GET)
    public ResponseEntity<String> findFondsAssociatedWithSeriesBySystemId(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemID of the Series",
                    required = true)
            @PathVariable("systemID") final String systemID) {

        /*Series series = seriesService.findBySystemId(systemID);
        if (series == null) {
            throw new NoarkEntityNotFoundException("Could not find series object with systemID " + systemID);
        }
        SeriesHateoas seriesHateoas = new
                SeriesHateoas(series);
        seriesHateoasHandler.addLinks(seriesHateoas, new Authorisation());
        */
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .eTag(series.getVersion().toString())
//                .body(seriesHateoas);
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Retrieve the ClassificationSystem associated with a Series given a systemId of the Series
    // GET [contextPath][api]/arkivstruktur/arkivdel/{systemId}/klassifikasjonssystem/
    @ApiOperation(value = "Retrieves the ClassificationSystem associated wth a Series given a systemId of " +
            "the Series", response = ClassificationSystem.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Series returned", response = ClassificationSystem.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + CLASSIFICATION_SYSTEM
            , method = RequestMethod.GET)
    public ResponseEntity<String> findClassificationSystemAssociatedWithSeriesBySystemId(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemID of the series to retrieve",
                    required = true)
            @PathVariable("systemID") final String systemID) {

        /*Series series = seriesService.findBySystemId(systemID);
        if (series == null) {
            throw new NoarkEntityNotFoundException("Could not find series object with systemID " + systemID);
        }
        SeriesHateoas seriesHateoas = new
                SeriesHateoas(series);
        seriesHateoasHandler.addLinks(seriesHateoas, new Authorisation());
        */
//   return ResponseEntity.status(HttpStatus.CREATED)
//                .eTag(classificationSystem.getVersion().toString())
//                .body(classificationSystemHateoas);
        return new ResponseEntity<>(API_MESSAGE_NOT_IMPLEMENTED, HttpStatus.NOT_IMPLEMENTED);
    }

    // Retrieve all Series (paginated)
    // GET [contextPath][api]/arkivstruktur/arkivdel/{systemId}/klassifikasjonssystem/
    @ApiOperation(value = "Retrieves multiple Series entities limited by ownership rights", notes = "The field skip" +
            "tells how many Series rows of the result set to ignore (starting at 0), while  top tells how many rows" +
            " after skip to return. Note if the value of top is greater than system value " +
            " nikita-noark5-core.pagination.maxPageSize, then nikita-noark5-core.pagination.maxPageSize is used. ",
            response = SeriesHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Series list found",
                    response = SeriesHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<SeriesHateoas> findAllSeries(
            HttpServletRequest request,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip) {

        SeriesHateoas seriesHateoas = new
                SeriesHateoas((List<INikitaEntity>) (List)
                seriesService.findSeriesByOwnerPaginated(top, skip));
        seriesHateoasHandler.addLinksOnRead(seriesHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(seriesHateoas);
    }

    // Retrieve all Records associated with a Series (paginated)
    // GET [contextPath][api]/arkivstruktur/arkivdel/{systemId}/registrering/
    // GET [contextPath][api]/arkivstruktur/arkivdel/{systemId}/registrering/?top=5&skip=1
    @ApiOperation(value = "Retrieves a lit of Records associated with a Series", notes = "The field skip" +
            "tells how many Record rows of the result set to ignore (starting at 0), while  top tells how many rows" +
            " after skip to return. Note if the value of top is greater than system value " +
            " nikita-noark5-core.pagination.maxPageSize, then nikita-noark5-core.pagination.maxPageSize is used. ",
            response = RecordHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Record list found",
                    response = RecordHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + REGISTRATION,
            method = RequestMethod.GET)
    public ResponseEntity<RecordHateoas> findAllRecordAssociatedWithSeries(
            HttpServletRequest request,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip,
            @ApiParam(name = "systemID",
                    value = "systemID of the series to retrieve",
                    required = true)
            @PathVariable("systemID") final String systemID) {
        Series series = seriesService.findBySystemId(systemID);
        RecordHateoas recordHateoas = new RecordHateoas((List<INikitaEntity>) (List) series.getReferenceRecord());
        recordHateoasHandler.addLinks(recordHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(recordHateoas);
    }

    // Retrieve all Files associated with a Series (paginated)
    // GET [contextPath][api]/arkivstruktur/arkivdel/{systemId}/mappe/
    // GET [contextPath][api]/arkivstruktur/arkivdel/{systemId}/mappe/?top=5&skip=1
    @ApiOperation(value = "Retrieves a list of Files associated with a Series", notes = "The field skip" +
            "tells how many File rows of the result set to ignore (starting at 0), while  top tells how many rows" +
            " after skip to return. Note if the value of top is greater than system value " +
            " nikita-noark5-core.pagination.maxPageSize, then nikita-noark5-core.pagination.maxPageSize is used. ",
            response = FileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "File list found",
                    response = FileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + FILE,
            method = RequestMethod.GET)
    public ResponseEntity<FileHateoas> findAllFileAssociatedWithSeries(
            HttpServletRequest request,
            @RequestParam(name = "top", required = false) Integer top,
            @RequestParam(name = "skip", required = false) Integer skip,
            @ApiParam(name = "systemID",
                    value = "systemID of the series to retrieve",
                    required = true)
            @PathVariable("systemID") final String systemID) {

        Series series = seriesService.findBySystemId(systemID);
        if (series == null) {
            throw new NoarkEntityNotFoundException("Could not find series object with systemID " + systemID);
        }
        FileHateoas fileHateoas = new FileHateoas((List<INikitaEntity>)
                (List) series.getReferenceFile());
        fileHateoasHandler.addLinks(fileHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(fileHateoas);
    }

    // Retrieve all CaseFiles associated with a Series (paginated)
    // GET [contextPath][api]/arkivstruktur/arkivdel/{systemId}/saksmappe/
    // GET [contextPath][api]/arkivstruktur/arkivdel/{systemId}/saksmappe/?top=5&skip=1
    @ApiOperation(value = "Retrieves a list of CaseFiles associated with a Series", notes = "The field skip" +
            "tells how many CaseFile rows of the result set to ignore (starting at 0), while  top tells how many rows" +
            " after skip to return. Note if the value of top is greater than system value " +
            " nikita-noark5-core.pagination.maxPageSize, then nikita-noark5-core.pagination.maxPageSize is used. ",
            response = CaseFileHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CaseFile list found",
                    response = CaseFileHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS + SLASH + CASE_FILE,
            method = RequestMethod.GET)
    public ResponseEntity<CaseFileHateoas> findAllCaseFileAssociatedWithCaseFile(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemID of the series to retrieve",
                    required = true)
            @PathVariable("systemID") final String systemID
            ) {
        seriesService.findAllCaseFileBySeries(systemID);

        CaseFileHateoas caseFileHateoas = new
                CaseFileHateoas((List<INikitaEntity>) (List)
                seriesService.findAllCaseFileBySeries(systemID));
        caseFileHateoasHandler.addLinksOnRead(caseFileHateoas, new Authorisation());
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .body(caseFileHateoas);
    }

    // API - All DELETE Requests (CRUD - DELETE)

    // Delete a Series identified by systemID
    // DELETE [contextPath][api]/arkivstruktur/arkivdel/{systemId}/
    @ApiOperation(value = "Deletes a single Series entity identified by systemID", response = FondsHateoas.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Parent Fonds returned", response = FondsHateoas.class),
            @ApiResponse(code = 401, message = API_MESSAGE_UNAUTHENTICATED_USER),
            @ApiResponse(code = 403, message = API_MESSAGE_UNAUTHORISED_FOR_USER),
            @ApiResponse(code = 500, message = API_MESSAGE_INTERNAL_SERVER_ERROR)})
    @Counted
    @Timed
    @RequestMapping(value = SLASH + LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS,
            method = RequestMethod.DELETE)
    public ResponseEntity<FondsHateoas> deleteSeriesBySystemId(
            HttpServletRequest request,
            @ApiParam(name = "systemID",
                    value = "systemID of the series to delete",
                    required = true)
            @PathVariable("systemID") final String systemID) {

        Series series = seriesService.findBySystemId(systemID);
        Fonds fonds = series.getReferenceFonds();
        FondsHateoas fondsHateoas = new FondsHateoas(fonds);
        fondsHateoasHandler.addLinks(fondsHateoas, new Authorisation());
        seriesService.deleteEntity(systemID);
        applicationEventPublisher.publishEvent(new AfterNoarkEntityDeletedEvent(this, fonds));
        return ResponseEntity.status(HttpStatus.OK)
                .allow(CommonUtils.WebUtils.getMethodsForRequestOrThrow(request.getServletPath()))
                .eTag(fonds.getVersion().toString())
                .body(fondsHateoas);
    }
}
