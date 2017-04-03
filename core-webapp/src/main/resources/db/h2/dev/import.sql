insert into nikita_user (id, username, email, password, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (1, 'admin', 'admin@example.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', true, true, true, true, '2016-08-08 00:00:00');
insert into nikita_user (id, username, email, password, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (2, 'recordkeeper', 'rm@example.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', true, true, true, true, '2016-08-08 00:00:00');
insert into nikita_user (id, username, email, password, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (3, 'casehandler', 'case_handler@example.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', true, true, true, true, '2016-08-08 00:00:00');
insert into nikita_user (id, username, email, password, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (4, 'leader', 'leader@example.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', true, true, true, true, '2016-08-08 00:00:00');
insert into nikita_user (id, username, email, password, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (5, 'guest', 'guest@example.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', true, true, true, true, '2016-08-08 00:00:00');

INSERT INTO nikita_authority (id, authority_name) VALUES (1, 'RECORDS_MANAGER');
INSERT INTO nikita_authority (id, authority_name) VALUES (2, 'RECORDS_KEEPER');
INSERT INTO nikita_authority (id, authority_name) VALUES (3, 'CASE_HANDLER');
INSERT INTO nikita_authority (id, authority_name) VALUES (4, 'LEADER');
INSERT INTO nikita_authority (id, authority_name) VALUES (5, 'GUEST');

insert into nikita_user_authority(user_id, authority_id) values (1, 1);
insert into nikita_user_authority(user_id, authority_id) values (2, 2);
insert into nikita_user_authority(user_id, authority_id) values (3, 3);
insert into nikita_user_authority(user_id, authority_id) values (4, 4);
insert into nikita_user_authority(user_id, authority_id) values (5, 5);

insert into document_medium (pk_document_medium_id, system_id, code, description, deleted, version) values (0, '48e067ae-d794-4216-8a9d-7cb0ef03472c', 'Fysisk medium', 'Bare fysiske dokumenter', false, 0);
insert into document_medium (pk_document_medium_id, system_id, code, description, deleted, version) values (1, '1b617405-9dd6-4187-bee6-81ed4f4a0e1f', 'Elektronisk arkiv', 'Bare elektroniske dokumenter', false, 0);
insert into document_medium (pk_document_medium_id, system_id, code, deleted, version) values (2, '00d21eea-c231-4542-8e3a-964f441fbbd3', 'Blandet fysisk og elektronisk arkiv', false, 0);

insert into fonds_status(pk_fonds_status_id, code, deleted, version) values (0, 'Opprettet', false, 0);
insert into fonds_status(pk_fonds_status_id, code, deleted, version) values (1, 'Avsluttet', false, 0);