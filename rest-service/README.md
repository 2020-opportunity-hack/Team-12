
# Spring RESTful API 

## Dependencies:
  - Spring Web
  - MySql Driver
  - Spring Data JPA

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and test system.

### Running in IntelliJ :   Option 1 : Git Import (Easy , Hassle Free)
A step by step series of examples that tell you how to get a development env running

- Clone the project
```
1. Click on Get from Version Control
2. Type https://github.com/2020-opportunity-hack/Team-12/
3. Click on Next
4. Click on Import Maven Project pop up to resolve dependencies

```
- Open the project in IntelliJ

- Go to `File` &#8594; `Project Structure`
    - &#8600;`Project`
         - Set `Project SDK` to 11 
         - Set  `Project level language` to 11
  
### Running in IntelliJ:    Option 2: (Import from zip)

Steps to getting Started
  1. Install Intellij Ultimate 2020
  2. Unzip rest-service
  3. Start Intellij -> Click on Import Project
  4. Click on Browse -> rest-service -> pom.xml
  5. Click on Open as Project
  
### Configuring JDBC Connection
  1. Goto resources -> application.properties
  
#### Define the below parameters as per your db settings
  1. spring.jpa.hibernate.ddl-auto= none
  2. spring.datasource.url = jdbc:mysql://localhost:3306/sunday_schema
  3. spring.datasource.username= root
  4. spring.datasource.password = password
    
### Running  the Application  
  1. go to Main ->  and Run the Application
 
 
### Sample API:
  GET Request : http://localhost:8080/users
 
