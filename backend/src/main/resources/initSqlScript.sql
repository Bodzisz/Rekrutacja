CREATE DATABASE IF NOT EXISTS restaurant;

USE restaurant;

DROP TABLE IF EXISTS tables;
DROP TABLE IF EXISTS reservations;

CREATE TABLE IF NOT EXISTS tables(
    number int NOT NULL AUTO_INCREMENT,
    min_number_of_seats int NOT NULL,
    max_number_of_seats int NOT NULL,
    PRIMARY KEY (number),
    FOREIGN KEY (number) REFERENCES reservations(seat_number)
);

CREATE TABLE IF NOT EXISTS reservations(
    id int NOT NULL AUTO_INCREMENT,
    date datetime NOT NULL,
    duration int NOT NULL,
    seat_number int NOT NULL,
    full_name varchar(50),
    phone varchar(12),
    email varchar(50),
    number_of_seats int NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS cancellations(
    id int NOT NULL,
    code varchar(6) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES reservations(id) ON DELETE CASCADE
);