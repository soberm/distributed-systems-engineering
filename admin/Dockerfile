# Using OpenJdk 8 as parent image, which uses Alpine Linux for smaller image sizes
FROM openjdk:8-jdk-alpine

# Metadata
LABEL maintainer="michael.sober@ymail.com"
LABEL version="1.0"
LABEL description="Dockerfile for Spring Boot Admin"

#Volume to store data on the filesystem
VOLUME /tmp

#Jar file, which should be copied to the container
ARG JAR_FILE="admin-0.0.1.jar"

# Copy the admin app into the container
COPY ./build/libs/${JAR_FILE} admin-0.0.1.jar

# Expose the port 8123 outside of the container
EXPOSE 8123

#Run this on container startup
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/admin-0.0.1.jar"]