# GiftCertificate
---

GiftCertificate Application is a second part of a training project described in [here.](https://github.com/mjc-school/MJC-School/blob/old/stage%20%233/java/module%20%233.%20REST%20API%20Advanced/rest_api_advanced.md)
First module of the project is available [here.](https://github.com/mjc-school/MJC-School/blob/old/stage%20%233/java/module%20%232.%20REST%20API%20Basics/rest_api_basics_task.md)

Application is designed to manage a database of gift certificates, tags, users and orders. Every gift certificate can be described with multiple tags. Users can place an order of chosen gift certificates.

## 1. Functionalities
***

### 1.1. Searching gift certificates:
+ find all  
  http://localhost:8080/web/gift_certificates
+ find by **key** word in name or description
  http://localhost:8080/web/gift_certificates/key/key
+ find by **id**  
  http://localhost:8080/web/gift_certificates/id
+ find by **tag**  
  http://localhost:8080/web/gift_certificates/tag/tag

### 1.2. Sorting gift certificates:
+ sort ascending by name  
  http://localhost:8080/web/gift_certificates/asc
+ sort descending by name  
  http://localhost:8080/web/gift_certificates/desc
+ sort ascending by date  
  http://localhost:8080/web/gift_certificates/asc/date
+ sort descending by date  
  http://localhost:8080/web/gift_certificates/desc/date

### 1.3. Adding gift certificate with tag
+ http://localhost:8080/web/gift_certificates

### 1.4. Updating gift certificate
+ http://localhost:8080/web/gift_certificates

### 1.5. Updating single filed of gift certificate
+ ???

### 1.6. Deleting gift certificate
+ http://localhost:8080/web/gift_certificates/id

### 1.7. Searching tags
+ find all   
  http://localhost:8080/web/tags
+ find by **id**   
  http://localhost:8080/web/tags/id
+ find by **name**  
  http://localhost:8080/web/tags/name/name

### 1.8. Adding tag
+ http://localhost:8080/web/tags/

### 1.9. Deleting tag
+ http://localhost:8080/web/tags/18

### 1.10. Deleting tag
+ http://localhost:8080/web/tags/18

### 1.11. Get user (by id, name, e-mail)
+ ???

### 1.12. Create order
+ ???

### 1.12. Create order
+ ???

### 1.13. Get order by user
+ ???

### 1.14. Get the most widely used tag of user with the highest cost of all orders
+ ???

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

- [ ] Develop web service for Gift Certificates system with the following entities (many-to-many)
  - [ ] CreateDate, LastUpdateDate - format ISO 8601 (https://en.wikipedia.org/wiki/ISO_8601). Example: 2018-08-29T06:12:15.156. More discussion here: https://stackoverflow.com/questions/3914404/how-to-get-current-moment-in-iso-8601-format-with-date-hour-and-minute
  - [ ] Duration - in days (expiration period)
- [ ] The system should expose REST APIs to perform the following operations:
  - [ ] CRUD operations for GiftCertificate. If new tags are passed during creation/modification – they should be created in the DB. For update operation - update only fields, that pass in request, others should not be updated. Batch insert is out of scope.
  - [ ] CRD operations for Tag.
  - [ ] Get certificates with tags (all params are optional and can be used in conjunction):
    - [ ] by tag name (ONE tag)
    - [ ] search by part of name/description (can be implemented, using DB function call)
    - [ ] sort by date or by name ASC/DESC (extra task: implement ability to apply both sort type at the same time).
  - [ ] Change single field of gift certificate (e.g. implement the possibility to change only duration of a certificate or only price).
  - [ ] Add new entity User and implement only get operations for user entity.
  - [ ] Make an order on gift certificate for a user (user should have an ability to buy a certificate).
  - [ ] Get information about user’s orders.
  - [ ] Get information about user’s order: cost and timestamp of a purchase.
    - [ ] The order cost should not be changed if the price of the gift certificate is changed.
  - [ ] Get the most widely used tag of a user with the highest cost of all orders.
    - [ ] Create separate endpoint for this query.
  - [ ] Search for gift certificates by several tags (“and” condition).
  - [ ] Pagination should be implemented for all GET endpoints. Create a flexible and non-erroneous solution. Handle all exceptional cases.
  - [ ] Support HATEOAS on REST endpoints.

### 5.2. Application requirements

- [ ] JDK version: 8 – use Streams, java.time.*, etc. where it is possible. (the JDK version can be increased in agreement with the mentor/group coordinator/run coordinator)
- [ ] Application packages root: com.epam.esm
- [ ] Use transactions where it’s necessary.
- [ ] Java Code Convention is mandatory (exception: margin size – 120 chars).
- [ ] Build tool: Maven/Gradle, latest version. Multi-module project.
- [ ] Web server: Apache Tomcat/Jetty.
- [ ] Application container: Spring IoC. Spring Framework, the latest version.
- [ ] Database: PostgreSQL/MySQL, latest version.
- [ ] Testing: JUnit 5.+, Mockito.
- [ ] Service layer should be covered with unit tests not less than 80%.
- [ ] Hibernate should be used as a JPA implementation for data access.
- [ ] Audit data should be populated using JPA features.

### 5.3. General requirements
- [ ] Code should be clean and should not contain any “developer-purpose” constructions.
- [ ] App should be designed and written with respect to OOD and SOLID principles.
- [ ] Code should contain valuable comments where appropriate.
- [ ] Public APIs should be documented (Javadoc).
- [ ] Clear layered structure should be used with responsibilities of each application layer defined.
- [ ] JSON should be used as a format of client-server communication messages.
- [ ] Convenient error/exception handling mechanism should be implemented: all errors should be meaningful and localized on backend side.
- [ ] Abstraction should be used everywhere to avoid code duplication.
- [ ] Several configurations should be implemented (at least two - dev and prod).

### 5.4. Restrictions
- [ ] Hibernate specific features.
- [ ] Spring Data.


