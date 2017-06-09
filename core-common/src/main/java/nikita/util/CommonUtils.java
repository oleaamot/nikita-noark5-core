package nikita.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import nikita.config.N5ResourceMappings;
import nikita.model.noark5.v4.FondsCreator;
import nikita.model.noark5.v4.Series;
import nikita.model.noark5.v4.admin.AdministrativeUnit;
import nikita.model.noark5.v4.admin.User;
import nikita.model.noark5.v4.casehandling.CaseParty;
import nikita.model.noark5.v4.casehandling.DocumentFlow;
import nikita.model.noark5.v4.casehandling.Precedence;
import nikita.model.noark5.v4.casehandling.secondary.*;
import nikita.model.noark5.v4.hateoas.Link;
import nikita.model.noark5.v4.interfaces.*;
import nikita.model.noark5.v4.interfaces.entities.*;
import nikita.model.noark5.v4.interfaces.entities.admin.IAdministrativeUnitEntity;
import nikita.model.noark5.v4.interfaces.entities.admin.IUserEntity;
import nikita.model.noark5.v4.interfaces.entities.casehandling.*;
import nikita.model.noark5.v4.metadata.CorrespondencePartType;
import nikita.model.noark5.v4.secondary.*;
import nikita.util.exceptions.NikitaException;
import nikita.util.exceptions.NikitaMalformedInputDataException;
import org.springframework.http.HttpMethod;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static nikita.config.Constants.*;
import static nikita.config.HATEOASConstants.*;
import static nikita.config.N5ResourceMappings.*;
import static org.springframework.http.HttpMethod.*;

public final class CommonUtils {

    /**
     * Holds a list of serlvetPaths and their HTTP methods
     */
    private static Map<String, Set<HttpMethod>> requestMethodMap = new HashMap<>();

    // You shall not instantiate me!
    private CommonUtils() {
    }

    public static final class Validation {

        /**
         * Home made validation utility. We decided not to use springs validation framework as it is done on a
         * per-class basis. We need to validate on a per-class, per-CRUD method basis. E.g. A incoming fonds
         * will be validated differently if it's a CREATE operation as opposed to a UPDATE operation.
         *
         * @param nikitaEntity
         * @return true if valid. If not valid an exception is thrown
         */
        public static boolean validateUpdateNoarkEntity(@NotNull INikitaEntity nikitaEntity) {

            if (nikitaEntity instanceof INoarkTitleDescriptionEntity) {
                rejectIfEmptyOrWhitespace(((INoarkTitleDescriptionEntity) nikitaEntity).getDescription());
                rejectIfEmptyOrWhitespace(((INoarkTitleDescriptionEntity) nikitaEntity).getTitle());
            }
            if (nikitaEntity instanceof INoarkTitleDescriptionEntity) {
                rejectIfEmptyOrWhitespace(((INoarkTitleDescriptionEntity) nikitaEntity).getDescription());
                rejectIfEmptyOrWhitespace(((INoarkTitleDescriptionEntity) nikitaEntity).getTitle());
            }


            return true;
        }

        public static void rejectIfEmptyOrWhitespace(String stringToCheck) {

        }

        public static boolean validateCreateNoarkEntity(@NotNull INoarkGeneralEntity noarkEntity) {
            return true;
        }

    }

    public static final class WebUtils {

        public final static String getSuccessStatusStringForDelete() {
            return "{\"status\" : \"Success\"}";
        }

        /**
         * requestMethodMap maps servletPaths to HTTP methods. If a particular
         * servletPath supports
         * <p>
         * As servletPaths and corresponding methods are added they are formatted into a proper
         * Allows description. An example /hateaoas-api/arkivstruktur/ny-arkivskaper supports both
         * GET and POST. Therefore the mapping of /hateaoas-api/arkivstruktur/ny-arkivskaper to its
         * methods should be GET, HEAD, POST
         * <p>
         * Note GET automatically implies support for HEAD. Added.
         * <p>
         * Consider adding a list of allowed HTTP methods, but assuming spring will return allowed methods
         *
         * @param servletPath The incoming servletPath e.g. /hateoas-api/arkivstruktur/
         * @param method      An instance of a single HTTP method
         */
        public static void addRequestToMethodMap(@NotNull String servletPath, @NotNull Set<HttpMethod> method) {

            Set<HttpMethod> methods = requestMethodMap.get(servletPath.toLowerCase());
            if (null == methods) {
                methods = new TreeSet<>();
            }

            // GET automatically implies HEAD
            if (method.contains(GET)) {
                methods.add(GET);
                methods.add(HEAD);
            }
            if (!method.contains(OPTIONS)) {
                methods.add(OPTIONS);
            }
            methods.addAll(method);
            requestMethodMap.put(servletPath.toLowerCase(), methods);
        }


        /**
         * Provides the ability to throw an Exception if this call fails.
         * This is just a helper to make the code more readable in other places.
         *
         * @param servletPath
         */
        public static HttpMethod[] getMethodsForRequestOrThrow(@NotNull String servletPath) {
            HttpMethod[] methods = getMethodsForRequest(servletPath);
            if (null == methods) {
                throw new NikitaException("Error servletPath [" + servletPath + "] has no known HTTP methods");
            }
            return methods;
        }

        /**
         * Provides the ability to throw an Exception if this call fails.
         * This is just a helper to make the code more readable in other places.
         *
         * @param servletPath
         */
        public static HttpMethod[] getMethodsForRequest(@NotNull String servletPath) {
            // Adding a trailing slash as the map is setup with a trailing slash
            if (false == servletPath.endsWith("/")) {
                servletPath += SLASH;
            }
            //Next, we have to replace any occurrences of an actual UUID with the word systemID

            // The following pattern is taken from
            // https://stackoverflow.com/questions/136505/searching-for-uuids-in-text-with-regex#6640851
            Pattern pattern = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");
            Matcher matcher = pattern.matcher(servletPath.toLowerCase());
            String updatedServletPath = matcher.replaceFirst(LEFT_PARENTHESIS + SYSTEM_ID + RIGHT_PARENTHESIS);

            Set<HttpMethod> methods = requestMethodMap.get(updatedServletPath.toLowerCase());
            if (methods == null) {
                return null;
            }
            return methods.toArray(new HttpMethod[methods.size()]);
        }
    }

    public static final class Hateoas {

        public static final class Deserialize {

            public static Date parseDateFormat(String value)
                throws ParseException
            {
                SimpleDateFormat dateFormat = new SimpleDateFormat(NOARK_DATE_FORMAT_PATTERN);
                return dateFormat.parse(value);
            }

            public static Date parseDateTimeFormat(String value)
                throws ParseException
            {
                SimpleDateFormat dateTimeFormat = new SimpleDateFormat(NOARK_DATE_TIME_FORMAT_PATTERN);
                return dateTimeFormat.parse(value);
            }

            public static void deserialiseDocumentMedium(IDocumentMedium documentMediumEntity, ObjectNode objectNode) {
                // Deserialize documentMedium
                JsonNode currentNode = objectNode.get(DOCUMENT_MEDIUM);
                if (null != currentNode) {
                    documentMediumEntity.setDocumentMedium(currentNode.textValue());
                    objectNode.remove(DOCUMENT_MEDIUM);
                }
            }

            public static void deserialiseNoarkSystemIdEntity(INikitaEntity noarkSystemIdEntity,
                                                              ObjectNode objectNode) {
                // Deserialize systemId
                JsonNode currentNode = objectNode.get(SYSTEM_ID);
                if (null != currentNode) {
                    noarkSystemIdEntity.setSystemId(currentNode.textValue());
                    objectNode.remove(SYSTEM_ID);
                }
            }


            public static void deserialiseNoarkMetadataEntity(IMetadataEntity metadataEntity,
                                                              ObjectNode objectNode) {
                // Deserialize systemId
                deserialiseNoarkSystemIdEntity(metadataEntity, objectNode);

                JsonNode currentNode = objectNode.get(CODE);
                if (null != currentNode) {
                    metadataEntity.setCode(currentNode.textValue());
                    objectNode.remove(CODE);
                }
                currentNode = objectNode.get(DESCRIPTION);
                if (null != currentNode) {
                    metadataEntity.setCode(currentNode.textValue());
                    objectNode.remove(DESCRIPTION);
                }
            }

            public static void deserialiseKeyword(IKeyword keywordEntity, ObjectNode objectNode) {
                // Deserialize keyword
                JsonNode currentNode = objectNode.get(KEYWORD);

                if (null != currentNode) {
                    TreeSet<Keyword> keywords = new TreeSet<>();
                    if (currentNode.isArray()) {
                        currentNode.iterator();
                        for (JsonNode node : currentNode) {
                            String keywordText = node.textValue();
                            Keyword keyword = new Keyword();
                            keyword.setKeyword(keywordText);
                            keywords.add(keyword);
                        }
                        keywordEntity.setReferenceKeyword(keywords);
                    }
                    objectNode.remove(KEYWORD);
                }
            }

            private static void deserialiseCorrespondencePartType(ICorrespondencePartEntity correspondencePart,
                                                                  ObjectNode objectNode) {
                CorrespondencePartType correspondencePartType = new CorrespondencePartType();
                JsonNode currentNode = objectNode.get(CORRESPONDENCE_PART_TYPE);
                if (null != currentNode) {
                    ObjectNode correspondencePartTypeObjectNode = currentNode.deepCopy();
                    deserialiseNoarkMetadataEntity(correspondencePartType, correspondencePartTypeObjectNode);
                    objectNode.remove(CORRESPONDENCE_PART_TYPE);
                } else {
                    throw new NikitaMalformedInputDataException("The CorrespondencePartType object you tried to " +
                            "create is missing  " + CORRESPONDENCE_PART_TYPE);
                }
                correspondencePart.setCorrespondencePartType(correspondencePartType);
            }

            public static void deserialiseAuthor(IAuthor authorEntity,
                                                 ObjectNode objectNode) {

                // Deserialize author
                JsonNode currentNode = objectNode.get(AUTHOR);
                if (null != currentNode) {
                    TreeSet<Author> authors = new TreeSet<>();
                    if (currentNode.isArray()) {
                        currentNode.iterator();
                        for (JsonNode node : currentNode) {
                            String location = node.textValue();
                            Author author = new Author();
                            author.setSystemId(UUID.randomUUID().toString());
                            author.setAuthor(location);
                            authors.add(author);
                        }
                        authorEntity.setReferenceAuthor(authors);
                    }
                    objectNode.remove(AUTHOR);
                }
            }

