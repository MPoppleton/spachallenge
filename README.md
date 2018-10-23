# Hypothesis Challenge
Submission for the Hypothesis SPA Challenge

## Getting Started
The following steps aim to get the code built, and ready to run

### Prerequisites
* Java 1.8
* Maven

### Structure
The project is structured in two modules with under a parent module `spasubmission`

spasubmission \
 |-- spafrontend \
 |-- spabackend
 
### Generating the executable Jar
Run 
```
mvn clean install
```
This will install the node and yarn versions which will download the required node modules for the frontend project, and then generate the production Angular6 app for the front end. \
After that it will compile and test the backend code, and then copy the angular app into the static files directory. \
The spring boot plugin will then create the executable jar, which is then copied to the spasubmission directory.

### Issues 
Currently my mailgun account has been blocked because I was using it to test \ 
To get around this when building you can run 
```
mvn clean install -DskipTests
```
or alternately comment out the Test annotation on the following file:
```
spabackend/src/test/java/au/com/hypothesisconsulting/spachallenge/MailGunTests
```

## Running the app

From cmd/bash in the spasubmission directory, run the following
```
java -jar spachallenge.jar
```
This will start the spring boot app, accessible at
[http://localhost:8080/](http://localhost:8080/)

## Author
**Miles Poppleton**
