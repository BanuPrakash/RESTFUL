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

Recap: @Component, @Repository, @Service, @Configuration, @Bean, @Autowired [avoid it] prefer Constructor DI, BeanPostProcessor, application.properties, @Value, @ConfigurationProperties [type-safe binding], @ShellComponent, @ShellCommand

@SpringBootApplication, ApplicationContext [interface to access Spring Container]

==================================

Building RESTful WS with JPA 

Java Persistence API is an abstraction on top of ORM [Object Relational Mapping]
ORM : Objects are mapped to tables of RDBMS;
class is mapped to database table
fields are mapped to columns of table

ORM generates DDL and DML statements 

Java ORM frameworks: Hibernate, TopLink, KODO, JDO, OpenJPA, ...

Spring Framework:
```
     @Bean("h2")
    public DataSource getDataSource() throws Exception{
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass( "org.h2.Driver"); //loads the jdbc driver
        cpds.setJdbcUrl( "jdbc:h2:mem:testdb");
        cpds.setUser("sa");
        cpds.setPassword("password");
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(20);
        return cpds;
    }

    @Bean
    public LocalContainerEntityManagerFactory getEmf(DataSource ds) {
        LocalContainerEntityManagerFactory emf = new LocalContainerEntityManagerFactory(ds);
        emf.setJpaVendor(new HibernateJpaVendor());
        emf.setPackagesToScan("com.adobe.prj.entity");
        ...
        return emf;
    }

```

Spring Boot, default is configured to use Spring Data Jpa module
Spring Data JPA module provides:
1) out of the box DataSource using HikariCP --> pool of DB connections based on entries present in application.properties
2) EntityManagerFactory is configured out of the box to use Hibernate as JPA Vendor
3) we need to just create interfaces, implementation classes [@Repository] are generated by Spring Data JPA.


```

% docker exec -it local-mysql bash
# mysql -u root -p
mysql> create database DB_REST;
mysql> use DB_REST;
mysql> create table products (id int PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), price double, qty int);

```

New Spring Boot application:
1) lombok for Code generation
2) mysql for JDBC
3) Spring Data JPA for Hibernate and JPA

CommandLineRunner is a Spring interface used to execute code after the Spring application context is fully initialized

https://docs.spring.io/spring-boot/appendix/application-properties/index.html

1) spring.jpa.hibernate.ddl-auto=create
create tables when application starts; drop them when application terminates
good for testing purpose only

2) spring.jpa.hibernate.ddl-auto=verify
map entities to existing tables; don't create new tables or alter them
good for Bottom to Top approach

3) spring.jpa.hibernate.ddl-auto=update
map to existing tables; if tables are not present create tables;
if required alter tables like size of column; insert new column
Good for Top Down approach

Settings: Build, Execution, Deployment, Compiler, Annotation Processor
select orderapp --> Obtain Processor from project classpath

==============

RESTful Web Services

