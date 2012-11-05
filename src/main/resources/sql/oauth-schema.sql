-- User details
CREATE TABLE users (
    username VARCHAR(256) NOT NULL PRIMARY KEY,
    password VARCHAR(256) NOT NULL,
    authorities VARCHAR(1024) NOT NULL,
    deleted TINYINT);
    