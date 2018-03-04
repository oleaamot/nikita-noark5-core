/* document_medium / dokumentmedium  */
INSERT INTO document_medium (pk_document_medium_id, system_id, code,
                             description, deleted, version)
VALUES (1, '48e067ae-d794-4216-8a9d-7cb0ef03472c', 'Fysisk medium',
        'Bare fysiske dokumenter', FALSE, 0);
INSERT INTO document_medium (pk_document_medium_id, system_id, code,
                             description, deleted, version)
VALUES (2, '1b617405-9dd6-4187-bee6-81ed4f4a0e1f', 'Elektronisk arkiv',
        'Bare elektroniske dokumenter', FALSE, 0);
INSERT INTO document_medium (pk_document_medium_id, system_id, code,
                             description, deleted, version)
VALUES (3, '00d21eea-c231-4542-8e3a-964f441fbbd3',
        'Blandet fysisk og elektronisk arkiv',
        'Blanding av fysiske og elektroniske dokumenter ', FALSE, 0);

/* fonds_status / arkivstatus */
INSERT INTO fonds_status (pk_fonds_status_id, system_id, code,
                          description, deleted, version)
VALUES (1, '9ed3f5d3-9934-446d-85b8-b2ada9498aa7', 'Opprettet',
        NULL, FALSE, 0);
INSERT INTO fonds_status (pk_fonds_status_id, system_id, code,
                          description, deleted, version)
VALUES (2, 'b94eb9cf-e71f-4d68-964b-b68c7f9962b6', 'Avsluttet',
        NULL, FALSE, 0);

/* series_status / arkivdelstatus*/
INSERT INTO series_status (pk_series_status_id, system_id, code,
                           description, deleted, version)
VALUES (1, '21dca458-5715-455b-baef-6c81c6e1e73a', 'A',
        'Aktiv periode', FALSE, 0);
INSERT INTO series_status (pk_series_status_id, system_id, code,
                           description, deleted, version)
VALUES (2, '24ee7d87-4e5e-46d8-ba9a-d236609fdeeb', 'O',
        'Overlappingsperiode', FALSE, 0);
INSERT INTO series_status (pk_series_status_id, system_id, code,
                           description, deleted, version)
VALUES (3, 'a67ae905-a556-4cf6-bfa9-43ad15836f61', 'P',
        'Avsluttet periode', FALSE, 0);
INSERT INTO series_status (pk_series_status_id, system_id, code,
                           description, deleted, version)
VALUES (4, '30bec9da-7299-4f50-9233-8a556cc5708c', 'U',
        'Uaktuelle mapper', FALSE, 0);

/* sign_off_method / avskrivningsmaate */
INSERT INTO sign_off_method (pk_sign_off_method_id, system_id, code,
                             description, deleted, version)
VALUES (1, '5ccea231-c2cc-4507-8a72-709c9c54bcd5', 'BU',
        'Besvart med brev', FALSE, 0);
INSERT INTO sign_off_method (pk_sign_off_method_id, system_id, code,
                             description, deleted, version)
VALUES (2, '3ea3d7c2-2479-46a4-b56d-8cc0c31208a7', 'BE',
        'Besvart med e-post', FALSE, 0);
INSERT INTO sign_off_method (pk_sign_off_method_id, system_id, code,
                             description, deleted, version)
VALUES (3, 'e549d858-f6d6-4d86-b811-014ade0ca6c6', 'TLF',
        'Besvart på telefon', FALSE, 0);
INSERT INTO sign_off_method (pk_sign_off_method_id, system_id, code,
                             description, deleted, version)
VALUES (4, 'ec14aac1-df6d-495a-bcf5-caa9a0c18bb8', 'TE',
        'Tatt til etterretning', FALSE, 0);
INSERT INTO sign_off_method (pk_sign_off_method_id, system_id, code,
                             description, deleted, version)
