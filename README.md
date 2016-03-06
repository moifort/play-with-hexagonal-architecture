# hexagonal-architecture-sample

This project is a sample of an hexagonal architecture. This sample implements functionality to manage files:

* Save/delete/get files
* Permission on the file (owner of the file)
* Sharing file (share the file with different permission to other user)
* Send notification when save/delete/get/sharing are call
* Notification manager that allow to implement several notification services (IRC, mail, Twitter, etc.) and allow to the user to configure it (e.i. when the user delete a file it's send the notification by email only or when the file is share it's send a notification by mail and Twitter)

## Requirement

* Java 8

## Build & Run

To build the project execute in the root directory: 

```bash
./mvnw clean install
```

### Simple REST API with IRC Event handler

Before to run, you need to build the application (see below). To run the application execute in the root directory:

```bash
java -jar infra/application/rest-api/target/rest-api-1.0-SNAPSHOT.jar server
```

#### On Kiwiirc chan

Go to `https://kiwiirc.com/client/irc.freenode.net/`

Connect to the chan `#HewaBot` and lets the window open.

#### On your machine (REST api)

Open on your favorite web browser 

##### Add a file

```http
http://localhost:8080/file/add?name=Hexagonal
```
Response:
```json
{"id":0,"name":"Hexagonal"}
```

##### Get the file

```http
http://localhost:8080/file/0
```
Response:
```json
{"id":0,"name":"Hexagonal"}
```

##### Get all files

```http
http://localhost:8080/file
```
Response:
```json
[{"id":0,"name":"Hexagonal"}]
```

#### Go back on Kiwiirc chan

