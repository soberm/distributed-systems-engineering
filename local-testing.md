Build the projects you have updated with *gradle clean build* (the built .jar file is used in the docker container)
or build the whole broject with **gradle clean build -x test** in the main directory with skipping tests.

execute:
```bash
docker-compose -f docker-compose.dev-local.yml up --build
```

stop it with:
```bash
docker-compose -f docker-compose.dev-local.yml stop
```
