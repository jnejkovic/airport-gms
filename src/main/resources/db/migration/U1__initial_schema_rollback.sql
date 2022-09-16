ALTER TABLE flight
  DROP CONSTRAINT fk7_gate_id;

ALTER TABLE gate
  DROP CONSTRAINT fk_airport_id;

DROP TABLE airport;

DROP TABLE flight;

DROP TABLE gate;

DROP TABLE hibernate_sequence;

DELETE FROM airport_gate.flyway_schema_history
WHERE  version = 1;
