Add update users through Rest. 

The solution is deployed on Spring Boot and MySql. 

[Spring Boot](https://spring.io/projects/spring-boot)

[MySql](https://www.mysql.com/)

[Testcontainers](https://www.testcontainers.org/)

[Resilience4j](https://resilience4j.readme.io/)

# Prerequites
* JDK 11
* Maven

# Setup
### Build
```console
mvn clean install
```
### Start Mysql docker

```console
docker run \
    -e MYSQL_ROOT_PASSWORD=root \
    -e MYSQL_DATABASE=test \
    -p 3306:3306 \
    -d mysql:8
```
### Start Server

```console
./mvnw spring-boot:run
```

# Testing

```console
cd src/test/resources/payload/sample001

./curl-get.sh 
{"userId":222,"title":"Mrs","firstn":"Lea","lastname":"Go","gender":"Female","address":{"street":"Francis Road","city":"NSW","state":"Sydney","postcode":2000}}

./curl-add-update.sh 
{"userId":888,"title":"Mrs","firstn":"Monkey","lastname":"Go","gender":"Female","address":{"street":"Francis Road","city":"NSW","state":"Sydney","postcode":2000}}

./curl-add-update-unauthorized.sh 
{"timestamp":"2020-10-13T00:25:07.091+00:00","status":401,"error":"Unauthorized","message":"","path":"/user/add"}

```