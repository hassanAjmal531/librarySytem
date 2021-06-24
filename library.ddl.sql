
CREATE TABLE Author (
    author VARCHAR2(255) NOT NULL
);

CREATE TABLE book (
    isbn        INTEGER NOT NULL,
    title       VARCHAR2(255) NOT NULL,
    language    VARCHAR2(255) NOT NULL,
    quantity    VARCHAR2 (255) NOT NULL

     
  
);

ALTER TABLE book ADD CONSTRAINT book_pk PRIMARY KEY ( isbn );

CREATE TABLE category (
    id    INTEGER NOT NULL,
    Cname  VARCHAR2(255) NOT NULL
);

ALTER TABLE category ADD CONSTRAINT category_pk PRIMARY KEY ( id );

CREATE TABLE emplogin (
    password     VARCHAR2(255) NOT NULL,
    employee_id  INTEGER NOT NULL
);

CREATE UNIQUE INDEX emplogin__idx ON
    emplogin (
        employee_id
    ASC );

CREATE TABLE employee (
    id       INTEGER NOT NULL,
    fname    VARCHAR2(255) NOT NULL,
    lname    VARCHAR2(255) NOT NULL,
    contact  VARCHAR2(255) NOT NULL,
    address  VARCHAR2 (255)
--  ERROR: VARCHAR2 size not specified 
     NOT NULL
);

ALTER TABLE employee ADD CONSTRAINT employee_pk PRIMARY KEY ( id );

CREATE TABLE faculty (
    id          INTEGER NOT NULL,
    fid_1       INTEGER NOT NULL,
    department  VARCHAR2 (255)
--  ERROR: VARCHAR2 size not specified 
     NOT NULL
);

ALTER TABLE faculty ADD CONSTRAINT faculty_pk PRIMARY KEY ( id );

ALTER TABLE faculty ADD CONSTRAINT faculty_pkv1 UNIQUE ( fid_1 );

create table author(
    id int not null PRIMARY key,
    name varchar2(255) not null
);

create table hasAuthor(
    book_isbn int not null,
    title varchar2(255) not null,
    author_id int not null
);
ALTER TABLE hasAuthor ADD CONSTRAINT hasAuthor_fk foreign KEY ( book_isbn, title) references book (isbn, title);


CREATE TABLE history (
    title       VARCHAR2(255) NOT NULL,
    issuedate   DATE NOT NULL,
    returndate  DATE NOT NULL,
    member_id   INTEGER NOT NULL
);

CREATE UNIQUE INDEX history__idx ON
    history (
        member_id
    ASC );
    
CREATE TABLE member (
    id               INTEGER NOT NULL,
    fname            VARCHAR2(255),
    lname            VARCHAR2(255) NOT NULL,
    email            VARCHAR2(255) NOT NULL,
    contact_address  VARCHAR2(255) NOT NULL,
    address          VARCHAR2(255) NOT NULL
);

ALTER TABLE member ADD CONSTRAINT member_pk PRIMARY KEY ( id );

CREATE TABLE mlogin (
    password   VARCHAR2(255) NOT NULL,
    member_id  INTEGER NOT NULL
);

CREATE UNIQUE INDEX mlogin__idx ON
    mlogin (
        member_id
    ASC );

CREATE TABLE news (
    id    INTEGER NOT NULL,
    text  VARCHAR2(255) NOT NULL
);

ALTER TABLE news ADD CONSTRAINT news_pk PRIMARY KEY ( id );

create table publisher(
    book_isbn int not null,
    name varchar(255) not null
);


 ALTER TABLE publisher ADD CONSTRAINT publisher_fk FOREIGN KEY ( book_isbn ) REFERENCES book (isbn); 
 
 
 CREATE TABLE roles (
    role         VARCHAR2(255) NOT NULL,
    employee_id  INTEGER NOT NULL
);

CREATE TABLE student (
    id        INTEGER NOT NULL,
    rnum      INTEGER NOT NULL,
    degree    VARCHAR2(255) NOT NULL,
    semester  VARCHAR2(10) NOT NULL
);
create table hasCategory(
    
    bookid int not null,
    booktitle varchar2(255) not null,
    cid int not null 

);
ALTER TABLE categoryhasbooks ADD CONSTRAINT categoryhasbooks_fk foreign KEY (id) references category(id);

ALTER TABLE hascategory ADD CONSTRAINT hascategroy_fk foreign KEY (bookid, booktitle) references book (isbn,title);