            public static void deserialiseStorageLocation(IStorageLocation storageLocationEntity,
                                                          ObjectNode objectNode) {
                // Deserialize storageLocation
                JsonNode currentNode = objectNode.get(STORAGE_LOCATION);

                if (null != currentNode) {
                    TreeSet<StorageLocation> storageLocations = new TreeSet<>();
                    if (currentNode.isArray()) {
                        currentNode.iterator();
                        for (JsonNode node : currentNode) {
                            String location = node.textValue();
                            StorageLocation storageLocation = new StorageLocation();
                            storageLocation.setSystemId(UUID.randomUUID().toString());
                            storageLocation.setStorageLocation(location);
                            storageLocations.add(storageLocation);
                        }
                        storageLocationEntity.setReferenceStorageLocation(storageLocations);
                    }
                    objectNode.remove(STORAGE_LOCATION);
                }
            }

            public static void deserialiseNoarkCreateEntity(INoarkCreateEntity noarkCreateEntity,
                                                            ObjectNode objectNode) {
                // Deserialize createdDate
                JsonNode currentNode = objectNode.get(CREATED_DATE);
                if (null != currentNode) {
                    try {
                        Date parsedDate = parseDateFormat(currentNode.textValue());
                        noarkCreateEntity.setCreatedDate(parsedDate);
                    } catch (ParseException e) {
                        throw new NikitaMalformedInputDataException("The Noark object you tried to create " +
                                "has a malformed opprettetDato. Make sure format is " +
                                NOARK_DATE_TIME_FORMAT_PATTERN);
                    }
                    objectNode.remove(CREATED_DATE);
                }

                // Deserialize createdBy
                currentNode = objectNode.get(CREATED_BY);
                if (null != currentNode) {
                    noarkCreateEntity.setCreatedBy(currentNode.textValue());
                    objectNode.remove(CREATED_BY);
                }
            }

            public static void deserialiseNoarkFinaliseEntity(INoarkFinaliseEntity finaliseEntity,
                                                              ObjectNode objectNode) {
                // Deserialize finalisedDate
                JsonNode currentNode = objectNode.get(FINALISED_DATE);
                if (null != currentNode) {
                    try {
                        Date parsedDate = parseDateTimeFormat(currentNode.textValue());
                        finaliseEntity.setFinalisedDate(parsedDate);
                    } catch (ParseException e) {
                        throw new NikitaMalformedInputDataException("The Noark object you tried to create " +
                                "has a malformed avsluttetDato. Make sure format is " +
                                NOARK_DATE_TIME_FORMAT_PATTERN);
                    }
                    objectNode.remove(FINALISED_DATE);
                }

                // Deserialize finalisedBy
                currentNode = objectNode.get(FINALISED_BY);
                if (null != currentNode) {
                    finaliseEntity.setFinalisedBy(currentNode.textValue());
                    objectNode.remove(FINALISED_BY);
                }
            }

            public static void deserialiseNoarkTitleDescriptionEntity(INoarkTitleDescriptionEntity
                                                                              titleDescriptionEntity,
                                                                      ObjectNode objectNode) {
                // Deserialize title
                JsonNode currentNode = objectNode.get(TITLE);
                if (null != currentNode) {
                    titleDescriptionEntity.setTitle(currentNode.textValue());
                    objectNode.remove(TITLE);
                }

                // Deserialize description
                currentNode = objectNode.get(DESCRIPTION);
                if (null != currentNode) {
                    titleDescriptionEntity.setDescription(currentNode.textValue());
                    objectNode.remove(DESCRIPTION);
                }
            }

            public static void deserialiseNoarkEntity(INoarkGeneralEntity noarkEntity, ObjectNode objectNode) {
                deserialiseNoarkSystemIdEntity(noarkEntity, objectNode);
                deserialiseNoarkTitleDescriptionEntity(noarkEntity, objectNode);
                deserialiseNoarkCreateEntity(noarkEntity, objectNode);
                deserialiseNoarkFinaliseEntity(noarkEntity, objectNode);
            }

            public static String checkNodeObjectEmpty(JsonNode objectNode) {
                StringBuffer result = new StringBuffer("");
                if (objectNode.size() != 0) {
                    Iterator<Map.Entry<String, JsonNode>> nodes = objectNode.fields();
                    while (nodes.hasNext()) {
                        Map.Entry entry = nodes.next();
                        String keyField = (String) entry.getKey();
                        result.append(keyField);
                        if (nodes.hasNext()) {
                            result.append(", ");
                        }
                    }
                }
                return result.toString();
            }

            // TODO: FIX THIS!!!!
            public static Set<CrossReference> deserialiseCrossReferences(ObjectNode objectNode) {
                Set<CrossReference> crossReferences = new TreeSet<>();

                //deserialiseCrossReference(crossReference, objectNode);
                return crossReferences;
            }

            public static void deserialiseCrossReference(ICrossReferenceEntity crossReferenceEntity,
                                                         ObjectNode objectNode) {

                // Deserialize referenceToRecord
                JsonNode currentNode = objectNode.get(CROSS_REFERENCE_RECORD);
                if (null != currentNode) {
                    crossReferenceEntity.setReferenceToRecord(currentNode.textValue());
                    objectNode.remove(CROSS_REFERENCE_RECORD);
                }

                // Deserialize referenceToFile
                currentNode = objectNode.get(CROSS_REFERENCE_FILE);
                if (null != currentNode) {
                    crossReferenceEntity.setReferenceToFile(currentNode.textValue());
                    objectNode.remove(CROSS_REFERENCE_FILE);
                }

                // Deserialize referenceToClass
                currentNode = objectNode.get(CROSS_REFERENCE_CLASS);
                if (null != currentNode) {
                    crossReferenceEntity.setReferenceToClass(currentNode.textValue());
                    objectNode.remove(CROSS_REFERENCE_CLASS);
                }
                objectNode.remove(CROSS_REFERENCE);
            }

            public static void deserialiseComments(IComment commentObject, ObjectNode objectNode) {
                Set<Comment> comments = commentObject.getReferenceComment();
                for (Comment comment : comments) {
                    deserialiseCommentEntity(comment, objectNode);
                }
            }

            public static void deserialiseCommentEntity(ICommentEntity commentEntity, ObjectNode objectNode) {
                // Deserialize commentText
                JsonNode currentNode = objectNode.get(COMMENT_TEXT);
                if (null != currentNode) {
                    commentEntity.setCommentText(currentNode.textValue());
                    objectNode.remove(COMMENT_TEXT);
                }
                // Deserialize commentType
                currentNode = objectNode.get(COMMENT_TYPE);
                if (null != currentNode) {
                    commentEntity.setCommentType(currentNode.textValue());
                    objectNode.remove(COMMENT_TYPE);
                }

                // Deserialize commentDate
                currentNode = objectNode.get(COMMENT_DATE);
                if (null != currentNode) {
                    try {
                        Date parsedDate = parseDateFormat(currentNode.textValue());
                        commentEntity.setCommentDate(parsedDate);
                    } catch (ParseException e) {
                        throw new NikitaMalformedInputDataException("The Comment object you tried to create " +
                                "has a malformed merknadsdato. Make sure the format is " +
                                NOARK_DATE_FORMAT_PATTERN);
                    }
                    objectNode.remove(COMMENT_DATE);
                }
                // Deserialize commentRegisteredBy
                currentNode = objectNode.get(COMMENT_REGISTERED_BY);
                if (null != currentNode) {
                    commentEntity.setCommentRegisteredBy(currentNode.textValue());
                    objectNode.remove(COMMENT_REGISTERED_BY);
                }
                objectNode.remove(COMMENT);
            }


            public static Set<Series> deserialiseReferenceMultipleSeries(ObjectNode objectNode) {
                Set<Series> referenceSeries = null;
                JsonNode node = objectNode.get(REFERENCE_SERIES);
                if (node != null) {
                    referenceSeries = new TreeSet<>();
                    deserialiseReferenceSeries(referenceSeries, objectNode.deepCopy());
                }
                objectNode.remove(REFERENCE_SERIES);
                return referenceSeries;
            }

            public static void deserialiseReferenceSeries(Set<Series> referenceSeries, ObjectNode objectNode) {

            }

            public static Disposal deserialiseDisposal(ObjectNode objectNode) {
                Disposal disposal = null;
                JsonNode disposalNode = objectNode.get(DISPOSAL);
                if (disposalNode != null) {
                    disposal = new Disposal();
                    deserialiseDisposalEntity(disposal, objectNode);
                }
                objectNode.remove(DISPOSAL);
                return disposal;
            }

            public static void deserialiseDisposalEntity(IDisposalEntity disposalEntity, ObjectNode objectNode) {

                // Deserialize disposalDecision
                JsonNode currentNode = objectNode.get(DISPOSAL_DECISION);
                if (null != currentNode) {
                    disposalEntity.setDisposalDecision(currentNode.textValue());
                    objectNode.remove(DISPOSAL_DECISION);
                }
                // Deserialize disposalAuthority(
                currentNode = objectNode.get(DISPOSAL_AUTHORITY);
                if (null != currentNode) {
                    disposalEntity.setDisposalAuthority(currentNode.textValue());
                    objectNode.remove(DISPOSAL_AUTHORITY);
                }
                // Deserialize preservationTime
                currentNode = objectNode.get(DISPOSAL_PRESERVATION_TIME);
                if (null != currentNode) {
                    disposalEntity.setPreservationTime(Integer.valueOf(currentNode.intValue()));
                    objectNode.remove(DISPOSAL_PRESERVATION_TIME);
                }
                // Deserialize disposalDate
                currentNode = objectNode.get(DISPOSAL_DATE);
                if (null != currentNode) {
                    try {
                        Date parsedDate = parseDateFormat(currentNode.textValue());
                        disposalEntity.setDisposalDate(parsedDate);
                    } catch (ParseException e) {
                        throw new NikitaMalformedInputDataException("The Disposal object you tried to create " +
                                "has a malformed kassasjonsdato. Make sure the format is " +
                                NOARK_DATE_FORMAT_PATTERN);
                    }
                    objectNode.remove(DISPOSAL_DATE);
                }
            }

