import groovy.sql.Sql

Sql sql = new Sql(connection);
sql.execute("CREATE TABLE users (id INT PRIMARY KEY, email VARCHAR(255) NOT NULL, password VARCHAR(255), enabled BOOLEAN DEFAULT 'TRUE')");
sql.execute("INSERT INTO users VALUES (1, 'cnelson@slac.com', 'pw', 'TRUE')");
