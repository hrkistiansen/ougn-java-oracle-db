#Talking Databasish to Oracle

This is a repository with sample projects using different techniques to communicate between Java and the Oracle database.

##Pre-requisites
- JDK 8 (fra Oracle)
- Gradle
- Eclipse JEE
- Oracle XE 11g

**Install Oracle JDBC Driver into your local repository**

The Oracle JDBC-driver is not part of a public repository, and must be installed manually.

- Download Oracle JDBC-stasjonen, pt .: http://www.oracle.com/technetwork/apps-tech/jdbc-112010-090769.html
- Choose OJDBC6.jar
- `mvn install:install-file -Dfile={Path/to/your/}ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0.4 -Dpackaging=jar`

##Opening the projects

- Get the sample code from git `git clone https://github.com/hrkistiansen/ougn-java-oracle-db.git`
- Go into the project you are interrested in `cd ougn-java-oracle-db/jdbc`
- Prepare the project for opening in Eclipse `gradle eclipse`
- Create the database user and sample objects
--`sqlplus "/ as sysdba"`
--`@ougn-java-oracle-db/ougn-java-oracle-db/jdbc/main/resources/sql/create_db_user.sql`





##The Projects

A short description of the different projects

###jdbc

Using the most low level form of communication.

###jdbc-spring

Uses Spring JDBC to ease the code. Improves on the plain JDBC project.

###eclipselink

Uses plain eclipselink

###hibernate

Uses plain Hibernate

###hibernate-entitymanager

Uses Hibernate with an entity manager.

###hibernate-spring

Uses Hibernate with Spring

###mybatis

Uses MyBatis 

###ebean

Uses Ebean

