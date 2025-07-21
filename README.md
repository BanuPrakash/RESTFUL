# Building RESTful WS using Spring Boot 3.5

```
Banu Prakash C
Full Stack Architect, Corporate Trainer
Co-founder & CTO: Lucida Technologies Pvt Ltd.,
Email: banuprakashc@yahoo.co.in; banuprakash.cr@gmail.com;
https://www.linkedin.com/in/banu-prakash-50416019/
https://github.com/BanuPrakash/RESTFUL

===================================

Softwares Required:
1) openJDK 21
https://jdk.java.net/java-se-ri/21

 For Mac machine USE SDKMAN to manage java

curl -s "https://get.sdkman.io" | bash

sdk install java 21.0.6-tem

sdk default java 21.0.6-tem 

https://mydeveloperplanet.com/2022/04/05/how-to-manage-your-jdks-with-sdkman/#:~:text=Some%20time%20ago%2C%20a%20colleague%20of%20mine,maintain%20different%20versions%20of%20JDKs%2C%20Maven%2C%20etc.


2) IntelliJ Ultimate edition https://www.jetbrains.com/idea/download/?section=mac

3) MySQL  [ Prefer on Docker]

Install Docker Desktop

Docker steps:

a) docker pull mysql
b) docker run --name local-mysql –p 3306:3306 -e MYSQL_ROOT_PASSWORD=Welcome123 -d mysql

container name given here is "local-mysql"

For Mac:
docker run -p 3306:3306 -d --name local-mysql -e MYSQL_ROOT_PASSWORD=Welcome123 mysql


c) CONNECT TO A MYSQL RUNNING CONTAINER:

$ docker exec -t -i local-mysql bash

d) Run MySQL client:

bash terminal> mysql -u "root" -p

mysql> exit

```
Contents to Cover:
* Spring and Spring Boot Framework introduction
* RESTful WS with Spring Data JPA / ORM 
* AOP
* Exception Handling and Validation
* Unit Testing
* Caching
* HATEOAS
* ASYNC
* Security
* MicroServices

==========================

Spring Framework: provides a Container to support life cycle management of beans and wiring.

Bean: any object managed by Spring framework

Spring Framework uses metadata -- XML or Annotation

Spring instantiates classes which has one of these annotations at class level:
* @Component
* @Repository: exception transalation
https://github.com/spring-projects/spring-framework/blob/main/spring-jdbc/src/main/resources/org/springframework/jdbc/support/sql-error-codes.xml
```
    try {

        // Code
    } catch(SQLException ex) {
        // for MySQL
        if(ex.getErrorCode() == 1062) {
            thrown new DuplicateKeyException(....);
        }
         // for Oracle
        if(ex.getErrorCode() == 1) {
            thrown new DuplicateKeyException(....);
        }
    }

```

* @Service
Transactional facade over Repository and Business logic
* @Configuration
* @Controller
* @RestController
* @ControllerAdvice
* @ShellComponent

Spring also uses Factory method to instantiate
* @Bean

Wiring:
* Field level or setter injection using @Autowired
* Constructor Dependency Injection

Spring Framework --> Inversion Of Control to acheive Dependency Injection
* Loose Coupling application
* Easy to test

```

UI --> Service --> Repository --> Database 

UI <-- Service <-- Repository <-- Database 

```

Spring Boot framework is built on top of Spring Framework: Highly Opiniated framework
* Lots of configuration comes out of the box
1) If we are using RDBMS --> Database Connection pool is configured out of box
If we are using ORM --> Hiberante is configured [Toplink / KODO / JDO / OpenJPA ...]
2) If we are building Web application
a) Embedded Tomcat container is configured [jetty / netty / .. are alternates]
b) Java to JSON, JSON to Java conversion is configured to use Jackson library [ Jettison / GSON / Moxy ..]

Spring boot 3x is built on top of Spring Framework 6

```

interface EmployeeRepo {
    void addEmployee(Employee e);
}

@Repository
public class EmployeeRepoDbImpl implements EmployeeRepo {
    public void addEmployee(Employee e) {
        ...
    }
}

@Service
public class AppService {
    @Autowired
    private EmployeeRepo employeeRepo; // no tight coupling

    public void doTask(Employee e) {
        employeeRepo.addEmployee(e);
    }
}


```

Issue - Required a single bean, instead found 2 beans

```
@Primary
@Repository
public class EmployeeRepoMongoImpl implements EmployeeRepo {
    public void addEmployee(Employee e) {
        ...
    }
}

```

Solutions :
1) @Primary

