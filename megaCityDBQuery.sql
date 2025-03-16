create schema megacity_cabs_db
USE megacity_cabs_db

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    pWord VARCHAR(255) NOT NULL,
    uRole VARCHAR(50) DEFAULT 'cus',
    fullName VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL
);
SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE users;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `megacity_cabs_db`.`users` (`username`, `pWord`, `uRole`, `fullName`, `address`, `phone`, `email`) VALUES ('admin', '$2a$10$Ro3cwlJEEct7AMqGHFo.t.MMLiXsmjyUeVS1Di0V5wIkWypWQwGDC', 'adm', 'Admin', 'Colombo', '0111111111', 'admin@gmail.com');
INSERT INTO `megacity_cabs_db`.`users` (`username`, `pWord`, `uRole`, `fullName`, `address`, `phone`, `email`) VALUES ('user', '$2a$10$oMllgorxkr4seNL5hT6u4OKC2kdNQvCmrjSoiNm7YBnRqD1/TmQWu', 'cus', 'User', 'Colombo', '0111111111', 'user@gmail.com');

/* Admin- admin@gmail.com(PW- admin)
   User - user@gmail.com(PW user)   */

CREATE TABLE category (
    id INT PRIMARY KEY AUTO_INCREMENT,
    catName VARCHAR(255) NOT NULL,
    maxPsngr INT NOT NULL,
    perDayValue DECIMAL(10,2) NOT NULL,
    maxKmPerDay INT NOT NULL,
    milePkg1 DECIMAL(10,2) NOT NULL,
    pkg1Hrs INT NOT NULL DEFAULT 0,  
    milePkg2 DECIMAL(10,2) NOT NULL,
    pkg2Hrs INT NOT NULL DEFAULT 0,  
    waitingPerHr DECIMAL(10,2) NOT NULL,
    extraKm DECIMAL(10,2) NOT NULL,
    active ENUM('Active', 'Inactive') DEFAULT 'Active'
);
INSERT INTO `megacity_cabs_db`.`category` (`catName`, `maxPsngr`, `perDayValue`, `maxKmPerDay`, `milePkg1`, `pkg1Hrs`, `milePkg2`, `pkg2Hrs`, `waitingPerHr`, `extraKm`, `active`) VALUES ('Budget', '3', '5200', '120', '3200', '1', '4800', '2', '80', '100', 'Active');
INSERT INTO `megacity_cabs_db`.`category` (`catName`, `maxPsngr`, `perDayValue`, `maxKmPerDay`, `milePkg1`, `pkg1Hrs`, `milePkg2`, `pkg2Hrs`, `waitingPerHr`, `extraKm`, `active`) VALUES ('CityCar', '3', '5400', '120', '3400', '1', '4900', '2', '80', '100', 'Active');
INSERT INTO `megacity_cabs_db`.`category` (`catName`, `maxPsngr`, `perDayValue`, `maxKmPerDay`, `milePkg1`, `pkg1Hrs`, `milePkg2`, `pkg2Hrs`, `waitingPerHr`, `extraKm`, `active`) VALUES ('Semi', '4', '5800', '130', '3600', '1', '5000', '2', '90', '110', 'Active');
INSERT INTO `megacity_cabs_db`.`category` (`catName`, `maxPsngr`, `perDayValue`, `maxKmPerDay`, `milePkg1`, `pkg1Hrs`, `milePkg2`, `pkg2Hrs`, `waitingPerHr`, `extraKm`, `active`) VALUES ('Car', '4', '6000', '130', '4000', '1', '5500', '2', '95', '120', 'Active');
INSERT INTO `megacity_cabs_db`.`category` (`catName`, `maxPsngr`, `perDayValue`, `maxKmPerDay`, `milePkg1`, `pkg1Hrs`, `milePkg2`, `pkg2Hrs`, `waitingPerHr`, `extraKm`, `active`) VALUES ('MiniVan', '9', '7000', '150', '5500', '1', '6700', '2', '100', '140', 'Active');
INSERT INTO `megacity_cabs_db`.`category` (`catName`, `maxPsngr`, `perDayValue`, `maxKmPerDay`, `milePkg1`, `pkg1Hrs`, `milePkg2`, `pkg2Hrs`, `waitingPerHr`, `extraKm`, `active`) VALUES ('Van', '13', '8000', '150', '6500', '1', '7700', '2', '110', '160', 'Active');


