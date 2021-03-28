# SaskCycle
An app to connect people in Saskatoon to reuse options.   
**[See the Live App Here!](https://saskcycle.herokuapp.com/)**

## Installation Guides
**The easiest way to use the app is to visit the live site above!**
Due to some quirks in our frameworks, dependencies, and java versioning, sometimes getting the artifact to run locally can run into issues. 

The following methods can be used for Local Installations: 

### Run for Development using IntelliJ 
Follow these instructions if you wish to make changes to the application

Open Intellij, and create a new Project from Version Control, either File ->
New -> From Version Control or Get from VCS in the new project window. 

Enter the clone URL and press Clone

Once the project is cloned, make sure to press "load maven build scripts" on the "maven project detected" popup 

Once intellij finishes managing the maven dependencies, navigate to 
`src/main/java/com/saskcycle/saskcycle/SaskCycleApplication.java` and run the `SaskCycleApplication.java` class.

The first time you do this, Spring needs to download and install the needed NodeJS dependencies for Vaadin, which may take a few minutes. Once it's finished, you will see the server running. 

Navigate to `localhost:8080` on a web browser to use the application 

### Running via Maven
Clone the repo, then run  
`./mvnw run` 
in the root of the cloned directory. 

### Running the Release Jar 
Go to the [Release](https://git.cs.usask.ca/lkp821/saskcycle/-/releases/v1.0.0) for this Milestone. Download the .jar file and run it using `java -jar`.

Navigate to `localhost:8080` on a web browser to use the application 

### Creating a production .jar file using Maven
Clone our repository. Open a shell to the cloned directory and run the command:  
`./mvnw package -Pproduction -DskipTests`
Maven will compile all the needed classes and dependencies, this may take a few minutes. Once its complete you can run the jar, which will be found in `target/` 

Navigate to `localhost:8080` to use the application 

## Installation Troubleshooting
### SSL Socket Exceptions (Intellij Development) 
When trying to run the application for development, depending on your Java version and installation you may see SSL Socket Exceptions when trying to connect to the MongoDB remote database. 

A fix for this is to add the `-Djdk.tls.client.protocols=TLSv1.2` option to your Java compiler flags in the Intellij run configuration. 

### Production Install 
when trying to create a production .jar, you need to ensure that application properties has the following option set, otherwise the NodeJS dependencies may not be properly fetched.

 `vaadin.productionMode=true`