![IRC](https://raw.githubusercontent.com/moifort/hexagonal-architecture-sample/master/irc-handler.png)

### Command Line Application

Before to run, you need to build the application (see below). To run the application (it's will only show you `println()` in console output) execute in the root directory:

```bash
java -jar infra/application/command-line/target/command-line-1.0-SNAPSHOT-jar-with-dependencies.jar
```

Normally it will display:

```bash
Add new file   -> IMFile{id='0', name='test.txt', ownerId='1', sharedUsersIdWithPermission={}}
Get file id: 0 -> IMFile{id='0', name='test.txt', ownerId='1', sharedUsersIdWithPermission={}}
```


## Hexagonal architecture

We use maven module to isolate layer of hexagonal architecture. Main modules **domain** and **infra** represent the two layers of a hexagonal architecture.

```bash
├── domain
│   ├── pom.xml
│   └── src
├── infra
│   ├── application
│   │   └── command-line
│   │       ├── pom.xml
│   │       └── src
│   │   └── rest-api
│   │       ├── pom.xml
│   │       └── src
│   ├── persistence
│   │   ├── in-memory
│   │   │   ├── pom.xml
│   │   │   └── src
│   │   └── sql-spring-data
│   │       ├── pom.xml
│   │       └── src
│   ├── handler
│   │   ├── irc
│   │   │   ├── pom.xml
│   │   │   └── src
│   │   ├── mail
│   │   │   ├── pom.xml
│   │   │   └── src
│   └── pom.xml
└── pom.xml
```

### Domain

In hexagonal architecture domain represents only the business code of your application, no persistence,
no screen only business rule. When you want to add new business code you start in this module. More
precisely you start writing your test first (I use cucumber, that allow me to make BDD), you can see all 
scenarios in test/resources.

The domain module must contains a minimum of dependencies (test dependencies and maybe guava).

The structure of an hexagon is defined like this:
- **api** represents what the hexagon offers (business functionality)
- **spi** represents what the hexagon need (i.e. persistence)
- **core** represents the inside of the hexagon (where all the business code are)

I create two hexagons:
* **filemanager** implements all the file manager functionality (get/save/delete/share) and also a notification center how send a notification each time a functionnality is call.
* **notificationmanager** allows to manage all the notification services like IRC or mail, already implemented, or you can add slack, twitter, facebook or webhooks. With this hexagon you can aggregate all your notification services and give the possibility to the user to manage mapping between a notification type and a service. e.i. when the user delete a file it's send the notification by email only or when the file is share it's send a notification by mail and Twitter.

```bash
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── domain
    │   │       └── filemanager
    │   │           ├── api
    │   │           ├── core
    │   │           └── spi
    │   │       └── notificationmanager
    │   │           ├── api
    │   │           ├── core
    │   │           └── spi
    │   └── resources
    └── test
```

### Infra

In hexagonal architecture Infra represents everything except the business code. 
In this sample I group module by theme (application and persistence), obviously you can add other module like indexer with
Elasticsearch, cloud application API, webservice REST or an other hexagonal architecture project.  

#### How it's work?

**persistence** directory contains two modules: in-memory and sql-spring-data. Both implements the 
interface defined in the spi domain, here `FileRepository` that allow to `filemanager` hexagon to persist data.
* **in-memory** very simple module using a simple in memory HashMap 
* **sql-spring-data** more complex module using **Spring Boot** with **Spring Data JPA** and **Mapstruct** dependency with **H2** db

**handler** directory defines all technologies we can use to send notification from the domain.
* **irc** send event on an IRC channel
* **mail** send event through mail

**application** directory contains only one module **command-line**. Application module is little tricky, you need to connect 
your hexagon with module defined in the **infra** layer. In this sample you can choose between to kind of persistence **in-memory** or **sql-spring-data**.
* **command-line** module it's a very simple, it runs one action with a simple `println()`. You can create more complex application 
module with a REST webservice for instance. The only thing you need to do is to inject dependencies that your hexagon need when the application start.
* **rest-api** module it's REST api webservice sending action events on IRC chan

```bash
.
├── application
│   └── command-line
│   │   ├── pom.xml
│   │   └── src
│   │       ├── main
│   │       └── test
│   └── rest-api
│       ├── pom.xml
│       └──  src
│           ├── main
│           └── test
├── persistence
│   ├── in-memory
│   │   ├── pom.xml
│   │   ├── src
│   │   │   ├── main
│   │   │   └── test
│   └── sql-spring-data
│       ├── pom.xml
│       └── src
│           ├── main
│           └── test
├── handler
│   └── irc
│   │   ├── pom.xml
│   │   └── src
│   │       ├── main
│   │       └── test
│   └── mail
│       ├── pom.xml
│       └── src
│           ├── main
│           └── test
└── pom.xml
```

##  Maven architecture 
The cool thing with maven that it fits very well with the hexagonal architecture. We use maven modules who define a 
subproject and allow to create a specific `pom.xml`.

The maven architecture looks like this:

```bash
                  +------------------------------------+
                  |                                    |
                  |    hexagonal-architecture-sample   |
                  |                                    |
                  +------------------+-----------------+
                                     |
          +--------------------------+---------------------------+  
          |                                                      |
+---------+--------+                                   +---------+--------+
|                  |                                   |                  |
|      domain      |                                   |       infra      |                                    
|                  |                                   |                  |
+------------------+                                   +---------+--------+
                                                                 |
                                        +------------------------+------------------------+- - - - -
                                        |                        |                        |
                               +--------+---------+    +---------+---------+    +---------+--------+
                               |                  |    |                   |    |                  |
                               |     rest-api     |    |     in-memory     |    |        mail      |
                               |                  |    |                   |    |                  |
                               +------------------+    +-------------------+    +------------------+
```


* `hexagonal-architecture-sample` pom will build all your module respecting the order of each module. 
* `domain` is the first module to be build. As said in [Domain](#domain), the module depends only on a few external libraries (test only).
* `infra` will import the domain dependency for: `command-line`, `in-memory`, `sql-spring-data`. It's  allow a version coherency between the domain and infra layer. 

## Pro

* Working with the architecture **help you** a lot. Let me explain, the fact that the business code is totally isolate
from the rest of the world, it's help you to focus only on the business (no thinking about persistence or how you will display the
data on the screen, etc.). If you apply the Behavior Driven Development it will be very easy and very fast to create your business
layer.
* **You can think about the architecture later** (persistence, application, etc.). The hexagonal architecture help you to focus
first on the business and after on what the business need: SQL bdd or noSQL bdd? Webservice or AngularJS application? Which framework?
* **Flexibility and decoupling**. In my sample, you can switch between to **in-memory** or **sql-spring-data** by changing one line.
* **Isolated**. Every module can work on this own (with the external dependencies you want).

## Con

* This sample is quite a `HelloWorld` so I don't know how this kind of architecture will be in a real world
* **More code** :( Decoupling has the result of adding more code (essentially adapter layer)

## Conclusion

Today I am not mature enough on this architecture to give you more arguments. Don't hesitate to make PR on the readme or
to propose improvement on my current architecture! 