            public static DisposalUndertaken deserialiseDisposalUndertaken(ObjectNode objectNode) {
                DisposalUndertaken disposalUndertaken = null;
                JsonNode disposalUndertakenNode = objectNode.get(DISPOSAL_UNDERTAKEN);
                if (disposalUndertakenNode != null) {
                    disposalUndertaken = new DisposalUndertaken();
                    deserialiseDisposalUndertakenEntity(disposalUndertaken, objectNode);
                }
                objectNode.remove(DISPOSAL_UNDERTAKEN);
                return disposalUndertaken;
            }

            public static void deserialiseDisposalUndertakenEntity(IDisposalUndertakenEntity disposalUndertakenEntity,
                                                                   ObjectNode objectNode) {
                // Deserialize disposalBy
                JsonNode currentNode = objectNode.get(DISPOSAL_UNDERTAKEN_BY);
                if (null != currentNode) {
                    disposalUndertakenEntity.setDisposalBy(currentNode.textValue());
                    objectNode.remove(DISPOSAL_UNDERTAKEN_BY);
                }

                // Deserialize disposalDate
                currentNode = objectNode.get(DISPOSAL_UNDERTAKEN_DATE);
                if (null != currentNode) {
                    try {
                        Date parsedDate = parseDateFormat(currentNode.textValue());
                        disposalUndertakenEntity.setDisposalDate(parsedDate);
                    } catch (ParseException e) {
                        throw new NikitaMalformedInputDataException("The DisposalUndertaken object you tried to " +
                                "create has a malformed kassasjonsdato. Make sure the format is " +
                                NOARK_DATE_FORMAT_PATTERN);
                    }
                    objectNode.remove(DISPOSAL_UNDERTAKEN_DATE);
                }
            }

            public static Deletion deserialiseDeletion(ObjectNode objectNode) {
                Deletion deletion = null;
                JsonNode deletionNode = objectNode.get(DELETION);
                if (deletionNode != null) {
                    deletion = new Deletion();
                    deserialiseDeletionEntity(deletion, deletionNode.deepCopy());
                }
                return deletion;
            }

            public static void deserialiseDeletionEntity(IDeletionEntity deletionEntity, ObjectNode objectNode) {
                // Deserialize deletionBy
                JsonNode currentNode = objectNode.get(DELETION_BY);
                if (null != currentNode) {
                    deletionEntity.setDeletionBy(currentNode.textValue());
                    objectNode.remove(DELETION_BY);
                }

                // Deserialize deletionType
                currentNode = objectNode.get(DELETION_TYPE);
                if (null != currentNode) {
                    deletionEntity.setDeletionType(currentNode.textValue());
                    objectNode.remove(DELETION_TYPE);
                }

                // Deserialize deletionDate
                currentNode = objectNode.get(DELETION_DATE);
                if (null != currentNode) {
                    try {
                        Date parsedDate = parseDateFormat(currentNode.textValue());
                        deletionEntity.setDeletionDate(parsedDate);
                    } catch (ParseException e) {
                        throw new NikitaMalformedInputDataException("The deletion object you tried to create " +
                                "has a malformed slettetDato. Make sure the format is " +
                                NOARK_DATE_FORMAT_PATTERN);
                    }
                    objectNode.remove(DELETION_DATE);
                }
                objectNode.remove(DELETION);
            }

            public static void deserialiseCaseParties(ICaseParty casePartyObject, ObjectNode objectNode) {
                Set<CaseParty> caseParties = casePartyObject.getReferenceCaseParty();
                if (caseParties != null && caseParties.size() > 0) {
                    for (CaseParty caseParty : caseParties) {
                        deserialiseCaseParty(caseParty, objectNode);
                        objectNode.remove(CASE_PARTY);
                    }
                }
            }

            public static void deserialiseCaseParty(ICasePartyEntity casePartyEntity, ObjectNode objectNode) {

                // Deserialize casePartyId
                JsonNode currentNode = objectNode.get(CASE_PARTY_ID);
                if (null != currentNode) {
                    casePartyEntity.setCasePartyId(currentNode.textValue());
                    objectNode.remove(CASE_PARTY_ID);
                }
                // Deserialize casePartyName
                currentNode = objectNode.get(CASE_PARTY_NAME);
                if (null != currentNode) {
                    casePartyEntity.setCasePartyName(currentNode.textValue());
                    objectNode.remove(CASE_PARTY_NAME);
                }
                // Deserialize casePartyRole
                currentNode = objectNode.get(CASE_PARTY_ROLE);
                if (null != currentNode) {
                    casePartyEntity.setCasePartyRole(currentNode.textValue());
                    objectNode.remove(CASE_PARTY_ROLE);
                }
                // Deserialize postalAddress
                currentNode = objectNode.get(N5ResourceMappings.POSTAL_ADDRESS);
                if (null != currentNode) {
                    casePartyEntity.setPostalAddress(currentNode.textValue());
                    objectNode.remove(N5ResourceMappings.POSTAL_ADDRESS);
                }
                // Deserialize postCode
                currentNode = objectNode.get(N5ResourceMappings.POSTAL_NUMBER);
                if (null != currentNode) {
                    casePartyEntity.setPostCode(currentNode.textValue());
                    objectNode.remove(N5ResourceMappings.POSTAL_NUMBER);
                }
                // Deserialize postalTown
                currentNode = objectNode.get(POSTAL_TOWN);
                if (null != currentNode) {
                    casePartyEntity.setPostalTown(currentNode.textValue());
                    objectNode.remove(POSTAL_TOWN);
                }
                // Deserialize foreignAddress
                currentNode = objectNode.get(FOREIGN_ADDRESS);
                if (null != currentNode) {
                    casePartyEntity.setForeignAddress(currentNode.textValue());
                    objectNode.remove(FOREIGN_ADDRESS);
                }
                // Deserialize emailAddress
                currentNode = objectNode.get(N5ResourceMappings.EMAIL_ADDRESS);
                if (null != currentNode) {
                    casePartyEntity.setEmailAddress(currentNode.textValue());
                    objectNode.remove(N5ResourceMappings.EMAIL_ADDRESS);
                }
                // Deserialize telephoneNumber
                currentNode = objectNode.get(N5ResourceMappings.TELEPHONE_NUMBER);
                if (null != currentNode) {
                    casePartyEntity.setTelephoneNumber(currentNode.textValue());
                    objectNode.remove(N5ResourceMappings.TELEPHONE_NUMBER);
                }
                // Deserialize contactPerson
                currentNode = objectNode.get(CONTACT_PERSON);
                if (null != currentNode) {
                    casePartyEntity.setContactPerson(currentNode.textValue());
                    objectNode.remove(CONTACT_PERSON);
                }
            }

            public static Set<Precedence> deserialisePrecedences(ObjectNode objectNode) {
//                objectNode.remove(PRECEDENCE);
                // TODO : Looks like I'm missing!!!
                return null;
            }

            public static void deserialisePrecedence(IPrecedenceEntity precedenceEntity, ObjectNode objectNode) {

                deserialiseNoarkCreateEntity(precedenceEntity, objectNode);
                deserialiseNoarkTitleDescriptionEntity(precedenceEntity, objectNode);
                deserialiseNoarkFinaliseEntity(precedenceEntity, objectNode);

                // Deserialize precedenceDate
                JsonNode currentNode = objectNode.get(PRECEDENCE_DATE);
                if (null != currentNode) {
                    try {
                        Date parsedDate = parseDateFormat(currentNode.textValue());
                        precedenceEntity.setPrecedenceDate(parsedDate);
                    } catch (ParseException e) {
                        throw new NikitaMalformedInputDataException("The deletion object you tried to create " +
                                "has a malformed presedensDato. Make sure the format is " +
                                NOARK_DATE_FORMAT_PATTERN);
                    }
                    objectNode.remove(PRECEDENCE_DATE);
                }
                // Deserialize precedenceAuthority
                currentNode = objectNode.get(PRECEDENCE_AUTHORITY);
                if (null != currentNode) {
                    precedenceEntity.setPrecedenceAuthority(currentNode.textValue());
                    objectNode.remove(PRECEDENCE_AUTHORITY);
                }
                // Deserialize sourceOfLaw
                currentNode = objectNode.get(PRECEDENCE_SOURCE_OF_LAW);
                if (null != currentNode) {
                    precedenceEntity.setSourceOfLaw(currentNode.textValue());
                    objectNode.remove(PRECEDENCE_SOURCE_OF_LAW);
                }
                // Deserialize precedenceApprovedBy
                currentNode = objectNode.get(PRECEDENCE_APPROVED_BY);
                if (null != currentNode) {
                    precedenceEntity.setPrecedenceApprovedBy(currentNode.textValue());
                    objectNode.remove(PRECEDENCE_APPROVED_BY);
                }
                // Deserialize precedenceStatus
                currentNode = objectNode.get(PRECEDENCE_STATUS);
                if (null != currentNode) {
                    precedenceEntity.setPrecedenceStatus(currentNode.textValue());
                    objectNode.remove(PRECEDENCE_STATUS);
                }
                // Deserialize precedenceApprovedDate
                currentNode = objectNode.get(PRECEDENCE_APPROVED_DATE);
                if (null != currentNode) {
                    try {
                        Date parsedDate = parseDateFormat(currentNode.textValue());
                        precedenceEntity.setPrecedenceApprovedDate(parsedDate);
                    } catch (ParseException e) {
                        throw new NikitaMalformedInputDataException("The deletion object you tried to create " +
                                "has a malformed presedensGodkjentDato. Make sure the format " +
                                "is " + NOARK_DATE_FORMAT_PATTERN);
                    }
                    objectNode.remove(PRECEDENCE_APPROVED_DATE);
                }
            }

            public static Set<CaseParty> deserialiseCaseParties(ObjectNode objectNode) {
                TreeSet<CaseParty> caseParties = new TreeSet<>();
                JsonNode jsonCorrespondenceParts = objectNode.get(CORRESPONDENCE_PART);

                // TODO: I seem tobe missing my body of code ...
/*                for (CorrespondencePart correspondencePart: caseParties) {
                    deserialiseCorrespondencePart(correspondencePart, objectNode);
                    objectNode.remove(CORRESPONDENCE_PART);
                }
*/
                return caseParties;
            }

