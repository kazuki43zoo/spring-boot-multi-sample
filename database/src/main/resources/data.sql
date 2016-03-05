DELETE FROM account WHERE username IN ('demo','admin','user','kazuki43zoo');
DELETE FROM oauth_client_details WHERE client_id IN ('sample-client');

INSERT INTO account (account_id, username, password, first_name, last_name, authorities)
VALUES ('ce3a2058-e278-11e5-9730-9a79f06e9478', 'demo', '$2a$10$oxSJl.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK', 'Taro', 'Yamada',
        'ROLE_USER');

INSERT INTO account (account_id, username, password, first_name, last_name, authorities)
VALUES ('ce3a2904-e278-11e5-9730-9a79f06e9478', 'admin', '$2a$10$VUYxczgalQeizViQ7Lg56.rIq.QrdBk76tyR25engY3Z93pNAkrk2', 'Jiro', 'Yamada',
        'ROLE_ADMIN,ROLE_USER');

INSERT INTO account (account_id, username, password, first_name, last_name, authorities)
VALUES ('ce3a2b98-e278-11e5-9730-9a79f06e9478', 'user', '$2a$10$VUYxczgalQeizViQ7Lg56.rIq.QrdBk76tyR25engY3Z93pNAkrk2', 'Saburo', 'Yamada',
        'ROLE_USER');

INSERT INTO account (account_id, username, password, first_name, last_name, authorities)
VALUES
  ('ce3a2d96-e278-11e5-9730-9a79f06e9478', 'kazuki43zoo', '$2a$10$VUYxczgalQeizViQ7Lg56.rIq.QrdBk76tyR25engY3Z93pNAkrk2', 'Kazuki', 'Shimizu',
   'ROLE_USER');


INSERT INTO oauth_client_details (client_id, resource_ids, client_secret, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
VALUES
  ('sample-client', 'oauth2-resource', '$2a$10$VUYxczgalQeizViQ7Lg56.rIq.QrdBk76tyR25engY3Z93pNAkrk2', 'read,write',
                    'authorization_code,password,client_credentials,implicit,refresh_token', NULL, NULL, NULL,
                    NULL, NULL, NULL);


COMMIT;
