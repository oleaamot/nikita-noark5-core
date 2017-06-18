/**
 * Created by tsodring on 5/2/17.
 */


nikitaOptions = {
    baseUrl: 'localhost:8092',
    guiBaseUrl: 'http://localhost:3000/',
    appUrl: 'http://localhost:8092/noark5v4/hateoas-api',
    loginUrl: "http://localhost:8092/noark5v4/auth",
    protocol: 'http',
    appName: 'noark5v4',
    apiName: 'hateoas-api',
    authPoint: 'auth',
    displayFooterNote: true,
    enabled: true
};

console.log("Hello ....");

// Set the base url for application
var base_url =  "http://" + nikitaOptions.baseUrl + "/noark5v4";

// Set the url for the GUI across all web pages
var gui_base_url = nikitaOptions.guiBaseUrl;
// Starting point for the application
var app_url = nikitaOptions.appUrl
// Set the url where POST requests with login credentials should be sent
var login_url = nikitaOptions.loginUrl;
// Whether or not to dsiplay link to nikita source
var display_footer_note = nikitaOptions.displayFooterNote;

var fondsPageName = 'arkiv.html';
var seriesPageName = 'arkivdel.html';
var caseFilePageName = 'saksmappe.html';
var registryEntryPageName = 'journalpost.html';
var documentPageName = 'dokument.html';

var REL_NEW_REGISTRY_ENTRY = 'http://nikita.arkivlab.no/noark5/v4/ny-journalpost/';
var REL_NEW_DOCUMENT_DESCRIPTION = 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-dokumentbeskrivelse/';
var REL_DOCUMENT_DESCRIPTION = 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/dokumentbeskrivelse/';
var REL_DOCUMENT_OBJECT = 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/dokumentobjekt/';
var REL_NEW_DOCUMENT_OBJECT = 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/ny-dokumentobjekt/';
var REL_DOCUMENT_FILE = 'http://rel.kxml.no/noark5/v4/api/arkivstruktur/fil/';


// These will be picked up from the database
var mimeTypeList = [
    { id: 'rfc822', value: 'message/rfc822' },
    { id: 'pdf', value: 'application/pdf' },
    { id: 'odt', value: 'application/vnd.oasis.opendocument.text' },
    { id: 'ods', value: 'application/vnd.oasis.opendocument.spreadsheet' },
    { id: 'odp', value: 'application/vnd.oasis.opendocument.presentation' },
    { id: 'tiff', value: 'image/tiff' },
    { id: 'jpeg', value: 'image/jpeg' },
];

var variantFormatList = [{ id: 'P', value: 'Produksjonsformat' },
    { id: 'A', value: 'Arkivformat' },
    { id: 'O', value: 'Dokument hvor deler av innholdet er skjermet' }];

var documentTypeList = [{ id: 'B', value: 'Brev' },
                        { id: 'F', value: 'Faktura' },
                        { id: 'O', value: 'Ordrebekreftelse' }];

var tilknyttetRegistreringSomList = [ { id: 'H', value: 'Hoveddokument' },
                                      { id: 'V', value: 'Vedlegg' }];

var documentStatusList = [{ id: 'B', value: 'Dokumentet er under redigering' },
                 { id: 'F', value: 'Dokumentet er ferdigstilt' }];

var emptyList = [{ id: '', value: '' },
                 { id: '', value: '' },
                 { id: '', value: '' },
                 { id: '', value: '' }];


console.log("Setting nikita gui_base_url: " + gui_base_url);
console.log("Setting nikita app_url: " + app_url);
console.log("Setting nikita login_url: " + login_url);

var SetUserToken = function(t) {
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
    console.log("getting linktochosenfile="+localStorage.getItem("linkToChosenFile"));
    return localStorage.getItem("linkToChosenFile");
};

var SetLinkToChosenRecord = function (t) {
    localStorage.setItem("linkToChosenRecord", t);
    console.log("Setting linkToChosenRecord="+t);
};

var SetCurrentRecordSystemId = function (recordSystemId) {
    localStorage.setItem("currentRecordSystemId", recordSystemId);
    console.log("Setting currentRecordSystemId="+recordSystemId);
};

var GetSeriesSystemID = function () {
    console.log("Getting chosen currentSeriesSystemId="+localStorage.getItem("currentSeriesSystemId"));
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
    console.log("Setting linkToCreateDocumentDescription="+t);
};

// href of the link to use when getting a document description
var GetLinkToDocumentDescription = function () {
    return localStorage.getItem("linkToDocumentDescription");
};

// href of the link to use when getting a document object
var SetLinkToDocumentDescription = function (t) {
    localStorage.setItem("linkToDocumentDescription", t);
    console.log("Setting linkToDocumentDescription="+t);
};

var SetLinkToChosenSeries = function(t) {
    localStorage.setItem("linkToChosenSeries", t);
    console.log("Setting linkToChosenSeries="+t);
};

var SetChosenFonds = function(fondSystemId) {
    localStorage.setItem("chosenfonds", fondSystemId);
    console.log("Setting fondSystemId="+fondSystemId);
};

var SetLinkToChosenFile = function(t) {
    localStorage.setItem("linkToChosenFile", t);
    console.log("Setting linkToChosenFile="+t);
};

var GetLinkToSeriesAllFile = function () {
    return localStorage.getItem("linkToSeriesAllFile");
};

var SetLinkToDocumentFile = function (t) {
    localStorage.setItem("linkToUploadDocumentFile", t);
    console.log("Setting linkToUploadDocumentFile="+t);
};

var GetLinkToDocumentFile = function () {
    return localStorage.getItem("linkToUploadDocumentFile");
};

// href of the link to use when creating a document object
var SetLinkToCreateDocumentObject = function (t) {
    localStorage.setItem("linkToChosenDocumentObject", t);
    console.log("Setting linkToChosenDocumentObject="+t);
};
