// Create Noark 5 core users for the test run

insert into User (id, username, email, password_hash, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (1, 'case', 'case_handler@nikita.no', 'password', true, true, true, true, '2016-08-08 00:00:00');
insert into User (id, username, email, password_hash, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (2, 'rm', 'rm@nikita.no', 'password', true, true, true, true, '2016-08-08 00:00:00');
insert into User (id, username, email, password_hash, account_non_locked, credentials_non_expired, account_non_expired, enabled, account_created_date) values (3, 'admin', 'admin@nikita.no', 'password', true, true, true, true, '2016-08-08 00:00:00');

// Create groups / (spring) authorities
insert into Authority (name) values ('ADMIN');
insert into Authority (name) values ('CASE_HANDLER');
insert into Authority (name) values ('RECORDS_MANAGER');

// Assign users to a group
insert into User_authority(user_id, authority_name) values (1, 'CASE_HANDLER');
insert into User_authority(user_id, authority_name) values (2, 'RECORDS_MANAGER');
insert into User_authority(user_id, authority_name) values (3, 'ADMIN');
