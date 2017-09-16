/**
 * Created by tsodring on 5/2/17.
 */


nikitaOptions = {
    //baseUrl: "http://n5test.kxml.no/api",
    baseUrl: 'http://localhost:8092/noark5v4/',
    guiBaseUrl: 'http://localhost:3000/',
    appUrl: 'http://localhost:8092/noark5v4/hateoas-api',
    // Probably retrieve this value on the fly when required, added as a option
    // to speed things up!
    fondsStructureRoot: 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/',
    createFondsAddress: 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/ny-arkiv',
    createFondsCreatorAddress: 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/ny-arkivskaper',
    loginUrl: "http://localhost:8092/noark5v4/auth",
    protocol: 'http',
    appName: 'noark5v4',
    apiName: 'hateoas-api',
    authPoint: 'auth',
    displayFooterNote: true,
    displayBreadcrumb: true,
    enabled: true
};



// Set the base url for application
var base_url = nikitaOptions.baseUrl;

// Set the url for the GUI across all web pages
var gui_base_url = nikitaOptions.guiBaseUrl;
// Starting point for the application
var app_url = nikitaOptions.appUrl;

var fonds_structure_root = nikitaOptions.fondsStructureRoot;
var create_fonds_address = nikitaOptions.createFondsAddress;
var create_fonds__creator_address = nikitaOptions.createFondsCreatorAddress;
// Set the url where POST requests with login credentials should be sent
var login_url = nikitaOptions.loginUrl;
// Whether or not to dsiplay link to nikita source
var display_footer_note = nikitaOptions.displayFooterNote;
var display_breadcrumb = nikitaOptions.displayBreadcrumb;

var fondsListPageName = 'arkivliste.html';
var fondsPageName = 'arkiv.html';
var seriesPageName = 'arkivdel.html';
var seriesListPageName = 'arkivdeliste.html';
var caseFilePageName = 'saksmappe.html';
var caseHandlerDashboardPageName = 'saksbehandler-dashboard.html';
var registryEntryPageName = 'journalpost.html';
var documentPageName = 'dokument.html';
var correspondencePartPersonPageName = 'korrespondansepartperson.html';
var correspondencePartUnitPageName = 'korrespondansepartenhet.html';

/*
 These should probably be implemented as a 2D-array where we can easily pull out the required value
 */

var REL_NEW_REGISTRY_ENTRY = 'http://nikita.arkivlab.no/noark5/v4/ny-journalpost/';
var REL_REGISTRY_ENTRY = 'http://rel.kxml.no/noark5/v4/api/sakarkiv/journalpost/';
var REL_NEW_DOCUMENT_DESCRIPTION = 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-dokumentbeskrivelse/';
var REL_DOCUMENT_DESCRIPTION = 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/dokumentbeskrivelse/';
var REL_DOCUMENT_OBJECT = 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/dokumentobjekt/';
var REL_NEW_DOCUMENT_OBJECT = 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-dokumentobjekt/';
var REL_NEW_CORRESPONDENCE_PART_PERSON = 'http://rel.kxml.no/noark5/v4/api/sakarkiv/ny-korrespondansepartperson/';
var REL_CORRESPONDENCE_PART_PERSON = 'http://rel.kxml.no/noark5/v4/api/sakarkiv/korrespondansepartperson/';
var REL_CASE_FILE = 'http://rel.kxml.no/noark5/v4/api/sakarkiv/saksmappe/';
var REL_DOCUMENT_FILE = 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/fil/';
var REL_SERIES = "http://rel.kxml.no/noark5/v4/api/arkivstruktur/arkivdel/";
var REL_FONDS_STRUCTURE = 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/';
var REL_FONDS_STRUCTURE_NEW_FONDS = 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-arkiv/';
var REL_FONDS_CREATOR = "http://rel.kxml.no/noark5/v4/api/arkivstruktur/arkivskaper/";
var REL_SERIES = "http://rel.kxml.no/noark5/v4/api/arkivstruktur/arkivdel/";
var REL_NEW_SERIES = "http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-arkivdel/";
var REL_NEW_FONDS_CREATOR = "http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-arkivskaper/";
var REL_SELF = 'self';


