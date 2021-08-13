### Local Spring Boot Server working with MongoDB! 
> \(*Coded and Executed in IntelliJ Gradle project structure*\)

> Author : cthacker-udel (cthacker@udel.edu)

---
**Important files**:

Main Configuration:
* [Mongo Configuration](https://github.com/cthacker-udel/Springboot-MongoDB-Server/blob/master/src/main/java/com/example/firstserver/AppConfig.java)
* [Main Runner\(*initiates server*)](https://github.com/cthacker-udel/Springboot-MongoDB-Server/blob/master/src/main/java/com/example/firstserver/FirstServerApplication.java)
* [Example of CommandLineRunner implementation](https://github.com/cthacker-udel/Springboot-MongoDB-Server/blob/master/src/main/java/com/example/firstserver/AppRunner.java)

---

**MongoDB <---> Spring Boot Files**

Class Structure:
* [Admin Class](https://github.com/cthacker-udel/Springboot-MongoDB-Server/blob/master/src/main/java/com/example/firstserver/Admin.java)
* [Employee Class](https://github.com/cthacker-udel/Springboot-MongoDB-Server/blob/master/src/main/java/com/example/firstserver/Employee.java)
* [Soccer Player Class](https://github.com/cthacker-udel/Springboot-MongoDB-Server/blob/master/src/main/java/com/example/firstserver/SoccerPlayer.java)
* [Defender Class](https://github.com/cthacker-udel/Springboot-MongoDB-Server/blob/master/src/main/java/com/example/firstserver/Defender.java)
* [User Class](https://github.com/cthacker-udel/Springboot-MongoDB-Server/blob/master/src/main/java/com/example/firstserver/User.java)

Rest API Controllers:

* [Admin Controller](https://github.com/cthacker-udel/Springboot-MongoDB-Server/blob/master/src/main/java/com/example/firstserver/AdminController.java)\(*execute functions with admin documents on database*\)
* [Employee Controller](https://github.com/cthacker-udel/Springboot-MongoDB-Server/blob/master/src/main/java/com/example/firstserver/EmployeeController.java)\(*execute functions with employee documents on database*\)
* [Server Controller](https://github.com/cthacker-udel/Springboot-MongoDB-Server/blob/master/src/main/java/com/example/firstserver/ServerController.java)\(*make direct server requests*\)
* [User Controller](https://github.com/cthacker-udel/Springboot-MongoDB-Server/blob/master/src/main/java/com/example/firstserver/UserController.java)\(*execute functions with user documents on database\(uses auth\)*\)

Rest API Repositories\(*Spring Boot Interface Repository implementation*\):

* [Employee Repository](https://github.com/cthacker-udel/Springboot-MongoDB-Server/blob/master/src/main/java/com/example/firstserver/EmployeeRepository.java)
* [Soccer Player Repository](https://github.com/cthacker-udel/Springboot-MongoDB-Server/blob/master/src/main/java/com/example/firstserver/SoccerPlayerRepository.java)
* [User Repository](https://github.com/cthacker-udel/Springboot-MongoDB-Server/blob/master/src/main/java/com/example/firstserver/UserRepository.java)

Custom API Error Response:

* [Api Error Class](https://github.com/cthacker-udel/Springboot-MongoDB-Server/blob/master/src/main/java/com/example/firstserver/ApiError.java)
