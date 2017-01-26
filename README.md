

Для сборки и запуска сервера run.bat или run.sh. 
http://localhost:8080/list - получить список пользователей
POST http://localhost:8080/add - добавить пользователя

JSON
{
  "login": "Alex123",
  "name":"Alexander",
  "password": "Alex123", "roles": [1, 2, 3]
} 

PUT http://localhost:8080/edit - отредактировать пользователя

{
  "id":2,
	"login": "Alex123",
  "name":"Alexander",
  "password": "Alex123", "roles": [1]
}

DELETE http://localhost:8080/delete - удалить пользователя

{
	"id": 2
}

GET http://localhost:8080/get - получить пользователя

{
	"id": 2
}
