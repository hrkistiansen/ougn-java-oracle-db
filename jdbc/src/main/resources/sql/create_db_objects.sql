create table airplane (
   id               number not null,
   name             varchar2(255),
   created          date,
   created_by       varchar2(255),
   last_updated     date,
   last_updated_by  varchar2(255),
   constraint airplane_pk primary key (id)
);

create sequence hibernate_sequence  increment by 1 cache 20 noorder nocycle ;
