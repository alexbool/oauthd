-- User details
CREATE TABLE users (
    uid         BINARY(16)      NOT NULL PRIMARY KEY,
    username    VARCHAR(256)    NOT NULL,
    password    VARCHAR(256)    NOT NULL,
    authorities VARCHAR(1024)   NOT NULL,
    deleted     TINYINT,

    UNIQUE (username)
);

-- Client details
CREATE TABLE oauth_client_details (
    client_id               VARCHAR(256) PRIMARY KEY,
    resource_ids            VARCHAR(256),
    client_secret           VARCHAR(256),
    scope                   VARCHAR(256),
    authorized_grant_types  VARCHAR(256),
    web_server_redirect_uri VARCHAR(256),
    authorities             VARCHAR(256),
    access_token_validity   INTEGER,
    refresh_token_validity  INTEGER,
    additional_information  VARCHAR(4096)
);

-- Access tokens
CREATE TABLE oauth_access_token (
    token_id            VARCHAR(256) PRIMARY KEY,
    token               LONGVARBINARY,
    authentication_id   VARCHAR(256),
    user_name           VARCHAR(256),
    client_id           VARCHAR(256),
    authentication      LONGVARBINARY,
    refresh_token       VARCHAR(256)
);

-- Refresh tokens
CREATE TABLE oauth_refresh_token (
    token_id        VARCHAR(256) PRIMARY KEY,
    token           LONGVARBINARY,
    authentication  LONGVARBINARY
);
