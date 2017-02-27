DROP TABLE people IF EXISTS;

CREATE TABLE people  (
    id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);

INSERT INTO people(id, first_name, last_name) VALUES (1, 'John', 'Lennon');
INSERT INTO people(id, first_name, last_name) VALUES (2, 'George', 'Harrison');
INSERT INTO people(id, first_name, last_name) VALUES (3, 'Paul', 'McCartney');
INSERT INTO people(id, first_name, last_name) VALUES (4, 'Ringo', 'Starr');
INSERT INTO people(id, first_name, last_name) VALUES (5, 'Mick', 'Jagger');
INSERT INTO people(id, first_name, last_name) VALUES (6, 'Keith', 'Richards');
INSERT INTO people(id, first_name, last_name) VALUES (7, 'Ronnie', 'Wood');
INSERT INTO people(id, first_name, last_name) VALUES (8, 'Charlie', 'Watts');