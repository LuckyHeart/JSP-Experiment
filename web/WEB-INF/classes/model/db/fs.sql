CREATE TABLE fs
(
  id       INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name     VARCHAR(30)         NOT NULL,
  email    VARCHAR(30)         NOT NULL,
  nickname VARCHAR(30)         NOT NULL,
  password VARCHAR(30)         NOT NULL,
  INDEX (name),
  INDEX (password)
);