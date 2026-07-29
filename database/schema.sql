DROP TABLE IF EXISTS payment;
DROP TABLE IF EXISTS order_item;
DROP TABLE IF EXISTS "order";
DROP TABLE IF EXISTS cart_item;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS subcategory;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS "user";

CREATE TABLE "user" (
        user_id SERIAL PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        email VARCHAR(100) UNIQUE NOT NULL,
        password VARCHAR(255) NOT NULL,
        role_id INT NOT NULL DEFAULT 2
);

CREATE TABLE category (
        category_id SERIAL PRIMARY KEY,
        category_name VARCHAR(100) NOT NULL
);

CREATE TABLE subcategory (
        subcategory_id SERIAL PRIMARY KEY,
        category_id INT NOT NULL,
        subcategory_name VARCHAR(100) NOT NULL,

        FOREIGN KEY (category_id)
            REFERENCES category(category_id)
);

CREATE TABLE product (
        product_id SERIAL PRIMARY KEY,
        category_id INT NOT NULL,
        subcategory_id INT NOT NULL,
        product_name VARCHAR(100) NOT NULL,
        price NUMERIC(10, 2) NOT NULL,
        discount_percentage NUMERIC(5,2) NOT NULL,
        stock_quantity INT NOT NULL,
        status_id INT NOT NULL DEFAULT 1,

        FOREIGN KEY (category_id)
            REFERENCES category(category_id),

        FOREIGN KEY (subcategory_id)
            REFERENCES subcategory(subcategory_id)
);

CREATE TABLE cart (
        cart_id SERIAL PRIMARY KEY,
        user_id INT NOT NULL,

        FOREIGN KEY (user_id)
            REFERENCES "user"(user_id)
);

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

CREATE TABLE "order" (
        order_id SERIAL PRIMARY KEY,
        user_id INT NOT NULL,
        customer_name VARCHAR(100) NOT NULL,
        phone VARCHAR(20) NOT NULL,
        address VARCHAR(255) NOT NULL,
        total_amount NUMERIC(10, 2) NOT NULL,
        order_status_id INT NOT NULL DEFAULT 1,

        FOREIGN KEY (user_id)
            REFERENCES "user"(user_id)
);

CREATE TABLE order_item (
        order_item_id SERIAL PRIMARY KEY,
        order_id INT NOT NULL,
        product_id INT NOT NULL,
        quantity INT NOT NULL,
        price NUMERIC(10, 2) NOT NULL,

        FOREIGN KEY (order_id)
            REFERENCES "order"(order_id),

        FOREIGN KEY (product_id)
            REFERENCES product(product_id)
);

CREATE TABLE payment (
        payment_id SERIAL PRIMARY KEY,
        order_id INT NOT NULL,
        payment_method_id INT NOT NULL,
        payment_status_id INT NOT NULL DEFAULT 1,
        amount NUMERIC(10, 2) NOT NULL,
        transaction_id VARCHAR(100),

        FOREIGN KEY (order_id)
            REFERENCES "order"(order_id)
);
