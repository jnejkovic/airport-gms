CREATE TABLE airport
  (
     airport_id   INTEGER NOT NULL,
     airport_name VARCHAR(255) NOT NULL,
     version      INTEGER,
     PRIMARY KEY (airport_id),
     CONSTRAINT uk_airport_name UNIQUE (airport_name)
  );

CREATE TABLE gate
  (
     gate_id   INTEGER NOT NULL,
     gate_name VARCHAR(3) NOT NULL,
     status    VARCHAR(255) NOT NULL,
     version   INTEGER,
     airport   INTEGER,
     PRIMARY KEY (gate_id),
     CONSTRAINT fk_airport_id FOREIGN KEY (airport) REFERENCES airport (
     airport_id)
  );

CREATE TABLE flight
  (
     flight_id    INTEGER NOT NULL,
     flight_index VARCHAR(255) NOT NULL,
     version      INTEGER,
     gate         INTEGER,
     PRIMARY KEY (flight_id),
     CONSTRAINT fk7_gate_id FOREIGN KEY (gate) REFERENCES gate (gate_id),
     CONSTRAINT uk_flight_index UNIQUE (flight_index)
  );

CREATE TABLE hibernate_sequence
  (
     next_val BIGINT
  );

INSERT INTO hibernate_sequence
VALUES      (1);
