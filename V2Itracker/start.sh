#! /bin/bash
./wait-for-it.sh mongo:27017 -t 20
java -Djava.security.egd=file:/dev/./urandom -jar v2itracker-0.0.1.jar