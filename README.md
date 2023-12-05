1.Name of project: task-manager-api-spring-boot-postgresql-reactjs

2.Launch of project:
2.1 backend part:
  Build:
      docker-compose build
  Run:
      docker-compose up
2.2 frontend part:
    Build:
       \src\frontend-react\java-learn-app-main>npm install 
    Run:
       \src\frontend-react\java-learn-app-main>npm start

3.Ports of the project:
backend: http://localhost:8081
frontend: http://localhost:3000

4.Start page: http://localhost:3000

5.Logins/passwords/roles of users:
   admin/admin/ROLE_ADMIN,
   denis/user/ROLE_USER,
   peter/user/ROLE_USER,
   asya/user/ROLE_USER,
   jimmy/user/ROLE_USER,
   maricel/user/ROLE_USER

6.Configuration: resources/application.properties

7.Database scripts: resources/data.sql

8.Database PostgreSQL connection:
     Name: taskdb@localhost
     User: denmit
     Password: 1981
     Port: 5432

9.Rest controllers:

UserController:
registerUser(POST): http://localhost:8081 + body;
authenticationUser(POST): http://localhost:8081/auth + body
getAllExecutors(GET): http://localhost:8081/executors

TaskController:
save(POST): http://localhost:8081/tasks + body;
getAll(GET): http://localhost:8081/tasks;
getById(GET): http://localhost:8081/tasks/{taskId};
update(PUT): http://localhost:8081/tasks/{taskId};
delete(DELETE): http://localhost:8081/tasks/{taskId};
getTotalAmount(GET): http://localhost:8081/tasks/total;


