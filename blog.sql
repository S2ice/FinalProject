create database blog;

use blog;

CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    lastname VARCHAR(50),
    name VARCHAR(50),
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL CHECK (LENGTH(password) >= 8),
    address VARCHAR(50),
    description VARCHAR(255),
    status BIT NOT NULL,
    photo VARCHAR(255) NOT NULL,
    date_regestry DATE NOT NULL
);

CREATE TABLE Role (
    role_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    role_name VARCHAR(10) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE Report (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    text TEXT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE Category (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL
);

CREATE TABLE Post (
    post_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    category_id INT,
    title VARCHAR(50) NOT NULL,
    content VARCHAR(255) NOT NULL,
    post_creation DATE NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (category_id) REFERENCES Category(category_id)
);

CREATE TABLE Customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

CREATE TABLE `Order` (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    order_date DATE NOT NULL,
    order_status BIT,
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

CREATE TABLE Ad (
    ad_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT,
    ad_photo VARCHAR(255) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES `Order`(order_id)
);