```
<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
</dependency>

Including this dependency we get:
1) Embedded Tomcat Servlet Container [ alternates are Jetty / Netty / ..]
2) jackson library for Java <--> JSON [ GSON / Jettison / Moxy]
3) micrometer --> Observability
4) spring-webmvc module
Settings: Build, Execution, Deployment, Compiler, Annotation Processor
select orderapp --> Obtain Processor from project classpath

./mvnw dependency:tree

 org.springframework.boot:spring-boot-starter-web:jar:3.5.3:compile
[INFO] |  +- org.springframework.boot:spring-boot-starter-json:jar:3.5.3:compile
[INFO] |  |  +- com.fasterxml.jackson.core:jackson-databind:jar:2.19.1:compile
[INFO] |  |  |  +- com.fasterxml.jackson.core:jackson-annotations:jar:2.19.1:compile
[INFO] |  |  |  \- com.fasterxml.jackson.core:jackson-core:jar:2.19.1:compile
[INFO] |  |  +- com.fasterxml.jackson.datatype:jackson-datatype-jdk8:jar:2.19.1:compile
[INFO] |  |  +- com.fasterxml.jackson.datatype:jackson-datatype-jsr310:jar:2.19.1:compile
[INFO] |  |  \- com.fasterxml.jackson.module:jackson-module-parameter-names:jar:2.19.1:compile
[INFO] |  +- org.springframework.boot:spring-boot-starter-tomcat:jar:3.5.3:compile
[INFO] |  |  +- org.apache.tomcat.embed:tomcat-embed-core:jar:10.1.42:compile
[INFO] |  |  +- org.apache.tomcat.embed:tomcat-embed-el:jar:10.1.42:compile
[INFO] |  |  \- org.apache.tomcat.embed:tomcat-embed-websocket:jar:10.1.42:compile
[INFO] |  +- org.springframework:spring-web:jar:6.2.8:compile
[INFO] |  |  \- io.micrometer:micrometer-observation:jar:1.15.1:compile
[INFO] |  |     \- io.micrometer:micrometer-commons:jar:1.15.1:compile
[INFO] |  \- org.springframework:spring-webmvc:jar:6.2.8:compile
[INFO] |     \- org.springframework:spring-expression:jar:6.2.8:compile

```
REST --> Representation State Transfer --> Architectural pattern --> Roy Feilding in 2000

Resource --> things which you can name. present on the server like file, image, database, printer
Representation --> state of resource at a given point of time
ContentNegotionanHandler --> different formats of representation based on client sent HTTP Header Accept

REST API
URL to identify a resource
HTTP methods to perform CRUD

GET -- READ
POST -- CREATE a new resource
PUT/PATCH/JSON-PATCH -- for updating
DELETE -- for deleting

Guiding Principles of REST:
1) client-server: concerns has to be seperated
2) Uniform Indentifier
3) Stateless
4) Layered
5) Cacheable

Spring MVC architecture gives you DispatcherServlet as Front Controller, which intercepts all HTTP requests

uses HandlerMappings to map URI to @Controller or @RestController
@Controller is for traditional web applications where server is going to send rendered pages like pdf / html/ images
@RestController sends different formats of data representation like XML / JSON / CSV ..

https://developer.uber.com/docs/eats/references/api/v2/get-eats-stores-storeid-menu


```

Use Path Parameters for Singular resource [ / ]
http://api.example.com/device-management/managed-devices/{device-id}
http://api.example.com/user-management/users/{id}

use Query Parameters [ ? ] for subset
Paginate
http://localhost:8080/api/products?page=1&size=10 

Products by Range
http://localhost:8080/api/products?low=5000&high=25000

```



Task:
1) CustomerRepo for CRUD operations on Customer
2) Wire CustomerRepo to OrderService
3) CustomerController -> wire OrderService
handle GET and POST

============

@Controller [ Server Side rendering ] and @RestController [ Client side rendering ]

HandlerMapping, DispatcherServlet, Jackson library for java to JSON and JSON to java conversion
@GetMapping, @PostMapping, @RequestMapping, @PathVariable, @RequestParam 

JpaRepository --> most of the methods required for CRUD operations are generated.
We can also write custom Projection in interface
Implementation classes are generated [ @Repository ] 

@Service acts as a facade over Repoistory and Business logic

JpaRepository doesn't provide methods for Update; we need to write our own or use Dirty Checking

=======

Day 3:
@Modifying -> executeUpdate()

In JDBC, executeQuery() and executeUpdate() are methods used to execute SQL queries. executeQuery() is specifically designed for SELECT statements, returning a ResultSet containing the query results. executeUpdate() is used for INSERT, UPDATE, and DELETE statements, returning the number of rows affected by the operation as an integer. 


for all built-in JPARepository methods like save() and delete AUTO Commit is set to true
for custom methods we need to put @Transactional to commit;
@Transactional method --> no exception is propagated from the method --> commit
if any exception --> rollback

