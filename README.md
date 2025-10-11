# Spring Boot

************************ **Project 2** ************************

## Spring 
### solve problem (with introduction of dependency injection and IOC container)
- Tight Coupling
- Hard to Test
- Scattered object creation

### Spring Framework Modules
- The Spring Framework provides a comprehensive programming and configuration model for modern Java- based enterprise applications - on any kind of deployment platform.
![alt text](image-1.png)

### Spring Vs Spring Boot
| **Feature**             | **Spring**                             | **Spring Boot**                       |
|-------------------------|----------------------------------------|---------------------------------------|
| **Configuration**        | Manual (XML/Java-based)                | Auto-configuration                    |
| **Web Server**           | External (e.g., Tomcat)                | Embedded (e.g., Tomcat, Jetty)        |
| **Setup Complexity**     | High                                   | Low                                   |
| **Development Speed**    | Slower (more configuration)           | Faster (minimal setup)                |
| **Flexibility**          | High (manual setup, more control)      | Opinionated defaults (but customizable)|
| **Production-Ready**     | Needs additional setup                | Built-in features (metrics, health checks)|
| **Ideal For**            | Large, complex, or legacy apps        | Microservices, new projects, quick setups|


### Spring Boot
- extended version of Spring Framework
- Auto configuration, tooling, etc (you get spring boot)

### Lets Start with the spring boot
- site: start.spring.io
- select type and tech stack and generate the spring boot project
- eg: Maven, Java, Version, Jar, Java 21
- add dependencies (search and add dependencies)
    - Spring web (dependency) : apache tomcat server already configured
- generate project (zip file download)
- open the zip in intelliJ
- we will write all the code in src (main: all buisness logic, test)

- **pom.xml**
    - manage dependencies
    - change version
    - whole java project management

- **resource folder**
    - application.properties (file)
        - We can configure our project with help of this file
        - web, database, spring boot

- **Run the project** 
    - go to src/main/java/../projectName.java
    - run the main function (run button of intelliJ)
    - server start on port = 8080 (default) 
        - seee terminal log for more detail
        
### Internal Working of Spring Boot
- **Bean** : We will understand this with exmaple -> BeanRazorpayService
    - create class : RazorPayPaymentService
    - if we use object of this class in our spring boot application
        - RazorPayPaymentService payment = new RazorPayPaymentService();
        - tight copuling : our application is dependent on razorpay payment service, in future if we need to use different payment service we need to change in code, to avoid this we use Beans and Dependency injection
    
    - **Defination** : A bean in Spring is simply an object that is managed by the Spring container. It is created, configured, and maintained by the Spring IoC container.
    - **Annotation** : @Component, @Service, @Repository, @Controller

    - No need to create object to use (no need to use new keyword)

    - **How to use Bean**
        1) Dependency Injection (inject dependencies into spring managed beans)
        - create constructor with parameter (RazorPayPaymentService)
        - this is better than the autowired one, because here we tell explicitly that our class is dependent on which dependencies
        - also we can use final
            ```java
            // in main springbootapplication
            private BeanRazorPayPaymentService paymentService;

            public LearningSpringBootApplication(BeanRazorPayPaymentService paymentService){
                this.paymentService = paymentService;
            }
            ```
        2) Autowired
        - can't use final keyword
            ```java
            // in main springbootapplication
            @Autowired
            private BeanRazorPayPaymentService paymentService;
            ```

    - **Benifit**
        - Allow Loose Coupling in the code
    
    - if multiple bean
        - it will give error if no condition is defined for beans
        - solution: 
            - make one bean primary
            - use condition to select the bean
        - configuration (resources> application.properties)
            - payment.provider = stripe
            - 
        ```java
        public interface PaymentService{};

        @Component
        @ConditionalOnProperty(name="payment.provider", havingValue="razorpay")
        public class BeanRazorPayPaymentService implements PaymentService{};
        
        @Component
        @ConditionalOnProperty(name="payment.provider", havingValue="stripe")
        public class BeanStripePaymentService implements PaymentService{};

        // in main springbootapplication
        private final PaymentService paymentService;
        public LearningSpringBootApplication(PaymentService paymentService){
            this.paymentService = paymentService;
        }
        ```

