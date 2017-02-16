#!/usr/bin/env bash

# Put in directory location of json files to populate core with
#/PATH_TO_PROJECT/nikita-noark5-core/core-webapp/src/main/resources/curl/
curl_files_dir="./";

# Setup common curl options
contentTypeForPost+=(--header "Content-Type:application/vnd.noark5-v4+json");
curlOpts+=( -s -S --header "Accept:application/vnd.noark5-v4+json");
curlPostOpts+=("${curlOpts[@]}" "${contentTypeForPost[@]}" -X POST -b ~/tmp/cookie.txt );

# Setup curl options for fonds
curloptsCreateFonds+=("${curlPostOpts[@]}");
curloptsCreateFonds+=( --data @"$curl_files_dir"fonds-data.json  'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/ny-arkiv' );

# Create a fonds object and capture the systemId
curl -X POST -d username=admin -d password=password -c ~/tmp/cookie.txt 'http://localhost:8092/noark5v4/doLogin';
systemIDCreatedFonds=$(curl "${curloptsCreateFonds[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created Fonds 1             ($systemIDCreatedFonds) \n";
#echo  "${curloptsCreateFonds[@]}";

# Setup curl options for series
curloptsCreateSeries+=("${curlPostOpts[@]}");
curloptsCreateSeries+=( --data @"$curl_files_dir"series-data.json  'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkiv/'$systemIDCreatedFonds'/ny-arkivdel' )

# Create a series object and capture the systemId
systemIDCreatedSeries=$(curl "${curloptsCreateSeries[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created  Series 1            ($systemIDCreatedSeries) \n";
#echo  "${curloptsCreateSeries[@]}";

# Setup curl options for file
curloptsCreateFile+=("${curlPostOpts[@]}");
curloptsCreateFile+=( --data @"$curl_files_dir"file-data.json  'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkivdel/'$systemIDCreatedSeries'/ny-mappe' )
echo  "${curloptsCreateFile[@]}";
exit;


# Create a file object and capture the systemId
systemIDCreatedFile=$(curl "${curloptsCreateFile[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created   File 1              ($systemIDCreatedFile) \n";

# Create a record object and capture the systemId
# Note that record does not contain any administration to be uploaded in JSON to be created. createdBy etc are set by the core.
# It is handled as a POST request without data
# The only relevant data that could be uploaded is referenceSeries, which is a type of secondary reference to multiple series
# Setup curl options for Record
curloptsCreateRecord+=("${curlPostOpts[@]}");
curloptsCreateRecord+=( --data @"$curl_files_dir"record-data.json 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/mappe/'$systemIDCreatedFile'/ny-registrering' )

# Create record 1 associated with a file 1 and capture systemId
systemIDCreatedRecord=$(curl "${curloptsCreateRecord[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created    Record 1            ($systemIDCreatedRecord) associated with ($systemIDCreatedFile) \n";


# Setup curl options for documentDescription
curloptsCreateDocumentDescription+=("${curlPostOpts[@]}");
curloptsCreateDocumentDescription+=( --data @"$curl_files_dir"document-description-data.json  'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/registrering/'$systemIDCreatedRecord'/ny-dokumentbeskrivelse' )

# Create documentDescription 1 associated with a file 1 / record 1 and capture systemId
systemIDCreatedDocumentDescription=$(curl "${curloptsCreateDocumentDescription[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created     DocumentDescription ($systemIDCreatedDocumentDescription) associated with ($systemIDCreatedRecord) \n";

# Setup curl options for documentObject
curloptsCreateDocumentObject+=("${curlPostOpts[@]}");
curloptsCreateDocumentObject+=( --data @"$curl_files_dir"document-object-data.json  'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/dokumentbeskrivelse/'$systemIDCreatedDocumentDescription'/ny-dokumentobjekt' )

# Create documentObject 1 associated with file 1 / record 1 / documentDescription 1 /  and capture systemId
systemIDCreatedDocumentObject=$(curl "${curloptsCreateDocumentObject[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created      DocumentObject      ($systemIDCreatedDocumentObject) associated with ($systemIDCreatedDocumentDescription) \n";

curloptsCreateBasicRecord+=("${curlPostOpts[@]}");
curloptsCreateBasicRecord+=( --data @"$curl_files_dir"basic-record-data.json 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/mappe/'$systemIDCreatedFile'/ny-basisregistrering' )

# Create basicRecord 1 associated with a file 1 and capture systemId. Not creating DocumentDescription/Object
systemIDCreatedBasicRecord=$(curl "${curloptsCreateBasicRecord[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created    BasicRecord 1            ($systemIDCreatedBasicRecord) associated with ($systemIDCreatedFile) \n";

# Setup curl options for caseFile
curloptsCreateCaseFile+=("${curlPostOpts[@]}");
curloptsCreateCaseFile+=( --data @"$curl_files_dir"case-file-data.json  'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkivdel/'$systemIDCreatedSeries'/ny-saksmappe' )

# Create caseFile 1 associated with series 1 and capture systemId
systemIDCreatedCaseFile=$(curl "${curloptsCreateCaseFile[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created   CaseFile 1          ($systemIDCreatedCaseFile) \n";

# Setup curl options for registryEntry
curloptsCreateRegistryEntry+=("${curlPostOpts[@]}");
curloptsCreateRegistryEntry+=( --data @"$curl_files_dir"registry-entry-data.json 'http://localhost:8092/noark5v4/hateoas-api/sakarkiv/saksmappe/'$systemIDCreatedCaseFile'/ny-journalpost' )

# Create registryEntry 1 associated with a caseFile 1 and capture systemId
systemIDCreatedRegistryEntry=$(curl "${curloptsCreateRegistryEntry[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created    RegistryEntry 1     ($systemIDCreatedRegistryEntry) associated with ($systemIDCreatedCaseFile)\n";

# Create documentDescription 1 associated with a caseFile 1 / registryEntry 1 and capture systemId
systemIDCreatedDocumentDescription=$(curl "${curloptsCreateDocumentDescription[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created     DocumentDescription ($systemIDCreatedDocumentDescription) associated with ($systemIDCreatedRegistryEntry) \n";

# Create documentObject 1 associated with caseFile 1 / registryEntry 1 / documentDescription 1 /  and capture systemId
systemIDCreatedDocumentObject=$(curl "${curloptsCreateDocumentObject[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created      DocumentObject      ($systemIDCreatedDocumentObject) associated with ($systemIDCreatedDocumentDescription) \n";

# Create registryEntry 2 associated with a caseFile 1 and capture systemId
systemIDCreatedRegistryEntry=$(curl "${curloptsCreateRegistryEntry[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created    RegistryEntry 2     ($systemIDCreatedRegistryEntry) associated with ($systemIDCreatedCaseFile)\n";

# Create documentDescription 2 associated with a caseFile 1 / registryEntry 2 and capture systemId
systemIDCreatedDocumentDescription=$(curl "${curloptsCreateDocumentDescription[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created     DocumentDescription ($systemIDCreatedDocumentDescription) associated with ($systemIDCreatedRegistryEntry) \n";

# Create documentObject 1 associated with caseFile 1 / registryEntry 2 / documentDescription 2 /  and capture systemId
systemIDCreatedDocumentObject=$(curl "${curloptsCreateDocumentObject[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created      DocumentObject      ($systemIDCreatedDocumentObject) associated with ($systemIDCreatedDocumentDescription) \n";

# Create caseFile 2 associated with series 1 and capture systemId
systemIDCreatedCaseFile=$(curl "${curloptsCreateCaseFile[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created   CaseFile 2          ($systemIDCreatedCaseFile) \n";

# Create registryEntry 1 associated with a caseFile 2 and capture systemId
systemIDCreatedRegistryEntry=$(curl "${curloptsCreateRegistryEntry[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created    RegistryEntry 1     ($systemIDCreatedRegistryEntry) associated with ($systemIDCreatedCaseFile)\n";

# Create documentDescription 1 associated with a caseFile 2 / registryEntry 1 and capture systemId
systemIDCreatedDocumentDescription=$(curl "${curloptsCreateDocumentDescription[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created     DocumentDescription ($systemIDCreatedDocumentDescription) associated with ($systemIDCreatedRegistryEntry) \n";

# Create documentObject 1 associated with caseFile 2 / registryEntry 1 / documentDescription 1 /  and capture systemId
systemIDCreatedDocumentObject=$(curl "${curloptsCreateDocumentObject[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created      DocumentObject      ($systemIDCreatedDocumentObject) associated with ($systemIDCreatedDocumentDescription) \n";

# Create registryEntry 2 associated with a caseFile 2 and capture systemId
systemIDCreatedRegistryEntry=$(curl "${curloptsCreateRegistryEntry[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created    RegistryEntry 2     ($systemIDCreatedRegistryEntry) associated with ($systemIDCreatedCaseFile)\n";

# Create documentDescription 1 associated with a caseFile 2 / registryEntry 1 and capture systemId
systemIDCreatedDocumentDescription=$(curl "${curloptsCreateDocumentDescription[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created     DocumentDescription ($systemIDCreatedDocumentDescription) associated with ($systemIDCreatedRegistryEntry) \n";

# Create documentObject 1 associated with caseFile 2 / registryEntry 1 / documentDescription 1 /  and capture systemId
systemIDCreatedDocumentObject=$(curl "${curloptsCreateDocumentObject[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created      DocumentObject      ($systemIDCreatedDocumentObject) associated with ($systemIDCreatedDocumentDescription) \n";

# Create caseFile 3 associated with series 1 and capture systemId
systemIDCreatedCaseFile=$(curl "${curloptsCreateCaseFile[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created   CaseFile 3          ($systemIDCreatedCaseFile) \n";

# Create registryEntry 1 associated with a caseFile 1 and capture systemId
systemIDCreatedRegistryEntry=$(curl "${curloptsCreateRegistryEntry[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created    RegistryEntry 1     ($systemIDCreatedRegistryEntry) associated with ($systemIDCreatedCaseFile)\n";

# Create documentDescription 1 associated with a caseFile 3 / registryEntry 1 and capture systemId
systemIDCreatedDocumentDescription=$(curl "${curloptsCreateDocumentDescription[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created     DocumentDescription ($systemIDCreatedDocumentDescription) associated with ($systemIDCreatedRegistryEntry) \n";

# Create documentObject 1 associated with caseFile 3 / registryEntry 1 / documentDescription 1 /  and capture systemId
systemIDCreatedDocumentObject=$(curl "${curloptsCreateDocumentObject[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created      DocumentObject      ($systemIDCreatedDocumentObject) associated with ($systemIDCreatedDocumentDescription) \n";

# Create registryEntry 2 associated with a caseFile 1 and capture systemId
systemIDCreatedRegistryEntry=$(curl "${curloptsCreateRegistryEntry[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created    RegistryEntry 2     ($systemIDCreatedRegistryEntry) associated with ($systemIDCreatedCaseFile)\n";

# Create documentDescription 1 associated with a caseFile 3 / registryEntry 2 and capture systemId
systemIDCreatedDocumentDescription=$(curl "${curloptsCreateDocumentDescription[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created     DocumentDescription ($systemIDCreatedDocumentDescription) associated with ($systemIDCreatedRegistryEntry) \n";

# Create documentObject 1 associated with caseFile 3 / registryEntry 2 / documentDescription 1 /  and capture systemId
systemIDCreatedDocumentObject=$(curl "${curloptsCreateDocumentObject[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created      DocumentObject      ($systemIDCreatedDocumentObject) associated with ($systemIDCreatedDocumentDescription) \n";

curlGetOpts+=("${curlOpts[@]}");
curlGetOpts+=( -X GET -b ~/tmp/cookie.txt );

printf "Retrieving some of the created objects\n";
printf " -- Retrieving fonds with systemID $systemIDCreatedFonds \n";
curloptsGETFonds+=( "${curlGetOpts[@]}" 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkiv/'$systemIDCreatedFonds)
output=$(curl "${curloptsGETFonds[@]}");

printf "$output \n";

printf " -- Retrieving series with systemID $systemIDCreatedSeries \n";
curloptsGETSeries+=( "${curlGetOpts[@]}" 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkivdel/'$systemIDCreatedSeries)
output=$(curl "${curloptsGETSeries[@]}");
printf "$output \n";

printf " -- Retrieving caseFile with systemID $systemIDCreatedCaseFile \n";
curloptsGETCaseFile+=( "${curlGetOpts[@]}" 'http://localhost:8092/noark5v4/hateoas-api/sakarkiv/saksmappe/'$systemIDCreatedCaseFile)
output=$(curl "${curloptsGETCaseFile[@]}");
printf "$output \n";

printf " -- Retrieving registryEntry  with systemID $systemIDCreatedRegistryEntry \n";
curloptsGETRegistryEntry+=( "${curlGetOpts[@]}" 'http://localhost:8092/noark5v4/hateoas-api/sakarkiv/journalpost/'$systemIDCreatedRegistryEntry)
output=$(curl "${curloptsGETRegistryEntry[@]}");
printf "$output \n";

printf " -- Retrieving documentDescription with systemID $systemIDCreatedDocumentDescription \n";
curloptsGETDocumentDescription+=( "${curlGetOpts[@]}" 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/dokumentbeskrivelse/'$systemIDCreatedDocumentDescription)
output=$(curl "${curloptsGETDocumentDescription[@]}");
printf "$output \n";

printf " -- Retrieving documentObject with systemID $systemIDCreatedDocumentObject \n";
curloptsGETDocumentObject+=( "${curlGetOpts[@]}" 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/dokumentobjekt/'$systemIDCreatedDocumentObject)
output=$(curl "${curloptsGETDocumentObject[@]}");
printf "$output \n";
