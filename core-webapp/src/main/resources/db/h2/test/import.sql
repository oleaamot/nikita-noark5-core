// Create Noark 5 core users for the test run

insert into User (id, username, email, password_hash, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (1, 'case', 'case_handler@example.com', 'password', true, true, true, true, '2016-08-08 00:00:00');
insert into User (id, username, email, password_hash, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (2, 'rm', 'rm@example.com', 'password', true, true, true, true, '2016-08-08 00:00:00');
insert into User (id, username, email, password_hash, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (3, 'admin', 'admin@example.com', 'password', true, true, true, true, '2016-08-08 00:00:00');

// Create groups / (spring) authorities
insert into Authority (name) values ('ADMIN');
insert into Authority (name) values ('CASE_HANDLER');
insert into Authority (name) values ('RECORDS_MANAGER');

// Assign users to a group
insert into User_authority(user_id, authority_name) values (1, 'CASE_HANDLER');
insert into User_authority(user_id, authority_name) values (2, 'RECORDS_MANAGER');
insert into User_authority(user_id, authority_name) values (3, 'ADMIN');

INSERT INTO document_medium (pk_document_medium_id, system_id, code, description, deleted)
VALUES (0, '48e067ae-d794-4216-8a9d-7cb0ef03472c', 'Fysisk medium', 'Bare fysiske dokumenter', FALSE);
INSERT INTO document_medium (pk_document_medium_id, system_id, code, description, deleted)
VALUES (1, '1b617405-9dd6-4187-bee6-81ed4f4a0e1f', 'Elektronisk arkiv', 'Bare elektroniske dokumenter', FALSE);
INSERT INTO document_medium (pk_document_medium_id, system_id, code, description, deleted)
VALUES (2, '00d21eea-c231-4542-8e3a-964f441fbbd3', 'Blandet fysisk og elektronisk arkiv', '', FALSE);

INSERT INTO fonds_status (pk_fonds_status_id, code, deleted) VALUES (0, 'Opprettet', FALSE);
INSERT INTO fonds_status (pk_fonds_status_id, code, deleted) VALUES (1, 'Avsluttet', FALSE);