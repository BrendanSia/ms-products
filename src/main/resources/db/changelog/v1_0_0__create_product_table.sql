--liquibase formatted sql
--changeset author:brendan id:create-t_product

CREATE TABLE t_product (
    id SERIAL PRIMARY KEY,
    code VARCHAR(9) NOT NULL,
    name VARCHAR(90) NOT NULL,
    category VARCHAR(28) NOT NULL,
    brand VARCHAR(28),
    type VARCHAR(21),
    description VARCHAR(180),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT UX_product_code UNIQUE (code)
);

INSERT INTO t_product (id, code, name, category, brand, type, description) VALUES (1, 'P001', 'MASK ADULT Surgical 3 ply 50''S MEDICOS with box', 'Health Accessories', 'Medicos', 'Hygiene', 'Colour: Blue (ear loop outside, ear loop inside- random assigned), Green, Purple, White, Lime Green, Yellow, Pink');
INSERT INTO t_product (id, code, name, category, brand, type, description) VALUES (2, 'P002', 'Party Cosplay Player Unknown Battlegrounds Clothes Hallowmas PUBG', 'Men''s Clothing', 'No Brand', null, 'Suitable for adults and children.');
INSERT INTO t_product (id, code, name, category, brand, type, description) VALUES (3, 'P003', 'Xiaomi REDMI 8A Official Global Version 5000 mAh battery champion 31 days 2GB+32GB', 'Mobile & Gadgets', 'Xiaomi', 'Mobile Phones', 'Xiaomi Redmi 8A');
INSERT INTO t_product (id, code, name, category, brand, type, description) VALUES (4, 'P004', 'Naelofar Sofis - Printed Square', 'Hijab', 'Naelofar', 'Multi Colour Floral', 'Ornate Iris flower composition with intricate growing foliage');
INSERT INTO t_product (id, code, name, category, brand, type, description) VALUES (5, 'P005', 'Philips HR2051 / HR2056 / HR2059 Ice Crushing Blender Jar Mill', 'Small Kitchen Appliances', 'Philips', 'Mixers & Blenders', 'Philips HR2051 Blender (350W, 1.25L Plastic Jar, 4 stars stainless steel blade)');
INSERT INTO t_product (id, code, name, category, brand, type, description) VALUES (6, 'P006', 'Gemei GM-6005 Rechargeable Trimmer Hair Cutter Machine', 'Hair Styling Tools', 'Gemei', 'Trimmer', 'The GEMEI hair clipper is intended for professional use.');
INSERT INTO t_product (id, code, name, category, brand, type, description) VALUES (7, 'P007', 'Oreo Crumb Small Crushed Cookie Pieces 454g', 'Snacks', 'Oreo', 'Biscuits & Cookies', 'Oreo Crumb Small Crushed Cookie Pieces 454g - Retail & Wholesale New Stock Long Expiry!!!');
INSERT INTO t_product (id, code, name, category, brand, description) VALUES (8, 'P008', 'Non-contact Infrared Forehead Thermometer ABS', 'Kids Health & Skincare', 'No Brand', 'Non-contact Infrared Forehead Thermometer ABS for Adults and Children with LCD Display Digital');
INSERT INTO t_product (id, code, name, category, brand, type, description) VALUES (9, 'P009', 'Nordic Marble Starry Sky Bedding Sets', 'Bedding', 'No Brand', 'Bedding Sheets', 'Printing process: reactive printing. Package：quilt cover ,bed sheet ,pillow case. Not include comforter or quilt.');
INSERT INTO t_product (id, code, name, category, brand, type, description) VALUES (10, 'P010', 'Samsung Galaxy Tab A 10.1''', 'Mobile & Gadgets', 'Samsung', 'Tablets', '4GB RAM. - 64GB ROM. - 1.5 ghz Processor. - 10.1 inches LCD Display');
INSERT INTO t_product (id, code, name, category, brand, type, description) VALUES (11, 'P011', 'REALME 5 PRO 6+128GB', 'Mobile & Gadgets', 'Realme', 'Mobile Phones', 'REALME 5 PRO 6+128GB');
INSERT INTO t_product (id, code, name, category, brand, type, description) VALUES (12, 'P012', 'Nokia 2.3 - Cyan Green', 'Mobile & Gadgets', 'Nokia', 'Mobile Phones', 'Nokia smartphones get 2 years of software upgrades and 3 years of monthly security updates.');
INSERT INTO t_product (id, code, name, category, brand, type, description) VALUES (13, 'P013', 'AKEMI Cotton Select Fitted Bedsheet Set - Adore 730TC', 'Bedding', 'Akemi', 'Bedding Sheets', '100% Cotton Twill. Super Single.');
INSERT INTO t_product (id, code, name, category, brand, type, description) VALUES (14, 'P014', 'Samsung Note10+ Phone', 'Mobile & Gadgets', 'OEM', 'Mobile Phones', 'OEM Phone Models');
INSERT INTO t_product (id, code, name, category, brand, type, description) VALUES (15, 'P015', 'Keknis Basic Wide Long Shawl', 'Hijab', 'No Brand', 'Plain Shawl', '1.8m X 0.7m (+/-). Heavy chiffon (120 gsm).');