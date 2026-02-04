CREATE DATABASE ocean_view_resort_db;
USE ocean_view_resort_db;

-- USERS table (for Login)
CREATE TABLE users (
  user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  full_name VARCHAR(100) NULL,
  role VARCHAR(30) NOT NULL DEFAULT 'STAFF',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- RESERVATIONS table (core)
CREATE TABLE reservations (
  reservation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  reservation_number VARCHAR(30) NOT NULL UNIQUE,
  guest_name VARCHAR(100) NOT NULL,
  address VARCHAR(255) NOT NULL,
  contact_number VARCHAR(20) NOT NULL,
  room_type VARCHAR(30) NOT NULL,
  check_in_date DATE NOT NULL,
  check_out_date DATE NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ROOM RATES table (good for billing + easier updates)
CREATE TABLE room_rates (
  rate_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  room_type VARCHAR(30) NOT NULL UNIQUE,
  rate_per_night DECIMAL(10,2) NOT NULL
);

-- Seed room rates (insert only if not already there)
INSERT INTO room_rates (room_type, rate_per_night)
VALUES
('SINGLE', 8000.00),
('DOUBLE', 12000.00),
('SUITE', 20000.00)
ON DUPLICATE KEY UPDATE rate_per_night = VALUES(rate_per_night);

-- Create a default user for login testing
INSERT INTO users (username, password, full_name, role)
VALUES ('admin', 'admin123', 'System Admin', 'ADMIN')
ON DUPLICATE KEY UPDATE username = username;

USE ocean_view_resort_db;
SELECT * FROM room_rates;
SELECT * FROM users;
SELECT * FROM reservations;
SELECT username, password, role FROM users;