insert into User (id, username, email, password_hash, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (1, 'casehandler', 'case_handler@example.com', 'password', true, true, true, true, '2016-08-08 00:00:00');
insert into User (id, username, email, password_hash, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (2, 'recordkeeper', 'rm@example.com', 'password', true, true, true, true, '2016-08-08 00:00:00');
insert into User (id, username, email, password_hash, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (3, 'admin', 'admin@example.com', 'password', true, true, true, true, '2016-08-08 00:00:00');
insert into User (id, username, email, password_hash, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (4, 'leader', 'leader@example.com', 'password', true, true, true, true, '2016-08-08 00:00:00');
insert into User (id, username, email, password_hash, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (5, 'guest', 'guest@example.com', 'password', true, true, true, true, '2016-08-08 00:00:00');

INSERT INTO Authority (name) VALUES ('RECORDS_MANAGER');
INSERT INTO Authority (name) VALUES ('RECORDS_KEEPER');
INSERT INTO Authority (name) VALUES ('CASE_HANDLER');
INSERT INTO Authority (name) VALUES ('LEADER');
INSERT INTO Authority (name) VALUES ('GUEST');

insert into User_authority(user_id, authority_name) values (1, 'CASE_HANDLER');
insert into User_authority(user_id, authority_name) values (2, 'RECORDS_KEEPER');
insert into User_authority(user_id, authority_name) values (3, 'RECORDS_MANAGER');
insert into User_authority(user_id, authority_name) values (4, 'LEADER');
insert into User_authority(user_id, authority_name) values (5, 'GUEST');

insert into document_medium (pk_document_medium_id, system_id, code, description, deleted) values (0, '48e067ae-d794-4216-8a9d-7cb0ef03472c', 'Fysisk medium', 'Bare fysiske dokumenter', false);
insert into document_medium (pk_document_medium_id, system_id, code, description, deleted) values (1, '1b617405-9dd6-4187-bee6-81ed4f4a0e1f', 'Elektronisk arkiv', 'Bare elektroniske dokumenter', false);
insert into document_medium (pk_document_medium_id, system_id, code, description, deleted) values (2, '00d21eea-c231-4542-8e3a-964f441fbbd3', 'Blandet fysisk og elektronisk arkiv', '', false);

insert into fonds_status(pk_fonds_status_id, code, deleted) values (0, 'Opprettet', false);
insert into fonds_status(pk_fonds_status_id, code, deleted) values (1, 'Avsluttet', false);