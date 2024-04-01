-- liquibase formatted sql


-- changeset andresduran0205:inserts_table_account.0.1 context: dev,test
INSERT INTO products.account (client_id, account_number, account_type, balance, status)
VALUES ((SELECT cl.id
         FROM clients.client cl
                  LEFT JOIN clients.person p ON (cl.person_id = p.id)
         WHERE identification_number = '12345678901'
         LIMIT 1), 478758, 'AHORRO', 2000, TRUE);

INSERT INTO products.account (client_id, account_number, account_type, balance, status)
VALUES ((SELECT cl.id
         FROM clients.client cl
                  LEFT JOIN clients.person p ON (cl.person_id = p.id)
         WHERE identification_number = '12345678902'
         LIMIT 1), 225487, 'CORRIENTE', 100, TRUE);

INSERT INTO products.account (client_id, account_number, account_type, balance, status)
VALUES ((SELECT cl.id
         FROM clients.client cl
                  LEFT JOIN clients.person p ON (cl.person_id = p.id)
         WHERE identification_number = '12345678903'
         LIMIT 1), 495878, 'AHORRO', 0, TRUE);

INSERT INTO products.account (client_id, account_number, account_type, balance, status)
VALUES ((SELECT cl.id
         FROM clients.client cl
                  LEFT JOIN clients.person p ON (cl.person_id = p.id)
         WHERE identification_number = '12345678902'
         LIMIT 1), 496825, 'AHORRO', 540, TRUE);
