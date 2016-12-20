# Open Source Business Application
Demo application with **[Spring Boot](https://projects.spring.io/spring-boot/)** and **[Eclipse Scout](http://www.eclipse.org/scout/) UI**

**Please Note**: This is work in progress and has not (yet) been tested for productive usage.

## Application Description
Minimal but fully functioning business application
* Business use case: Task management
* Administration use case: User, role and permission management
* Interfaces: Both UI and REST API

## Technologies per Component
* Main frameworks: Spring Boot and Eclipse Scout
* Authentication and authorization: Servlet filters, java.security, Eclipse Scout
* Internationalization: Eclipse Scout
* Persistence: javax.persistence, Spring Data JPA, Hibernate, H2
* Validation: javax.validation
* Business logic: Plain Java: Independent of Spring and Scout
* User interface: Eclipse Scout
* REST API: Spring

## Implemented Features / Status
* Spring and Scout integration
* Business logic
* Persistences for tasks, users and roles
* Authorization and authentication (for UI)
* User interface for tasks and admin
* REST API (readonly, no authentication so far)

## Roadmap
* Add authentication for REST services
* Add tests and Travis CI
* Dockerize application with multi-container setup: Data, DB, Application
* Use PostgreSQL for realistic setup

View [README](org.eclipse.scout.springboot/README.txt) for more information
