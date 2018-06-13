1.) create a cluster with the name **cluster-dse-group-14** and the following setup
```bash
Name:           cluster-dse-group-14
Ort:            Zonal
Zone:           europe-west1-c
Clusterversion: 1.8.10-gke.0 (Standard)
Maschinentyp:   1vCPU (3,75 GB Speicherplatz)
Knoten-Image:   Container-Optimized OS (cos)
Größe:          5
Automatische Knotenupgrades:    Deaktiviert
Automatische Knotenreparatur:   Aktiviert
Alte Autorisierung:             Deaktiviert
Stackdriver Logging:            Aktiviert
Stackdriver Monitoring:         Aktiviert
-- ganz unten:
MEHR - Zugriffsbereiche
Uneingeschränkten Zugriff auf alle Cloud-APIs zulassen
```

2.) Setup your local gcloud console with the following commands to connect to the cluster:
```bash
gcloud config set project dse-group-14
gcloud config set compute/zone europe-west1-c
gcloud container clusters get-credentials cluster-dse-group-14
```

3.) To build our project for cubernetes, build/use the newest images and upload it to the gcloud image registry with the following script:
```bash
./build-and-push-images-kubernets.sh
```

4) Create deployment
```bash
kubectl create -f kubernetes-deployment.yml
```

5) Delete deployment
```bash
kubectl create -f kubernetes-deployment.yml
```






