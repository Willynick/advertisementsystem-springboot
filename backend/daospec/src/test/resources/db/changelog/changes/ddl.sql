CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(200) NOT NULL,
	status VARCHAR(15) NOT NULL,
	language VARCHAR(4) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS countries (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS cities (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	country_id BIGINT NOT NULL,
	name VARCHAR(50) NOT NULL,
	FOREIGN KEY (country_id) REFERENCES countries (id)
);

CREATE TABLE IF NOT EXISTS categories (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL,
	parent_category_id BIGINT DEFAULT NULL,
	FOREIGN KEY (parent_category_id) REFERENCES categories (id)
);

CREATE TABLE IF NOT EXISTS profiles (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	user_id BIGINT NOT NULL,
	first_name VARCHAR(50) DEFAULT NULL,
    second_name VARCHAR(50) DEFAULT NULL,
	email VARCHAR(50) DEFAULT NULL,
    date_of_birth DATE DEFAULT NULL,
	gender BOOLEAN DEFAULT NULL,
    amount_of_money NUMERIC(12,2) DEFAULT NULL,
	average_rating NUMERIC(4,2) DEFAULT NULL,
	FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS ratings (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	rating INT DEFAULT NULL,
	sender_id BIGINT NOT NULL,
	recipient_id BIGINT NOT NULL,
	FOREIGN KEY (sender_id) REFERENCES profiles (id),
	FOREIGN KEY (recipient_id) REFERENCES profiles (id)
);

CREATE TABLE IF NOT EXISTS advertisements (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	profile_id BIGINT NOT NULL,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(5000) NOT NULL,
	manufacturer VARCHAR(50) DEFAULT NULL,
	product_condition VARCHAR(15) DEFAULT NULL,
	phone_number VARCHAR(15) NOT NULL,
    upload_date TIMESTAMP NOT NULL,
	change_date TIMESTAMP DEFAULT NULL,
    price NUMERIC(12,2) NOT NULL,
    status VARCHAR(15) NOT NULL,
	category_id BIGINT DEFAULT NULL,
	FOREIGN KEY (profile_id) REFERENCES profiles (id),
	FOREIGN KEY (category_id) REFERENCES categories (id)
);

CREATE TABLE IF NOT EXISTS addresses (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	address VARCHAR(500) DEFAULT NULL,
	city_id BIGINT DEFAULT NULL,
	advertisement_id BIGINT DEFAULT NULL,
	profile_id BIGINT DEFAULT NULL,
	FOREIGN KEY (city_id) REFERENCES cities (id),
	FOREIGN KEY (advertisement_id) REFERENCES advertisements (id),
	FOREIGN KEY (profile_id) REFERENCES profiles (id)
);

CREATE TABLE IF NOT EXISTS roles (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS users_roles (
    user_id BIGINT NOT NULL,
	role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
	FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS permissions (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS roles_permissions (
	role_id BIGINT NOT NULL,
	permission_id BIGINT NOT NULL,
	PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles (id),
	FOREIGN KEY (permission_id) REFERENCES permissions (id)
);

CREATE TABLE IF NOT EXISTS comments (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	text VARCHAR(2000) NOT NULL,
	upload_date TIMESTAMP NOT NULL,
	profile_id BIGINT NOT NULL,
	advertisement_id BIGINT NOT NULL,
	FOREIGN KEY (profile_id) REFERENCES profiles (id),
	FOREIGN KEY (advertisement_id) REFERENCES advertisements (id)
);

CREATE TABLE IF NOT EXISTS messages (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	sender_id BIGINT NOT NULL,
	recipient_id BIGINT NOT NULL,
	message VARCHAR(2000) NOT NULL,
	upload_date TIMESTAMP NOT NULL,
	readed BOOLEAN NOT NULL,
	FOREIGN KEY (sender_id) REFERENCES profiles (id),
	FOREIGN KEY (recipient_id) REFERENCES profiles (id)
);

CREATE TABLE IF NOT EXISTS orders (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	profile_id BIGINT NOT NULL,
	date TIMESTAMP NOT NULL,
	delivery_type VARCHAR(50) DEFAULT NULL,
	FOREIGN KEY (profile_id) REFERENCES profiles (id)
);

CREATE TABLE IF NOT EXISTS purchases (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	earned_money NUMERIC(12,2) DEFAULT NULL,
	advertisement_id BIGINT NOT NULL,
	order_id BIGINT NOT NULL,
	FOREIGN KEY (advertisement_id) REFERENCES advertisements (id),
	FOREIGN KEY (order_id) REFERENCES orders (id)
);


CREATE TABLE IF NOT EXISTS status_orders (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    status VARCHAR(10) NOT NULL,
	change_date TIMESTAMP NOT NULL,
	order_id BIGINT NOT NULL,
	FOREIGN KEY (order_id) REFERENCES orders (id)
);