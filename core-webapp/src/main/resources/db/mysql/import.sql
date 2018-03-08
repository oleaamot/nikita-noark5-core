INSERT INTO nikita_user (id, username, email, password, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date)
VALUES
  (1, 'admin', 'admin@example.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', TRUE, TRUE, TRUE,
   TRUE, '2016-08-08 00:00:00');
INSERT INTO nikita_user (id, username, email, password, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date)
VALUES (2, 'recordkeeper', 'rm@example.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', TRUE, TRUE,
        TRUE, TRUE, '2016-08-08 00:00:00');
INSERT INTO nikita_user (id, username, email, password, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date)
VALUES
  (3, 'casehandler', 'case_handler@example.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', TRUE,
   TRUE, TRUE, TRUE, '2016-08-08 00:00:00');
INSERT INTO nikita_user (id, username, email, password, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date)
VALUES
  (4, 'leader', 'leader@example.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', TRUE, TRUE, TRUE,
   TRUE, '2016-08-08 00:00:00');
INSERT INTO nikita_user (id, username, email, password, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date)
VALUES
  (5, 'guest', 'guest@example.com', '$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC', TRUE, TRUE, TRUE,
   TRUE, '2016-08-08 00:00:00');

INSERT INTO nikita_authority (id, authority_name) VALUES (1, 'RECORDS_MANAGER');
INSERT INTO nikita_authority (id, authority_name) VALUES (2, 'RECORDS_KEEPER');
INSERT INTO nikita_authority (id, authority_name) VALUES (3, 'CASE_HANDLER');
INSERT INTO nikita_authority (id, authority_name) VALUES (5, 'GUEST');

INSERT INTO nikita_user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO nikita_user_authority (user_id, authority_id) VALUES (2, 2);
INSERT INTO nikita_user_authority (user_id, authority_id) VALUES (3, 3);
INSERT INTO nikita_user_authority (user_id, authority_id) VALUES (4, 4);
INSERT INTO nikita_user_authority (user_id, authority_id) VALUES (5, 5);
