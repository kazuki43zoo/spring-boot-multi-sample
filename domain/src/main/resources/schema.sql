CREATE TABLE IF NOT EXISTS todo (
  todo_id       VARCHAR(36),
  username      VARCHAR(36) NOT NULL,
  todo_title    VARCHAR(30) NOT NULL,
  deadline_date DATE,
  finished      BOOLEAN,
  created_at    TIMESTAMP   NOT NULL,
  tracking_id   VARCHAR(36) NOT NULL,
  CONSTRAINT pk_todo PRIMARY KEY (todo_id)
);

CREATE TABLE IF NOT EXISTS account (
  account_id VARCHAR(36),
  username   VARCHAR(128),
  password   VARCHAR(60),
  first_name VARCHAR(128),
  last_name  VARCHAR(128),
  authorities      TEXT,
  CONSTRAINT pk_account PRIMARY KEY (account_id),
  CONSTRAINT uk_account UNIQUE (username)
);
