# codeanything
code anything

# build project with maven
mvn clean install

# run docker-compose.yml
docker-compose up

# Manually Run the application
mvn spring-boot:run

# run Dockerfile
docker build -t codeanything.jar .
docker run -p 8080:8080 codeanything.jar