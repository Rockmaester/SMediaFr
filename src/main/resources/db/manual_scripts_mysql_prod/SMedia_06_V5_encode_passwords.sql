use socialmedia_db;

update users set password = MD5(password);