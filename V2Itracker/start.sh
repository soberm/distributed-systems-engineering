#! /bin/bash
./wait-for-it.sh iwas:27017 -t 15
sleep 15
java -Djava.security.egd=file:/dev/./urandom -jar v2itracker-0.0.1.jar