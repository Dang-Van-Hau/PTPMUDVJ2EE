INSERT INTO role(name)
SELECT 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'ADMIN');

INSERT INTO role(name)
SELECT 'STUDENT'
WHERE NOT EXISTS (SELECT 1 FROM role WHERE name = 'STUDENT');

INSERT INTO student(username, password, email)
SELECT 'admin', '', 'admin@example.com'
WHERE NOT EXISTS (SELECT 1 FROM student WHERE username = 'admin');

INSERT INTO student(username, password, email)
SELECT 'student', '', 'student@example.com'
WHERE NOT EXISTS (SELECT 1 FROM student WHERE username = 'student');

INSERT INTO student_role(student_id, role_id)
SELECT s.student_id, r.role_id
FROM student s
JOIN role r ON r.name = 'ADMIN'
WHERE s.username = 'admin'
  AND NOT EXISTS (
      SELECT 1
      FROM student_role sr
      WHERE sr.student_id = s.student_id
        AND sr.role_id = r.role_id
  );

INSERT INTO student_role(student_id, role_id)
SELECT s.student_id, r.role_id
FROM student s
JOIN role r ON r.name = 'STUDENT'
WHERE s.username = 'student'
  AND NOT EXISTS (
      SELECT 1
      FROM student_role sr
      WHERE sr.student_id = s.student_id
        AND sr.role_id = r.role_id
  );

INSERT INTO category(name)
SELECT 'Programming'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = 'Programming');

INSERT INTO category(name)
SELECT 'Database'
WHERE NOT EXISTS (SELECT 1 FROM category WHERE name = 'Database');

INSERT INTO course(name, image, credits, lecturer, category_id)
SELECT 'Java Basics', NULL, 3, 'John Doe', c.id
FROM category c
WHERE c.name = 'Programming'
  AND NOT EXISTS (SELECT 1 FROM course WHERE name = 'Java Basics');

INSERT INTO course(name, image, credits, lecturer, category_id)
SELECT 'MySQL Fundamentals', NULL, 3, 'Jane Smith', c.id
FROM category c
WHERE c.name = 'Database'
  AND NOT EXISTS (SELECT 1 FROM course WHERE name = 'MySQL Fundamentals');
