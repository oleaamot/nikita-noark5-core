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
authToken=$(curl -s  --header Accept:application/json --header Content-Type:application/json -X POST  --data '{"username" : "admin", "password" : "password"}' http://localhost:8092/noark5v4/auth | jq '.token' | sed 's/\"//g');

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
#echo "${curloptsCreateFondsCreatorFromRoot[@]}" ;



curloptsCreateFondsCreatorUpdate+=("${curlPutOpts[@]}");
curloptsCreateFondsCreatorUpdate+=(  --header Etag:\"0\" --data '{"arkivskaperID": "123456789U", "arkivskaperNavn": "Eksempel kommune UPDATED",  "beskrivelse": "Eksempel kommune ligger i eksempel fylke nord for nord UPDATED" } ' 'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkivskaper/'$systemIDCreatedFondsCreator );
systemIDCreatedFondsCreator=$(curl "${curloptsCreateFondsCreatorUpdate[@]}" | jq '.systemID' | sed 's/\"//g');
printf "updated FondsCreator 1      ($systemIDCreatedFondsCreator) \n";
#echo "${curloptsCreateFondsCreatorUpdate[@]}";

# Setup curl options for series
curloptsCreateSeries+=("${curlPostOpts[@]}");
curloptsCreateSeries+=( --data @"$curl_files_dir"series-data.json  'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/arkiv/'$systemIDCreatedFonds'/ny-arkivdel' )

# Create a series object and capture the systemId
systemIDCreatedSeries=$(curl "${curloptsCreateSeries[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created  Series 1            ($systemIDCreatedSeries) \n";
#echo ${curloptsCreateSeries[@]};

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

# Setup curl options for correspondencePartPErson
curloptsCorrespondencePart+=("${curlPostOpts[@]}");
curloptsCorrespondencePart+=( --data @"$curl_files_dir"correspondence-part-person-data.json 'http://localhost:8092/noark5v4/hateoas-api/sakarkiv/journalpost/'$systemIDCreatedRegistryEntry'/ny-korrespondansepartperson' )
#echo "${curloptsCorrespondencePart[@]}";

systemIDCreatedCorrespondencePart=$(curl "${curloptsCorrespondencePart[@]}" | jq '.systemID' | sed 's/\"//g');
echo "${curloptsCorrespondencePart[@]}";
printf "created   CorrespondencePart ($systemIDCreatedCorrespondencePart) \n";

# Setup curl options for documentDescription
curloptsCreateDocumentDescription+=("${curlPostOpts[@]}");
curloptsCreateDocumentDescription+=( --data @"$curl_files_dir"document-description-data.json  'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/registrering/'$systemIDCreatedRegistryEntry'/ny-dokumentbeskrivelse' )

# Create documentDescription 1 associated with a caseFile 1 / registryEntry 1 and capture systemId
systemIDCreatedDocumentDescription=$(curl "${curloptsCreateDocumentDescription[@]}" | jq '.systemID' | sed 's/\"//g');
#echo "${curloptsCreateDocumentDescription[@]}";

printf "created     DocumentDescription ($systemIDCreatedDocumentDescription) associated with ($systemIDCreatedRegistryEntry) \n";

# Setup curl options for documentObject
curloptsCreateDocumentObject+=("${curlPostOpts[@]}");
curloptsCreateDocumentObject+=( --data @"$curl_files_dir"document-object-data.json  'http://localhost:8092/noark5v4/hateoas-api/arkivstruktur/dokumentbeskrivelse/'$systemIDCreatedDocumentDescription'/ny-dokumentobjekt' )

# Create documentObject 1 associated with caseFile 1 / registryEntry 1 / documentDescription 1 /  and capture systemId
systemIDCreatedDocumentObject=$(curl "${curloptsCreateDocumentObject[@]}" | jq '.systemID' | sed 's/\"//g');
printf "created      DocumentObject      ($systemIDCreatedDocumentObject) associated with ($systemIDCreatedDocumentDescription) \n";
