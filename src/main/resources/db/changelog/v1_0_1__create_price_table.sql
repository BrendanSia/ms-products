--liquibase formatted sql
--changeset author:your_name id:create-t_product

CREATE TABLE t_price (
    id SERIAL PRIMARY KEY,
    amount NUMERIC(10,2) NOT NULL,
    valid_from DATE NOT NULL,
    valid_to DATE,
    product_id INT NOT NULL,
    CONSTRAINT FK_price_product FOREIGN KEY (product_id) REFERENCES t_product(id)
);

INSERT INTO t_price (id, amount, valid_from, product_id) VALUES (1, 29.99, CURRENT_DATE(), 15);
