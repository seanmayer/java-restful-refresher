#  Java Spring Framework Refresher

Quick refresher on Java Spring Framework with REST Webservices.

## Spring Framework

Spring Framework is a Java platform that provides comprehensive infrastructure support for developing Java applications. Spring handles the infrastructure so you can focus on your application.

## Start up

1. Prefered IDE: VSCode
2. VSCode extensions: 
- Spring Boot Dashboard
- Spring Boot Extension Pack
- Spring Boot Tools
- Spring Initializer Java Support
- Test Runner for Java
- Maven for Java
- Debugger for Java
- Extension Pack for Java
- Java prettier formatter
- XML
3. Start webservices: `./mvnw spring-boot:run` or VSCode UI

## Running Web Services App
1. Build your project with `mvn install` command
2. Upload the deployable `.jar` file to a production server
3. Run application with `java -jar` <filname> command

### Running Webservices without STS
1. Navigate to `/mobile-app-ws` directroy folder
2. Run `./mvnw install` this will compile, build the project and unit tests
3. Run `./mvnw spring-boot:run` this will run the RESTful webservices application in a apache tomcat server container

### Running Application as a Java App (with a Apache tomcat container)
1. `Run `./mvnw install` this will compile, build the project and unit tests (create jar in target)
2. `cd /mobile-app-ws/target`
3. `cp mobile-app-ws-0.0.1-SNAPSHOT.jar /Users/{user-name}/Desktop`
4. Navigate to desktop where the jar has been copied
5. `java -jar mobile-app-ws-0.0.1-SNAPSHOT.jar`

### Running Application as a War file (on an existing Apache tomcat container [Mac])
1. Update `pom.xml` -> 
```
<packaging>war</packaging>
```
2. `./mvnw clean` clears out target folder
3. `./mvnw install` creates the war package in `/target`
4. Install tomcat zip from http://tomcat.apache.org (with the appropriate corrosponding java version)
5. Unzip on to desktop
6. Switch directories to `cd /Desktop/apache-tomcat-9.0.65/bin/
7. Elevate permissions to make script files executable `chmod a+x *.sh`
8. Run `./startup.sh`
9. Navigate to http://localhost:8080 (tomat is running!)
10. Run `./shutdown.sh`
11. Create Tomcat user for deployments
12. Switch directoires to `cd /Desktop/apach-tomcat-9.0.65/conf`
13. Open `tomcat-users.xml`
14. Add 
``` 
<role rolename="manager-gui"/>
<user username="admin" password="admin" roles="manager-gui"/>
```
15. Go to maven project directory where the war package has been created `/mobile-app-ws/target/`
16. Rename `mobile-app-ws-0.0.1-SNAPSHOT.war` -> `mobile-app-ws.war`
17. Deploy war on tomcat 'WAR file to deploy': 
![Screenshot](readme/images/tomcat.png)
18. Webservices are deployed :)

### Deploying to Amazon Cloud EC2

1. Login to https://aws.amazon.com/
2. Select availability zone
![Screenshot](readme/images/aws_select_avail.png)

## Spring Security
### Adding Spring Security to a project
1. Update `Pom.xml`
- 		 <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency> 
2. Run Project and use generate security password
3. Navigate to `localhost:8080/login`
![Screenshot](readme/images/spring_security.png)

