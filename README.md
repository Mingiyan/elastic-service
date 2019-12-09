## elastic-service
##### Передача данных о пользователях в Elasticsearch

#### Модули:
+ loader - грузит пользователей из базы 
+ service - обертка над Elasticsearch

#### Сборка проекта:

##### Для сборки нужен *Maven 3.3.9* и выше

 собирается командой: 
 ```yaml
 mvn -s settings.xml package
 ```
 или если в модулях нужно удалить папку `target`:
 ```yaml
mvn -s settings.xml clean package
```

#### Запуск:
После сборки в папке `target` модулей появятся файлы с расширением `jar`
##### loader

```yaml
java -jar loader-1.0-SNAPSHOT.jar
```

##### Service

```yaml
java service-1.0-SNAPSHOT.jar
```
после старта сервис доступен по порту 8102
