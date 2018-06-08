Before using the docker-compose.dev-local.yml file,
the local google cloud credentials have to be saved to the host system with
```bash
gcloud auth application-default login
```

The docker dev file builds all images.
In order to get the newest version of a project, before building the images the gradle/build for the updated projects has to be rebuilt with:
```bash
gradle clean build
```