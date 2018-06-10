# Using OpenJdk 8 as parent image, which uses Alpine Linux for smaller image sizes
FROM openjdk:8-jdk-slim

# Metadata
LABEL maintainer="wechselauer.josef@gmx.at"
LABEL version="1.0"
LABEL description="Dockerfile for the data-simulator"

#Volume to store data on the filesystem
VOLUME /tmp

#Jar file, which should be copied to the container
ARG JAR_FILE="data-simulator-0.0.1-SNAPSHOT.jar"

# Copy the gateway app into the container
COPY ./build/libs/${JAR_FILE} data-simulator-0.0.1.jar
COPY ./src/main/resources/data ./data

# Expose the port 8138 outside of the container
EXPOSE 8138

COPY start.sh start.sh
COPY wait-for-it.sh wait-for-it.sh
RUN sh -c 'touch app.jar'
ENTRYPOINT ["./start.sh"]

#
##Run this on container startup
#ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/data-simulator-0.0.1.jar"]