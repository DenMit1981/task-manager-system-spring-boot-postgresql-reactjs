INSERT INTO users(id, login, password, role) VALUES(1, 'denis', '$2a$10$CYYohB8EuqXQz4EFJyfLh.S3My3oBbOhoM6xd6ppox51whg/RyD2m', 'ROLE_USER'), (2, 'peter', '$2a$10$CYYohB8EuqXQz4EFJyfLh.S3My3oBbOhoM6xd6ppox51whg/RyD2m', 'ROLE_USER'), (3, 'asya', '$2a$10$CYYohB8EuqXQz4EFJyfLh.S3My3oBbOhoM6xd6ppox51whg/RyD2m', 'ROLE_USER'), (4, 'jimmy', '$2a$10$CYYohB8EuqXQz4EFJyfLh.S3My3oBbOhoM6xd6ppox51whg/RyD2m', 'ROLE_USER'), (5, 'maricel', '$2a$10$CYYohB8EuqXQz4EFJyfLh.S3My3oBbOhoM6xd6ppox51whg/RyD2m', 'ROLE_USER'), (6, 'admin', '$2a$10$PbmapvvL/qPQesatobBLOeYiTNaHimcTNwljtcjSqyDHtjkJe8K6u', 'ROLE_ADMIN');
SELECT setval('user_id_seq', (SELECT MAX(id) from users));

INSERT INTO tasks(id, title, description, creation_date, last_modified_date, status) VALUES (1, 'Task1', 'Something needs to do in this task', '2023-04-04', '2023-05-04', 'DONE'), (2, 'Task2', 'Something needs to do in this task', '2023-07-05', '2023-07-25', 'ACCEPTED'), (3, 'Task3', 'Something needs to do in this task', '2023-08-05', '2023-09-25', 'DONE'), (4, 'Task4', 'Something needs to do in this task', '2023-09-14', '2023-10-14', 'DONE'), (5, 'Task5', 'Something needs to do in this task', '2023-10-05', '2023-10-15', 'ACCEPTED'), (6, 'Task6', 'Something needs to do in this task', '2023-10-18', '2023-10-20', 'NEW'), (7, 'Task7', 'Something needs to do in this task', '2023-10-19', '2023-10-19', 'NEW');
SELECT setval('task_id_seq', (SELECT MAX(id) from tasks));

UPDATE tasks SET admin_id= 6, executor_id = 1 WHERE id = 1;
UPDATE tasks SET admin_id= 6, executor_id = 1 WHERE id = 2;
UPDATE tasks SET admin_id= 6, executor_id = 2 WHERE id = 3;
UPDATE tasks SET admin_id= 6, executor_id = 4 WHERE id = 4;
UPDATE tasks SET admin_id= 6, executor_id = 2 WHERE id = 5;
UPDATE tasks SET admin_id= 6, executor_id = 5 WHERE id = 6;
UPDATE tasks SET admin_id= 6, executor_id = 2 WHERE id = 7;