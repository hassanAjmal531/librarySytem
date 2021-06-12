select * from employee;
insert into employee (id, fname, lname, contact, address) values (1,'hassan','ajmal','123','abc');
insert into employee (id, fname, lname, contact, address) values (2,'a1','ajmal','123','abc');
insert into employee (id, fname, lname, contact, address) values (3,'a2','ajmal','123','abc');
insert into employee (id, fname, lname, contact, address) values (4,'a3','ajmal','123','abc');

insert into emplogin (password, employee_id) values ('abc',1);
insert into emplogin (password, employee_id) values ('abc',2);
insert into emplogin (password, employee_id) values ('abc',3);
insert into emplogin (password, employee_id) values ('abc',4);

insert into roles (role, employee_id) values ('admin',1);
insert into roles (role, employee_id) values ('librarian',2);
insert into roles (role, employee_id) values ('admin',2);
insert into roles (role, employee_id) values ('staff',3);
alter session set "_ORACLE_SCRIPT"=true;

create user intro_user identified by mypassword;
grant connect to intro_user;
grant create session, grant any PRIVILEGE to intro_user;
grant unlimited TABLESPACE to intro_user;
grant create table to intro_user;



