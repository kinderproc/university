\set db_name foxdb
\set user_name foxdb
\set user_password 'foxdb'
CREATE DATABASE :db_name;
CREATE USER :user_name WITH ENCRYPTED PASSWORD :'user_password';
GRANT ALL PRIVILEGES ON DATABASE :db_name TO :user_name;
