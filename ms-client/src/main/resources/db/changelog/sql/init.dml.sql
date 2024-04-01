-- liquibase formatted sql


-- changeset andresduran0205:inserts_table_person.0.3 context: dev,test

INSERT INTO clients.person (first_name, last_name, phone, address, gender, birth_date, identification_type, identification_number)
VALUES ('Jose', 'Lema', '098254785', 'Otavalo sn y principal', 'M', '1995-03-25', 'CC', '12345678901');

INSERT INTO clients.person (first_name, last_name, phone, address, gender, birth_date, identification_type, identification_number)
VALUES ('Marianela', 'Montalvo', '097548965', 'Amazonas y NNUU', 'M', '1994-02-25', 'CC', '12345678902');

INSERT INTO clients.person (first_name, last_name, phone, address, gender, birth_date, identification_type, identification_number)
VALUES ('Juan', 'Osorio', '098874587', '13 junio y Equinoccial', 'M', '1992-01-25', 'CC', '12345678903');


-- changeset andresduran0205:inserts_table_client.0.3 context: dev,test

INSERT INTO clients.client (person_id, password, status)
VALUES ((SELECT id FROM clients.person WHERE identification_number = '12345678901' LIMIT 1), MD5('1234'), true);

INSERT INTO clients.client (person_id, password, status)
VALUES ((SELECT id FROM clients.person WHERE identification_number = '12345678902' LIMIT 1), MD5('5678'), true);

INSERT INTO clients.client (person_id, password, status)
VALUES ((SELECT id FROM clients.person WHERE identification_number = '12345678903' LIMIT 1), MD5('1245'), true);