###Here you can find all the instructions to run this project

#### Database setup
- First you need to connect to your MySQL database. Then
 you need to run my DDL script located in **/src/main/resources/**
and called **initSqlScript.sql** to create schemas
- Then you have to configure application properties. You can delete line
    ```.properties
    spring.profiles.active=local
  ```
  and enter your MySQL credentials in **application.properties**: 
  ```.properties
    spring.datasource.username=
    spring.datasource.password=
  ```
  or you can create
  your own spring profile, copy all the properties and set is as active.
- Make sure that your datasource URL is the same as in properties or change
  the property:
  ```.properties
    spring.datasource.url=jdbc:mysql://localhost:3306/restaurant?allowPublicKeyRetrieval=true&useSSL=false
  ```