CREATE TABLE vehicles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    catId INT NOT NULL,
    vehicleNo VARCHAR(50) UNIQUE NOT NULL,
    regExpDate DATE NOT NULL,
    stat ENUM('Active', 'Suspended') DEFAULT 'Active',
    FOREIGN KEY (catId) REFERENCES category(id) ON DELETE CASCADE
);

CREATE TABLE drivers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    dName VARCHAR(255) NOT NULL,
    dAddress TEXT NOT NULL,
    dTel VARCHAR(20) NOT NULL,
    dLNum VARCHAR(50) UNIQUE NOT NULL,
    dLExpDate DATE NOT NULL,
    stat ENUM('Active', 'Suspended') DEFAULT 'Active'
);

CREATE TABLE discounts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    diskId VARCHAR(255) UNIQUE NOT NULL,
    percentage DECIMAL(5,2) NOT NULL,
    startDate DATE NOT NULL,
    endDate DATE NOT NULL,
    dStatus ENUM('Active', 'Inactive') DEFAULT 'Active'
);

CREATE TABLE discount_availability (
    id INT PRIMARY KEY AUTO_INCREMENT,
    userId INT NOT NULL,
    dissId INT NOT NULL,
    usedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (dissId) REFERENCES discounts(id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_discount (userId, dissId) -- Ensure a user can use a discount only once
);

CREATE TABLE reservations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    userId INT NOT NULL,
    vehicleId INT NOT NULL,
    driverId INT NULL,
    dissId INT NULL, 
    ratId INT NULL, 
    stDate DATE NOT NULL,
    endDate DATE NOT NULL,
    stTime TIME NOT NULL,
    stLocation TEXT NOT NULL,
    stat ENUM('Pending', 'Approved', 'Rejected', 'Cancelled', 'Confirmed', 'PendingPayment', 'Finalized') DEFAULT 'Approved', 
    finalPrice DECIMAL(10,2) NULL, 
    comments TEXT NULL,
    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (vehicleId) REFERENCES vehicles(id) ON DELETE CASCADE,
    FOREIGN KEY (driverId) REFERENCES drivers(id) ON DELETE SET NULL,
    FOREIGN KEY (dissId) REFERENCES discounts(id) ON DELETE SET NULL, 
    FOREIGN KEY (ratId) REFERENCES ratings(id) ON DELETE SET NULL 
);


CREATE TABLE vehicle_availability (
    id INT PRIMARY KEY AUTO_INCREMENT,
    vehicleId INT NOT NULL,
    stDate DATE NOT NULL,
    endDate DATE NOT NULL,
    FOREIGN KEY (vehicleId) REFERENCES vehicles(id) ON DELETE CASCADE
);

CREATE TABLE driver_availability (
    id INT PRIMARY KEY AUTO_INCREMENT,
    driverId INT NOT NULL,
    stDate DATE NOT NULL,
    endDate DATE NOT NULL,
    FOREIGN KEY (driverId) REFERENCES drivers(id) ON DELETE CASCADE
);

CREATE TABLE ratings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    userId INT NOT NULL,
    tripRating DECIMAL(2,1) NOT NULL,
    vehicleRating DECIMAL(2,1) NOT NULL,
    driverRating DECIMAL(2,1) NOT NULL,
    comment TEXT NULL,
    FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE reservation_finalize (
    id INT AUTO_INCREMENT PRIMARY KEY,
    resId INT NOT NULL,
    extraKm DECIMAL(10,2) DEFAULT 0,
    extraHr DECIMAL(10,2) DEFAULT 0,
    price DECIMAL(10,2) NOT NULL,
    stat ENUM('Pending', 'Paid') NOT NULL DEFAULT 'Pending',
    FOREIGN KEY (resId) REFERENCES reservations(id) ON DELETE CASCADE
);
