#! /bin/bash
./wait-for-it.sh vehicledata:8001 -t 60
./wait-for-it.sh http://35.195.254.163/kubernetesdatasimulator/, -t 60
java -Djava.security.egd=file:/dev/./urandom -jar data-simulator-0.0.1.jar
