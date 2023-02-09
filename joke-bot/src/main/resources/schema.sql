CREATE TABLE IF NOT EXISTS users(
    user_id SERIAL PRIMARY KEY,
    user_name VARCHAR(255) NOT NULL,
    chat_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS categories(
    category_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS jokes(
    joke_id SERIAL PRIMARY KEY,
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

CREATE TABLE IF NOT EXISTS rates(
    user_id BIGINT NOT NULL,
    joke_id BIGINT NOT NULL,
    rates BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (joke_id) REFERENCES jokes(joke_id)
 );