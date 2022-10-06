# GiftCertificate
---

GiftCertificate Application is a second part of a training project described in [here.](https://github.com/mjc-school/MJC-School/blob/old/stage%20%233/java/module%20%233.%20REST%20API%20Advanced/rest_api_advanced.md)
First module of the project is available [here.](https://github.com/mjc-school/MJC-School/blob/old/stage%20%233/java/module%20%232.%20REST%20API%20Basics/rest_api_basics_task.md)

Application is designed to manage a database of gift certificates, tags, users and orders. Every gift certificate can be described with multiple tags. Users can place an order of chosen gift certificates.

## 1. Functionalities
***

### 1.1. Gift certificates (with pagination and HATEOAS)

+ get all (optionally **page**, optionally **size**) - GET   
  e.g. http://localhost:8085/gift_certificates?page=1&size=5
+ get by **id** - GET   
  e.g. http://localhost:808/gift_certificates/5
+ get by param (optionally **page**, optionally **size**, optionally **sort**, optionally **direction**,  optionally **key**, optionally **tags**) - GET   
  e.g. http://localhost:8085/gift_certificates?page=1&size=5&sort=name&direction=asc&key=example&tags=shopping,fun
+ add gift certificate - POST   
  http://localhost:8085/gift_certificates
+ update gift certificate - PUT   
  http://localhost:8085/gift_certificates
+ delete gift certificate by **id** - DELETE   
  e.g. http://localhost:8085/gift_certificates/5

### 1.2. Tags (with pagination and HATEOAS)
+ get all (optionally **page**, optionally **size**) - GET   
  e.g. http://localhost:8085/tags?page=1&size=5
+ get by **id** - GET   
  e.g. http://localhost:8085/tags/5
+ get by param (optionally **page**, optionally **size**, optionally **id**, optionally **name**) - GET   
  e.g. http://localhost:8085/tags?page=1&size=5&name=fun
+ get the most widely tag for **user** - GET   
  e.g. http://localhost:8085/tags/popular
+ add tag - POST   
  http://localhost:8085/tags
+ delete tag by **id** - DELETE   
  e.g. http://localhost:8085/tags/5

### 1.2 Orders (with pagination and HATEOAS)
+ get all (optionally **page**, optionally **size**) - GET   
  e.g. http://localhost:8085/orders?page=1&size=5
+ get by **id** - GET   
  e.g. http://localhost:8085/orders/5
+ get by **user** (optionally **page**, optionally **size**) - GET   
  e.g. http://localhost:8085/orders?page=1&size=5&user=5
+ place an order - POST   
  http://localhost:8085/orders
+ update order - PUT   
  http://localhost:8085/orders
+ delete order by **id** - DELETE   
  e.g. http://localhost:8085/orders/5

### 1.3 Users (with pagination and HATEOAS)
+ get all (optionally **page**, optionally **size**) - GET   
  e.g. http://localhost:8085/users?page=1&size=5
+ get by **id** - GET   
  e.g. http://localhost:8085/users/5
+ get by **nickname** - GET   
  e.g. http://localhost:8085/users?name=buddy

## 2. Technology stack
***

GiftCertificate uses following technologies:
1. JDK v.1.8.0_311
2. Maven v.3.8.4
3. Spring Framework v.5.3.21
4. Spring Boot Framework v.2.7.3
5. Hibernate JPA v.1.0.2
6. PostgreSQL JDBC v.42.3.6 and H2 v.2.1.214
7. JUnit v.5.9.0-M1, AssertJ v.3.23.1, Mockito v.4.6.1


## 3. Prerequisites
***

Using GiftCertificate requires:
1. Intellij IDEA v.2022.1
2. PostgreSQL
3. Postman / SOAP

## 4. Build and run application
***

### 4.1. Run tests
+ from terminal:
  `mvn verify`

### 4.2. Run application
+ from terminal in `web` directory:
  `mvn spring-boot:run`
+ from Intellij IDEA:
  `Shift + F10`

## 5. Requirements and Restrictions
***

### 5.1. Business requirements

- [X] Develop web service for Gift Certificates system with the following entities (many-to-many)
  - [X] CreateDate, LastUpdateDate - format ISO 8601 (https://en.wikipedia.org/wiki/ISO_8601). Example: 2018-08-29T06:12:15.156. More discussion here: https://stackoverflow.com/questions/3914404/how-to-get-current-moment-in-iso-8601-format-with-date-hour-and-minute
  - [X] Duration - in days (expiration period)
- [X] The system should expose REST APIs to perform the following operations:
  - [X] CRUD operations for GiftCertificate. If new tags are passed during creation/modification – they should be created in the DB. For update operation - update only fields, that pass in request, others should not be updated. Batch insert is out of scope.
  - [X] CRD operations for Tag.
  - [X] Get certificates with tags (all params are optional and can be used in conjunction):
    - [X] by tag name (ONE tag)
    - [X] search by part of name/description (can be implemented, using DB function call)
    - [X] sort by date or by name ASC/DESC (extra task: implement ability to apply both sort type at the same time).
  - [X] Change single field of gift certificate (e.g. implement the possibility to change only duration of a certificate or only price).
  - [ ] Add new entity User and implement only get operations for user entity.
  - [ ] Make an order on gift certificate for a user (user should have an ability to buy a certificate).
  - [ ] Get information about user’s order: cost and timestamp of a purchase.
    - [ ] The order cost should not be changed if the price of the gift certificate is changed.
  - [ ] Get the most widely used tag of a user with the highest cost of all orders.
    - [ ] Create separate endpoint for this query.
  - [X] Search for gift certificates by several tags (“and” condition).
  - [X] Pagination should be implemented for all GET endpoints. Create a flexible and non-erroneous solution. Handle all exceptional cases.
  - [X] Support HATEOAS on REST endpoints.

### 5.2. Application requirements

- [X] JDK version: 8 – use Streams, java.time.*, etc. where it is possible. (the JDK version can be increased in agreement with the mentor/group coordinator/run coordinator)
- [X] Application packages root: com.epam.esm
- [X] Use transactions where it’s necessary.
- [X] Java Code Convention is mandatory (exception: margin size – 120 chars).
- [X] Build tool: Maven/Gradle, latest version. Multi-module project.
- [X] Web server: Apache Tomcat/Jetty.
- [X] Application container: Spring IoC. Spring Framework, the latest version.
- [X] Database: PostgreSQL/MySQL, latest version.
- [X] Testing: JUnit 5.+, Mockito.
- [ ] Service layer should be covered with unit tests not less than 80%.
- [X] Hibernate should be used as a JPA implementation for data access.
- [X] Audit data should be populated using JPA features.

### 5.3. General requirements
- [X] Code should be clean and should not contain any “developer-purpose” constructions.
- [X] App should be designed and written with respect to OOD and SOLID principles.
- [X] Code should contain valuable comments where appropriate.
- [X] Public APIs should be documented (Javadoc).
- [X] Clear layered structure should be used with responsibilities of each application layer defined.
- [X] JSON should be used as a format of client-server communication messages.
- [X] Convenient error/exception handling mechanism should be implemented: all errors should be meaningful and localized on backend side.
- [X] Abstraction should be used everywhere to avoid code duplication.
- [X] Several configurations should be implemented (at least two - dev and prod).

### 5.4. Restrictions
- [X] Hibernate specific features.
- [X] Spring Data.


