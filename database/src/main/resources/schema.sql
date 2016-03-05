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
  account_id  VARCHAR(36),
  username    VARCHAR(128) NOT NULL,
  password    VARCHAR(60)  NOT NULL,
  first_name  VARCHAR(128),
  last_name   VARCHAR(128),
  authorities TEXT,
  CONSTRAINT pk_account PRIMARY KEY (account_id),
  CONSTRAINT uk_account UNIQUE (username)
);

CREATE TABLE IF NOT EXISTS oauth_client_details (
  client_id               VARCHAR(256),
  resource_ids            TEXT,
  client_secret           VARCHAR(60) NOT NULL,
  scope                   TEXT,
  authorized_grant_types  TEXT,
  web_server_redirect_uri VARCHAR(256),
  authorities             TEXT,
  access_token_validity   INTEGER,
  refresh_token_validity  INTEGER,
  additional_information  TEXT,
  autoapprove             TEXT,
  CONSTRAINT pk_oauth_client_details PRIMARY KEY (client_id)
);
