    drop table if exists airport;

    drop table if exists flight;

    drop table if exists gate;

    drop table if exists hibernate_sequence;

    create table airport (
       airport_id integer not null,
        airport_name varchar(255) not null,
        version integer,
        primary key (airport_id)
    ) engine=InnoDB;

    create table flight (
       flight_id integer not null,
        flight_index varchar(255) not null,
        version integer,
        gate integer,
        primary key (flight_id)
    ) engine=InnoDB;

    create table gate (
       gate_id integer not null,
        gate_name varchar(3) not null,
        status varchar(255) not null,
        version integer,
        airport integer,
        primary key (gate_id)
    ) engine=InnoDB;

    create table hibernate_sequence (
       next_val bigint
    ) engine=InnoDB;

    insert into hibernate_sequence values ( 1 );

    alter table airport
       add constraint UK_airport_name unique (airport_name);

    alter table flight
       add constraint UK_flight_index unique (flight_index);

    alter table flight
       add constraint FK7_gate_id
       foreign key (gate)
       references gate (gate_id);

    alter table gate
       add constraint FK_airport_id
       foreign key (airport)
       references airport (airport_id);