            public static void deserialiseAdministrativeUnitEntity(IAdministrativeUnitEntity administrativeUnit,
                                                                   ObjectNode objectNode) {
                if (null != administrativeUnit) {

                    deserialiseNoarkSystemIdEntity(administrativeUnit, objectNode);
                    deserialiseNoarkCreateEntity(administrativeUnit, objectNode);
                    deserialiseNoarkFinaliseEntity(administrativeUnit, objectNode);

                    // Deserialize kortnavn
                    JsonNode currentNode = objectNode.get(SHORT_NAME);
                    if (null != currentNode) {
                        administrativeUnit.setShortName(currentNode.textValue());
                        objectNode.remove(SHORT_NAME);
                    }

                    // Deserialize administrativEnhetNavn
                    currentNode = objectNode.get(ADMINISTRATIVE_UNIT_NAME);
                    if (null != currentNode) {
                        administrativeUnit.setAdministrativeUnitName(currentNode.textValue());
                        objectNode.remove(ADMINISTRATIVE_UNIT_NAME);
                    }

                    // It is unclear if status values are to be set by special endpoint calls
                    // e.g. adminunit->avsluttAdministrativeEnhet
                    // Deserialize administrativEnhetsstatus
                    currentNode = objectNode.get(ADMINISTRATIVE_UNIT_STATUS);
                    if (null != currentNode) {
                        administrativeUnit.setAdministrativeUnitStatus(currentNode.textValue());
                        objectNode.remove(ADMINISTRATIVE_UNIT_STATUS);
                    }

                    // Deserialize referanseOverordnetEnhet
                    currentNode = objectNode.get(ADMINISTRATIVE_UNIT_PARENT_REFERENCE);
                    if (null != currentNode) {
                        AdministrativeUnit parent = new AdministrativeUnit();
                        parent.setSystemId(currentNode.textValue());
                        parent.getReferenceChildAdministrativeUnit().add((AdministrativeUnit) administrativeUnit);
                        administrativeUnit.setParentAdministrativeUnit(parent);
                        objectNode.remove(ADMINISTRATIVE_UNIT_PARENT_REFERENCE);
                    }
                }
            }

            public static void deserialiseUserEntity(IUserEntity user, ObjectNode objectNode) {

            }

            public static void deserialiseContactInformationEntity(IContactInformationEntity contactInformation,
                                                                   ObjectNode objectNode) {
                if (contactInformation != null) {
                    // Deserialize telefonnummer
                    JsonNode currentNode = objectNode.get(TELEPHONE_NUMBER);
                    if (null != currentNode) {
                        contactInformation.setTelephoneNumber(currentNode.textValue());
                        objectNode.remove(TELEPHONE_NUMBER);
                    }
                    // Deserialize epostadresse
                    currentNode = objectNode.get(EMAIL_ADDRESS);
                    if (null != currentNode) {
                        contactInformation.setEmailAddress(currentNode.textValue());
                        objectNode.remove(EMAIL_ADDRESS);
                    }
                    // Deserialize mobiltelefon
                    currentNode = objectNode.get(MOBILE_TELEPHONE_NUMBER);
                    if (null != currentNode) {
                        contactInformation.setMobileTelephoneNumber(currentNode.textValue());
                        objectNode.remove(MOBILE_TELEPHONE_NUMBER);
                    }
                }
            }

            public static void deserialiseSimpleAddressEntity(ISimpleAddressEntity simpleAddress, ObjectNode objectNode) {
                if (simpleAddress != null) {
                    // Deserialize adresselinje1
                    JsonNode currentNode = objectNode.get(ADDRESS_LINE_1);
                    if (null != currentNode) {
                        simpleAddress.setAddressLine1(currentNode.textValue());
                        objectNode.remove(ADDRESS_LINE_1);
                    }
                    // Deserialize adresselinje2
                    currentNode = objectNode.get(ADDRESS_LINE_2);
                    if (null != currentNode) {
                        simpleAddress.setAddressLine2(currentNode.textValue());
                        objectNode.remove(ADDRESS_LINE_2);
                    }
                    // Deserialize adresselinje3
                    currentNode = objectNode.get(ADDRESS_LINE_3);
                    if (null != currentNode) {
                        simpleAddress.setAddressLine3(currentNode.textValue());
                        objectNode.remove(ADDRESS_LINE_3);
                    }
                    // Deserialize postnummer
                    currentNode = objectNode.get(POSTAL_NUMBER);
                    if (null != currentNode) {
                        simpleAddress.setPostalNumber(new PostalNumber(currentNode.textValue()));
                        objectNode.remove(POSTAL_NUMBER);
                    }
                    // Deserialize poststed
                    currentNode = objectNode.get(POSTAL_TOWN);
                    if (null != currentNode) {
                        simpleAddress.setPostalTown(currentNode.textValue());
                        objectNode.remove(POSTAL_TOWN);
                    }
                    // Deserialize landkode
                    currentNode = objectNode.get(COUNTRY_CODE);
                    if (null != currentNode) {
                        simpleAddress.setCountryCode(currentNode.textValue());
                        objectNode.remove(COUNTRY_CODE);
                    }
                }
            }

            public static void deserialiseCorrespondencePartPersonEntity(ICorrespondencePartPersonEntity
                                                                                 correspondencePartPersonEntity,
                                                                         ObjectNode objectNode) {

                deserialiseCorrespondencePartType(correspondencePartPersonEntity, objectNode);

                // Deserialize foedselsnummer
                JsonNode currentNode = objectNode.get(SOCIAL_SECURITY_NUMBER);
                if (null != currentNode) {
                    correspondencePartPersonEntity.setSocialSecurityNumber(currentNode.textValue());
                    objectNode.remove(SOCIAL_SECURITY_NUMBER);
                }

                // Deserialize dnummer
                currentNode = objectNode.get(D_NUMBER);
                if (null != currentNode) {
                    correspondencePartPersonEntity.setdNumber(currentNode.textValue());
                    objectNode.remove(D_NUMBER);
                }

                // Deserialize navn
                currentNode = objectNode.get(NAME);
                if (null != currentNode) {
                    correspondencePartPersonEntity.setName(currentNode.textValue());
                    objectNode.remove(NAME);
                }

                // Deserialize postalAddress
                currentNode = objectNode.get(N5ResourceMappings.POSTAL_ADDRESS);
                if (null != currentNode) {
                    PostalAddress postalAddressEntity = new PostalAddress();
                    postalAddressEntity.setAddressType(POSTAL_ADDRESS);
                    deserialiseSimpleAddressEntity(postalAddressEntity, currentNode.deepCopy());
                    correspondencePartPersonEntity.setPostalAddress(postalAddressEntity);
                    objectNode.remove(N5ResourceMappings.POSTAL_ADDRESS);
                }

                // Deserialize residingAddress
                currentNode = objectNode.get(RESIDING_ADDRESS);
                if (null != currentNode) {
                    ResidingAddress residingAddressEntity = new ResidingAddress();
                    residingAddressEntity.setAddressType(RESIDING_ADDRESS);
                    deserialiseSimpleAddressEntity(residingAddressEntity, currentNode.deepCopy());
                    correspondencePartPersonEntity.setResidingAddress(residingAddressEntity);
                    objectNode.remove(RESIDING_ADDRESS);
                }

                // Deserialize kontaktinformasjon
                currentNode = objectNode.get(CONTACT_INFORMATION);
                if (null != currentNode) {
                    ContactInformation contactInformation = new ContactInformation();
                    deserialiseContactInformationEntity(contactInformation, currentNode.deepCopy());
                    correspondencePartPersonEntity.setContactInformation(contactInformation);
                    objectNode.remove(CONTACT_INFORMATION);
                }
            }

            public static void deserialiseCorrespondencePartInternalEntity(ICorrespondencePartInternalEntity
                                                                                   correspondencePartInternal,
                                                                           ObjectNode objectNode) {
                deserialiseCorrespondencePartType(correspondencePartInternal, objectNode);

                // Deserialize administrativEnhet
                JsonNode currentNode = objectNode.get(ADMINISTRATIVE_UNIT);
                if (null != currentNode) {
                    correspondencePartInternal.setAdministrativeUnit(currentNode.textValue());
                    objectNode.remove(ADMINISTRATIVE_UNIT);
                }
                // Deserialize saksbehandler
                currentNode = objectNode.get(CASE_HANDLER);
                if (null != currentNode) {
                    correspondencePartInternal.setCaseHandler(currentNode.textValue());
                    objectNode.remove(CASE_HANDLER);
                }

                // Deserialize referanseAdministratitivEnhet
                currentNode = objectNode.get(REFERENCE_ADMINISTRATIVE_UNIT);
                if (null != currentNode) {
                    AdministrativeUnit administrativeUnit = new AdministrativeUnit();
                    deserialiseAdministrativeUnitEntity(administrativeUnit, currentNode.deepCopy());
                    correspondencePartInternal.setReferenceAdministrativeUnit(administrativeUnit);
                    objectNode.remove(REFERENCE_ADMINISTRATIVE_UNIT);
                }

                // Deserialize referanseSaksbehandler
                currentNode = objectNode.get(REFERENCE_CASE_HANDLER);
                if (null != currentNode) {
                    User user = new User();
                    deserialiseUserEntity(user, currentNode.deepCopy());
                    correspondencePartInternal.setReferenceCaseHandler(user);
                    objectNode.remove(REFERENCE_CASE_HANDLER);
                }
            }

