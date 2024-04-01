-- liquibase formatted sql

-- changeset andresduran0205:create_schema.0.1 context: dev,test,prod
CREATE SCHEMA IF NOT EXISTS clients;


-- changeset andresduran0205:drop_tables_schema_clients.0.4 context: dev,test,prod
DROP TABLE IF EXISTS clients.client;
DROP TABLE IF EXISTS clients.person;


-- changeset andresduran0205:create_table_person.0.4 context: dev,test,prod

CREATE TABLE IF NOT EXISTS clients.person
(
    id                    UUID                     NOT NULL DEFAULT gen_random_uuid(),
    first_name            VARCHAR(255)             NOT NULL,
    last_name             VARCHAR(255)             NOT NULL,
    phone                 NUMERIC                  NOT NULL,
    address               VARCHAR                  NOT NULL,
    gender                CHAR                     NOT NULL,
    birth_date            DATE                     NOT NULL,
    identification_number VARCHAR                  NOT NULL,
    identification_type   VARCHAR                  NOT NULL,
    updated_at            TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT person_pkey PRIMARY KEY (id),
    CONSTRAINT chk_genere CHECK (gender = 'M' OR gender = 'F'),
    CONSTRAINT chk_identification_type CHECK (identification_type = 'CC' OR identification_type = 'TI' OR identification_type = 'NIT')
);


-- changeset andresduran0205:create_table_client.0.4 context: dev,test,prod
CREATE TABLE IF NOT EXISTS clients.client
(
    id         UUID                     NOT NULL DEFAULT gen_random_uuid(),
    person_id  UUID                     NOT NULL,
    password   TEXT,
    status     BOOLEAN                  NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT client_pkey PRIMARY KEY (id),
    CONSTRAINT person_fkey FOREIGN KEY (person_id) REFERENCES clients.person (id)
);
