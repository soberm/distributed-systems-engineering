#! /bin/bash
./wait-for-it.sh vehicledata:8001 -t 20
java -Djava.security.egd=file:/dev/./urandom -jar data-simulator-0.0.1.jar