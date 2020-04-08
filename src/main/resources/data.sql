INSERT INTO User(firstname, lastname, phonenumber, email, encrypted_Password, enabled) VALUES ('Simon', 'Hood', '1234567890', 'simon@gmail.com', '$2a$10$1ltibqiyyBJMJQ4hqM7f0OusP6np/IHshkYc4TjedwHnwwNChQZCy', 1);
INSERT INTO User(firstname, lastname, phonenumber, email, encrypted_Password, enabled) VALUES ('Jon', 'Wick', '1234567899','jon@gmail.com','$2a$10$1ltibqiyyBJMJQ4hqM7f0OusP6np/IHshkYc4TjedwHnwwNChQZCy', 1);

INSERT INTO Role(rolename) VALUES('ROLE_ADMIN');
INSERT INTO Role(rolename) VALUES('ROLE_USER');

INSERT INTO User_Roles(users_id, roles_id) VALUES(1, 1);
INSERT INTO User_Roles(users_id, roles_id) VALUES(1, 2);
INSERT INTO User_Roles(users_id, roles_id) VALUES(2, 2);

insert into Timeslot (flag, timeselect) values (true, '10:00 AM');
insert into Timeslot (flag, timeselect) values (true, '11:00 AM');
insert into Timeslot (flag, timeselect) values (true, '12:00 PM');
insert into Timeslot (flag, timeselect) values (true, '01:00 PM');
insert into Timeslot (flag, timeselect) values (true, '02:00 PM');
insert into Timeslot (flag, timeselect) values (true, '03:00 PM');
insert into Timeslot (flag, timeselect) values (true, '04:00 PM');
insert into Timeslot (flag, timeselect) values (true, '05:00 PM');
insert into Timeslot (flag, timeselect) values (true, '06:00 PM');