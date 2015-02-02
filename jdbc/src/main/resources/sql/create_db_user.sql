drop user hibtest cascade;

create user hibtest identified by hibtest default tablespace users temporary tablespace temp quota unlimited on users;

grant create any context to hibtest;
grant create cluster to hibtest;
grant create database link to hibtest;
grant create dimension to hibtest;
grant create indextype to hibtest;
grant create job to hibtest;
grant create materialized view to hibtest;
grant create operator to hibtest;
grant create procedure to hibtest;
grant create sequence to hibtest;
grant create synonym to hibtest;
grant create table to hibtest;
grant create trigger to hibtest;
grant create type to hibtest;
grant create view to hibtest;
grant debug any procedure to hibtest;
grant debug connect session to hibtest;
grant select any dictionary to hibtest;
grant connect to hibtest;
alter user hibtest default role all;