# adnscala schema

# --- !Ups

create table eps
(
    nit   varchar(10) not null,
    name  varchar(70) not null,
    phone varchar(20) not null,
    email varchar(50) not null,
    primary key (nit)
);

create table doctor
(
    identification           numeric(20, 0) not null,
    name                     varchar(70)    not null,
    last_name                varchar(70)    not null,
    creation_date            date           not null,
    email                    varchar(50)    not null,
    specialty                varchar(70)    not null,
    professional_card_number varchar(20)    not null,
    primary key (identification)
);

create table patient
(
    identification numeric(20, 0) not null,
    name           varchar(70)    not null,
    last_name      varchar(70)    not null,
    creation_date  date           not null,
    email          varchar(50)    not null,
    nit_eps        varchar(10)    not null,
    stratum        numeric(3, 0)  not null,
    primary key (identification),
    foreign key (nit_eps) references eps (nit)
);

create table date
(
    id                     int            not null auto_increment,
    appointment_date       date           not null,
    identification_patient numeric(20, 0) not null,
    identification_doctor  numeric(20, 0) not null,
    price                  double         not null,
    primary key (id),
    foreign key (identification_patient) references patient (identification),
    foreign key (identification_doctor) references doctor (identification)
);

create table parameter
(
    id    int          not null auto_increment,
    name  varchar(100) not null,
    value varchar(100) not null,
    type  varchar(50)  not null,
    primary key (id)
);

insert into parameter(name, value, type) values('CANTIDAD_CITAS_DIA', '5', 'GENERAL');
insert into parameter(name, value, type) values('DSCTO_ESTRATO_BAJO', '35', 'GENERAL');
insert into parameter(name, value, type) values('DSCTO_ESTRATO_MEDIO', '20', 'GENERAL');
insert into parameter(name, value, type) values('DSCTO_ESTRATO_ALTO', '10', 'GENERAL');

insert into parameter(name, value, type) values('NAVIDAD', '2020-12-24', 'FESTIVO');
insert into parameter(name, value, type) values('ANO_NUEVO', '2020-12-31', 'FESTIVO');


insert into parameter(name, value, type) values('CORNEOLOGO', '190000', 'ESPECIALIDAD');
insert into parameter(name, value, type) values('RETINOLOGO', '150000', 'ESPECIALIDAD');
insert into parameter(name, value, type) values('GLAUCOMATOLOGO', '210000', 'ESPECIALIDAD');
insert into parameter(name, value, type) values('OCULOPLASTICO', '230000', 'ESPECIALIDAD');
insert into parameter(name, value, type) values('NO', '130000', 'ESPECIALIDAD');

insert into eps(nit, name, phone, email) values('1', 'Sanitas', '7441111', 'sanitas@mail.com');

# --- !Downs
