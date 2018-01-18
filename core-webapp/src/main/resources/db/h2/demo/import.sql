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
