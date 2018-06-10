## Postgres passwords

In docker-compose, the **compose** spring profile of the projects is used.
Make sure that you add the password for the remote postgres database in the projects
* NOTYfier
* GOVstat
* VEHICLEdata

Before committing changes, remove the passwords again! If you commit a password, change it to a new one and do not commit it!

## Use local gcloud credentials
Before using the docker-compose.dev-local.yml file, the local google cloud credentials have to be saved to the host system with
```bash
gcloud auth application-default login
```
This step needs only to be done once.

## Build project changes
The dockerfiles use the jar files located in the build folder created by gradle.
In order to get the newest version of a project, before building the images the gradle/build for each updated projects (or in the main-folder of the repository to build all) has to be rebuilt with:
```bash
gradle clean build

# to skip tests use:

gradle clean build -x test
```

## Start all projects
To start all projects and rebuild the docker images with docker-compose, simply use the command:
```bash
docker-compose -f docker-compose.dev-local.yml up --build
```
in the root folder of the project.