VALUES (5, 'c62c80d9-7700-4243-b2e1-fd14af9a9559', 'TO',
        'Tatt til orientering', FALSE, 0);

/* document_status / dokumentstatus */
INSERT INTO document_status (pk_document_status_id, system_id, code,
                             description, deleted, version)
VALUES (1, '553287bc-4592-4107-8567-0402458fdc4e', 'B',
        'Dokumentet er under redigering', FALSE, 0);
INSERT INTO document_status (pk_document_status_id, system_id, code,
                             description, deleted, version)
VALUES (2, '29ad0b9a-fe52-4e4b-af9a-1a4841f6ad43', 'F',
        'Dokumentet er ferdigstilt', FALSE, 0);

INSERT INTO document_type (pk_document_type_id, system_id, code,
                           description, deleted, version)
VALUES (1, '26ef04f8-5efb-4f19-9cde-d99a40d8cb1b', 'B',
        'Brev', FALSE, 0);
INSERT INTO document_type (pk_document_type_id, system_id, code,
                           description, deleted, version)
VALUES (2, 'e12f0bf6-aac4-4bb0-8f23-9847f0b67453', 'R',
        'Rundskriv', FALSE, 0);
INSERT INTO document_type (pk_document_type_id, system_id, code,
                           description, deleted, version)
VALUES (3, '1f4ec7b5-a717-433d-a3dc-d357e6579de0', 'F',
        'Faktura', FALSE, 0);
INSERT INTO document_type (pk_document_type_id, system_id, code,
                           description, deleted, version)
VALUES (3, '99281649-0d0c-402f-9494-7df99b7d73f1', 'O',
        'Ordrebekreftelse', FALSE, 0);

/* electronic_signature_security_level / elektronsiksignatursikkerhetsnivaa */
INSERT INTO electronic_signature_security_level (
  pk_electronic_signature_security_level_id, system_id, code,
  description, deleted, version)
VALUES (1, 'dc55f30f-a549-40ed-86e3-b3104cb1f7f5', 'SK',
        'Symmetrisk kryptert', FALSE, 0);
INSERT INTO electronic_signature_security_level (
  pk_electronic_signature_security_level_id, system_id, code,
  description, deleted, version)
VALUES (2, 'e1872a1e-4233-4a6e-884d-d8bdb3c48754', 'V',
        'Sendt med PKI/virksomhetssertifikat', FALSE, 0);
INSERT INTO electronic_signature_security_level (
  pk_electronic_signature_security_level_id, system_id, code,
  description, deleted, version)
VALUES (3, '2aa9657b-8498-4d33-8e41-0acf649a8d6d', 'PS',
        'Sendt med PKI/"personstandard"-sertifikat', FALSE, 0);
INSERT INTO electronic_signature_security_level (
  pk_electronic_signature_security_level_id, system_id, code,
  description, deleted, version)
VALUES (4, '558401c6-b99e-4adf-9576-811da4c7e461', 'PH',
        'Sendt med PKI/"person høy"-sertifikat', FALSE, 0);

/* electronic_signature_verified / elektronsiksignaturverifisert */
INSERT INTO electronic_signature_verified (
  pk_electronic_signature_verified_id, system_id, code,
  description, deleted, version)
VALUES (1, 'e8ff13ad-dba4-450b-92b7-f41da771b300', 'I',
        'Signatur påført, ikke verifisert', FALSE, 0);

INSERT INTO electronic_signature_verified (
  pk_electronic_signature_verified_id, system_id, code,
  description, deleted, version)
VALUES (2, 'f2006c7b-44f4-4a84-8c62-bbcc7f9ee608', 'V',
        'Signatur påført og verifisert', FALSE, 0);

/* flow_Status flytstatus */
INSERT INTO flow_status (pk_flow_status_id, system_id, code,
                         description, deleted, version)
VALUES (1, 'd854743a-8c5a-4791-b56c-a10fd240e768', 'G',
        'Godkjent', FALSE, 0);
