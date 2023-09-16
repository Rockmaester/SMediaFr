use smedia_db;

update users set password = MD5(password);