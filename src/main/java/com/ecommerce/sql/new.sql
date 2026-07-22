DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS "order";
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS cart_item;

CREATE TABLE "user" (
     user_id SERIAL PRIMARY KEY,
	 name VARCHAR(50) NOT NULL,
	 email VARCHAR(50) UNIQUE NOT NULL,
	 password VARCHAR(50) NOT NULL,
     role INT NOT NULL CHECK(role IN(1,2))
);

SELECT * FROM "user";

CREATE TABLE category (
    category_id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

INSERT INTO category(name)
VALUES ('Electronics');

INSERT INTO category(name)
VALUES ('Fashion');

INSERT INTO category(name)
VALUES ('Grocery');

INSERT INTO category(name)
VALUES ('Home & Kitchen');

SELECT * FROM category;

CREATE TABLE product (
    product_id SERIAL PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,
    category_id INT NOT NULL,
    FOREIGN KEY (category_id)
        REFERENCES category(category_id)
);

SELECT * FROM product;

CREATE TABLE cart (
    cart_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES "user"(user_id),
    FOREIGN KEY (product_id)
        REFERENCES product(product_id)
);

ALTER TABLE cart
DROP COLUMN product_id;

ALTER TABLE cart
DROP COLUMN quantity;

SELECT * FROM cart;

CREATE TABLE cart_item (
    cart_item_id SERIAL PRIMARY KEY,
    cart_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,

    FOREIGN KEY (cart_id)
        REFERENCES cart(cart_id),
    FOREIGN KEY (product_id)
        REFERENCES product(product_id)
);

SELECT * FROM cart_item;

CREATE TABLE "order" (

    order_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    address TEXT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id)
        REFERENCES "user"(user_id)
);

SELECT * FROM "order";

CREATE TABLE order_item (

    order_item_id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (order_id)
        REFERENCES "order"(order_id),
    FOREIGN KEY (product_id)
        REFERENCES product(product_id)
);

SELECT * FROM order_item;