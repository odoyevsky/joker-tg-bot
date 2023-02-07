CREATE TABLE IF NOT EXISTS users(
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(255) NOT NULL,
    chat_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS categories(
    category_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS jokes(
    joke_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT NOT NULL,
    text VARCHAR(5000) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE IF NOT EXISTS favourite_jokes(
    user_id BIGINT NOT NULL,
    joke_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (joke_id) REFERENCES jokes(joke_id)
);
