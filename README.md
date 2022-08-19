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

#### Create EC2 Linux Instance
1. Login to https://aws.amazon.com/
2. Navigate to EC2 Dashboard
3. Select availability zone
![Screenshot](readme/images/aws_select_avail.png)
4. Select 'Launch instance'
5. Configuration settings:
- Name and tags: 'DemoAPIServer'
- App and OS Images: Amazon Linux (Free tier)
- Instance type: t2.micro (Free tier)
- Key pair (login): Create private key for login
- Storage (volumes): 8GB (Free tier)
- Network settings: SSH, HTTP, HTTPS, Custom TCP port 8080
- Advanced settings: Shutdown behaviour: Stop
6. Launch the instance
#### Connect to EC2 via SSH
7. Navigate to EC2 Running containers 
8. Get Public IPv4 DNS name to start SSH the connection
9. SSH into AWS instance:
- `sudo su` (if required)
- `chmod 400 myprivatekey.cer` (if required)
- `ssh -i myprivatekey.cer ec2-user@{DNS-name}`
#### Update EC2 and install Java
10. Install updates: `sudo yum update`
11. Check Java version: `sudo java -version`
12. Check all Java packages available: `sudo yum list java`
13. Install Java: `sudo yum install java-1.8.0`
14. Switch Java version:
- `sudo /usr/sbin/alternatives --config java`
- `sudo /usr/sbin/alternatives --config javac`
#### Download and install Tomcat
15. Go to https://tomcat.apache.org/ select the version and get the url link for the `tar.gz` file type
16. Download tomcat with the link extracted (example):`sudo wget https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.65/bin/apache-tomcat-9.0.65.tar.gz`
17. Check file is there: `ls`
18. Extract files: `sudo tar xvf apache-tomcat-9.0.65.tar.gz -C /usr/share`
19. Check its extracted: `ls -lrt /usr/share`
20. Rename tomact directory: `sudo ln -s /usr/share/apache-tomcat-9.0.65 /usr/share/tomcat9`
21. `ls -lrt /usr/share` is now: `tomcat9 -> /usr/share/apache-tomcat-9.0.65` :)
#### Configure remote access to Manager app
22. Create a new tomcat group: 
- `sudo groupadd --system tomcat`
- `sudo useradd -d /usr/share/tomcat9 -r -s /bin/false -g tomcat tomcat`
- `sudo chown -R tomcat:tomcat /usr/share/apache-tomcat-9.0.65` (set tomcat folder permissions for this new user)
23. Make tomcat start on reboot (will need to create a service file)
- `sudo vi /etc/systemd/system/tomcat9.service`
- Copy and paste these setting into this file:
```
[Unit]
Description=Tomcat Server
After=syslog.target network.target

[Service]
Type=forking
User=tomcat
Group=tomcat

Environment=JAVA_HOME=/usr/lib/jvm/jre
Environment='JAVA_OPTS=-Djava.awt.headless=true'
Environment=CATALINA_HOME=/usr/share/tomcat9
Environment=CATALINA_BASE=/usr/share/tomcat9
Environment=CATALINA_PID=/usr/share/tomcat9/temp/tomcat.pid
Environment='CATALINA_OPTS=-Xms512M -Xmx1024M'
ExecStart=/usr/share/tomcat9/bin/catalina.sh start
ExecStop=/usr/share/tomcat9/bin/catalina.sh stop

[Install]
WantedBy=multi-user.target

```
- `sudo systemctl daemon-reload`
24. Start tomcat
- `sudo systemctl enable tomcat9`
- `sudo systemctl start tomcat9` (to stop `sudo systemctl stop tomcat9`)
25. Navigate to tomcat URL
- {AWS Public IPv4 DNS}.com:8080
26. `sudo vi /usr/share/tomcat9/webapps/manager/META-INF/context.xml`
- Comment out line (key i -> INSERT to edit):
```
  <!--  <Valve className="org.apache.catalina.valves.RemoteAddrValve"
  allow="127\.\d+\.\d+\.\d+|::1|0:0:0:0:0:0:0:1" /> -->
```
- esc + `:wq` to save changes
- sudo systemctl restart tomcat9
#### Configure Tomcat users
27. `sudo vi /usr/share/tomcat9/conf/tomcat-users.xml`
- (Copy and paste from the tomcat 401 page) (key i -> INSERT to edit):
```
<role rolename="manager-gui"/>
<user username="tomcat" password="s3cret" roles="manager-gui"/>
```
- esc + `:wq` to save changes
28. `sudo systemctl restart tomcat9`
#### Install MySQL
29. `sudo yum install https://dev.mysql.com/get/mysql80-community-release-el7-6.noarch.rpm`
30. `sudo amazon-linux-extras install epel -y`
31. `sudo yum install mysql-community-server`
- Optional: Navigate to `/etc/yum.repos.d/mysql-community.repo` and disable gpg if you trust repos
```
gpgcheck=0
```
32. `sudo systemctl enable --now mysqld`
33. `systemctl status mysqld`
34. `sudo grep 'temporary password' /var/log/mysqld.log`
35. `sudo mysql_secure_installation`
- Set new password
- Remove anonymous users? - Yes
- Disallow root login remotely? - Yes
- Remove test Database? - Yes
- Reload privilege tables now? - Yes
36. `mysql -u root -p`
- Login
#### Create database and add user
37. Sign into user root: `mysql -u root -p`
38. `create database photo_app;`
39. `show databases;`
40. `create user '{my username here}'@'localhost' identified by '{my password here}';`
41. `grant all privileges on photo_app.* to '{my username here}'@'localhost';`
42. Make changes to take affect: `flush privileges;`
43. `exit`
44. `mysql -u {my username here} -p;`
45. `show databases`;
#### Deploy webservices on tomcat 
46. Navigate to maven webservices project: e.g. `cd mobile-app-ws`
47. `./mvnw install`
48. Go to `cd mobile-app-ws/target/mobile-app-ws-0.0.1-SNAPSHOT.war`
- Rename it if needed
49. Navigate to tomcat URL
- {AWS Public IPv4 DNS}.com:8080
- Login if needed
- Navigate to tomcat web application manager
50. Deploy `.war` file to "War file to deploy


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

