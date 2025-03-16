CREATE TABLE users.access_history (
    id SERIAL PRIMARY KEY,
    user_id BIGINT,
    ip_address VARCHAR NOT NULL,
    city VARCHAR NOT NULL,
    region VARCHAR NOT NULL,
    country VARCHAR NOT NULL,
    user_agent VARCHAR NOT NULL,
    succeeded BOOLEAN NOT NULL,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_access_history_user FOREIGN KEY (user_id) REFERENCES users.users(id) ON DELETE SET NULL
);

CREATE INDEX idx_access_history_user_id ON users.access_history(user_id);
