insert into User (id, username, email, password_hash, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (1, 'case', 'case_handler@example.com', 'password', true, true, true, true, '2016-08-08 00:00:00');
insert into User (id, username, email, password_hash, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (2, 'rm', 'rm@example.com', 'password', true, true, true, true, '2016-08-08 00:00:00');
insert into User (id, username, email, password_hash, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (3, 'admin', 'admin@example.com', 'password', true, true, true, true, '2016-08-08 00:00:00');
INSERT INTO Authority (name) VALUES ('ADMIN');
INSERT INTO Authority (name) VALUES ('CASE_HANDLER');
INSERT INTO Authority (name) VALUES ('RECORDS_MANAGER');
insert into User_authority(user_id, authority_name) values (1, 'CASE_HANDLER');
insert into User_authority(user_id, authority_name) values (2, 'RECORDS_MANAGER');
insert into User_authority(user_id, authority_name) values (3, 'ADMIN');

insert into document_medium (pk_document_medium_id, code, description, deleted) values (0, 'Fysisk medium ', 'Bare fysiske dokumenter', false);
insert into document_medium (pk_document_medium_id, code, description, deleted) values (1, 'Elektronisk arkiv', 'Bare elektroniske dokumenter', false);
insert into document_medium (pk_document_medium_id, code, description, deleted) values (2, 'Blandet fysisk og elektronisk arkiv', '', false);

insert into fonds_status(pk_fonds_status_id, code, deleted) values (0, 'Opprettet', false);
insert into fonds_status(pk_fonds_status_id, code, deleted) values (1, 'Avsluttet', false);