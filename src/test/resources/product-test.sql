CREATE TABLE IF NOT EXISTS t_product (
  id INT NOT NULL PRIMARY KEY,
  code VARCHAR(9) NOT NULL,
  name VARCHAR(90) NOT NULL,
  category VARCHAR(28) NOT NULL,
  brand VARCHAR(28),
  type VARCHAR(21),
  description VARCHAR(180),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT UX_product_code UNIQUE (code)
);

CREATE TABLE IF NOT EXISTS t_price (
  id INT NOT NULL PRIMARY KEY,
  amount DECIMAL(10,2) NOT NULL,
  valid_from DATE NOT NULL,
  valid_to DATE,
  product_id INT NOT NULL,
  CONSTRAINT FK_price_product FOREIGN KEY (product_id) REFERENCES t_product(id)
);

INSERT INTO t_product (id, code, name, category, brand, type, description) VALUES (1, 'P001', 'MASK ADULT Surgical 3 ply 50''S MEDICOS with box', 'Health Accessories', 'Medicos', 'Hygiene', 'Colour: Blue (ear loop outside, ear loop inside- random assigned), Green, Purple, White, Lime Green, Yellow, Pink');
INSERT INTO t_product (id, code, name, category, brand, type, description) VALUES (2, 'P002', 'Party Cosplay Player Unknown Battlegrounds Clothes Hallowmas PUBG', 'Men''s Clothing', 'No Brand', null, 'Suitable for adults and children.');
INSERT INTO t_product (id, code, name, category, brand, type, description) VALUES (3, 'P003', 'Xiaomi REDMI 8A Official Global Version 5000 mAh battery champion 31 days 2GB+32GB', 'Mobile & Gadgets', 'Xiaomi', 'Mobile Phones', 'Xiaomi Redmi 8A');

INSERT INTO t_price (id, amount, valid_from, product_id) VALUES (1, 29.99, CURRENT_DATE(), 1);
COMMIT;