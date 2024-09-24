INSERT INTO permissions(id, name) 
VALUES
(1, 'UPDATE_USER'), 
(2, 'UPDATE_PROFILE'), 
(3, 'UPDATE_ADVERTISEMENT'), 
(4, 'UPDATE_CATEGORY'),
(5, 'UPDATE_CITY'),
(6, 'UPDATE_COMMENT'),
(7, 'UPDATE_COUNTRY'),
(8, 'UPDATE_MESSAGE'),
(9, 'VIEW_MESSAGE'),
(10, 'DELETE_ORDER'),
(11, 'VIEW_ORDER'),
(12, 'DELETE_PURCHASE'),
(13, 'VIEW_PURCHASE'),
(14, 'DELETE_RATING');

INSERT INTO roles(id, name) 
VALUES
(1, 'USER'), 
(2, 'ADMIN'),
(3, 'MODERATOR'),
(4, 'HELPER');

INSERT INTO roles_permissions(role_id, permission_id)
VALUES
(2, 1),
(2, 2),
(2, 3),
(2, 4),
(2, 5),
(2, 6),
(2, 7),
(2, 8),
(2, 9),
(2, 10),
(2, 11),
(2, 12),
(2, 13),
(2, 14),
(3, 2),
(3, 3),
(3, 6),
(3, 8),
(3, 9),
(3, 10),
(3, 11),
(3, 12),
(3, 13),
(4, 3),
(4, 9),
(4, 11),
(4, 13);

INSERT INTO users(id, username, password, status, language)
VALUES
(1, 'ilya', '$2a$10$20o3yS3fDTE8bwWbIVPQI.3lUWJMDDjlGBhRWaqoyIOpR6Y1X4.M.', 'ACTIVE', 'ENG'),
(2, 'fUser', '$2a$10$20o3yS3fDTE8bwWbIVPQI.3lUWJMDDjlGBhRWaqoyIOpR6Y1X4.M.', 'ACTIVE', 'BEL'),
(3, 'sUser', '$2a$10$20o3yS3fDTE8bwWbIVPQI.3lUWJMDDjlGBhRWaqoyIOpR6Y1X4.M.', 'ACTIVE', 'RUS'),
(4, 'tUser', '$2a$10$20o3yS3fDTE8bwWbIVPQI.3lUWJMDDjlGBhRWaqoyIOpR6Y1X4.M.', 'ACTIVE', 'ENG');

INSERT INTO users_roles(user_id, role_id)
VALUES
(1, 2),
(2, 1),
(3, 3),
(4, 4);

INSERT INTO countries(id, name)
VALUES
(1, 'Belarus'),
(2, 'Poland'),
(3, 'Russia'),
(4, 'Ukraine');

INSERT INTO cities(id, country_id, name)
VALUES
(1, 1, 'Grodno'),
(2, 1, 'Minsk'),
(3, 1, 'Vitebsk'),
(4, 1, 'Gomel'),
(5, 1, 'Mogilev'),
(6, 1, 'Brest'),
(7, 2, 'Warsaw'),
(8, 3, 'Moscow'),
(9, 4, 'Kyev');

INSERT INTO profiles(id, user_id, first_name, second_name, email, date_of_birth, gender, amount_of_money, average_rating)
VALUES
(1, 1, 'Ilya', 'Kshyvicki', 'ilyakshivitskiy2@gmail.com', '2001-08-02', true, 999.99, 10.0),
(2, 2, 'First', 'User', 'fuser@gmail.com', '2000-09-02', true, 156.99, 9.0),
(3, 3, 'Second', 'User', 'suser@gmail.com', '2000-08-26', false, 85.99, 9.0),
(4, 4, 'Third', 'User', 'tuser@gmail.com', '2001-01-06', true, 382.99, 9.0);

INSERT INTO addresses(id, address, city_id, profile_id)
VALUES
(1, 'st. Dubko, 602A', 1, 1),
(2, 'st. BLK, 65', 2, 2),
(3, 'st. Gorkogo, 82', 7, 3),
(4, 'st. Bogdana Hmelnickogo, 602A', 9, 4);

INSERT INTO ratings(id, rating, sender_id, recipient_id)
VALUES
(1, 10, 2, 1),
(2, 10, 3, 1),
(3, 10, 4, 1),
(4, 9, 1, 2);

INSERT INTO messages(id, sender_id, recipient_id, message, upload_date, readed)
VALUES
(1, 1, 2, 'Hi!!!', '2020-11-25 23:00:00', 1),
(2, 2, 1, 'Hello!', '2020-11-25 23:00:00', 1);

INSERT INTO categories(id, name, parent_category_id)
VALUES
(1, 'Electronics', null),
(2, 'Computer Equipment', 1),
(3, 'Laptops', 2),
(4, 'Notebooks', 2),
(5, 'Smartphones', 2),
(6, 'Household Equipment', 1),
(7, 'Irons', 6),
(8, 'Cleaners', 6);

INSERT INTO advertisements(id, profile_id, title, description, manufacturer, product_condition, 
						   phone_number, upload_date, price, status, category_id)
VALUES
(1, 2, 'Sell laptop HP 856', 'Sell laptop HP 856, took recently, almost new', 'HP', 'NEW',
	'+375445301356', '2020-11-25 23:00:00', '81.26', 'ACTIVE', 3),
(2, 3, 'Sell Smartphone Samsung A50', 'Sell Samsung A50, took recently, almost new', 'Samsung', 'NEW',
	'+375445301356', '2020-11-25 23:00:00', '25.26', 'ACTIVE', 5);
	
INSERT INTO comments(id, text, upload_date, profile_id, advertisement_id)
VALUES
(1, 'Wow, I want it!!!', '2020-11-25 23:00:00', 4, 1),
(2, 'Yeaaaa, I too', '2020-11-25 23:00:00', 3, 1),
(3, 'O MY GOD, I LOVE IT', '2020-11-25 23:00:00', 2, 2);

INSERT INTO orders(id, profile_id, date)
VALUES
(1, 4, '2020-11-26 23:00:00'),
(2, 3, '2020-11-26 23:00:00'),
(3, 2, '2020-11-27 23:00:00');

INSERT INTO purchases(id, earned_money, advertisement_id, order_id)
VALUES
(1, 81.26, 1, 1),
(2, 25.26, 2, 1),
(3, 0.0, 1, 2),
(4, 0.0, 2, 3);

INSERT INTO status_orders(id, status, change_date, order_id)
VALUES
(1, 'NEW', '2020-11-26 23:00:00', 1),
(2, 'COMPLETED', '2020-11-27 23:00:00', 1),
(3, 'NEW', '2020-11-26 23:00:00', 2),
(4, 'NEW', '2020-11-27 23:00:00', 3);