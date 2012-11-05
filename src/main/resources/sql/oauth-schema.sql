-- User details
CREATE TABLE users (
    username VARCHAR(256) NOT NULL PRIMARY KEY,
    password VARCHAR(256) NOT NULL,
    authorities VARCHAR(1024) NOT NULL,
    deleted TINYINT);

-- Access tokens
CREATE TABLE oauth_access_token (
    token_id VARCHAR(256),
    token LONGVARBINARY,
    authentication_id VARCHAR(256),
    user_name VARCHAR(256),
    client_id VARCHAR(256),
    authentication LONGVARBINARY,
    refresh_token VARCHAR(256)
);

-- Refresh tokens
CREATE TABLE oauth_refresh_token (
    token_id VARCHAR(256),
    token LONGVARBINARY,
    authentication LONGVARBINARY
);
