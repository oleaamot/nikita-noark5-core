#!/usr/bin/env bash


# This is a project internal script used as a basic test mechanism. It will be replaced by a proper testing framework
# later.

# Put in directory location of json files to populate core with
#$1 should be set to /PATH_TO_PROJECT/nikita-noark5-core/scripts/json-example-data
# or simple ./populate_core_with_data.sh ./json-example-data
curl_files_dir=$1;

# Pick up directory with JSON files
if [[ -n "$curl_files_dir" ]]; then
    length=${#curl_files_dir}
    length=$(expr $length - 1)
    if [ "${curl_files_dir:length}" != "/" ]; then
      echo "Adding missing trailing slash"
      curl_files_dir=$curl_files_dir"/"
    fi
    echo "running with json files in $curl_files_dir";
else
    echo "usage: $0 directory";
    exit;
fi

# login to the core using the JWT method
authToken=$(curl -s --header Content-Type:application/json -X POST  --data '{"username" : "admin", "password" : "password"}' http://localhost:8092/noark5v4/auth | jq '.token' | sed 's/\"//g');

# Note It seems to returning the word "null" if empty
if [ $authToken = "null" ]; then
    echo "Did not get a proper token back from the core."
    exit;
fi

# Setup common curl options
contentTypeForPost+=(--header "Content-Type:application/vnd.noark5-v4+json");
curlOpts+=( -s --header "Accept:application/vnd.noark5-v4+json" --header Authorization:$authToken);
curlPostOpts+=("${curlOpts[@]}" "${contentTypeForPost[@]}" -X POST );
curlPutOpts+=("${curlOpts[@]}" "${contentTypeForPost[@]}" -X PUT );

# Setup curl options for fonds
curloptsCreateFonds+=("${curlPostOpts[@]}");
curloptsCreateFonds+=( --data @"$curl_files_dir"fonds-data.json  'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/ny-arkiv' );

# Create a fonds object and capture the systemId
systemIDCreatedFonds=$(curl "${curloptsCreateFonds[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created Fonds 1             ($systemIDCreatedFonds) \n";
#echo  "${curloptsCreateFonds[@]}";

# Setup curl options for fondsCreator from root
curloptsCreateFondsCreatorFromRoot+=("${curlPostOpts[@]}");
curloptsCreateFondsCreatorFromRoot+=( --data @"$curl_files_dir"fonds-creator-data.json  'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/ny-arkivskaper' );
systemIDCreatedFondsCreator=$(curl "${curloptsCreateFondsCreatorFromRoot[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created FondsCreator 1(root)($systemIDCreatedFondsCreator) \n";
echo "${curloptsCreateFondsCreatorFromRoot[@]}" ;
exit;

curloptsCreateFondsCreatorUpdate+=("${curlPutOpts[@]}");
curloptsCreateFondsCreatorUpdate+=( --data '{"systemID" : "'$systemIDCreatedFondsCreator'", "arkivskaperID": "123456789U", "arkivskaperNavn": "Eksempel kommune UPDATED",  "beskrivelse": "Eksempel kommune ligger i eksempel fylke nord for nord UPDATED" } ' 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkivskaper/' );
systemIDCreatedFondsCreator=$(curl "${curloptsCreateFondsCreatorUpdate[@]}" | jq '.systemID' | sed 's/\"//g');
printf "updated FondsCreator 1      ($systemIDCreatedFondsCreator) \n";
echo "${curloptsCreateFondsCreatorUpdate[@]}";
exit;

# Setup curl options for fondsCreator from existing fonds
curloptsCreateFondsCreator+=("${curlPostOpts[@]}");
curloptsCreateFondsCreator+=( --data @"$curl_files_dir"fonds-creator-data.json  'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkiv/'$systemIDCreatedFonds'/ny-arkivskaper' );
systemIDCreatedFondsCreator=$(curl "${curloptsCreateFondsCreator[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created FondsCreator 2(arkiv)($systemIDCreatedFondsCreator) \n";

# Setup curl options for fonds from existing fondsCreator
curloptsCreateFondsFromFondsCreator+=("${curlPostOpts[@]}");
curloptsCreateFondsFromFondsCreator+=( --data @"$curl_files_dir"fonds-data.json  'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkivskaper/'$systemIDCreatedFondsCreator'/ny-arkiv' );
systemIDCreatedFonds=$(curl "${curloptsCreateFondsFromFondsCreator[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created Fonds 2(arkivskaper)($systemIDCreatedFonds) \n";

# Setup curl options for series
curloptsCreateSeries+=("${curlPostOpts[@]}");
curloptsCreateSeries+=( --data @"$curl_files_dir"series-data.json  'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkiv/'$systemIDCreatedFonds'/ny-arkivdel' )

# Create a series object and capture the systemId
systemIDCreatedSeries=$(curl "${curloptsCreateSeries[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created  Series 1            ($systemIDCreatedSeries) \n";

# Setup curl options for file
curloptsCreateFile+=("${curlPostOpts[@]}");
curloptsCreateFile+=( --data @"$curl_files_dir"file-data.json  'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkivdel/'$systemIDCreatedSeries'/ny-mappe/' )

# Create a file object and capture the systemId
systemIDCreatedFile=$(curl "${curloptsCreateFile[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created   File 1              ($systemIDCreatedFile) \n";
#echo ${curloptsCreateFile[@]};

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
#echo "${curloptsCreateDocumentObject[@]}" "\n";

# Setup curl options for uploading file associated with documentObject
# Note /dev/null means this won't work on windows, probably want to pipe the output with >> or similar approach
# For windows, just remove  -o /dev/null and ignore output on screen
curlPostFileOpts+=( -s  -X POST --header "Accept:application/vnd.noark5-v4+json" --header Authorization:$authToken --header CONTENT-Length:21774 --header Content-Type:application/pdf -o /dev/null  --data-binary "@"$curl_files_dir"test_upload_document.pdf");
curloptsUploadFile+=("${curlPostFileOpts[@]}");
curloptsUploadFile+=( -w "%{http_code}" 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/dokumentobjekt/'$systemIDCreatedDocumentObject'/referanseFil' )
#echo "${curloptsUploadFile[@]} ";

resultFileUpload=$(curl "${curloptsUploadFile[@]}");
printf "uploaded file to DocumentObject  ($systemIDCreatedDocumentObject) Result $resultFileUpload\n";

curlGetFileOpts+=( -s -S -X GET -b /tmp/cookie.txt  -o downloaded.pdf -w "%{http_code}");
curloptsDownloadFile+=("${curlGetFileOpts[@]}");
curloptsDownloadFile+=( 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/dokumentobjekt/'$systemIDCreatedDocumentObject'/referanseFil' )

resultFileDownload=$(curl "${curloptsUploadFile[@]}");
#echo "${curloptsDownloadFile[@]}";
printf "downloaded file from DocumentObject  ($systemIDCreatedDocumentObject) Result $resultFileDownload\n";

curloptsCreateBasicRecord+=("${curlPostOpts[@]}");
curloptsCreateBasicRecord+=( --data @"$curl_files_dir"basic-record-data.json 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/mappe/'$systemIDCreatedFile'/ny-basisregistrering' )

# Create basicRecord 1 associated with a file 1 and capture systemId. Not creating DocumentDescription/Object
systemIDCreatedBasicRecord=$(curl "${curloptsCreateBasicRecord[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created    BasicRecord 1            ($systemIDCreatedBasicRecord) associated with ($systemIDCreatedFile) \n";

# Setup curl options for caseFile
curloptsCreateCaseFile+=("${curlPostOpts[@]}");
curloptsCreateCaseFile+=( --data @"$curl_files_dir"case-file-data.json  'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkivdel/'$systemIDCreatedSeries'/ny-saksmappe/' )

# Create caseFile 1 associated with series 1 and capture systemId
systemIDCreatedCaseFile=$(curl "${curloptsCreateCaseFile[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created   CaseFile 1          ($systemIDCreatedCaseFile) \n";
#echo "${curloptsCreateCaseFile[@]}";

# Setup curl options for registryEntry
curloptsCreateRegistryEntry+=("${curlPostOpts[@]}");
curloptsCreateRegistryEntry+=( --data @"$curl_files_dir"registry-entry-data.json 'http://localhost:8092/noark5v4/hateoas-api/sakarkiv/saksmappe/'$systemIDCreatedCaseFile'/ny-journalpost' )

# Create registryEntry 1 associated with a caseFile 1 and capture systemId
systemIDCreatedRegistryEntry=$(curl "${curloptsCreateRegistryEntry[@]}" | jq '.systemID' | sed 's/\"//g');
#echo "${curloptsCreateRegistryEntry[@]}";
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
#echo "${curloptsCreateDocumentObject[@]}";

# Check that the GET ny-* links are working
# Currently, it doesn't required a correct systemId as the returned objects would be very work-domain specific and based
# on who is logged in and what area they work with

exit;

curlGetNewEntityOpts+=( -s -S -X GET --header "Accept:application/vnd.noark5-v4+json" -b /tmp/cookie.txt -w "%{http_code} ");

curloptsNewFonds+=("${curlGetNewEntityOpts[@]}" -o ./fonds-data.json );
curloptsNewFonds+=( 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/ny-arkiv' )
curloptsNewSeries+=("${curlGetNewEntityOpts[@]}" -o ./series-data.json);
curloptsNewSeries+=( 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkiv/'$systemIDCreatedFonds'/ny-arkivdel' )
curloptsNewFile+=("${curlGetNewEntityOpts[@]}" -o ./file-data.json);
curloptsNewFile+=( 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkivdel/'$systemIDCreatedSeries'/ny-mappe/' )
curloptsNewRecord+=("${curlGetNewEntityOpts[@]}" -o ./record-data.json);
curloptsNewRecord+=( 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/mappe/'$systemIDCreatedFile'/ny-registrering' )
curloptsNewBasicRecord+=("${curlGetNewEntityOpts[@]}" -o ./basic-record-data.json);
curloptsNewBasicRecord+=( 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/mappe/'$systemIDCreatedFile'/ny-basisregistrering' )
curloptsNewDocumentDescription+=("${curlGetNewEntityOpts[@]}" -o ./document-description-data.json);
curloptsNewDocumentDescription+=( 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/registrering/'$systemIDCreatedRecord'/ny-dokumentbeskrivelse' )
curloptsNewDocumentObject+=("${curlGetNewEntityOpts[@]}" -o ./document-object-data.json);
curloptsNewDocumentObject+=( 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/dokumentbeskrivelse/'$systemIDCreatedDocumentDescription'/ny-dokumentobjekt' )
curloptsNewDocumentObject2+=("${curlGetNewEntityOpts[@]}" -o /dev/null);
curloptsNewDocumentObject2+=( 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/registrering/'$systemIDCreatedRecord'/ny-dokumentobjekt' )
curloptsNewCaseFile+=("${curlGetNewEntityOpts[@]}" -o ./case-file-data.json);
curloptsNewCaseFile+=( 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkivdel/'$systemIDCreatedSeries'/ny-saksmappe/' )
curloptsNewRegistryEntry+=("${curlGetNewEntityOpts[@]}" -o ./registry-entry-data.json);
curloptsNewRegistryEntry+=( 'http://localhost:8092/noark5v4/hateoas-api/sakarkiv/saksmappe/'$systemIDCreatedCaseFile'/ny-journalpost' )

resultNewFonds=$(curl "${curloptsNewFonds[@]}");


printf "Test of ny-arkiv is $resultNewFonds\n";

resultNewSeries=$(curl "${curloptsNewSeries[@]}");
printf "Test of ny-arkivdel is $resultNewSeries\n";

resultNewFile=$(curl "${curloptsNewFile[@]}");
printf "Test of ny-mappe is $resultNewFile\n";

resultNewRecord=$(curl "${curloptsNewRecord[@]}");
printf "Test of ny-registrering is $resultNewRecord\n";

resultNewBasicRecord=$(curl "${curloptsNewBasicRecord[@]}");
printf "Test of ny-basisregistrering is $resultNewBasicRecord\n";

resultNewDocumentDescription=$(curl "${curloptsNewDocumentDescription[@]}");
printf "Test of ny-dokumentbeskrivelse is $resultNewDocumentDescription\n";

resultNewDocumentObject=$(curl "${curloptsNewDocumentObject[@]}");
printf "Test of ny-dokumentobjekt is $resultNewDocumentObject\n";

resultNewDocumentObject2=$(curl "${curloptsNewDocumentObject2[@]}");
printf "Test of ny-dokumentobjekt (on record) is $resultNewDocumentObject2\n";

resultNewCaseFile=$(curl "${curloptsNewCaseFile[@]}");
printf "Test of ny-saksmappes is $resultNewCaseFile\n";

resultNewRegistryEntry=$(curl "${curloptsNewRegistryEntry[@]}");
printf "Test of ny-journalpost is $resultNewRegistryEntry\n";

# Print some Noark 5 entities just to show that Hateoas links are working
# This should probably be configurable but ending here

exit

curlGetOpts+=("${curlOpts[@]}");
curlGetOpts+=( -X GET -b /tmp/cookie.txt );

printf "Retrieving some of the created objects\n";
printf " -- Retrieving fonds with systemID $systemIDCreatedFonds \n";
curloptsGETFonds+=( "${curlGetOpts[@]}" 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkiv/'$systemIDCreatedFonds)
output=$(curl "${curloptsGETFonds[@]}");

printf "$output \n";

printf " -- Retrieving series with systemID $systemIDCreatedSeries \n";
curloptsGETSeries+=( "${curlGetOpts[@]}" 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkivdel/'$systemIDCreatedSeries/)
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

## login
#curl -i -X POST -d username=admin -d password=password -c/tmp/cookie.txt http://localhost:8092/noark5v4/doLogin
#
## Get template for arkiv
#curl -v -s -S --header Accept:application/vnd.noark5-v4+json -X GET -b /tmp/cookie.txt  http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/ny-arkiv
## Create an arkiv
#curl -v -s -S --header Accept:application/vnd.noark5-v4+json -X POST -b /tmp/cookie.txt  --header Content-Type:application/vnd.noark5-v4+json --data @fonds-data.json http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/ny-arkiv
#
## Get template for arkivdel using ny-arkivdel
#curl -v -s -S --header Accept:application/vnd.noark5-v4+json -X GET -b /tmp/cookie.txt  http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkiv/f7c3077f-d40b-494a-bc77-da7b0ec8debc/ny-arkivdel
## Create an arkivdel
#curl -v -s -S --header Accept:application/vnd.noark5-v4+json -X POST -b /tmp/cookie.txt  --header Content-Type:application/vnd.noark5-v4+json --data @series-data.json http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkiv/f7c3077f-d40b-494a-bc77-da7b0ec8debc/ny-arkivdelcurl -v -s -S --header Accept:application/vnd.noark5-v4+json -X GET -b /tmp/cookie.txt  http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkivdel/42d4ccab-24eb-46fd-bea7-18d44c20f8c4/ny-mappe
#
## Get template for File using ny-mappe
#curl -v -s -S --header Accept:application/vnd.noark5-v4+json -X GET -b /tmp/cookie.txt  http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkivdel/42d4ccab-24eb-46fd-bea7-18d44c20f8c4/ny-mappe/
## Create a mappe
#curl -v -s -S --header Accept:application/vnd.noark5-v4+json -X POST -b /tmp/cookie.txt  --header Content-Type:application/vnd.noark5-v4+json --data @file-data.json http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkivdel/42d4ccab-24eb-46fd-bea7-18d44c20f8c4/ny-mappe
#
## Get template for record using ny-registrering
#curl -v -s -S --header Accept:application/vnd.noark5-v4+json -X GET -b /tmp/cookie.txt  http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/mappe/7dec239f-05d7-4e65-acb1-be562759e01d/ny-registrering/
#
## Create a record
#curl -v -s -S --header Accept:application/vnd.noark5-v4+json -X POST -b /tmp/cookie.txt  --header Content-Type:application/vnd.noark5-v4+json --data @record-data.json http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/mappe/7dec239f-05d7-4e65-acb1-be562759e01d/ny-registrering
#
## Get template for record using ny-dokumentbeskrivelse
#curl -v -s -S --header Accept:application/vnd.noark5-v4+json -X GET -b /tmp/cookie.txt  http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/mappe/7dec239f-05d7-4e65-acb1-be562759e01d/ny-dokumentbeskrivelse/
#
## Create a dokumentbeskrivelse
#curl -v -s -S --header Accept:application/vnd.noark5-v4+json -X POST -b /tmp/cookie.txt  --header Content-Type:application/vnd.noark5-v4+json --data @record-data.json http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/registrering/7dec239f-05d7-4e65-acb1-be562759e01d/ny-dokumentbeskrivelse
#
## Get tempate for CaseFile using ny-saksmappe
#curl -v -s -S --header Accept:application/vnd.noark5-v4+json -X GET -b /tmp/cookie.txt  http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkivdel/42d4ccab-24eb-46fd-bea7-18d44c20f8c4/ny-saksmappe/
## Create a casefile
# curl -v -s -S --header Accept:application/vnd.noark5-v4+json -X POST -b /tmp/cookie.txt  --header Content-Type:application/vnd.noark5-v4+json --data @case-file-data.json http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkivdel/42d4ccab-24eb-46fd-bea7-18d44c20f8c4/ny-saksmappe/
