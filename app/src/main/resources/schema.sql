-- Drop table
DROP TABLE IF EXISTS order_item;
DROP TABLE IF EXISTS "order";
DROP TABLE IF EXISTS cart_item;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS sub_category;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS "user";

-- User Table
CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role INT NOT NULL DEFAULT 2,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
SELECT * FROM "user";

-- Category table
CREATE TABLE category (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
SELECT * FROM category;

-- Subcategory table
CREATE TABLE sub_category (
    subcategory_id SERIAL PRIMARY KEY,
    subcategory_name VARCHAR(100) NOT NULL,
    category_id INT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (category_id)
        REFERENCES category(category_id)
);
SELECT * FROM sub_category;

-- Product table
CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    seller_id INT NOT NULL,
    subcategory_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    discount NUMERIC(5,2),
    quantity INT NOT NULL,
    status_id INT NOT NULL,
    tax NUMERIC(5,2),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (subcategory_id)
        REFERENCES sub_category(subcategory_id),

    FOREIGN KEY (seller_id)
        REFERENCES "user"(id)
);
SELECT * FROM product;

-- Order table
CREATE TABLE "order" (
    order_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    seller_id INT NOT NULL,
    customer_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address TEXT NOT NULL,
    total_amount DOUBLE PRECISION NOT NULL,
    payment_method INT NOT NULL,
    payment_status INT NOT NULL,
    transaction_id VARCHAR(100) NOT NULL,
    order_status INT NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id)
        REFERENCES "user"(id),
    FOREIGN KEY (seller_id)
        REFERENCES "user"(id)
);
SELECT * FROM "order";

-- Order item table
CREATE TABLE order_item (
    order_item_id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,

    FOREIGN KEY (order_id)
        REFERENCES "order"(order_id),

    FOREIGN KEY (product_id)
        REFERENCES product(id)
);
SELECT * from order_item;

-- Cart item table
CREATE TABLE cart_item (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,

    FOREIGN KEY (user_id)
        REFERENCES "user"(id),

    FOREIGN KEY (product_id)
        REFERENCES product(id)
);
SELECT * FROM cart_item;

-- Review table
CREATE TABLE review (
    review_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT NOT NULL,
    reply_to_review_id INT,

    FOREIGN KEY (user_id)
        REFERENCES "user"(id),

    FOREIGN KEY (product_id)
        REFERENCES product(id),

    FOREIGN KEY (reply_to_review_id)
        REFERENCES review(review_id)
);
SELECT * FROM review;