INSERT INTO flow_status (pk_flow_status_id, system_id, code,
                         description, deleted, version)
VALUES (2, '949ad9cf-59ff-492e-9ce5-8a3bdced9cc9', 'I',
        'Ikke godkjent', FALSE, 0);
INSERT INTO flow_status (pk_flow_status_id, system_id, code,
                         description, deleted, version)
VALUES (3, '8a802fc2-7d9c-43ae-babd-72cc19d47a46', 'S',
        'Sendt tilbake til saksbehandler med kommentarer', FALSE, 0);

/* format / format */
INSERT INTO format (pk_format_id, system_id, code,
                    description, deleted, version)
VALUES (1, '93ac2b0f-1729-4c78-a917-a0f494a035ca', 'RA-TEKST',
        'Ren tekst', FALSE, 0);
INSERT INTO format (pk_format_id, system_id, code,
                    description, deleted, version)
VALUES (2, 'ba4c8c2c-4072-4fa7-b194-8812ea38697f', 'RA-TIFF6',
        'TIFF versjon 6', FALSE, 0);
INSERT INTO format (pk_format_id, system_id, code,
                    description, deleted, version)
VALUES (3, 'eb53170a-14b1-48e2-a5c4-965fef380ca6', 'RA-PDF',
        'PDF/A - ISO 19005-1:2005', FALSE, 0);
INSERT INTO format (pk_format_id, system_id, code,
                    description, deleted, version)
VALUES (4, '3d2c0bad-c8e3-4f03-b52e-d99867d53230', 'RA-XML',
        'XML', FALSE, 0);
INSERT INTO format (pk_format_id, system_id, code,
                    description, deleted, version)
VALUES (5, '18468b06-b491-4029-9e6f-395ad1d27b03', 'RA-JPEG',
        'JPEG', FALSE, 0);
INSERT INTO format (pk_format_id, system_id, code,
                    description, deleted, version)
VALUES (6, 'd5063523-83a2-4e51-b8aa-925385849e33', 'RA-SOSI',
        'SOSI', FALSE, 0);
INSERT INTO format (pk_format_id, system_id, code,
                    description, deleted, version)
VALUES (7, '9ae48e86-3c9e-47fd-bbf2-4d9f67b7de85', 'RA-MPEG-2',
        'MPEG-2', FALSE, 0);
INSERT INTO format (pk_format_id, system_id, code,
                    description, deleted, version)
VALUES (8, '7412833e-ceaf-40fd-95b5-e634f4640f99', 'RA-MP3',
        'MP3', FALSE, 0);

/* correspondence_part_type korrespondanseparttype */
INSERT INTO correspondence_part_type (
  pk_correspondence_part_type_id, system_id, code,
  description, deleted, version)
VALUES (1, '5b4588a3-7e4d-43ae-a3a8-4033e52944c0', 'EA',
        'Avsender', FALSE, 0);
INSERT INTO correspondence_part_type (
  pk_correspondence_part_type_id, system_id, code,
  description, deleted, version)
VALUES (2, 'c4a2097c-8cd1-496f-9cf5-bd16de1d85fe', 'EM',
        'Mottaker', FALSE, 0);
INSERT INTO correspondence_part_type (
  pk_correspondence_part_type_id, system_id, code,
  description, deleted, version)
VALUES (3, '6c2ace2f-05b5-45c0-96da-cff41cc5b2e7', 'EK',
        'Kopimottaker', FALSE, 0);
INSERT INTO correspondence_part_type (
  pk_correspondence_part_type_id, system_id, code,
  description, deleted, version)
VALUES (4, 'dcb06157-c8d3-4b43-a065-5cc21e80fbf6', 'GM',
        'Gruppemottaker', FALSE, 0);
INSERT INTO correspondence_part_type (
  pk_correspondence_part_type_id, system_id, code,
  description, deleted, version)
