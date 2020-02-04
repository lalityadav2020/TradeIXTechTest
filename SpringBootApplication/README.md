### Implemented Spring Boot Application
##### This a Java project which can be Used to Create, Update, Delete Pet.

###### The logic is present in package com.example.demo in src/main folder
###### the tests are present in DemoApplicationTests.java in src/test folder

**To Run this project**

`Pre-requisites-`
######JDK 1.8
######Maven

**To Run The Project**
* First Run 'mvn clean install'

* To start the service Run DemoApplication.java will start on 8090 port
`To Create new Pet`
##### POST - http://localhost:8090/pet
##### Request Body Json Example is present at the end

###### Operations supported

`To Get all Pets`
GET - http://localhost:8090/pet

`To get Pet By ID`
GET - http://localhost:8090/pet/{id}

`To update Pet detail`
PUT - http://localhost:8090/pet/{id}

`To delete Pet`
DELETE - http://localhost:8090/pet/{id}


### TO Run the Tests

mvn test


######Request Body Json Example
{
  "categoryName": "update",
  "petName": "doggie",
  "photoUrl":"",
  "tagName": "new",
  "status": "AVAILABLE"
}