PATCH for partial updates -- query parameter for data
PUT for major update -- new data generally is sent as payload

====

https://zuplo.com/blog/2024/10/10/unlocking-the-power-of-json-patch
https://jsonpatch.me/

JSON Patch is a standard format for describing changes to a JSON document.



```
 mysql> insert into customers values ('roger@adobe.com', 'Roger' , 'Smith');
Query OK, 1 row affected (0.01 sec)

mysql> insert into customers values ('geetha@adobe.com', 'Geetha' , 'Mohan');
Query OK, 1 row affected (0.00 sec)

mysql> insert into customers values ('rita@adobe.com', 'Rita' , 'Jones');
Query OK, 1 row affected (0.00 sec)

mysql> select * from customers;

```

https://martinfowler.com/bliki/BoundedContext.html


 @JoinColumn(name="customer_fk") used with ManyToOne introduces foreign key in owning table/entity [orders]

 @JoinColumn(name="order_fk") used with OneToMany introduces foreign key in child table [items]

Assume one order has 4 items;

```

    @OneToMany
    @JoinColumn(name="order_fk")
    private List<LineItem> items = new ArrayList<>(); // order has many items

    Place an order:
    orderRepo.save(order);
    itemRepo.save(i1);
    itemRepo.save(i2);
    itemRepo.save(i3);
    itemRepo.save(i4);

    Delete order:
    orderRepo.delete(order);
    itemRepo.delete(i1);
    itemRepo.delete(i2);
    itemRepo.delete(i3);
    itemRepo.delete(i4);

```

With Cascade: for composition and not aggregation relationship

```

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="order_fk")
    private List<LineItem> items = new ArrayList<>(); // order has many items

     Place an order:
     orderRepo.save(order); // saves orders and 4 line items

        Delete order:
        orderRepo.delete(order); // delete order and its 4 line items
```

Within a @Transactional boundary if entity becomes dirty /change -> ORMs will send UPDATE SQL

failed to lazily initialize a collection of role: com.adobe.orderapp.entity.Order.items

By default ManyToOne is EAGER fetching 
fetchong Order gets Customer also

and OneToMany is Lazy fetching
fetching orders doesn't get items

By default
spring.jpa.open-in-view=true

Connection / Hibernate Session is kept alive, hence lazy loading is possible

spring.jpa.open-in-view=false
Connection / Hibernate Session is closed once repo method is executed, when children objects are required by client it tries to fetch them [ lazy] but connection is lost ant throws LazyInitializationException 

==============

Recap:
PUT / PATCH
JSON-PATCH: op : add, remove, replace
[
    {"op": "replace", "path": "/items/8/qty" , value:"2"}
]
```

{
    "oid": 5,
    "orderDate": "2025-07-24T03:35:43.002+00:00",
    "customer": {
      "email": "geetha@adobe.com",
      "firstName": "Geetha",
      "lastName": "Mohan"
    },
    "items": [
      {
        "itemid": 8,
        "product": {
          "id": 2,
          "name": "Sony Bravia",
          "price": 297000.0,
          "quantity": 49
        },
        "qty": 1,
        "amount": 297000.0
      },
      {
        "itemid": 9,
        "product": {
          "id": 1,
          "name": "iPhone 16",
          "price": 89000.0,
          "quantity": 87
        },
        "qty": 2,
        "amount": 178000.0
      }

```

* OneToMany [cascade, EAGER vs LAZY]
* ManyToOne

@Transactional --> Atomic operation / Unit of Work
DIRTY CHECKING --> Within PersistenceContext if entity becomes dirty --> ORM will flush the state to database by issuing UPDATE SQL

JPQL Projections
SQL vs JPQL 

===========

Day 4:

HttpMessageConvertor are available for all primitive data types in Spring.
String --> int, boolean, long, double, float, char
Not available for Date type
