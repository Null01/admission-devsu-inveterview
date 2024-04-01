# ms-client

Microservices to resolve requirements for entities Client, Person

## build .jar

```shell
# Locate home directory folder for project.
  ./mvnw clean install

# Run application in local.  
  ./mvnw spring-boot:run
  ./mvnw spring-boot:run -Dspring-boot.run.profiles=develop
```

## deployment

```dockerfile
# create only once time.
docker network create network-devsu

docker build -t ms-client:latest .

docker run -d -p 8081:8081 ms-client

docker run -d --network network-devsu --name ms-client -p 8081:8081 ms-client

```

## repository
https://github.com/orgs/devsu-interviews/repositories