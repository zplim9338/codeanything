# codeanything
code anything

# build project with maven
mvn clean install

# run docker-compose-db.yml (database image)
docker-compose -f docker-compose-db.yml up

# build the application image after changed
docker-compose build

# run docker-compose.yml
docker-compose --env-file .env up

# Manually Run the application
mvn spring-boot:run

# run Dockerfile
docker build -t codeanything.jar .
docker run -p 8080:8080 codeanything.jar

# export docker image 
docker save -o codeanything-api.tar codeanything-app

# import docker image 
sudo docker load -i codeanything-api.tar