            public static void deserialiseCorrespondencePartUnitEntity(ICorrespondencePartUnitEntity
                                                                               correspondencePartUnit,
                                                                       ObjectNode objectNode) {
                deserialiseCorrespondencePartType(correspondencePartUnit, objectNode);

                // Deserialize kontaktperson
                JsonNode currentNode = objectNode.get(CONTACT_PERSON);
                if (null != currentNode) {
                    correspondencePartUnit.setContactPerson(currentNode.textValue());
                    objectNode.remove(CONTACT_PERSON);
                }

                // Deserialize navn
                currentNode = objectNode.get(NAME);
                if (null != currentNode) {
                    correspondencePartUnit.setName(currentNode.textValue());
                    objectNode.remove(NAME);
                }

                // Deserialize organisasjonsnummer
                currentNode = objectNode.get(ORGANISATION_NUMBER);
                if (null != currentNode) {
                    correspondencePartUnit.setOrganisationNumber(currentNode.textValue());
                    objectNode.remove(ORGANISATION_NUMBER);
                }

                // Deserialize kontaktperson
                currentNode = objectNode.get(CONTACT_PERSON);
                if (null != currentNode) {
                    correspondencePartUnit.setContactPerson(currentNode.textValue());
                    objectNode.remove(CONTACT_PERSON);
                }

                // Deserialize postadresse
                currentNode = objectNode.get(N5ResourceMappings.POSTAL_ADDRESS);
                if (null != currentNode) {
                    PostalAddress postalAddressEntity = new PostalAddress();
                    postalAddressEntity.setAddressType(POSTAL_ADDRESS);
                    deserialiseSimpleAddressEntity(postalAddressEntity, currentNode.deepCopy());
                    correspondencePartUnit.setPostalAddress(postalAddressEntity);
                    objectNode.remove(N5ResourceMappings.POSTAL_ADDRESS);
                }

                // Deserialize forretningsadresse
                currentNode = objectNode.get(BUSINESS_ADDRESS);
                if (null != currentNode) {
                    BusinessAddress businessAddressEntity = new BusinessAddress();
                    businessAddressEntity.setAddressType(BUSINESS_ADDRESS);
                    deserialiseSimpleAddressEntity(businessAddressEntity, currentNode.deepCopy());
                    correspondencePartUnit.setBusinessAddress(businessAddressEntity);
                    objectNode.remove(BUSINESS_ADDRESS);
                }

                // Deserialize kontaktinformasjon
                currentNode = objectNode.get(CONTACT_INFORMATION);
                if (null != currentNode) {
                    ContactInformation contactInformation = new ContactInformation();
                    deserialiseContactInformationEntity(contactInformation, currentNode.deepCopy());
                    correspondencePartUnit.setContactInformation(contactInformation);
                    objectNode.remove(CONTACT_INFORMATION);
                }

            }

            // TODO: Double check how the JSON of this looks if multiple fondsCreators are embedded within a fonds
            // object There might be some 'root' node in the JSON to remove
            // This might be implemented as an array???
            public static Set<FondsCreator> deserialiseFondsCreators(ObjectNode objectNode) {
                return null;
            }

            public static void deserialiseFondsCreator(IFondsCreatorEntity fondsCreatorEntity, ObjectNode objectNode) {
                // Deserialize systemID
                JsonNode currentNode = objectNode.get(SYSTEM_ID);
                if (null != currentNode) {
                    fondsCreatorEntity.setSystemId(currentNode.textValue());
                    objectNode.remove(SYSTEM_ID);
                }
                // Deserialize fondsCreatorId
                currentNode = objectNode.get(FONDS_CREATOR_ID);
                if (null != currentNode) {
                    fondsCreatorEntity.setFondsCreatorId(currentNode.textValue());
                    objectNode.remove(FONDS_CREATOR_ID);
                }
                // Deserialize fondsCreatorName
                currentNode = objectNode.get(FONDS_CREATOR_NAME);
                if (null != currentNode) {
                    fondsCreatorEntity.setFondsCreatorName(currentNode.textValue());
                    objectNode.remove(FONDS_CREATOR_NAME);
                }
                // Deserialize description
                currentNode = objectNode.get(DESCRIPTION);
                if (null != currentNode) {
                    fondsCreatorEntity.setDescription(currentNode.textValue());
                    objectNode.remove(DESCRIPTION);
                }
            }

            public static Screening deserialiseScreening(ObjectNode objectNode) {
                Screening screening = null;
                JsonNode screeningNode = objectNode.get(SCREENING);
                if (screeningNode != null) {
                    screening = new Screening();
                    deserialiseScreeningEntity(screening, screeningNode.deepCopy());
                }
                objectNode.remove(SCREENING);
                return screening;
            }

            public static void deserialiseScreeningEntity(IScreeningEntity screeningEntity, ObjectNode objectNode) {
                // Deserialize accessRestriction
                JsonNode currentNode = objectNode.get(SCREENING_ACCESS_RESTRICTION);
                if (null != currentNode) {
                    screeningEntity.setAccessRestriction(currentNode.textValue());
                    objectNode.remove(SCREENING_ACCESS_RESTRICTION);
                }
                // Deserialize screeningAuthority
                currentNode = objectNode.get(SCREENING_AUTHORITY);
                if (null != currentNode) {
                    screeningEntity.setScreeningAuthority(currentNode.textValue());
                    objectNode.remove(SCREENING_AUTHORITY);
                }
                // Deserialize screeningMetadata
                currentNode = objectNode.get(SCREENING_METADATA);
                if (null != currentNode) {
                    screeningEntity.setScreeningMetadata(currentNode.textValue());
                    objectNode.remove(SCREENING_METADATA);
                }
                // Deserialize screeningDocument
                currentNode = objectNode.get(SCREENING_DOCUMENT);
                if (null != currentNode) {
                    screeningEntity.setScreeningDocument(currentNode.textValue());
                    objectNode.remove(SCREENING_DOCUMENT);
                }
                // Deserialize screeningExpiresDate
                currentNode = objectNode.get(SCREENING_EXPIRES_DATE);
                if (null != currentNode) {
                    try {
                        Date parsedDate = parseDateFormat(currentNode.textValue());
                        screeningEntity.setScreeningExpiresDate(parsedDate);
                    } catch (ParseException e) {
                        throw new NikitaMalformedInputDataException("The deletion object you tried to create " +
                                "has a malformed skjermingOpphoererDato. Make sure the" +
                                " format is " +
                                NOARK_DATE_FORMAT_PATTERN);
                    }
                    objectNode.remove(SCREENING_EXPIRES_DATE);
                }
                // Deserialize screeningDuration
                currentNode = objectNode.get(SCREENING_DURATION);
                if (null != currentNode) {
                    screeningEntity.setScreeningDuration(currentNode.textValue());
                    objectNode.remove(SCREENING_DURATION);
                }
                objectNode.remove(SCREENING);
            }

            public static Classified deserialiseClassified(ObjectNode objectNode) {
                Classified classified = null;
                JsonNode classifiedNode = objectNode.get(CLASSIFIED);
                if (classifiedNode != null) {
                    classified = new Classified();
                    deserialiseClassifiedEntity(classified, classifiedNode.deepCopy());
                }
                //TODO: Only remove if the hashset is actually empty, otherwise let it go back up with the extra values
                objectNode.remove(CLASSIFIED);
                return classified;
            }

            public static void deserialiseClassifiedEntity(IClassifiedEntity classifiedEntity, ObjectNode objectNode) {

                // Deserialize classification
                JsonNode currentNode = objectNode.get(CLASSIFICATION);
                if (null != currentNode) {
                    classifiedEntity.setClassification(currentNode.textValue());
                    objectNode.remove(CLASSIFICATION);
                }
                // Deserialize classificationDate
                currentNode = objectNode.get(CLASSIFICATION_DATE);
                if (null != currentNode) {
                    try {
                        Date parsedDate = parseDateFormat(currentNode.textValue());
                        classifiedEntity.setClassificationDate(parsedDate);
                    } catch (ParseException e) {
                        throw new NikitaMalformedInputDataException("The screening object you tried to create " +
                                "has a malformed graderingsdato. Make sure the format is " +
                                NOARK_DATE_FORMAT_PATTERN);
                    }
                    objectNode.remove(CLASSIFICATION_DATE);
                }

                // Deserialize classificationBy
                currentNode = objectNode.get(CLASSIFICATION_BY);
                if (null != currentNode) {
                    classifiedEntity.setClassificationBy(currentNode.textValue());
                    objectNode.remove(CLASSIFICATION_BY);
                }
                // Deserialize classificationDowngradedDate
                currentNode = objectNode.get(CLASSIFICATION_DOWNGRADED_DATE);
                if (null != currentNode) {
                    try {
                        Date parsedDate = parseDateFormat(currentNode.textValue());
                        classifiedEntity.setClassificationDowngradedDate(parsedDate);
                    } catch (ParseException e) {
                        throw new NikitaMalformedInputDataException("The screening object you tried to create " +
                                "has a malformed nedgraderingsdato. Make sure the " +
                                "format is " +
                                NOARK_DATE_FORMAT_PATTERN);
                    }
                    objectNode.remove(CLASSIFICATION_DOWNGRADED_DATE);
                }
                // Deserialize
                currentNode = objectNode.get(CLASSIFICATION_DOWNGRADED_BY);
                if (null != currentNode) {
                    classifiedEntity.setClassificationDowngradedBy(currentNode.textValue());
                    objectNode.remove(CLASSIFICATION_DOWNGRADED_BY);
                }
                objectNode.remove(CLASSIFIED);
            }
        }

        public static final class Serialize {
            public static String formatDate(Date value) {
                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(NOARK_DATE_FORMAT_PATTERN);
                return DATE_FORMAT.format(value);
            }

            public static String formatDateTime(Date value) {
                SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(NOARK_DATE_TIME_FORMAT_PATTERN);
                return DATE_TIME_FORMAT.format(value);
            }

            public static void printTitleAndDescription(JsonGenerator jgen,
                                                        INoarkTitleDescriptionEntity titleDescriptionEntity)
                    throws IOException {

                if (titleDescriptionEntity != null) {
                    if (titleDescriptionEntity.getTitle() != null) {
                        jgen.writeStringField(TITLE, titleDescriptionEntity.getTitle());
                    }
                    if (titleDescriptionEntity.getDescription() != null) {
                        jgen.writeStringField(DESCRIPTION, titleDescriptionEntity.getDescription());
                    }
                }
            }

            public static void printCreateEntity(JsonGenerator jgen,
                                                 INoarkCreateEntity createEntity)
                    throws IOException {
                if (createEntity != null) {
                    if (createEntity.getCreatedDate() != null) {
                        jgen.writeStringField(CREATED_DATE,
                                              formatDateTime(createEntity.getCreatedDate()));
                    }
                    if (createEntity.getCreatedBy() != null) {
                        jgen.writeStringField(CREATED_BY, createEntity.getCreatedBy());
                    }
                }
            }

