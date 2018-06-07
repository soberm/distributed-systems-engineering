# Using OpenJdk 8 as parent image, which uses Alpine Linux for smaller image sizes
FROM openjdk:8-jdk-slim

# Metadata
LABEL maintainer="wechselauer.josef@gmx.at"
LABEL version="1.0"
LABEL description="Dockerfile for the V2ITracker"

#Volume to store data on the filesystem
VOLUME /tmp

#Jar file, which should be copied to the container
ARG JAR_FILE="V2Itracker-0.0.1-SNAPSHOT.jar"

# Copy the v2itracker app into the container
COPY ./build/libs/${JAR_FILE} v2itracker-0.0.1.jar
ADD /src/main/resources/config/application-dev.properties conf/application-dev.properties

#RUN apk add --no-cache libc6-compat

# Expose the port 4000 outside of the container
EXPOSE 4000

#Run this on container startup
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-Dspring.profiles.active=dev", "-jar", "/v2itracker-0.0.1.jar", "--spring.config.location=classpath:file:/conf/application-dev.properties"]