```

@Primary
@Repository
public class EmployeeRepoMongoImpl implements EmployeeRepo {

@Repository
public class EmployeeRepoDbImpl implements EmployeeRepo {


 @Autowired
    private EmployeeRepo employeeRepo; Wiring of employeeRepoMongoImpl is done
```

2) @Qualifer
 
```
   Note: remove @Primary

   @Repository
    public class EmployeeRepoMongoImpl implements EmployeeRepo {

    @Repository
    public class EmployeeRepoDbImpl implements EmployeeRepo { 

    
    @Service
    public class AppService {
    @Qualifer("employeeRepoMongoImpl")
        @Autowired
        private EmployeeRepo employeeRepo; 

     @Service
    public class AdminService {
        @Qualifer("employeeRepoDbImp")
        @Autowired
        private EmployeeRepo employeeRepo; 

```

3) @Profile

```
@Profile("prod")
@Repository
public class EmployeeRepoMongoImpl implements EmployeeRepo {

@Profile("dev")
@Repository
public class EmployeeRepoDbImpl implements EmployeeRepo {


     @Service
    public class AdminService {
        @Autowired
        private EmployeeRepo employeeRepo;  // Base on Profile dev or prod
```

4) @ConditionalOnMissingBean

```
@Repository
public class EmployeeRepoMongoImpl implements EmployeeRepo {

@ConditionalOnMissingBean("employeeRepoMongoImpl")
@Repository
public class EmployeeRepoDbImpl implements EmployeeRepo {


     @Service
    public class AdminService {
        @Autowired
        private EmployeeRepo employeeRepo; 

```
5) @ConditionalOnProperty


./mvnw dependency:tree


@SpringBootApplication in 3 in 1:
1) @Configuration
2) @ComponentScan
 defaults to scan "com.adobe.demo" and sub packages for annotations like @Component, @Repository,..
3) @EnableAutoConfiguration --> for out of the box configurations like Embedded Tomcat Servlet Container

SpringApplication.run() creates a spring container and initializes it
Spring Container can be accessed using BeanFactory or ApplicationContext 

Prefer below one if application is reading ENVIRONMENT varaiables
./mvnw spring-boot:run

resources/application.properties
spring.profiles.active=dev

===================

Factory method: @Bean
* Using 3rd party classes in Spring framework; they don't have @Component, @Service, .. annotation on those classes
* Object creation is complex

https://www.mchange.com/projects/c3p0/


========

Spring Bean Life Cycle:
* Instantiation
* Populate Properties: Inject dependencies; using @Autowired or Constructor DI
* Aware interface: BeanNameAware, BeanFactoryAware
BeanNameAware: Typical usage for logging purpose
BeanFactoryAware: can used to integrate spring bean with legacy code
* BeanPostProcessors -> Pre Initialization and Post Initialization
Use case --> Custom initialization logic.

Sample code for implementing Pub-Sub model.

* Publisher publishes an event
* Subscribes has to subscribe for a type of event

Check :Guava EventBus within a Spring Boot application for ready to use pub-sub

  @Lazy makes sure an proxy object is created instead of actual one.
  Actual instance is created only if we start using it.

=============

Spring Boot 3.5 improvements in reading environment variables.

Older way:

```
 
    @Value("{DRIVER}")
    String driver;
    @Value("{URL}")
    String url;
    AppConfig
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( driver); //loads the jdbc driver
        cpds.setJdbcUrl( url );


```

New way in Spring Boot 3.5 -- database credentials, API keys, token variables can be stored in environment variables, ConfigServer, Docker, Kubernetes, Vault making it more secure and dynamic

Example:
```
Set Environment Variables:
in terminal:

export DATABASE_CONFIG="
 DB_URL=jdbc:h2:mem:testdb
 DB_DRIVER=org.h2.Driver
 "
 % echo $DATABASE_CONFIG

application.properties
spring.config.import=env:DATABASE_CONFIG

 ./mvnw spring-boot:run

```

https://developer.hashicorp.com/vault

```
spring.config.import=vault://
spring.clound.vault.host:
spring.clound.vault.port:
spring.clound.vault.token:0000-0000-0000-0000

```

spring.config.import=optional:configserver:http://server.com:9141

===========================

Spring Shell CLI

```

<dependency>
            <groupId>org.springframework.shell</groupId>
            <artifactId>spring-shell-starter</artifactId>
            <version>3.0.0</version>
        </dependency>

@ShellComponent and @ShellMethod

shell:>hello
Hello World
shell:>bye
Bye User!!!
shell:>exit


```