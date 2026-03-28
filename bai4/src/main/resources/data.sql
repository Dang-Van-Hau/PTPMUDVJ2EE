INSERT INTO role(name)
SELECT 'ROLE_ADMIN'
WHERE NOT EXISTS (
    SELECT 1 FROM role WHERE name = 'ROLE_ADMIN'
);

INSERT INTO role(name)
SELECT 'ROLE_USER'
WHERE NOT EXISTS (
    SELECT 1 FROM role WHERE name = 'ROLE_USER'
);

INSERT INTO account(login_name, password)
SELECT 'admin', '$2a$10$qCoFgUPTuiCvUCgAZlkfqOIOTr2vbBpcZ4jF9TxqfacMK2EL5crpG'
WHERE NOT EXISTS (
    SELECT 1 FROM account WHERE login_name = 'admin'
);

INSERT INTO account(login_name, password)
SELECT 'user', '$2a$10$qCoFgUPTuiCvUCgAZlkfqOIOTr2vbBpcZ4jF9TxqfacMK2EL5crpG'
WHERE NOT EXISTS (
    SELECT 1 FROM account WHERE login_name = 'user'
);

INSERT INTO account_role(account_id, role_id)
SELECT a.id, r.id
FROM account a
JOIN role r ON r.name = 'ROLE_ADMIN'
WHERE a.login_name = 'admin'
  AND NOT EXISTS (
      SELECT 1
      FROM account_role ar
      WHERE ar.account_id = a.id
        AND ar.role_id = r.id
  );

INSERT INTO account_role(account_id, role_id)
SELECT a.id, r.id
FROM account a
JOIN role r ON r.name = 'ROLE_USER'
WHERE a.login_name = 'user'
  AND NOT EXISTS (
      SELECT 1
      FROM account_role ar
      WHERE ar.account_id = a.id
        AND ar.role_id = r.id
  );

INSERT INTO product(name, price, description)
SELECT 'Laptop Dell', 1200, 'Office laptop'
WHERE NOT EXISTS (
    SELECT 1 FROM product WHERE name = 'Laptop Dell'
);

INSERT INTO product(name, price, description)
SELECT 'Phone Samsung', 800, 'Android smartphone'
WHERE NOT EXISTS (
    SELECT 1 FROM product WHERE name = 'Phone Samsung'
);
