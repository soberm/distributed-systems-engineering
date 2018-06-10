# Using OpenJdk 8 as parent image, which uses Alpine Linux for smaller image sizes
FROM openjdk:8-jdk-slim

# Metadata
LABEL maintainer="wechselauer.josef@gmx.at"
LABEL version="1.0"
LABEL description="Dockerfile for the GOVstat"

#Volume to store data on the filesystem
VOLUME /tmp

#Jar file, which should be copied to the container
ARG JAR_FILE="GOVstat-0.0.1-SNAPSHOT.jar"

# Copy the VEHICLEdata into the container
COPY ./build/libs/${JAR_FILE} govstat-0.0.1.jar

# Expose the port 8242 outside of the container
EXPOSE 8242

#Run this on container startup
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/govstat-0.0.1.jar"]