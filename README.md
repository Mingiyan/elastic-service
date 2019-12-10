## elastic-service
##### Passing user data to Elasticsearch

#### Modules:
+ loader - loads users from the database
+ service - a wrapper over Elasticsearch

#### Project assembly:

##### To build, you need Maven *3.3.9* and higher

assembled by the command: 
 ```yaml
 mvn package
 ```
or if you need to delete the folder in the modules `target`:
 ```yaml
mvn clean package
```

#### Launch:
After assembly, files with the `jar` extension will appear in the `target` folders of the modules
##### loader

```yaml
java -jar loader-1.0-SNAPSHOT.jar
```

##### Service

```yaml
java -jar service-1.0-SNAPSHOT.jar
```
after start service is available on port 8102
