-- liquibase formatted sql

-- changeset andresduran0205:create_products_schema.0.1 context: dev,test,prod
CREATE SCHEMA IF NOT EXISTS products;

-- changeset andresduran0205:drop_tables_schema_products.0.1 context: dev,test
DROP TABLE IF EXISTS products.transaction_account;
DROP TABLE IF EXISTS products.account;


-- changeset andresduran0205:create_table_account.0.1 context: dev,test
CREATE TABLE IF NOT EXISTS products.account
(
    id             UUID                     NOT NULL DEFAULT gen_random_uuid(),
    client_id      UUID                     NOT NULL,
    account_number NUMERIC                  NOT NULL,
    account_type   VARCHAR(12)              NOT NULL,
    balance        NUMERIC                  NOT NULL,
    status         BOOLEAN                  NOT NULL,
    created_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT account_pkey PRIMARY KEY (id),
    CONSTRAINT client_fkey FOREIGN KEY (client_id) REFERENCES clients.client (id),
    CONSTRAINT chk_account_type CHECK (account_type = 'AHORRO' OR account_type = 'CORRIENTE'),
    CONSTRAINT unique_account_number UNIQUE (account_number, account_type)
);


-- changeset andresduran0205:create_table_transaction_account.0.1 context: dev,test
CREATE TABLE IF NOT EXISTS products.transaction_account
(
    id               UUID                     NOT NULL DEFAULT gen_random_uuid(),
    account_id       UUID                     NOT NULL,
    transaction_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    transaction_type VARCHAR(12)              NOT NULL,
    amount           NUMERIC                  NOT NULL,
    balance          NUMERIC                  NOT NULL,
    CONSTRAINT client_pkey PRIMARY KEY (id),
    CONSTRAINT chk_genere CHECK (transaction_type = 'RETIRO' OR transaction_type = 'DEPOSITO')
);

-- changeset andresduran0205:create_idx_transaction_account.0.1 context: dev,test
CREATE UNIQUE INDEX account_id_idx ON products.transaction_account (account_id);