// These will be picked up from the database
var mimeTypeList = [
    {id: 'rfc822', value: 'message/rfc822'},
    {id: 'pdf', value: 'application/pdf'},
    {id: 'odt', value: 'application/vnd.oasis.opendocument.text'},
    {id: 'ods', value: 'application/vnd.oasis.opendocument.spreadsheet'},
    {id: 'odp', value: 'application/vnd.oasis.opendocument.presentation'},
    {id: 'tiff', value: 'image/tiff'},
    {id: 'jpeg', value: 'image/jpeg'},
];

var variantFormatList = [
    {id: 'P', value: 'Produksjonsformat'},
    {id: 'A', value: 'Arkivformat'},
    {id: 'O', value: 'Dokument hvor deler av innholdet er skjermet'}];

var documentTypeList = [
    {id: 'B', value: 'Brev'},
    {id: 'F', value: 'Faktura'},
    {id: 'O', value: 'Ordrebekreftelse'}];

var documentMediumList = [
    {id: 'F', value: 'Fysisk medium'},
    {id: 'E', value: 'Elektronisk arkiv'},
    {id: 'B', value: 'Blandet fysisk og elektronisk arkiv'}];

var fondsStatusList = [
    {id: 'O', value: 'Opprettet'},
    {id: 'A', value: 'Avsluttet'}];

var seriesStatusList = [
    {id: 'O', value: 'Opprettet'},
    {id: 'A', value: 'Avsluttet'}];

var seriessStatusList = [
    {id: 'O', value: 'Opprettet'},
    {id: 'A', value: 'Avsluttet'}];

var tilknyttetRegistreringSomList = [
    {id: 'H', value: 'Hoveddokument'},
    {id: 'V', value: 'Vedlegg'}];

var correspondencePartTypeList = [
    {id: 'EA', value: 'Avsender'},
    {id: 'EM', value: 'Mottaker'},
    {id: 'EK', value: 'Kopimottaker'},
    {id: 'GM', value: 'Gruppemottaker'},
    {id: 'IA', value: 'Intern avsender'},
    {id: 'IM', value: 'Intern mottaker'},
    {id: 'IK', value: 'Intern kopimottaker'}
];

var documentStatusList = [
    {id: 'B', value: 'Dokumentet er under redigering'},
    {id: 'F', value: 'Dokumentet er ferdigstilt'}];

var emptyList = [{id: '', value: ''},
    {id: '', value: ''},
    {id: '', value: ''},
    {id: '', value: ''}];

var loginOptions = [
    {id: 'SA', value: "saksbehandler"},
    {id: 'AR', value: "arkivar"}];


console.log("Setting nikita gui_base_url: " + gui_base_url);
console.log("Setting nikita app_url: " + app_url);
console.log("Setting nikita login_url: " + login_url);

var SetUserToken = function (t) {
    localStorage.setItem("token", t);
    console.log("Adding token " + t + " to local storage");
};

var GetUserToken = function (t) {
    return localStorage.getItem("token");
};

var GetFileSystemID = function (t) {
    return localStorage.getItem("current_case_file");
};

var GetLinkToChosenFile = function () {
    console.log("getting linktochosenfile=" + localStorage.getItem("linkToChosenFile"));
    return localStorage.getItem("linkToChosenFile");
};

var SetLinkToChosenRecord = function (t) {
    localStorage.setItem("linkToChosenRecord", t);
    console.log("Setting linkToChosenRecord=" + t);
};

var SetCurrentRecordSystemId = function (recordSystemId) {
    localStorage.setItem("currentRecordSystemId", recordSystemId);
    console.log("Setting currentRecordSystemId=" + recordSystemId);
};

var GetSeriesSystemID = function () {
    console.log("Getting chosen currentSeriesSystemId=" + localStorage.getItem("currentSeriesSystemId"));
    return localStorage.getItem("currentSeriesSystemId");
};

