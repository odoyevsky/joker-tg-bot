DROP TABLE IF EXISTS User cascade;
DROP TABLE IF EXISTS Category cascade;
DROP TABLE IF EXISTS Joke cascade;
DROP TABLE IF EXISTS UserJoke cascade;
DROP TABLE IF EXISTS UserCategory cascade;

CREATE TABLE User(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    userName BIGINT VARCHAR(255) not null,
    chatId BIGINT not null
);

CREATE TABLE Category(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) not null
);

CREATE TABLE Joke(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    text VARCHAR(5000) not null,
    FOREIGN KEY (id_category) REFERENCES Category(id)
);

CREATE TABLE UserJoke(
    FOREIGN KEY (id_user) REFERENCES User(id),
    FOREIGN KEY (id_joke) REFERENCES Joke(id)
);

CREATE TABLE UserCategory(
    FOREIGN KEY (id_user) REFERENCES User(id),
    FOREIGN KEY (id_category) REFERENCES Category(id)
);
