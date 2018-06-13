#!/usr/bin/env sh

#gradle build -x test

#cd StoreFrontUI
#docker build -t eu.gcr.io/dse-group-14/storefrontui .
#gcloud docker -- push eu.gcr.io/dse-group-14/storefrontui
#cd ..

#cd APIgateway
#docker build -t eu.gcr.io/dse-group-14/apigateway .
#gcloud docker -- push eu.gcr.io/dse-group-14/apigateway
#cd ..
#
#cd VEHICLEdata
#docker build -t eu.gcr.io/dse-group-14/vehicledata .
#gcloud docker -- push eu.gcr.io/dse-group-14/vehicledata
#cd ..
#
#cd data-simulator
#docker build -t gcr.io/dse-group-14/data-simulator .
#gcloud docker -- push gcr.io/dse-group-14/data-simulator
#cd ..
#
#cd GOVstat
#docker build -t eu.gcr.io/dse-group-14/govstat .
#gcloud docker -- push eu.gcr.io/dse-group-14/govstat
#cd ..
#
#cd NOTYfier
#gradle clean build -x test
#docker build -t eu.gcr.io/dse-group-14/notyfier .
#gcloud docker -- push eu.gcr.io/dse-group-14/notyfier
#cd ..
#
#cd V2Itracker
#
#docker build -t eu.gcr.io/dse-group-14/v2itracker .
#gcloud docker -- push eu.gcr.io/dse-group-14/v2itracker
#cd ..
#
#cd database
#docker build -t eu.gcr.io/dse-group-14/mongodb-dse-group-14 .
#gcloud docker -- push eu.gcr.io/dse-group-14/mongodb-dse-group-14
#cd ..

gcloud config set project dse-group-14
gcloud config set compute/zone europe-west1-c
gcloud container clusters get-credentials cluster-dse-group-14