            public static void printDocumentMedium(JsonGenerator jgen,
                                                   IDocumentMedium documentMedium)
                    throws IOException {
                if (documentMedium.getDocumentMedium() != null) {
                    jgen.writeStringField(DOCUMENT_MEDIUM, documentMedium.getDocumentMedium());
                }

            }


            public static void printFinaliseEntity(JsonGenerator jgen,
                                                   INoarkFinaliseEntity finaliseEntity)
                    throws IOException {
                if (finaliseEntity.getFinalisedBy() != null) {
                    jgen.writeStringField(FINALISED_BY, finaliseEntity.getFinalisedBy());
                }
                if (finaliseEntity.getFinalisedDate() != null) {
                    jgen.writeStringField(FINALISED_DATE,
                                          formatDateTime(finaliseEntity.getFinalisedDate()));
                }
            }

            public static void printSystemIdEntity(JsonGenerator jgen,
                                                   INikitaEntity systemIdEntity)
                    throws IOException {
                if (systemIdEntity != null && systemIdEntity.getSystemId() != null) {
                    jgen.writeStringField(SYSTEM_ID, systemIdEntity.getSystemId());
                }
            }

            public static void printCaseParty(JsonGenerator jgen, ICaseParty casePartyObject)
                    throws IOException {
                if (casePartyObject != null) {
                    Set<CaseParty> caseParties = casePartyObject.getReferenceCaseParty();
                    if (caseParties != null && caseParties.size() > 0) {
                        jgen.writeArrayFieldStart(CASE_PARTY);
                        for (CaseParty caseParty : caseParties) {
                            if (caseParty != null) {
                                jgen.writeObjectFieldStart(CASE_PARTY);

                                if (caseParty.getCasePartyId() != null) {
                                    jgen.writeStringField(CASE_PARTY_ID, caseParty.getCasePartyId());
                                }
                                if (caseParty.getCasePartyName() != null) {
                                    jgen.writeStringField(CASE_PARTY_NAME, caseParty.getCasePartyName());
                                }
                                if (caseParty.getCasePartyRole() != null) {
                                    jgen.writeStringField(CASE_PARTY_ROLE, caseParty.getCasePartyRole());
                                }
                                if (caseParty.getPostalAddress() != null) {
                                    jgen.writeStringField(N5ResourceMappings.POSTAL_ADDRESS, caseParty.getPostalAddress());
                                }
                                if (caseParty.getPostCode() != null) {
                                    jgen.writeStringField(N5ResourceMappings.POSTAL_NUMBER, caseParty.getPostCode());
                                }
                                if (caseParty.getPostalTown() != null) {
                                    jgen.writeStringField(POSTAL_TOWN, caseParty.getPostalTown());
                                }
                                if (caseParty.getForeignAddress() != null) {
                                    jgen.writeStringField(FOREIGN_ADDRESS, caseParty.getForeignAddress());
                                }
                                if (caseParty.getEmailAddress() != null) {
                                    jgen.writeStringField(N5ResourceMappings.EMAIL_ADDRESS, caseParty.getEmailAddress());
                                }
                                if (caseParty.getTelephoneNumber() != null) {
                                    jgen.writeStringField(N5ResourceMappings.TELEPHONE_NUMBER, caseParty.getTelephoneNumber());
                                }
                                if (caseParty.getContactPerson() != null) {
                                    jgen.writeStringField(CONTACT_PERSON, caseParty.getContactPerson());
                                }
                                jgen.writeEndObject();
                            }
                        }
                        jgen.writeEndArray();
                    }
                }
            }

            // Note: This method assumes that the startObject has already been written
            public static void printHateoasLinks(JsonGenerator jgen, Set<Link> links) throws IOException {

                if (links != null && links.size() > 0) {
                    jgen.writeArrayFieldStart(LINKS);
                    for (Link link : links) {
                        jgen.writeStartObject(link.getLinkName());
                        jgen.writeStringField(HREF, link.getHref());
                        jgen.writeStringField(REL, link.getRel());
                        jgen.writeBooleanField(TEMPLATED, link.getTemplated());
                        jgen.writeEndObject();
                    }
                    jgen.writeEndArray();
                } else {
                    jgen.writeArrayFieldStart(LINKS);
                    jgen.writeEndArray();
                }
            }

            public static void printMetadataEntity(JsonGenerator jgen, IMetadataEntity metadataEntity)
                    throws IOException {
                // TODO:  Looks like the interface standard requires basetype here. Make sure it's implemented
                // e.g."mappetype" {}
                jgen.writeFieldName(metadataEntity.getBaseTypeName());
                jgen.writeStartObject();
                jgen.writeStringField(SYSTEM_ID, metadataEntity.getSystemId());
                jgen.writeStringField(CODE, metadataEntity.getCode());
                jgen.writeStringField(DESCRIPTION, metadataEntity.getDescription());
                jgen.writeEndObject();
            }


            private static void printCorrespondencePart(JsonGenerator jgen, ICorrespondencePartEntity correspondencePart)
                    throws IOException {

                if (correspondencePart != null) {
                    printSystemIdEntity(jgen, correspondencePart);
                    if (correspondencePart.getCorrespondencePartType() != null) {
                        printMetadataEntity(jgen, correspondencePart.getCorrespondencePartType());
                    }
                }
            }


            public static void printContactInformation(JsonGenerator jgen, IContactInformationEntity contactInformation)
                    throws IOException {

                if (null != contactInformation) {
                    jgen.writeFieldName(CONTACT_INFORMATION);
                    jgen.writeStartObject();
                    if (null != contactInformation.getEmailAddress()) {
                        jgen.writeStringField(EMAIL_ADDRESS, contactInformation.getEmailAddress());
                    }
                    if (null != contactInformation.getMobileTelephoneNumber()) {
                        jgen.writeStringField(MOBILE_TELEPHONE_NUMBER, contactInformation.getMobileTelephoneNumber());
                    }
                    if (null != contactInformation.getTelephoneNumber()) {
                        jgen.writeStringField(TELEPHONE_NUMBER, contactInformation.getTelephoneNumber());
                    }
                    jgen.writeEndObject();
                }
            }

            public static void printAddress(JsonGenerator jgen, ISimpleAddressEntity address) throws IOException {

                if (null != address) {
                    jgen.writeFieldName(address.getAddressType());
                    jgen.writeStartObject();
                    printSystemIdEntity(jgen, address);

                    if (null != address.getAddressLine1()) {
                        jgen.writeStringField(ADDRESS_LINE_1, address.getAddressLine1());
                    }
                    if (null != address.getAddressLine2()) {
                        jgen.writeStringField(ADDRESS_LINE_2, address.getAddressLine2());
                    }
                    if (null != address.getAddressLine3()) {
                        jgen.writeStringField(ADDRESS_LINE_3, address.getAddressLine3());
                    }
                    if (null != address.getPostalNumber()) {
                        PostalNumber postalNumber = address.getPostalNumber();
                        if (null != postalNumber && null != postalNumber.getPostalNumber()) {
                            jgen.writeStringField(POSTAL_NUMBER, postalNumber.getPostalNumber());
                        }
                    }
                    if (null != address.getPostalTown()) {
                        jgen.writeStringField(POSTAL_TOWN, address.getPostalTown());
                    }
                    if (null != address.getCountryCode()) {
                        jgen.writeStringField(COUNTRY_CODE, address.getCountryCode());
                    }
                    jgen.writeEndObject();
                }
            }

            public static void printCorrespondencePartPerson(JsonGenerator jgen,
                                                             ICorrespondencePartPersonEntity correspondencePartPerson)
                    throws IOException {
                if (null != correspondencePartPerson) {
                    printCorrespondencePart(jgen, correspondencePartPerson);

                    if (null != correspondencePartPerson.getSocialSecurityNumber()) {
                        jgen.writeStringField(SOCIAL_SECURITY_NUMBER, correspondencePartPerson.getSocialSecurityNumber());
                    }
                    if (null != correspondencePartPerson) {
                        jgen.writeStringField(D_NUMBER, correspondencePartPerson.getdNumber());
                    }
                    if (null != correspondencePartPerson.getName()) {
                        jgen.writeStringField(NAME, correspondencePartPerson.getName());
                    }
                    if (null != correspondencePartPerson.getPostalAddress()) {
                        printAddress(jgen, correspondencePartPerson.getPostalAddress());
                    }
                    if (null != correspondencePartPerson.getResidingAddress()) {
                        printAddress(jgen, correspondencePartPerson.getResidingAddress());
                    }
                    if (null != correspondencePartPerson.getContactInformation()) {
                        printContactInformation(jgen, correspondencePartPerson.getContactInformation());
                    }

                }
            }

            public static void printCorrespondencePartInternal(JsonGenerator jgen,
                                                               ICorrespondencePartInternalEntity correspondencePartInternal)
                    throws IOException {
                if (null != correspondencePartInternal) {
                    printCorrespondencePart(jgen, correspondencePartInternal);
                    if (null != correspondencePartInternal.getAdministrativeUnit()) {
                        jgen.writeStringField(ADMINISTRATIVE_UNIT, correspondencePartInternal.getAdministrativeUnit());
                    }
                    if (null != correspondencePartInternal.getReferenceAdministrativeUnit()) {
                        String systemID = correspondencePartInternal.getReferenceAdministrativeUnit().getSystemId();
                        if (null != systemID) {
                            jgen.writeStringField(REFERENCE_ADMINISTRATIVE_UNIT, systemID);
                        }
                    }
                    if (null != correspondencePartInternal.getCaseHandler()) {
                        jgen.writeStringField(CASE_HANDLER, correspondencePartInternal.getCaseHandler());
                    }
                    if (null != correspondencePartInternal.getReferenceCaseHandler()) {
                        String systemID = correspondencePartInternal.getReferenceCaseHandler().getSystemId();
                        if (null != systemID) {
                            jgen.writeStringField(REFERENCE_CASE_HANDLER, systemID);
                        }
                    }
                }
            }