- **IOC(Inversion of Control) or Spring Container** 
    - IoC refers to the principle where the control of object creation and management is inverted. In a traditional program, you create objects manually (using new). In IoC, the Spring container is responsible for instantiating and managing objects.

- Component Scanning
    - Start from @SpringBootApplication, only one in one project, used with main project class
    - Component Scanning is the process where Spring automatically detects and registers beans (classes annotated with @Component, @Service, @Repository, @Controller, etc.) within the application context.


### What happens when you run spring boot application
![alt text](image.png)

************************ **Project 2** ************************

## How Does the Web Server work in Spring Boot
![img_1.png](img_1.png)
![img.png](img.png)
- MVC Architecture
- Spring MVC (traditional flow)
  ![img_2.png](img_2.png)
- Spring MVC with REST API
  ![img_3.png](img_3.png)

## Build REST API
- We will use 3 layer Architecture (controller, service, repository)
  - ![img_5.png](img_5.png)
    -  1. **Client Request:**
        - The client sends data (usually in JSON format) via HTTP.
    -  2. **Presentation Layer (Controller):**
        - Receives the request.
        - Converts JSON into a **DTO** (Data Transfer Object).
    -  3. **DTO:**
        - A simplified object that carries data between layers (decouples client data and internal models).
    -  4. **Service Layer:**
        - Contains **business logic**.
        - Receives the DTO, processes it, and interacts with the persistence layer.
        - Maps **DTO** to **Entity** for database operations.
    -  5. **Persistence Layer:**
        - Interacts with the database.
        - Saves or retrieves **Entities** (representations of database tables).
    -  6. **Database:**
        - Stores and retrieves data (using **Entities**).


- Request (Client) → Controller → Service → Repository → Entity/Model ↔ Database ↔ Repository → Service → Controller → Response (JSON/View)
![img_6.png](img_6.png)


### Student Management (RETS API)
- **Controller**
- Use `@RestController` to make class as controller
  - `@RestController`: Combines `@Controller` and `@ResponseBody` to simplify the creation of RESTful APIs that return data directly in response (usually in JSON).

- **Handler**
  - `@GetMapping` : Maps HTTP GET requests to a specific method in the controller to fetch data.
  - `@GetMapping("/endpoint")` : Use to make GET Request Handler for `/endpoint`

-**DTO (Data Transfer Object)**
  - A simplified object that carries data between layers, decoupling external client data from internal models.

#### Lombok annotation
  - `@Data` : It simplifies the creation of Java beans (POJOs) by automatically generating common boilerplate code, such as getters, setters, toString(), equals(), and hashCode() methods.
  - **Key Features of `@Data`:**
    - **`@Getter`**: Automatically generates getter methods for all fields.
    - **`@Setter`**: Automatically generates setter methods for all fields.
    - **`@ToString`**: Automatically generates a `toString()` method that includes all fields.
    - **`@EqualsAndHashCode`**: Automatically generates `equals()` and `hashCode()` methods based on all fields.
    - **`@RequiredArgsConstructor`**: Generates a constructor for all final fields or fields marked with `@NonNull`.

  - **`@AllArgsConstructor`**: It creates a constructor for the class with **all fields** as parameters.

  - **`@RequiredArgsConsturctor`**: automatically generates a constructor with parameters for all final fields or fields marked with `@NonNull`.
  
  - **`@NoArgsConstructor`** : utomatically generates a public no-argument constructor for a Java class at compile time — eliminating boilerplate.

- We will use PostGreSQL for database and DBeaver for graphical analysis
  - install using chocolatey
  - `choco install postgresql`
  - `choco install dbeaver`

#### DBeaver
- DBeaver is a universal database management tool — it’s a GUI (graphical user interface) client that helps you interact with databases visually, instead of using just command-line tools like psql.
  - how to use DBeaver
    - open dbeaver app
    - check is postgres running or not using command `Get-Service -Name postgresql*`
    - connect the database, here we will use postgresql
    - provide hostname, database, username, password and testconnect to check connection is establishing or not, then finish
    - **Note** : If Database driver is not installed install it using download button popup when clicked test connection

