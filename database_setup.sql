-- MySQL Database Setup for E-commerce Application
-- Run this script in MySQL Workbench or MySQL command line

-- Create database
CREATE DATABASE IF NOT EXISTS ecommerce_db;
USE ecommerce_db;

-- Create users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(20),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create products table
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    original_price DECIMAL(10, 2),
    brand VARCHAR(100),
    category VARCHAR(100),
    image_url VARCHAR(500),
    stock_quantity INT DEFAULT 0,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Create carts table
CREATE TABLE IF NOT EXISTS carts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create cart_items table
CREATE TABLE IF NOT EXISTS cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    price_at_time DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
    UNIQUE KEY unique_cart_product (cart_id, product_id)
);

-- Create orders table
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    order_number VARCHAR(50) UNIQUE NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING',
    shipping_address TEXT,
    payment_method VARCHAR(50),
    payment_status VARCHAR(50) DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Create order_items table
CREATE TABLE IF NOT EXISTS order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price_at_time DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- Insert sample products
INSERT INTO products (name, description, price, original_price, brand, category, image_url, stock_quantity) VALUES
('Pure Satin Benarasi Saree', 'Pure satin benarasi with maggam work saree', 4150.00, 7999.00, 'Cara', 'Sarees', '/img/products/c2-1.png', 10),
('Designer Silk Saree', 'Pure satin benarasi with maggam work saree', 4150.00, 7999.00, 'Cara', 'Sarees', '/img/products/c3-1.png', 15),
('Traditional Cotton Saree', 'Pure satin benarasi with maggam work saree', 4150.00, 7999.00, 'Cara', 'Sarees', '/img/products/c4-1.png', 12),
('Elegant Party Saree', 'Pure satin benarasi with maggam work saree', 4150.00, 7999.00, 'Cara', 'Sarees', '/img/products/c5-1.png', 8),
('Bridal Collection Saree', 'Pure satin benarasi with maggam work saree', 4150.00, 7999.00, 'Cara', 'Sarees', '/img/products/c6-1.png', 5),
('Festival Special Saree', 'Pure satin benarasi with maggam work saree', 4150.00, 7999.00, 'Cara', 'Sarees', '/img/products/c7-1.png', 20),
('Classic Handloom Saree', 'Pure satin benarasi with maggam work saree', 4150.00, 7999.00, 'Cara', 'Sarees', '/img/products/c2-2.png', 18),
('Modern Designer Saree', 'Pure satin benarasi with maggam work saree', 4150.00, 7999.00, 'Cara', 'Sarees', '/img/products/c3-2.png', 14),
('Royal Collection Saree', 'Pure satin benarasi with maggam work saree', 4150.00, 7999.00, 'Cara', 'Sarees', '/img/products/c4-2.png', 9),
('Premium Silk Saree', 'Pure satin benarasi with maggam work saree', 4150.00, 7999.00, 'Cara', 'Sarees', '/img/products/c5-2.png', 11),
('Luxury Designer Saree', 'Pure satin benarasi with maggam work saree', 4150.00, 7999.00, 'Cara', 'Sarees', '/img/products/c6-2.png', 7),
('Exclusive Collection Saree', 'Pure satin benarasi with maggam work saree', 4150.00, 7999.00, 'Cara', 'Sarees', '/img/products/d1-1.png', 13),
('Heritage Saree', 'Pure satin benarasi with maggam work saree', 4150.00, 7999.00, 'Cara', 'Sarees', '/img/products/d2-1.jpg', 16),
('Artisan Crafted Saree', 'Pure satin benarasi with maggam work saree', 4150.00, 7999.00, 'Cara', 'Sarees', '/img/products/d3-1.png', 6),
('Vintage Style Saree', 'Pure satin benarasi with maggam work saree', 4150.00, 7999.00, 'Cara', 'Sarees', '/img/products/d4-1.png', 19),
('Contemporary Saree', 'Pure satin benarasi with maggam work saree', 4150.00, 7999.00, 'Cara', 'Sarees', '/img/products/d5-1.png', 22);

-- Create indexes for better performance
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_products_category ON products(category);
CREATE INDEX idx_products_brand ON products(brand);
CREATE INDEX idx_carts_user_id ON carts(user_id);
CREATE INDEX idx_cart_items_cart_id ON cart_items(cart_id);
CREATE INDEX idx_cart_items_product_id ON cart_items(product_id);
CREATE INDEX idx_orders_user_id ON orders(user_id);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_order_items_order_id ON order_items(order_id);

-- Display success message
SELECT 'Database setup completed successfully!' as Status;