            public static void printCorrespondencePartUnit(JsonGenerator jgen,
                                                           ICorrespondencePartUnitEntity correspondencePartUnit)
                    throws IOException {
                if (null != correspondencePartUnit) {
                    printCorrespondencePart(jgen, correspondencePartUnit);

                    if (null != correspondencePartUnit.getOrganisationNumber()) {
                        jgen.writeStringField(ORGANISATION_NUMBER, correspondencePartUnit.getOrganisationNumber());
                    }
                    if (null != correspondencePartUnit.getName()) {
                        jgen.writeStringField(NAME, correspondencePartUnit.getName());
                    }
                    if (null != correspondencePartUnit.getBusinessAddress()) {
                        printAddress(jgen, correspondencePartUnit.getBusinessAddress());
                    }
                    if (null != correspondencePartUnit.getPostalAddress()) {
                        printAddress(jgen, correspondencePartUnit.getPostalAddress());
                    }
                    if (null != correspondencePartUnit.getContactInformation()) {
                        printContactInformation(jgen, correspondencePartUnit.getContactInformation());
                    }
                    if (null != correspondencePartUnit.getContactPerson()) {
                        jgen.writeStringField(CONTACT_PERSON, correspondencePartUnit.getContactPerson());
                    }

                }
            }

            public static void printCorrespondenceParts(JsonGenerator jgen, ICorrespondencePart correspondencePartObject)
                    throws IOException {
                Set<CorrespondencePart> correspondenceParts = correspondencePartObject.getReferenceCorrespondencePart();
                if (correspondenceParts != null && correspondenceParts.size() > 0) {
                    jgen.writeArrayFieldStart(CORRESPONDENCE_PART);
                    for (CorrespondencePart correspondencePart : correspondenceParts) {
                        jgen.writeStartObject();
                        printCorrespondencePart(jgen, correspondencePart);
                        jgen.writeEndObject();
                    }
                    jgen.writeEndArray();
                }
            }

            public static void printSignOff(JsonGenerator jgen, ISignOff signOffEntity)
                    throws IOException {
                Set<SignOff> signOffs = signOffEntity.getReferenceSignOff();
                if (signOffs != null && signOffs.size() > 0) {
                    jgen.writeArrayFieldStart(SIGN_OFF);
                    for (SignOff signOff : signOffs) {
                        if (signOff != null) {

                            jgen.writeObjectFieldStart(SIGN_OFF);

                            if (signOff.getSignOffDate() != null) {
                                jgen.writeStringField(SIGN_OFF_DATE,
                                                      formatDate(signOff.getSignOffDate()));
                            }
                            if (signOff.getSignOffBy() != null) {
                                jgen.writeStringField(SIGN_OFF_BY, signOff.getSignOffBy());
                            }
                            if (signOff.getSignOffMethod() != null) {
                                jgen.writeStringField(SIGN_OFF_METHOD, signOff.getSignOffMethod());
                            }

                            jgen.writeEndObject();
                        }
                    }
                    jgen.writeEndArray();
                }
            }

            public static void printDocumentFlow(JsonGenerator jgen, IDocumentFlow documentFlowEntity)
                    throws IOException {
                Set<DocumentFlow> documentFlows = documentFlowEntity.getReferenceDocumentFlow();
                if (documentFlows != null && documentFlows.size() > 0) {
                    jgen.writeArrayFieldStart(DOCUMENT_FLOW);
                    for (DocumentFlow documentFlow : documentFlows) {
                        if (documentFlow != null) {

                            jgen.writeObjectFieldStart(DOCUMENT_FLOW);
                            if (documentFlow.getFlowTo() != null) {
                                jgen.writeStringField(DOCUMENT_FLOW_FLOW_TO, documentFlow.getFlowTo());
                            }
                            if (documentFlow.getFlowFrom() != null) {
                                jgen.writeStringField(DOCUMENT_FLOW_FLOW_FROM, documentFlow.getFlowFrom());
                            }
                            if (documentFlow.getFlowReceivedDate() != null) {
                                jgen.writeStringField(DOCUMENT_FLOW_FLOW_RECEIVED_DATE,
                                                      formatDate(documentFlow.getFlowReceivedDate()));
                            }
                            if (documentFlow.getFlowSentDate() != null) {
                                jgen.writeStringField(DOCUMENT_FLOW_FLOW_SENT_DATE,
                                                      formatDate(documentFlow.getFlowSentDate()));
                            }
                            if (documentFlow.getFlowStatus() != null) {
                                jgen.writeStringField(DOCUMENT_FLOW_FLOW_STATUS, documentFlow.getFlowStatus());
                            }
                            if (documentFlow.getFlowComment() != null) {
                                jgen.writeStringField(DOCUMENT_FLOW_FLOW_COMMENT, documentFlow.getFlowComment());
                            }
                            jgen.writeEndObject();
                        }
                    }
                    jgen.writeEndArray();
                }
            }

            public static void printPrecedence(JsonGenerator jgen, IPrecedenceEntity precedence) throws IOException {
                if (precedence != null) {
                    if (null != precedence.getPrecedenceDate()) {
                        jgen.writeStringField(PRECEDENCE_DATE,
                                              formatDate(precedence.getPrecedenceDate()));
                    }
                    if (null != precedence.getCreatedDate()) {
                        jgen.writeStringField(CREATED_DATE,
                                              formatDateTime(precedence.getCreatedDate()));
                    }
                    if (null != precedence.getCreatedBy()) {
                        jgen.writeStringField(CREATED_BY, precedence.getCreatedBy());
                    }
                    if (null != precedence.getTitle()) {
                        jgen.writeStringField(TITLE, precedence.getTitle());
                    }
                    if (null != precedence.getDescription()) {
                        jgen.writeStringField(DESCRIPTION, precedence.getDescription());
                    }
                    if (null != precedence.getPrecedenceAuthority()) {
                        jgen.writeStringField(PRECEDENCE_AUTHORITY, precedence.getPrecedenceAuthority());
                    }
                    if (null != precedence.getSourceOfLaw()) {
                        jgen.writeStringField(PRECEDENCE_SOURCE_OF_LAW, precedence.getSourceOfLaw());
                    }
                    if (null != precedence.getPrecedenceApprovedDate()) {
                        jgen.writeStringField(PRECEDENCE_APPROVED_DATE,
                                              formatDate(precedence.getPrecedenceApprovedDate()));
                    }
                    if (null != precedence.getPrecedenceApprovedBy()) {
                        jgen.writeStringField(PRECEDENCE_APPROVED_BY, precedence.getPrecedenceApprovedBy());
                    }
                    if (null != precedence.getFinalisedDate()) {
                        jgen.writeStringField(FINALISED_DATE,
                                              formatDateTime(precedence.getFinalisedDate()));
                    }
                    if (null != precedence.getFinalisedBy()) {
                        jgen.writeStringField(FINALISED_BY, precedence.getFinalisedBy());
                    }
                    if (null != precedence.getPrecedenceStatus()) {
                        jgen.writeStringField(PRECEDENCE_STATUS, precedence.getPrecedenceStatus());
                    }
                }
            }

            public static void printPrecedences(JsonGenerator jgen, IPrecedence precedenceObject)
                    throws IOException {
                Set<Precedence> precedences = precedenceObject.getReferencePrecedence();
                if (precedences != null && precedences.size() > 0) {
                    jgen.writeArrayFieldStart(PRECEDENCE);
                    for (Precedence precedence : precedences) {
                        jgen.writeStartObject();
                        printPrecedence(jgen, precedence);
                        jgen.writeEndObject();
                    }
                    jgen.writeEndArray();
                }
            }

            public static void printAuthor(JsonGenerator jgen, IAuthor authorEntity)
                    throws IOException {
                Set<Author> author = authorEntity.getReferenceAuthor();
                if (author != null && author.size() > 0) {
                    jgen.writeArrayFieldStart(AUTHOR);
                    for (Author location : author) {
                        if (location.getAuthor() != null) {
                            jgen.writeString(location.getAuthor());
                        }
                    }
                    jgen.writeEndArray();
                }
            }

            public static void printStorageLocation(JsonGenerator jgen, IStorageLocation storageLocationEntity)
                    throws IOException {
                Set<StorageLocation> storageLocation = storageLocationEntity.getReferenceStorageLocation();
                if (storageLocation != null && storageLocation.size() > 0) {
                    jgen.writeArrayFieldStart(STORAGE_LOCATION);
                    for (StorageLocation location : storageLocation) {
                        if (location.getStorageLocation() != null) {
                            jgen.writeString(location.getStorageLocation());
                        }
                    }
                    jgen.writeEndArray();
                }
            }

            public static void printConversion(JsonGenerator jgen,
                                               IConversion conversionEntity)
                    throws IOException {
                Set<Conversion> conversions = conversionEntity.getReferenceConversion();
                if (conversions != null && conversions.size() > 0) {
                    for (Conversion conversion : conversions) {

                        if (conversion != null) {
                            jgen.writeObjectFieldStart(ELECTRONIC_SIGNATURE);
                            if (conversion.getConvertedDate() != null) {
                                jgen.writeStringField(CONVERTED_DATE,
                                                      formatDate(conversion.getConvertedDate()));
                            }
                            if (conversion.getConvertedBy() != null) {
                                jgen.writeStringField(CONVERTED_BY,
                                        conversion.getConvertedBy());
                            }
                            if (conversion.getConvertedFromFormat() != null) {
                                jgen.writeStringField(CONVERTED_FROM_FORMAT,
                                        conversion.getConvertedFromFormat());
                            }
                            if (conversion.getConvertedToFormat() != null) {
                                jgen.writeStringField(CONVERTED_TO_FORMAT,
                                        conversion.getConvertedToFormat());
                            }
                            if (conversion.getConversionTool() != null) {
                                jgen.writeStringField(CONVERSION_TOOL,
                                        conversion.getConversionTool());
                            }
                            if (conversion.getConversionComment() != null) {
                                jgen.writeStringField(CONVERSION_COMMENT,
                                        conversion.getConversionComment());
                            }
                            jgen.writeEndObject();
                        }
                    }
                }
            }

            public static void printFondsCreators(JsonGenerator jgen, IFondsCreator fondsCreatorObject)
                    throws IOException {

                Set<FondsCreator> fondsCreators = fondsCreatorObject.getReferenceFondsCreator();
                if (fondsCreators != null) {
                    for (FondsCreator fondsCreator : fondsCreators) {
                        if (fondsCreator != null) {
                            jgen.writeObjectFieldStart(FONDS_CREATOR);
                            printFondsCreator(jgen, fondsCreator);
                            jgen.writeEndObject();
                        }
                    }
                }
            }