VALUES (5, '896d5f6c-2093-492d-a531-7dd845d45808', 'IA',
        'Intern avsender', FALSE, 0);
INSERT INTO correspondence_part_type (
  pk_correspondence_part_type_id, system_id, code,
  description, deleted, version)
VALUES (6, '77e1fbc4-e480-4e41-a97e-f429b816629e', 'IM',
        'Intern mottaker', FALSE, 0);
INSERT INTO correspondence_part_type (
  pk_correspondence_part_type_id, system_id, code,
  description, deleted, version)
VALUES (7, '78e59b26-819c-4cf7-abe1-69fb6e457bcb', 'IK',
        'Intern kopimottaker', FALSE, 0);

/* registry_entry_status journalpoststatus */
INSERT INTO registry_entry_status (
  pk_registry_entry_status_id, system_id, code,
  description, deleted, version)
VALUES (1, '0b4cfcb9-e979-4cdd-94d3-1660172976a4', 'J',
        'Journalført', FALSE, 0);
INSERT INTO registry_entry_status (
  pk_registry_entry_status_id, system_id, code,
  description, deleted, version)
VALUES (2, '2f140067-8239-4ea3-88a3-c42380ec3885', 'F',
        'Ferdigstilt fra saksbehandler', FALSE, 0);
INSERT INTO registry_entry_status (
  pk_registry_entry_status_id, system_id, code,
  description, deleted, version)
VALUES (3, '218deb74-f9e0-48e2-a523-3f47a6a44aba', 'G',
        'Godkjent av leder', FALSE, 0);
INSERT INTO registry_entry_status (
  pk_registry_entry_status_id, system_id, code,
  description, deleted, version)
VALUES (4, '35940d96-e531-4e5b-8f0a-63f8c7d08cb0', 'E',
        'Ekspedert', FALSE, 0);
INSERT INTO registry_entry_status (
  pk_registry_entry_status_id, system_id, code,
  description, deleted, version)
VALUES (5, '74aef294-f3d1-4e77-a3c1-abcb45a7eeea', 'A',
        'Arkivert', FALSE, 0);
INSERT INTO registry_entry_status (
  pk_registry_entry_status_id, system_id, code,
  description, deleted, version)
VALUES (6, '8bc27628-f722-41c5-9ce9-4f46fc54bf34', 'U',
        'Utgår', FALSE, 0);
INSERT INTO registry_entry_status (
  pk_registry_entry_status_id, system_id, code,
  description, deleted, version)
VALUES (7, '395ff299-e60d-4550-979e-5b682d8e4f61', 'M',
        'Midlertidig registrering av', FALSE, 0);

/* registry_entry_type journalposttype */
INSERT INTO registry_entry_type (
  pk_registry_entry_type_id, system_id, code,
  description, deleted, version)
VALUES (1, '4827e5e0-0fae-4b9e-a01d-88ce00e8c62e', 'I',
        'Inngående dokument', FALSE, 0);
INSERT INTO registry_entry_type (
  pk_registry_entry_type_id, system_id, code,
  description, deleted, version)
VALUES (2, 'f87a8b7f-bb31-4dea-98c6-13e1ac17e24f', 'U',
        'Utgående dokument', FALSE, 0);
INSERT INTO registry_entry_type (
  pk_registry_entry_type_id, system_id, code,
  description, deleted, version)
VALUES (3, '001f176e-d68a-42f4-95f8-555a72c1a748', 'N',
        'Organinternt dokument for oppfølging', FALSE, 0);
INSERT INTO registry_entry_type (
  pk_registry_entry_type_id, system_id, code,
  description, deleted, version)
VALUES (4, '2014243a-5d33-4fd3-98af-eda7ddd4cdc8', 'X',
        'Organinternt dokument uten oppfølging', FALSE, 0);
