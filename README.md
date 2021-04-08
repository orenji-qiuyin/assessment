# assessment

This module contains Spring boot based application with RESTful API support.
APIs functions include: create, update, search.

## Details assumptions
- api-key: The value of api key is placed in application.properties file. The key need to be passed in header, else the API will response with HTTP error code 401.
- create property: The API called by POST method with uri '/api/add'.
- update property: The API called by PATCH method with uri '/api/{id}'.
- approve property: The API called by PATCH method with uri '/api/patch/{id}'.
- get specific property: The API called by GET method with uri '/api/{id}'.
- search property list: The API called by GET method with uri '/api/search'.
- unit testing and integration testing are executed by JUnit.

## limitations for features
- for search's API, it can filter by sorting, page-number & page-size using URI and header.
  e.g.: http://localhost:8080/api/search?sort=name,desc (sorting)
        http://localhost:8080/api/search header--> Page-Number:1, Page-Size:10
- api-key is maintainable in application.properties file.
- some dynamic values (for set into columns in db) is maintained in API_Constants.java.
        
### Reference Articles: 

- [Building a RESTful Web Service]
(https://spring.io/guides/gs/rest-service/)
- [Accessing data with MySQL]
(https://spring.io/guides/gs/accessing-data-mysql/)
- [Spring Boot: Creating a filter to verify an API key header]
(https://shout.setfive.com/2020/03/20/spring-boot-creating-a-filter-to-verify-an-api-key-header/)
- [Get HTTP POST Body in Spring Boot with @RequestBody]
(https://stackabuse.com/get-http-post-body-in-spring/)
- [Spring Boot: how to design an efficient search REST API? ( with Live Demo )]
(https://medium.com/quick-code/spring-boot-how-to-design-efficient-search-rest-api-c3a678b693a0)
- [Using generated security password Spring Boot Security]
(https://www.yawintutor.com/using-generated-security-password-spring-boot/#:~:text=The%20log%20%E2%80%9CUsing%20generated%20security,of%20the%20spring%20boot%20application.)
- [How to run unit test with Maven]
(https://mkyong.com/maven/how-to-run-unit-test-with-maven/)
- [Unit Testing Rest Services with Spring Boot and JUnit]
(https://www.springboottutorial.com/unit-testing-for-spring-boot-rest-services)
- [How to test POST method in Spring boot using Mockito and JUnit]
(https://stackoverflow.com/questions/51346781/how-to-test-post-method-in-spring-boot-using-mockito-and-junit)
- [Integration Testing in Spring Boot]
(https://dzone.com/articles/integration-testing-in-spring-boot-1)

### Build the Project
```
mvn clean install
mvn spring-boot:run
```

### Set up MySQL
```
mysql -u root -p 
> CREATE USER 'springuser'@'%' IDENTIFIED BY 'ThePassword';
> GRANT ALL PRIVILEGES ON db_property.* TO 'springuser'@'%';
> FLUSH PRIVILEGES;
```
