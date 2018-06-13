# Using OpenJdk 8 as parent image, which uses Alpine Linux for smaller image sizes
FROM openjdk:8-jdk-slim

# Metadata
LABEL maintainer="wechselauer.josef@gmx.at"
LABEL version="1.0"
LABEL description="Dockerfile for the NOTYfier"

#Volume to store data on the filesystem
VOLUME /tmp

#Jar file, which should be copied to the container
ARG JAR_FILE="NOTYfier-0.0.1-SNAPSHOT.jar"

# Copy the NOTYfier into the container
COPY ./build/libs/${JAR_FILE} notyfier-0.0.1.jar

# Expose the port 8248 outside of the container
EXPOSE 8248

#Run this on container startup
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/notyfier-0.0.1.jar"]