CREATE OR REPLACE FUNCTION createDB() RETURNS void AS $$
    DECLARE
    BEGIN
        RAISE NOTICE 'createDB -start';

        -- CREATE TABLES

        -- table contains customers/users of application
        CREATE TABLE customer (
          id integer,
          name text,
          PRIMARY KEY (id)
        );

        -- debt attached to customer
        CREATE TABLE product (
          id integer,
          customer_id integer NOT NULL,
          product_type_id integer NOT NULL,
          PRIMARY KEY (id)
        );

        --types of debts that customer can buy
        CREATE TABLE product_type (
          id integer,
          PRIMARY KEY (id)
        );

            --setting of debts that customer can buy
        CREATE TABLE product_type_setting (
          id integer,
          product_type_id integer NOT NULL,
          setting_type_id integer NOT NULL,
          value text,
          PRIMARY KEY (id),
          UNIQUE (product_type_id, setting_type_id)
        );

            --setting of debts that customer has bought
        CREATE TABLE IF NOT EXISTS setting_type (
          id integer,
          name text,
          PRIMARY KEY (id),
          UNIQUE(name)
        );

            -- describes details about product owned by customer
        CREATE TABLE IF NOT EXISTS product_setting (
          id integer,
          product_id integer NOT NULL,
          setting_type_id integer NOT NULL,
          value text,
          PRIMARY KEY (id),
          UNIQUE (product_id, setting_type_id)
        );

        -- CREATE FOREIGN KEYS

        --product
        ALTER TABLE product ADD CONSTRAINT fk_product_customer_id FOREIGN KEY(customer_id) REFERENCES customer(id);
        ALTER TABLE product ADD CONSTRAINT fk_product_product_type_id FOREIGN KEY (product_type_id) REFERENCES product_type(id);

        --product type setting
        ALTER TABLE product_type_setting ADD CONSTRAINT fk_product_type_setting_product_type_id FOREIGN KEY (product_type_id) REFERENCES product_type(id);
        ALTER TABLE product_type_setting ADD CONSTRAINT fk_product_type_setting_setting_type_id FOREIGN KEY (setting_type_id) REFERENCES setting_type(id);

        --prorduct_setting
        ALTER TABLE product_setting ADD CONSTRAINT fk_product_setting_product_id FOREIGN KEY (product_id) REFERENCES product(id);
        ALTER TABLE product_setting ADD CONSTRAINT fk_product_setting_setting_type_id FOREIGN KEY (setting_type_id) REFERENCES setting_type(id);

        RAISE NOTICE 'createDB -end';
    END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION insertTestData() RETURNS void AS $$
    DECLARE
    BEGIN
        RAISE NOTICE 'insertTestData - start';

        -- insert a customer
        INSERT INTO customer (id, name) VALUES (1, 'Johny');

        --insert a product
        INSERT INTO product_type (id) VALUES (1);

        --insert settings describing a product
        INSERT INTO setting_type (id, name) VALUES (1, 'min amount');
        INSERT INTO setting_type (id, name) VALUES (2, 'max amount');
        INSERT INTO setting_type (id, name) VALUES (3, 'min term');
        INSERT INTO setting_type (id, name) VALUES (4, 'max term');
        INSERT INTO setting_type (id, name) VALUES (5, 'min rejection hour');
        INSERT INTO setting_type (id, name) VALUES (6, 'max rejection hour');
        INSERT INTO setting_type (id, name) VALUES (7, 'loan type');
        INSERT INTO setting_type (id, name) VALUES (8, 'rate of interest');
        INSERT INTO setting_type (id, name) VALUES (9, 'due date');
        INSERT INTO setting_type (id, name) VALUES (10, 'extension term');
        INSERT INTO setting_type (id, name) VALUES (11, 'amount');
        INSERT INTO setting_type (id, name) VALUES (12, 'application date');


        --insert settings describing a debt with id = 1
        INSERT INTO product_type_setting (id, product_type_id, setting_type_id, value) VALUES (1, 1, 1, '1000');
        INSERT INTO product_type_setting (id, product_type_id, setting_type_id, value) VALUES (2, 1, 2, '5000');
        INSERT INTO product_type_setting (id, product_type_id, setting_type_id, value) VALUES (3, 1, 3, '3');
        INSERT INTO product_type_setting (id, product_type_id, setting_type_id, value) VALUES (4, 1, 4, '60');
        INSERT INTO product_type_setting (id, product_type_id, setting_type_id, value) VALUES (5, 1, 5, '00:00:00');
        INSERT INTO product_type_setting (id, product_type_id, setting_type_id, value) VALUES (6, 1, 6, '06:00:00');
        INSERT INTO product_type_setting (id, product_type_id, setting_type_id, value) VALUES (7, 1, 7, 'principal');
        INSERT INTO product_type_setting (id, product_type_id, setting_type_id, value) VALUES (8, 1, 8, '10');
        INSERT INTO product_type_setting (id, product_type_id, setting_type_id, value) VALUES (9, 1, 9, '');
        INSERT INTO product_type_setting (id, product_type_id, setting_type_id, value) VALUES (10, 1, 10, '7');
        INSERT INTO product_type_setting (id, product_type_id, setting_type_id, value) VALUES (11, 1, 11, '');
        INSERT INTO product_type_setting (id, product_type_id, setting_type_id, value) VALUES (12, 1, 12, '');


        RAISE NOTICE 'insertTestData - end';
    END;
$$ LANGUAGE plpgsql;

 SELECT current_database();

BEGIN;
  SELECT createDB();
  SELECT insertTestData();
COMMIT;