ALTER TABLE student ADD CONSTRAINT student_pk PRIMARY KEY ( id );

ALTER TABLE student ADD CONSTRAINT student_pkv1 UNIQUE ( rnum );

ALTER TABLE book
    ADD CONSTRAINT book_member_fk FOREIGN KEY ( member_id )
        REFERENCES member ( id );

ALTER TABLE emplogin
    ADD CONSTRAINT emplogin_employee_fk FOREIGN KEY ( employee_id )
        REFERENCES employee ( id );

ALTER TABLE faculty
    ADD CONSTRAINT faculty_member_fk FOREIGN KEY ( id )
        REFERENCES member ( id );



ALTER TABLE has_author
    ADD CONSTRAINT has_author_author_fk FOREIGN KEY ( author_id )
        REFERENCES author ( id );

ALTER TABLE has_author
    ADD CONSTRAINT has_author_book_fk FOREIGN KEY ( book_isbn )
        REFERENCES book ( isbn );



ALTER TABLE history
    ADD CONSTRAINT history_member_fk FOREIGN KEY ( member_id )
        REFERENCES member ( id );

ALTER TABLE mlogin
    ADD CONSTRAINT mlogin_member_fk FOREIGN KEY ( member_id )
        REFERENCES member ( id );

ALTER TABLE roles
    ADD CONSTRAINT roles_employee_fk FOREIGN KEY ( employee_id )
        REFERENCES employee ( id );

ALTER TABLE student
    ADD CONSTRAINT student_member_fk FOREIGN KEY ( id )
        REFERENCES member ( id );
        
        
        
-- data insertions


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

insert into member (id, fname,lname, email, contact_address, address) values(1,'usama','ajmal', 'a@gmail','1234456','ajsjfd' );
insert into member (id, fname,lname, email, contact_address, address) values(2,'u1','ajmal', 'a@gmail','6','ajjfd' );
insert into member (id, fname,lname, email, contact_address, address,fine) values(3,'u2','ajmal', 'a@gmail','126','sdjfd',0 );
insert into member (id, fname,lname, email, contact_address, address, fine) values(4,'u3','ajmal', 'a@gmail','16','sdjfd',0 );

insert into mlogin (password, member_id) values ('abc',1);
insert into mlogin (password, member_id) values ('abc',2);
insert into mlogin (password, member_id) values ('abc',3);
insert into mlogin (password, member_id) values ('abc',4);

insert into student (id, rnum, degree, semester) values(1, 1,'bcs','1');
insert into student (id, rnum, degree, semester) values(2, 2,'bcs','4');

insert into faculty (id, fid_1, department) values (4, 1,'computer science');
insert into faculty (id, fid_1, department) values (3, 2,'computer science');


insert into book (isbn, title, language, quantity) VALUES(1,'harry potter v1', 'ennglish','1');
insert into book (isbn, title, language, quantity) VALUES(2,'harry potter v2', 'ennglish','1');
insert into book (isbn, title, language, quantity) VALUES(3,'harry potter v3', 'ennglish','1');
insert into book (isbn, title, language, quantity) VALUES(4,'harry potter v4', 'ennglish','1');
insert into book (isbn, title, language, quantity) VALUES(6,'art of knife making', 'ennglish','1');

insert into publisher (book_isbn,name) values (1,'p2');
insert into publisher (book_isbn,name) values (2,'p3');
insert into publisher (book_isbn,name) values (3,'p4');
insert into publisher (book_isbn,name) values (4,'p3');

insert into author (id,name) values(1,'noman');
insert into author (id,name) values(2,'a1');
insert into author (id,name) values(3,'a2');
insert into author (id,name) values(4,'a2');

insert into hasAuthor (book_isbn, author_id) values (1,1);
insert into hasAuthor (book_isbn, author_id) values (4,2);
insert into hasAuthor (book_isbn, author_id) values (2,1);
insert into hasAuthor (book_isbn, author_id) values (3,4);


insert into hascategory (bookid, booktitle, cid) values (1,'harry potter v1', 1);
insert into hascategory (bookid, booktitle, cid) values (2,'harry potter v2', 2);
insert into hascategory (bookid, booktitle, cid) values (3,'harry potter v3', 1);
insert into hascategory (bookid, booktitle, cid) values (4,'harry potter v4', 1);
insert into hascategory (bookid, booktitle, cid) values (4,'harry potter v4', 2);
        
        