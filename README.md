# Loan Rest API
Rest API for giving and extending loan.

## Motivation
This project was created as a job recruitment task. The main goal was to create rest API for system offering loans to customers. The application was supposed to offer two endpoints: one for requesting loans and another for prolonging the loan owned by customer.  
  
### Request for loan  
Part of the created system was a decision system. Based on defined rules a request for loan could be refused if loan was not within time or amount range. In case of this task there was a special rule as well that says that if loan is being asked in specified hour range and maximum allowed amount is asked then request should be refused.  
  
### Extending the loan  
Each loan has a prespecified extension term. Upon customer's request a payday is extended by term.  
  
### Additional notes  
As a general rule upon granting the loan its properties with relation to the customer should not change. Bank should not be able to change loan's properties owned by customer at will. Exception to this rule is extending the payday but this action is done at the request of the customer. Security issues, such as authentication, are outside of the scope of this task. All dates/time are in the same timezone.  
  
  
  
## Data model  
![resources/doc/db_schema.png](https://raw.githubusercontent.com/t4upl/Loan_Rest/master/src/main/resources/doc/db_schema.png)  
  
#### Tables:  
- Customer - Customers who can request a loan  
- Product - Thing a system sells i.e. loan  
- Product_type - Type of products that customer can request  
- Product_type_setting - Cross table between *product_type* and *setting* tables. Tells what is the value of setting (loan property) for chosen type of product (loan).  
- Setting - Types of properties that can describe the product (loan)  
- Data_type - A collection of Java types for casting text values from database  
- Product_setting - Cross table between *product* and *setting*. Tells what are the properties of the product (loan) owned by customer.  
  
Loan properties are modelled as generic table. Name of properties are stored in *Setting* table while values of the properties are stored in *Product_type_setting* table as text. Upon retrieving a property from database the application casts the property to Java type based on value from *Data_type* table. Generic solution was needed as loans may require a lot of variables specific to only chosen types of loans.  
  
## To Do  
- <del>Endpoint for applying the loan<del>  
- <del>Endpoint for extending the loan<del>  
- <del>Liquibase integration<del>  
- <del>Google checkstyle + clean-up<del>  
- <del>H2 branch<del>  
  
## Installation  
Use *master* branch. Project has two profiles: *h2* and *postgres*. They define a database which will project will be using.  
  
### H2  
H2 is in-memory database. Start application by running main in *SpringLoanRestAPI* class with *h2* profile. On start-up Liquibase will create data model and initialize it with test data. H2 console is located under *localhost:8080/h2*. H2 is non-persistent i.e. it lives only as application runs and is destroyed upon shutting down the application.  
  
### Postgres  
  
For running application requires a PostgreSQL database with role *username2*. Script for creating the role can be found in project under path: *\resources\sql\CreateUserAndDatabase.sql*. Do the maven clean install by running *mvn clean install* from terminal. Liquibase is configured so that upon running the *install* phase it will run scripts which handle creating database schema and populating it with test data. After preparing the database application should be started as typical Spring boot project from *SpringLoanRestApi* class.  
## How to use  
Application offers two endpoints:  
- */products* (POST) - for creating a new loan. Example request body is located in *test/json/product_request_dto.json*.  
- */products/{id}* (PATCH) - for extending the loan. Id in the path specifies the id of the loan in the database (in *Product* table).  
  
## Technologies  
  
- Java  
- Spring Boot  
- Maven  
- JUnit  
- Mockito  
- PostgreSQL  
- Liquibase  
- Lombok  
- H2  
## Could haves  
Additional features that could have been but will not be implemented as part of this task:  
1. Loan versioning  
2. Authentication  
  
**Loan versioning**
Current data model does not allow a loan versioning. If bank officer would like to update any of the properties describing the existing loan it would mean that new loan with different properties should be added to the database. Loan with new properties would then become available for sell and loan with outdated properties would be out of offer. The old loan would be still available as read-only for customers who already own the the loan with outdated properties. The project in current state does not implement decommissioning of loans.  
  
In real life scenario it would be much better idea to have a loan versioning. Upon switching some properties of the existing loan a new version of the loan would be added to the database. Only the newest version of the loan could be requested by customer. Customer upon getting a loan would receive a type of loan along with its version. This way customer could find out the loan properties at the moment they requested it. Versioning would solve a problem of decommissioning outdated loans since only the loan in highest version would be available for sell.  
  
**Authentication**
In real life system an authentication of customer by system would be required before applying for the loan. As authentication was outside of the scope of the task it was not implemented.
