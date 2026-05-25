@echo off
cd /d "C:\Program Files\MySQL\MySQL Server 8.0\bin"
mysql.exe -u root -proot -h 127.0.0.1 -P 3306 < "C:\Users\Cristian\OneDrive\Imagens\Postagem-de-Blog-master\Postagem-de-Blog-master\bloguinho.sql"