            public static void printFondsCreator(JsonGenerator jgen,
                                                 IFondsCreatorEntity fondsCreatorEntity)
                    throws IOException {
                if (fondsCreatorEntity != null) {
                    if (fondsCreatorEntity.getSystemId() != null) {
                        jgen.writeStringField(SYSTEM_ID, fondsCreatorEntity.getSystemId());
                    }
                    if (fondsCreatorEntity.getFondsCreatorId() != null) {
                        jgen.writeStringField(FONDS_CREATOR_ID, fondsCreatorEntity.getFondsCreatorId());
                    }
                    if (fondsCreatorEntity.getFondsCreatorName() != null) {
                        jgen.writeStringField(FONDS_CREATOR_NAME, fondsCreatorEntity.getFondsCreatorName());
                    }
                    if (fondsCreatorEntity.getDescription() != null) {
                        jgen.writeStringField(DESCRIPTION, fondsCreatorEntity.getDescription());
                    }
                }
            }

            public static void printElectronicSignature(JsonGenerator jgen,
                                                        IElectronicSignature electronicSignatureEntity)
                    throws IOException {
                ElectronicSignature electronicSignature = electronicSignatureEntity.getReferenceElectronicSignature();
                if (electronicSignature != null) {
                    jgen.writeObjectFieldStart(ELECTRONIC_SIGNATURE);
                    if (electronicSignature.getElectronicSignatureSecurityLevel() != null) {
                        jgen.writeStringField(ELECTRONIC_SIGNATURE_SECURITY_LEVEL,
                                electronicSignature.getElectronicSignatureSecurityLevel());
                    }
                    if (electronicSignature.getElectronicSignatureVerified() != null) {
                        jgen.writeStringField(ELECTRONIC_SIGNATURE_VERIFIED,
                                electronicSignature.getElectronicSignatureVerified());
                    }
                    if (electronicSignature.getVerifiedDate() != null) {
                        jgen.writeStringField(ELECTRONIC_SIGNATURE_VERIFIED_DATE,
                                              formatDate(electronicSignature.getVerifiedDate()));
                    }
                    if (electronicSignature.getVerifiedBy() != null) {
                        jgen.writeStringField(ELECTRONIC_SIGNATURE_VERIFIED_BY,
                                electronicSignature.getVerifiedBy());
                    }
                    jgen.writeEndObject();
                }
            }

            public static void printClassified(JsonGenerator jgen, IClassified classifiedEntity)
                    throws IOException {
                if (classifiedEntity != null) {
                    Classified classified = classifiedEntity.getReferenceClassified();
                    if (classified != null) {
                        jgen.writeObjectFieldStart(CLASSIFIED);
                        if (classified.getClassification() != null) {
                            jgen.writeStringField(CLASSIFICATION, classified.getClassification());
                        }
                        if (classified.getClassificationDate() != null) {
                            jgen.writeStringField(CLASSIFICATION_DATE,
                                                  formatDateTime(classified.getClassificationDate()));
                        }
                        if (classified.getClassificationBy() != null) {
                            jgen.writeStringField(CLASSIFICATION_BY, classified.getClassificationBy());
                        }
                        if (classified.getClassificationDowngradedDate() != null) {
                            jgen.writeStringField(CLASSIFICATION_DOWNGRADED_DATE,
                                                  formatDateTime(classified.getClassificationDowngradedDate()));
                        }
                        if (classified.getClassificationDowngradedBy() != null) {
                            jgen.writeStringField(CLASSIFICATION_DOWNGRADED_BY,
                                    classified.getClassificationDowngradedBy());
                        }
                        jgen.writeEndObject();
                    }
                }
            }

            public static void printDisposal(JsonGenerator jgen, IDisposal disposalEntity)
                    throws IOException {
                if (disposalEntity != null) {
                    Disposal disposal = disposalEntity.getReferenceDisposal();
                    if (disposal != null) {
                        jgen.writeObjectFieldStart(DISPOSAL);
                        if (disposal.getDisposalDecision() != null) {
                            jgen.writeStringField(DISPOSAL_DECISION,
                                    disposal.getDisposalDecision());
                        }
                        if (disposal.getDisposalAuthority() != null) {
                            jgen.writeStringField(DISPOSAL_AUTHORITY,
                                    disposal.getDisposalAuthority());
                        }
                        if (disposal.getPreservationTime() != null) {
                            jgen.writeStringField(DISPOSAL_PRESERVATION_TIME,
                                    Integer.toString(disposal.getPreservationTime()));
                        }
                        if (disposal.getDisposalDate() != null) {
                            jgen.writeStringField(DISPOSAL_DATE,
                                                  formatDate(disposal.getDisposalDate()));
                        }
                        jgen.writeEndObject();
                    }
                }
            }

            public static void printDisposalUndertaken(JsonGenerator jgen, IDisposalUndertaken disposalUndertakenEntity)
                    throws IOException {
                if (disposalUndertakenEntity != null) {
                    DisposalUndertaken disposalUndertaken = disposalUndertakenEntity.getReferenceDisposalUndertaken();
                    if (disposalUndertaken != null) {
                        jgen.writeObjectFieldStart(DISPOSAL_UNDERTAKEN);
                        if (disposalUndertaken.getDisposalBy() != null) {
                            jgen.writeStringField(DISPOSAL_UNDERTAKEN_BY, disposalUndertaken.getDisposalBy());
                        }
                        if (disposalUndertaken.getDisposalDate() != null) {
                            jgen.writeStringField(DISPOSAL_UNDERTAKEN_DATE,
                                                  formatDate(disposalUndertaken.getDisposalDate()));
                        }
                        jgen.writeEndObject();
                    }
                }
            }

            public static void printDeletion(JsonGenerator jgen, IDeletion deletionEntity)
                    throws IOException {
                if (deletionEntity != null) {
                    Deletion deletion = deletionEntity.getReferenceDeletion();
                    if (deletion != null) {
                        jgen.writeObjectFieldStart(DELETION);
                        if (deletion.getDeletionBy() != null) {
                            jgen.writeStringField(DELETION_BY, deletion.getDeletionBy());
                        }
                        if (deletion.getDeletionType() != null) {
                            jgen.writeStringField(DELETION_TYPE, deletion.getDeletionType());
                        }
                        if (deletion.getDeletionDate() != null) {
                            jgen.writeStringField(DELETION_DATE,
                                                  formatDate(deletion.getDeletionDate()));
                        }
                        jgen.writeEndObject();
                    }
                }
            }

            public static void printScreening(JsonGenerator jgen, IScreening screeningEntity)
                    throws IOException {
                if (screeningEntity != null) {
                    Screening screening = screeningEntity.getReferenceScreening();
                    if (screening != null) {
                        jgen.writeObjectFieldStart(SCREENING);
                        if (screening.getAccessRestriction() != null) {
                            jgen.writeStringField(SCREENING_ACCESS_RESTRICTION,
                                    screening.getAccessRestriction());
                        }
                        if (screening.getScreeningAuthority() != null) {
                            jgen.writeStringField(SCREENING_AUTHORITY,
                                    screening.getScreeningAuthority());
                        }
                        if (screening.getScreeningMetadata() != null) {
                            jgen.writeStringField(SCREENING_METADATA,
                                    screening.getScreeningMetadata());
                        }
                        if (screening.getScreeningDocument() != null) {
                            jgen.writeStringField(SCREENING_DOCUMENT,
                                    screening.getScreeningDocument());
                        }
                        if (screening.getScreeningExpiresDate() != null) {
                            jgen.writeStringField(SCREENING_EXPIRES_DATE,
                                                  formatDate(screening.getScreeningExpiresDate()));
                        }
                        if (screening.getScreeningDuration() != null) {
                            jgen.writeStringField(SCREENING_DURATION,
                                    screening.getScreeningDuration());
                        }
                        jgen.writeEndObject();
                    }
                }
            }

            public static void printComment(JsonGenerator jgen, IComment commentEntity)
                    throws IOException {
                Set<Comment> comments = commentEntity.getReferenceComment();
                if (comments != null && comments.size() > 0) {
                    jgen.writeArrayFieldStart(COMMENT);
                    for (Comment comment : comments) {
                        if (comment.getCommentText() != null) {
                            jgen.writeStringField(COMMENT_TEXT, comment.getCommentText());
                        }
                        if (comment.getCommentType() != null) {
                            jgen.writeStringField(COMMENT_TYPE, comment.getCommentType());
                        }
                        if (comment.getCommentDate() != null) {
                            jgen.writeStringField(COMMENT_DATE,
                                                  formatDate(comment.getCommentDate()));
                        }
                        if (comment.getCommentRegisteredBy() != null) {
                            jgen.writeStringField(COMMENT_REGISTERED_BY, comment.getCommentRegisteredBy());
                        }
                    }
                    jgen.writeEndArray();
                }
            }

            public static void printCrossReference(JsonGenerator jgen, ICrossReferenceEntity crossReferenceEntity)
                    throws IOException {
                if (crossReferenceEntity != null) {
                    jgen.writeObjectFieldStart(CROSS_REFERENCE);
                    if (crossReferenceEntity.getReferenceToClass() != null) {
                        jgen.writeStringField(CROSS_REFERENCE_CLASS, crossReferenceEntity.getReferenceToClass());
                    }
                    if (crossReferenceEntity.getReferenceToFile() != null) {
                        jgen.writeStringField(CROSS_REFERENCE_FILE, crossReferenceEntity.getReferenceToFile());
                    }
                    if (crossReferenceEntity.getReferenceToRecord() != null) {
                        jgen.writeStringField(CROSS_REFERENCE_RECORD, crossReferenceEntity.getReferenceToRecord());
                    }
                    jgen.writeEndObject();
                }
            }


            public static void printKeyword(JsonGenerator jgen, IKeyword keywordEntity)
                    throws IOException {
                Set<Keyword> keywords = keywordEntity.getReferenceKeyword();
                if (keywords != null && keywords.size() > 0) {
                    jgen.writeArrayFieldStart(KEYWORD);
                    for (Keyword keyword : keywords) {
                        if (keyword.getKeyword() != null) {
                            jgen.writeString(keyword.getKeyword());
                        }
                    }
                    jgen.writeEndArray();
                }
            }
        }
    }
}
