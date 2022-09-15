alter table flight
    drop constraint FK7_gate_id;

alter table gate
    drop constraint FK_airport_id;

drop table airport;

drop table flight;

drop table gate;

drop table hibernate_sequence;

DELETE
FROM
	airport_gate.flyway_schema_history
WHERE
	version = 1;
