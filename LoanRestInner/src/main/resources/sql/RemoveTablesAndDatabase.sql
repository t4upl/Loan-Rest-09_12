-- run as postgresql sudouser

DROP TABLE IF EXISTS customer CASCADE;
DROP TABLE IF EXISTS product CASCADE;
DROP TABLE IF EXISTS product_type CASCADE;
DROP TABLE IF EXISTS product_setting CASCADE;
DROP TABLE IF EXISTS product_type_setting CASCADE;
DROP TABLE IF EXISTS setting_type CASCADE;

DROP DATABASE debtDB;
DROP USER username2;