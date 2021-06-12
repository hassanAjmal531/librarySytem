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

select * from roles;


select employee.id, employee.fname, emplogin.password from employee, emplogin,roles where employee.id = 1 and emplogin.employee_id = 1 and roles.employee_id =1 and roles.role = 'admin';

select * from emplogin;
select * from employee;
select * from roles;

insert into member (id, fname,lname, email, contact_address, address) values(1,'usama','ajmal', 'a@gmail','1234456','ajsjfd' );
insert into member (id, fname,lname, email, contact_address, address) values(2,'u1','ajmal', 'a@gmail','6','ajjfd' );
insert into member (id, fname,lname, email, contact_address, address,fine) values(3,'u2','ajmal', 'a@gmail','126','sdjfd',0 );
insert into member (id, fname,lname, email, contact_address, address, fine) values(4,'u3','ajmal', 'a@gmail','16','sdjfd',0 );
select * from member;
select * from mlogin;
insert into mlogin (password, member_id) values ('abc',1);
insert into mlogin (password, member_id) values ('abc',2);
insert into mlogin (password, member_id) values ('abc',3);
insert into mlogin (password, member_id) values ('abc',4);
select * from student;

select * from fine;

insert into fine (amount, member_id) values (1000,1);


insert into student (id, rnum, degree, semester) values(1, 1,'bcs','1');
insert into student (id, rnum, degree, semester) values(4, 2,'bcs','4');

insert into faculty (id, fid_1, department) values (4, 1,'computer science');
insert into faculty (id, fid_1, department) values (3, 2,'computer science');
select * from faculty;

select * from member, student where member.id = student.id;
select * from member, faculty where member.id = faculty.id;

select * from roles;

update roles set role = 'staff' where employee_id = 2 and role = 'admin';

select * from book;

insert into book (isbn, title, language, quantity) VALUES(1,'harry potter v1', 'ennglish','1');
insert into book (isbn, title, language, quantity) VALUES(2,'harry potter v2', 'ennglish','1');
insert into book (isbn, title, language, quantity) VALUES(3,'harry potter v3', 'ennglish','1');
insert into book (isbn, title, language, quantity) VALUES(4,'harry potter v4', 'ennglish','1');


alter table book drop COLUMN returndate;

select * from haspublisher;
insert into haspublisher (book_isbn, publisher_name) values (1,'abc');

insert into publisher (book_isbn, pid,name) values (1,5,'p2');
insert into publisher (book_isbn, pid,name) values (3,3,'p3');
insert into publisher (book_isbn, pid,name) values (4,4,'p4');


select * from publisher;

select * from member;
select *from member, student where member.id = 1 and member.id = student.id;
select * from member , faculty where member.id = 2 and member.id = faculty.id;
select * from author;
insert into author (id,name) values(1,'noman');
insert into author (id,name) values(2,'a1');
insert into author (id,name) values(3,'a2');
insert into author (id,name) values(4,'a2');

select * from hasAuthor;
insert into hasAuthor (book_isbn, author_id) values (1,1);
insert into hasAuthor (book_isbn, author_id) values (1,2);
insert into hasAuthor (book_isbn, author_id) values (2,1);
insert into hasAuthor (book_isbn, author_id) values (3,4);

select * from author;
select * from roles;
drop table author;
drop table has_author;

create table author(
    id int not null PRIMARY key,
    name varchar2(255) not null
);
drop table hasAuthor;
create table hasAuthor(
    book_isbn int not null,
    title varchar2(255) not null,
    author_id int not null
);
ALTER TABLE hasAuthor ADD CONSTRAINT hasAuthor_fk foreign KEY ( book_isbn, title) references book (isbn, title);
drop table publisher;
drop table hasPublisher;

create table publisher(
    book_isbn int not null,
    pid int not null,
    name varchar(255) not null
);

 ALTER TABLE publisher ADD CONSTRAINT publisher_pk PRIMARY KEY ( pid );
 ALTER TABLE publisher ADD CONSTRAINT publisher_fk FOREIGN KEY ( book_isbn ) REFERENCES book (isbn);                                                                 
 select * from publisher;
 
 insert into publisher (book_isbn, pid, name) values (1,1,'printers');


select book.isbn, book.title,book. language, book.quantity, publisher.name as publisherName from book, publisher where book.isbn = publisher.book_isbn;