var GetLinkToChosenRecord = function () {
    return localStorage.getItem("linkToChosenRecord");
};


// href of the link to use when creating a document description
var GetLinkToCreateDocumentDescription = function () {
    return localStorage.getItem("linkToCreateDocumentDescription");
};

// href of the link to use when creating a document object
var SetLinkToCreateDocumentDescription = function (t) {
    localStorage.setItem("linkToCreateDocumentDescription", t);
    console.log("Setting linkToCreateDocumentDescription=" + t);
};

// href of the link to use when getting a document description
var GetLinkToDocumentDescription = function () {
    return localStorage.getItem("linkToDocumentDescription");
};

// href of the link to use when getting a document object
var SetLinkToDocumentDescription = function (t) {
    localStorage.setItem("linkToDocumentDescription", t);
    console.log("Setting linkToDocumentDescription=" + t);
};

var SetLinkToChosenSeries = function (t) {
    localStorage.setItem("linkToChosenSeries", t);
    console.log("Setting linkToChosenSeries=" + t);
};

var SetChosenSeries = function (t) {
    localStorage.setItem("chosenSeries", JSON.stringify(t));
    console.log("Setting chosenSeries=" + JSON.stringify(t));
};

var GetChosenSeries = function () {
    console.log("Getting chosenSeries=" + localStorage.getItem("chosenSeries"));
    return JSON.parse(localStorage.getItem("chosenSeries"));
};


var GetLinkToCurrentSeries = function () {
    console.log("Getting linkToChosenSeries=" +
        localStorage.getItem("linkToChosenSeries"));
    return localStorage.getItem("linkToChosenSeries");
};

var SetLinkToCurrentSeries = function (t) {
    console.log("Setting linkToChosenSeries=" + t);
    return localStorage.setItem("linkToChosenSeries", t);
};


var SetChosenFonds = function (fonds) {
    localStorage.setItem("chosenFonds", JSON.stringify(fonds));
    console.log("Setting chosenFonds=" + JSON.stringify(fonds));
};

var GetChosenFonds = function () {
    console.log("Getting chosenFonds =" + localStorage.getItem("chosenFonds"));
    return JSON.parse(localStorage.getItem("chosenFonds"));
};


var SetLinkToChosenFile = function (t) {
    localStorage.setItem("linkToChosenFile", t);
    console.log("Setting linkToChosenFile=" + t);
};

var GetLinkToSeriesAllFile = function () {
    return localStorage.getItem("linkToSeriesAllFile");
};

var SetLinkToDocumentFile = function (t) {
    localStorage.setItem("linkToUploadDocumentFile", t);
    console.log("Setting linkToUploadDocumentFile=" + t);
};

var GetLinkToDocumentFile = function () {
    return localStorage.getItem("linkToUploadDocumentFile");
};

// href of the link to use when creating a document object
var SetLinkToCreateDocumentObject = function (t) {
    localStorage.setItem("linkToChosenDocumentObject", t);
    console.log("Setting linkToChosenDocumentObject=" + t);
};

var SetLinkToDocumentObject = function (t) {
    localStorage.setItem("linkToDocumentObject", t);
    console.log("Setting linkToDocumentObject=" + t);
};

var GetLinkToDocumentObject = function (t) {
    return localStorage.setItem("linkToDocumentObject");
};

var GetLinkToCurrentCorrespondencePartPerson = function () {
    console.log("Getting linkToChosenCorrespondencePartPerson=" +
        localStorage.getItem("linkToChosenCorrespondencePartPerson"));
    return localStorage.getItem("linkToChosenCorrespondencePartPerson");
};

var SetLinkToCurrentCorrespondencePartPerson = function (t) {
    localStorage.setItem("linkToChosenCorrespondencePartPerson", t);
    console.log("Setting linkToChosenCorrespondencePartPerson=" + t);
};

