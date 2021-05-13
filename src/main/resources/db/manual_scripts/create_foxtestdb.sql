\set db_name foxtestdb
\set user_name foxtestdb
\set user_password 'foxtestdb'
CREATE DATABASE :db_name;
CREATE USER :user_name WITH ENCRYPTED PASSWORD :'user_password';
GRANT ALL PRIVILEGES ON DATABASE :db_name TO :user_name;