select author_id from hasauthor where book_isbn = 1;
select hasauthor.author_id from hasAuthor where hasAuthor.book_isbn = 1;
select * from book cross join publisher cross join author;
select * from book cross join publisher cross join author where book.isbn =1 and book.isbn = publisher.book_isbn  and author.id in (select hasauthor.author_id from hasAuthor where book.isbn =1);
select * from (select * from book, publisher where book.isbn =3 and publisher.book_isbn = book.isbn) cross join (select * from author where author.id in (select hasauthor.author_id from hasAuthor
where hasauthor.book_isbn =3));
select * from author where author.id in (select hasauthor.author_id from hasAuthor
where hasauthor.book_isbn =3);
select * from book;
select * from hasAuthor;
select * from author;
drop table hasbooks;

create table hasbooks(
    book_isbnn int not null,
    author_idd int not null
);

ALTER TABLE hasbooks ADD CONSTRAINT hasbooks_fk foreign KEY ( author_idd) references author (id);

insert into hasbooks (book_isbnn, author_idd) values (1,1);
insert into hasbooks (book_isbnn, author_idd) values (1,2);
insert into hasbooks (book_isbnn, author_idd) values (2,1);
insert into hasbooks (book_isbnn, author_idd) values (3,4);

select * from book where book.isbn in (select hasbooks.book_isbnn from hasbooks where hasbooks.author_idd = 1);

drop table has_author;
drop table haspublisher;
drop table publisher;
drop table book;

alter table member add fine int not null;

select member.id, member.fname, member.lname, member.email, member.contact_address, member.address, faculty.fid_1, faculty.department, mlogin.password From member, faculty, mlogin where member.id = faculty.id and member.id = mlogin.medrop;
drop table author;


select * from book;
select * from publisher;
select * from hasauthor;

select * from category;


drop table hascategory;
create table hasCategory(
    
    bookid int not null,
    booktitle varchar2(255) not null,
    cid int not null 

);

create table categoryhasbooks(
    id int not null,
    bookid int not null
);

drop table categoryhasbooks;
ALTER TABLE categoryhasbooks ADD CONSTRAINT categoryhasbooks_fk foreign KEY (id) references category(id);

ALTER TABLE hascategory ADD CONSTRAINT hascategroy_fk foreign KEY (bookid, booktitle) references book (isbn,title);


insert into category(id, cname) values (1,'arts');
insert into category(id, cname) values (2,'fiction');
insert into category(id, cname) values (3,'comic');
insert into category(id, cname) values (4,'science');

select * from category;
select * from hascategory;

select * from book;

insert into hascategory (bookid, booktitle, cid) values (1,'harry potter v1', 1);
insert into hascategory (bookid, booktitle, cid) values (2,'harry potter v2', 2);
insert into hascategory (bookid, booktitle, cid) values (3,'harry potter v3', 1);
insert into hascategory (bookid, booktitle, cid) values (4,'harry potter v4', 1);
insert into hascategory (bookid, booktitle, cid) values (4,'harry potter v4', 2);


insert into categoryhasbooks(id, bookid) values (1,1);
insert into categoryhasbooks(id, bookid) values (2,2);
insert into categoryhasbooks(id, bookid) values (1,3);
insert into categoryhasbooks(id, bookid) values (1,4);

select * from hasauthor;
select * from author;
insert into hasauthor (book_isbn, title, author_id) values (1,'harry potter v1',1);
insert into hasauthor (book_isbn, title, author_id) values (2,'harry potter v2',2);
insert into hasauthor (book_isbn, title, author_id) values (3,'harry potter v3',4);
insert into hasauthor (book_isbn, title, author_id) values (2,'harry potter v2',3);


select  listagg(author.name, ', ') from author where author.id in (select hasauthor.author_id from hasauthor, book where book.isbn = hasauthor.book_isbn  and book.isbn = 2) ;
select listagg(category.cname,', ') from category where category.id in (select hascategory.cid from hascategory, book where book.isbn = hascategory.bookid and book.isbn = 2);


select book.isbn, book.title, book.language, book.quantity, publisher.name from book, publisher where  book.isbn = publisher.book_isbn and book.isbn =1 ;

(select hasauthor.author_id from hasauthor, book where book.isbn = hasauthor.book_isbn);
select book_isbn, listagg(author_id,',') within group (order by author_id) as authors from book, hasauthor where book_isbn =book.isbn  group by book_isbn; 
select book.isbn, book.title, book.language, book.quantity, publisher.name from book, publisher where  book.isbn = publisher.book_isbn and book.title = 'harry potter v1' ;
select book.isbn, book.title, book.language, book.quantity, publisher.name from book, publisher where  book.isbn = publisher.book_isbn and book.isbn in (select *)

select * from publisher;



COMMIT;


