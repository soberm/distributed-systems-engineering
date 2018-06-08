build tagged image (tag must contain HOSTNAME/PROJECT-NAME/IMAGE_NAME), for example:
```bash 
sudo docker build -t gcr.io/dse-group-14/registry .
```

push new image version to kubernetes with (for example for registry):
```bash 
sudo gcloud docker -- push gcr.io/dse-group-14/registry 
```

#### (only once) create cluster and push if not existent
do that in the google console, take as example the registry-cluster

setup local gcloud for our project, connect to existing cluster
```bash 
gcloud config set project dse-group-14
gcloud config set compute/zone europe-west1-c
gcloud container clusters get-credentials cluster-dse-group-14	
```
create deployment with:
```bash 
kubectl create -f deployment.yml 
```

look at the deployment (for example for the registry-deployment)
```bash 
kubectl describe deployment registry-deployment
kubectl get pods
kubectl log [podname]  
(--> get podname with previous command)
```

expose the service to the web, for example for the registry
```bash
kubectl expose deployment registry-deployment --type=LoadBalancer --port 8761 --target-port 8761
```

get the exposed services with
```bash
kubectl get service
```



# Removing and reverting

remove a service with the following command, for example for the registry
```bash
kubectl delete service registry-deployment
```

watch removal of load-balancing module
```bash
gcloud compute forwarding-rules list
```

remove container cluster, for example for the regitry:
```bash
gcloud container clusters delete registry-cluster
```
