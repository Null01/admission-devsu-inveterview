# ms-account

Microservices to resolve requirements for entities Account, TransactionAccount

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

docker build -t ms-account:latest .

docker run -d -p 8082:8082 ms-account

docker run -d --network network-devsu --name ms-account -p 8082:8082 ms-account

```

## repository
https://github.com/orgs/devsu-interviews/repositories