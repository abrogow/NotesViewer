
##Execution and Setup
You can use gradle wrapper to build project and run tests
```
$ ./gradlew clean build
$ ./gradlew test
```

You can run project directly from your IDEA and use swagger tool to call the API
```
http://localhost:8080/swagger-ui/index.html
```

You have to be authorized before calling the API
```
username: user
password: password
```

You can run application with customized properties
```
app:
  note:
    default-expiration-time-in-mins: 10
```
You can set default note expiration time