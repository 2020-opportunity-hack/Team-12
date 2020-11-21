

Spring RESTful API 

Dependencies:
  - Spring Web
  - MySql Driver
  - Spring Data JPA
  
  
Steps to getting Started
  1. Install Intellij Ultimate 2020
  2. Unzip rest-service
  3. Start Intellij -> Click on Import Project
  4. Click on Browse -> rest-service -> pom.xml
  5. Click on Open as Project
  
Set Java Environment to jdk 11
  1. Intellij -> Project Structure -> SDK set to 11 , Language set to 11
  
Configuring JDBC Connection
  1. Goto resources -> application.properties
  
Define the below parameters as per your db settings
  1. spring.jpa.hibernate.ddl-auto= none
  2. spring.datasource.url = jdbc:mysql://localhost:3306/sunday_schema
  3. spring.datasource.username= root
  4. spring.datasource.password = password
    
Run the Application  
  1. go to Main ->  and Run the Application
 
 
Sample API:
  GET Request : http://localhost:8080/users
 
