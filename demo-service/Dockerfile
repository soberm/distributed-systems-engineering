# Using OpenJdk 8 as parent image, which uses Alpine Linux for smaller image sizes
FROM openjdk:8-jdk-alpine

# Metadata
LABEL maintainer="michael.sober@ymail.com"
LABEL version="1.0"
LABEL description="Dockerfile for the demo service"

#Volume to store data on the filesystem
VOLUME /tmp

#Jar file, which should be copied to the container
ARG JAR_FILE="demo-service-0.0.1.jar"

# Copy the demo service app into the container
COPY ./build/libs/${JAR_FILE} demo-service-0.0.1.jar

# Expose the port 9090 outside of the container
EXPOSE 9090

#Run this on container startup
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/demo-service-0.0.1.jar"]