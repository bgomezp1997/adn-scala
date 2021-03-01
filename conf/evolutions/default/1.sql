# adnscala schema

# --- !Ups

create table persona (
    identification numeric(20,0) not null,
    name varchar(70) not null,
    last_name varchar(70) not null,
    age numeric(3,0) not null,
    date_birth date not null,
    primary key(identification)
);

# --- !Downs

drop table persona;