### Continue with the project
- Lets add two more dependencies
  - `PostgreSQL Driver` and `Spring Data JPA`
  - Go to start.spring.io and add dependencies and click on explore to get the xml and copy paste the dependency xml to pom.xml
  - After adding the dependencies to pom.xml use sync with maven button in intelliJ to download the dependencies

- configure the database, provide the configure in application.properties
  - spring.datasource.url=jdbc:postgresql://localhost:5432/studentsDB
  - spring.datasource.username=postgres
  - spring.datasource.password=qwerty123
  - spring.jpa.hibernate.ddl-auto=update
  - spring.jpa.show-sql=true
  - spring.jpa.properties.hibernate.format_sql=true

- We will create schema in entity, like student entity. This will create student table in database and create attributes as mentioned in the studentEntity
  - use `@Entity` annotation to create entity
  ```java
    @Entity
    public class Student {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    
        private String name;
        
        private String email;
    }
  ```
  - As attributes in entity are private, we need to create getter and setter to access the values of thta attributes
  - We will use `@Getter` and `@Setter` annotation to create getter and setter

#### ORM : Object-Relational Mapping
- ORM allows you to interact with your database using Java objects instead of writing raw SQL queries. You define entity classes in Java, and the ORM framework maps these entities to database tables and manages CRUD operations automatically.
  - Instead of writing lots of SQL queries to interact with the database,
  - You work with normal Java objects (called entities),
  - And ORM automatically takes care of saving, updating, deleting, or fetching these objects from the database.

#### Repository
- A Repository is a special interface in Spring Boot that acts as a bridge between your application and the database. It helps you perform database operations like saving, reading, updating, and deleting data — without writing SQL queries.
```java
  @Repository
  public interface StudentRepository extends JpaRepository<Student, Long> {}
```
- Parameter : <Student, Long> 
  - Student — the type of the entity (your Java class that maps to a database table). 
  - Long — the type of the entity's primary key (ID) field.

#### Service
- Service Interface which will implement ServiceImplementation
- We will need to make bean of ServiceImplementation so we will use `@Service` annotation 
- `@Service` is a specialized stereotype annotation in Spring that marks a class as a service component in the service layer of your application.
- ServiceImplementation class will interact with the repository

#### Path Variable (dynamic)
- Variable used in path, dynamic value
```java
@GetMapping("/student/{id}")
public void getStudentDetail(@PathVariable("id") Long studentId){
    return ;
}
```
- here {id} is pathVariable
- if we need to rename the path variable for the function
  - `@PathVariable("pathVariable") DataType newPathVariable`

#### Query Params
- ?key=value
- Used to filter or search data. They go in the URL after a ?.
- url: // GET /student?department=mechanical&year=2025
```java
@GetMapping("/student")
public void getStudentDetail(
        @RequestParam(value= "department", defaultValue = "Computer Science and Engineering") String departmentName,
        @RequestParam(required = false) String year){}
```

#### Model Mapper
**ModelMapper** is a Java library that simplifies the process of mapping one object to another — especially useful when converting between:
- `Entity ↔ DTO`
- `Request DTO ↔ Domain Model`
- `Domain Model ↔ Response DTO`

Instead of manually copying every field, **ModelMapper** can handle it for you automatically.
**Note** : Make sure both have same field and Need to have public no-args constructor

- Configure : /config/MapperConfig.java
```java
@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
```
- how to use
```java
private final ModelMapper modelMapper;
void func() {
    modelMapper.map(student, StudentDto.class);
    // This will create new object
}
```

#### ✅ ResponseEntity

`ResponseEntity<T>` is a generic wrapper for HTTP responses in Spring Boot. It allows you to:

- ✅ Set **status codes** (e.g., 200 OK, 404 Not Found, 201 Created)
- ✅ Set **headers**
- ✅ Return a **body**
- ✅ Handle **errors** or **conditional responses** cleanly

---

- **Syntax**
    ```java
    ResponseEntity<T> response = new ResponseEntity<>(body, status);
    ```
    
    Or using static helper methods:
    
    ```java
    ResponseEntity.ok(body); // 200 OK
    ```

