CREATE TABLE Person (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    fio varchar(100) NOT NULL UNIQUE,
    birthyear int NOT NULL
);

CREATE TABLE Book (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    id_person int REFERENCES Person(id) ON DELETE SET NULL,
    name varchar(100) NOT NULL,
    author varchar(100) NOT NULL,
    createyear int NOT NULL,
    taken timestamp
);