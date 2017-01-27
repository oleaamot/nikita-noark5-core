#!/usr/bin/env bash
# This is a script to populate the Noark core with imported data. It is assumed that you have the minimum amount of
# Noark data when creating Noark objects. The example json files have this information. Note this is really only
# important if you want to be able to create an extraction at some time in the futire.

# Put in directory location of json files to populate core with
#/PATH_TO_PROJECT/nikita-noark5-core/core-webapp/src/main/resources/curl/
curl_files_dir="./";

# Setup common curl options
curlopts+=( -s -S --header "Accept:application/vnd.noark5-v4+json" --header "Content-Type:application/vnd.noark5-v4+json" );
curlopts+=( -X POST -b ~/tmp/cookie.txt );

# Setup curl options for fonds
curloptsCreateFonds+=("${curlopts[@]}");
curloptsCreateFonds+=( --data @"$curl_files_dir"fonds-data-import.json  'http://localhost:8092/noark5v4/import-api/arkivstruktur/ny-arkiv' );

# Create a fonds object and capture the systemId
curl -X POST -d username=admin -d password=password -c ~/tmp/cookie.txt 'http://localhost:8092/noark5v4/doLogin';
systemIDCreatedFonds=$(curl "${curloptsCreateFonds[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created Fonds 1             ($systemIDCreatedFonds) \n";

# Setup curl options for series
curloptsCreateSeries+=("${curlopts[@]}");
curloptsCreateSeries+=( --data @"$curl_files_dir"series-data-import.json  'http://localhost:8092/noark5v4/import-api/arkivstruktur/arkiv/'$systemIDCreatedFonds'/ny-arkivdel' )

# Create a series object and capture the systemId
systemIDCreatedSeries=$(curl "${curloptsCreateSeries[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created  Series 1            ($systemIDCreatedSeries) \n";

# Setup curl options for caseFile
curloptsCreateCaseFile+=("${curlopts[@]}");
curloptsCreateCaseFile+=( --data @"$curl_files_dir"case-file-data-import.json  'http://localhost:8092/noark5v4/import-api/arkivstruktur/arkivdel/'$systemIDCreatedSeries'/ny-saksmappe' )

# Create caseFile 1 associated with series 1 and capture systemId
systemIDCreatedCaseFile=$(curl "${curloptsCreateCaseFile[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created   CaseFile 1          ($systemIDCreatedCaseFile) \n";

# Setup curl options for registryEntry
curloptsCreateRegistryEntry+=("${curlopts[@]}");
curloptsCreateRegistryEntry+=( --data @"$curl_files_dir"registry-entry-data-import.json 'http://localhost:8092/noark5v4/import-api/arkivstruktur/saksmappe/'$systemIDCreatedCaseFile'/ny-journalpost' )

# Create registryEntry 1 associated with a caseFile 1 and capture systemId
systemIDCreatedRegistryEntry=$(curl "${curloptsCreateRegistryEntry[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created    RegistryEntry 1     ($systemIDCreatedRegistryEntry) associated with ($systemIDCreatedCaseFile)\n";

# Setup curl options for documentDescription
basicCurloptsCreateDocumentDescription+=("${curlopts[@]}");
basicCurloptsCreateDocumentDescription+=( --data @"$curl_files_dir"document-description-data-import.json );

curloptsCreateDocumentDescription=("${basicCurloptsCreateDocumentDescription[@]}" 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/registrering/'$systemIDCreatedRegistryEntry'/ny-dokumentbeskrivelse' );

# Create documentDescription 1 associated with a caseFile 1 / registryEntry 1 and capture systemId
systemIDCreatedDocumentDescription=$(curl "${curloptsCreateDocumentDescription[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created     DocumentDescription ($systemIDCreatedDocumentDescription) associated with ($systemIDCreatedRegistryEntry) \n";
