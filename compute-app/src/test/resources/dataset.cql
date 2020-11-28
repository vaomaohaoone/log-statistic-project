CREATE KEYSPACE IF NOT EXISTS linux_logs WITH REPLICATION = {'class' : 'SimpleStrategy', 'replication_factor' : 1};
DROP TABLE IF EXISTS linux_logs.logs;
CREATE TABLE IF NOT EXISTS linux_logs.logs (id int PRIMARY KEY, timestamp timestamp, host text, process text, level int, path text);

INSERT INTO linux_logs.logs (id, timestamp, host, process, level, path)
values (1, '2020-11-20T00:05:00', 'localhost', 'root', 0, '/var/log/app.log');
INSERT INTO linux_logs.logs (id, timestamp, host, process, level, path)
values (2, '2020-11-20T00:07:00', 'localhost', 'root', 1, '/var/log/app.log');
INSERT INTO linux_logs.logs (id, timestamp, host, process, level, path)
values (3, '2020-11-20T00:16:00', 'localhost', 'root', 1, '/var/log/app.log');
INSERT INTO linux_logs.logs (id, timestamp, host, process, level, path)
values (4, '2020-11-20T00:23:00', 'localhost', 'root', 1, '/var/log/app.log');
INSERT INTO linux_logs.logs (id, timestamp, host, process, level, path)
values (5, '2020-11-20T00:26:00', 'localhost', 'root', 2, '/var/log/app.log');
INSERT INTO linux_logs.logs (id, timestamp, host, process, level, path)
values (6, '2020-11-20T00:31:00', 'localhost', 'root', 3, '/var/log/app.log');
INSERT INTO linux_logs.logs (id, timestamp, host, process, level, path)
values (7, '2020-11-20T00:34:00', 'localhost', 'root', 3, '/var/log/app.log');
INSERT INTO linux_logs.logs (id, timestamp, host, process, level, path)
values (8, '2020-11-20T00:45:00', 'localhost', 'root', 3, '/var/log/app.log');
INSERT INTO linux_logs.logs (id, timestamp, host, process, level, path)
values (9, '2020-11-20T00:59:00', 'localhost', 'root', 4, '/var/log/app.log');