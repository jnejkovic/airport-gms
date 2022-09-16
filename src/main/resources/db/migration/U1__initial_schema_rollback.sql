ALTER TABLE flight
  DROP CONSTRAINT FK_flight_gate_id;

ALTER TABLE gate
  DROP CONSTRAINT FK_gate_airport_id;

DROP TABLE airport;

DROP TABLE flight;

DROP TABLE gate;

DROP TABLE hibernate_sequence;

DELETE FROM airport_gate.flyway_schema_history
WHERE  version = 1;