var GetLinkToCreateCorrespondencePartPerson = function () {
    console.log("Getting linkToCreateCorrespondencePartPerson=" +
        localStorage.getItem("linkToCreateCorrespondencePartPerson"));
    return localStorage.getItem("linkToCreateCorrespondencePartPerson");
};

var SetLinkToCreateCorrespondencePartPerson = function (t) {
    localStorage.setItem("linkToCreateCorrespondencePartPerson", t);
    console.log("Setting linkToCreateCorrespondencePartPerson=" + t);
};

var ClearLinkToCorrespondencePartPerson = function (t) {
    console.log("Removing linkToChosenCorrespondencePartPerson=" +
        localStorage.getItem("linkToChosenCorrespondencePartPerson"));
    localStorage.removeItem("linkToChosenCorrespondencePartPerson")
};

var GetLinkToCorrespondencePartUnit = function () {
    console.log("Getting linkToChosenCorrespondencePartUnit=" +
        localStorage.getItem("linkToChosenCorrespondencePartUnit"));
    return localStorage.getItem("linkToChosenCorrespondencePartUnit");
};

var SetLinkToCorrespondencePartUnit = function (t) {
    localStorage.setItem("linkToChosenCorrespondencePartUnit", t);
    console.log("Setting linkToChosenCorrespondencePartUnit=" + t);
};


var SetCurrentCaseFileNumber = function (t) {
    localStorage.setItem("currentCaseFileNumber", t);
    console.log("Setting currentCaseFileNumber=" + t);
};

var getCurrentCaseFileNumber = function () {
    return localStorage.getItem("currentCaseFileNumber");
};

var SetCurrentCaseFile = function (t) {
    localStorage.setItem("currentCaseFile", JSON.stringify(t));
    console.log("Setting currentCaseFile=" + JSON.stringify(t));
};

var GetCurrentCaseFile = function () {
    console.log("Getting currentCaseFile=" + localStorage.getItem("currentCaseFile"));
    return JSON.parse(localStorage.getItem("currentCaseFile"));
};

var SetCurrentRegistryEntry = function (t) {
    localStorage.setItem("currentRegistryEntry", JSON.stringify(t));
    console.log("Setting currentRegistryEntry=" + JSON.stringify(t));
};

var GetCurrentRegistryEntry = function () {
    console.log("Getting currentRegistryEntry=" + localStorage.getItem("currentRegistryEntry"));
    return JSON.parse(localStorage.getItem("currentRegistryEntry"));
};

var SetLinkToChosenCaseFile = function (t) {
    localStorage.setItem("linkToCurrentCaseFile", t);
    console.log("Setting linkToCurrentCaseFile=" + t);
};

var GetLinkToChosenCaseFile = function () {
    console.log("Getting linkToCurrentCaseFile=" + localStorage.getItem("linkToCurrentCaseFile"));
    return localStorage.getItem("linkToCurrentCaseFile");
};

var SetLinkToCreateRegistryEntry = function (t) {
    localStorage.setItem("linkToCreateRegistryEntry", t);
    console.log("Setting linkToCreateRegistryEntry=" + t);
};

var GetLinkToCreateRegistryEntry = function () {
    console.log("Getting linkToCreateRegistryEntry=" + localStorage.getItem("linkToCreateRegistryEntry"));
    return localStorage.getItem("linkToCreateRegistryEntry");
};

var SetLinkToGetRegistryEntry = function (t) {
    localStorage.setItem("linkToGetRegistryEntry", t);
    console.log("Setting linkToGetRegistryEntry=" + t);
};

var GetLinkToGetRegistryEntry = function () {
    console.log("Getting linkToGetRegistryEntry=" + localStorage.getItem("linkToGetRegistryEntry"));
    return localStorage.getItem("linkToGetRegistryEntry");
};

var changeLocation = function ($scope, url, forceReload) {
    $scope = $scope || angular.element(document).scope();
    console.log("URL" + url);
    if (forceReload || $scope.$$phase) {
        window.location = url;
    }
    else {
        $location.path(url);
        $scope.$apply();
    }
};

