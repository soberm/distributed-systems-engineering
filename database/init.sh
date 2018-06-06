#!/bin/bash

auth="-u user -p password"

# MONGODB USER CREATION
(
echo "setup mongodb auth"
create_user="if (!db.getUser('user')) { db.createUser({ user: 'user', pwd: 'password', roles: [ {role:'readWrite', db:'tracker_db'} ]}) }"
until mongo tracker_db --eval "$create_user" || mongo tracker_db ${auth} --eval "$create_user"; do sleep 5; done
killall mongod
sleep 1
killall -9 mongod
) &

# INIT DUMP EXECUTION
(
if test -n "insert_script.js"; then
    echo "execute insert script"
	until mongo tracker_db ${auth} insert_script.js; do sleep 5; done
fi
) &

echo "start mongodb without auth"
chown -R mongodb /data/db
gosu mongodb mongod --config /mongod.conf "$@"

echo "restarting with auth on"
sleep 5
exec gosu mongodb mongod --auth --config /mongod.conf "$@"