INSERT INTO registry_entry_type (
  pk_registry_entry_type_id, system_id, code,
  description, deleted, version)
VALUES (5, '07c06822-50e8-47fb-a760-eaff70eaa64e', 'S',
        'Saksframlegg', FALSE, 0);

/* precedence_status / presedensstatus */
INSERT INTO precedence_status (
  pk_precedence_status_id, system_id, code,
  description, deleted, version)
VALUES (1, 'f2c39e2e-8353-49ca-91a1-6ae48c658cd1', 'G',
        'Gjeldende', FALSE, 0);

INSERT INTO precedence_status (
  pk_precedence_status_id, system_id, code,
  description, deleted, version)
VALUES (2, 'cacc4170-6149-4d65-8811-7ca5855e089b', 'F',
        'Foreldet', FALSE, 0);

/* case_party_role / sakspartrolle */
INSERT INTO case_party_role (
  pk_case_party_role_id, system_id, code,
  description, deleted, version)
VALUES (1, '391e6756-1885-4ef9-b2cb-226a7bbcd381', 'KLI',
        'Klient', FALSE, 0);

INSERT INTO case_party_role (
  pk_case_party_role_id, system_id, code,
  description, deleted, version)
VALUES (2, '882ada83-04ca-4a24-81ba-5572d653804d', 'PÅ',
        'Pårørende', FALSE, 0);

INSERT INTO case_party_role (
  pk_case_party_role_id, system_id, code,
  description, deleted, version)
VALUES (3, 'b49e85df-1512-4ad4-8ca9-5fe712bb16f7', 'FORM',
        'Formynder', FALSE, 0);

INSERT INTO case_party_role (
  pk_case_party_role_id, system_id, code,
  description, deleted, version)
VALUES (4, 'cd0373f5-01c3-4586-b9b7-9280b3e579b9', 'ADV',
        'Advokat', FALSE, 0);


/* file_type / mappetype
 Note. This is an open code list in the standard. I have added the following
 values based on common sense.
*/
INSERT INTO file_type (
  pk_file_type_id, system_id, code,
  description, deleted, version)
VALUES (1, '2a14a47d-4bbc-4d0d-b3e7-267b105ec539', 'SKM',
        'Saksmappe', FALSE, 0);

INSERT INTO file_type (
  pk_file_type_id, system_id, code,
  description, deleted, version)
VALUES (2, '84766390-8799-4e7d-96ab-1e5f26596804', 'PLM',
        'Personalmappe', FALSE, 0);

INSERT INTO file_type (
  pk_file_type_id, system_id, code,
  description, deleted, version)
VALUES (3, 'b027b486-9b42-48a0-88e3-c2a6ddc91589', 'BYM',
        'Byggemappe', FALSE, 0);

INSERT INTO file_type (
  pk_file_type_id, system_id, code,
  description, deleted, version)
VALUES (4, '1b8c9632-b5d1-49a1-a0ec-2ec2ee3e7483', 'MOM',
        'Møtemappe', FALSE, 0);

/* file_type / mappetype
 Note. This is an open code list in the standard. The following values are taken
 from the Noark 4 standard
*/
INSERT INTO comment_type (
  pk_comment_type_id, system_id, code,
  description, deleted, version)
VALUES (1, 'bf105de3-7eb7-4abc-b836-b89180df462c', 'MS',
        'Merknad fra saksbehandler', FALSE, 0);

INSERT INTO comment_type (
  pk_comment_type_id, system_id, code,
  description, deleted, version)
VALUES (2, 'c7d484ab-fb3c-4da5-b475-f39c085086e0', 'ML',
        'Merknad fra leder', FALSE, 0);

INSERT INTO comment_type (
  pk_comment_type_id, system_id, code,
  description, deleted, version)
VALUES (3, '7c1b04df-26b6-4a91-a949-3cb1717bd901', 'MA',
        'Merknad fra arkivansvarlig', FALSE, 0);
