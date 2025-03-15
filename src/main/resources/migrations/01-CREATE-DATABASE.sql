CREATE SCHEMA users;

CREATE TABLE users.users (
    id                  BIGSERIAL PRIMARY KEY,
    uuid                UUID NOT NULL UNIQUE,
    name                VARCHAR(60) NOT NULL,
    email               VARCHAR(60) NOT NULL,
    password            VARCHAR(60) NOT NULL,
    login               VARCHAR(60) NOT NULL,
    status              VARCHAR(1) NOT NULL,
    profile_picture_url VARCHAR(255) NULL,
    created_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at          TIMESTAMP,
    CONSTRAINT uuid_unique UNIQUE (uuid)
);

-- Triggers for automatic update of 'updated_at'
CREATE OR REPLACE FUNCTION users.update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER update_users_updated_at
BEFORE UPDATE ON users.users
FOR EACH ROW
EXECUTE FUNCTION users.update_updated_at_column();