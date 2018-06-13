# Using OpenJdk 8 as parent image, which uses Alpine Linux for smaller image sizes
FROM openjdk:8-jdk-slim

# Metadata
LABEL maintainer="wechselauer.josef@gmx.at"
LABEL version="1.0"
LABEL description="Dockerfile for the V2Itracker"

#Volume to store data on the filesystem
VOLUME /tmp

#Jar file, which should be copied to the container
ARG JAR_FILE="V2Itracker-0.0.1-SNAPSHOT.jar"

# Copy the V2Itracker into the container
COPY ./build/libs/${JAR_FILE} v2itracker-0.0.1.jar

# Expose the port 8241 outside of the container
EXPOSE 8241

COPY start.sh start.sh
COPY wait-for-it.sh wait-for-it.sh
RUN sh -c 'touch app.jar'
ENTRYPOINT ["./start.sh"]

#Run this on container startup
#ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/v2itracker-0.0.1.jar"]