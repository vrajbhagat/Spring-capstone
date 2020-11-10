--INSERT INTO User(firstname, lastname, phonenumber, email, encrypted_Password, is_Enabled) VALUES ('Jason', 'Wong', '6476097028', 'Juicepetgroomin@gmail.com', '$2a$10$DD/FQ0hTIprg3fGarZl1reK1f7tzgM4RuFKjAKyun0Si60w6g3v5i', true);
--
--INSERT INTO Role(rolename) VALUES('ROLE_ADMIN');
--INSERT INTO Role(rolename) VALUES('ROLE_USER');
--
--INSERT INTO User_Roles(users_id, roles_id) VALUES(1, 1);
--
--insert into Timeslot (flag, timeselect) values (true, '10:00 AM');
--insert into Timeslot (flag, timeselect) values (true, '11:00 AM');
--insert into Timeslot (flag, timeselect) values (true, '12:00 PM');
--insert into Timeslot (flag, timeselect) values (true, '01:00 PM');
--insert into Timeslot (flag, timeselect) values (true, '02:00 PM');
--insert into Timeslot (flag, timeselect) values (true, '03:00 PM');
--insert into Timeslot (flag, timeselect) values (true, '04:00 PM');
--insert into Timeslot (flag, timeselect) values (true, '05:00 PM');
--insert into Timeslot (flag, timeselect) values (true, '06:00 PM');


INSERT INTO User(firstname, lastname, phonenumber, email, encrypted_Password, is_Enabled) VALUES ('Jason', 'Wong', '6476097028', 'jason@gmail.com', '$2a$10$DD/FQ0hTIprg3fGarZl1reK1f7tzgM4RuFKjAKyun0Si60w6g3v5i', true);
INSERT INTO User(firstname, lastname, phonenumber, email, encrypted_Password, is_Enabled) VALUES ('Jainil', 'Parikh', '1234567899','parikhja@sheridancollege.ca','$2a$10$DD/FQ0hTIprg3fGarZl1reK1f7tzgM4RuFKjAKyun0Si60w6g3v5i', true);
INSERT INTO User(firstname, lastname, phonenumber, email, encrypted_Password, is_Enabled) VALUES ('Vraj', 'Bhagat', '1234567899','vrajbhagat1@gmail.com','$2a$10$DD/FQ0hTIprg3fGarZl1reK1f7tzgM4RuFKjAKyun0Si60w6g3v5i', true);

INSERT INTO Role(rolename) VALUES('ROLE_ADMIN');
INSERT INTO Role(rolename) VALUES('ROLE_USER');

INSERT INTO User_Roles(users_id, roles_id) VALUES(1, 1);
INSERT INTO User_Roles(users_id, roles_id) VALUES(2, 2);
INSERT INTO User_Roles(users_id, roles_id) VALUES(3, 2);





