#!/usr/bin/env bash

. ../connector_env.config

PID_FILE="${DEPLOY_LOCATION}/RUNNING_PID"
if [ -f ${PID_FILE} ]
 then
    EXISTING_PID=$(cat ${PID_FILE})
    echo "Shutting down..."
    kill -SIGTERM ${EXISTING_PID}
    rm ${PID_FILE}
 else
        echo "$PID_FILE not found. Unable to shut down"
fi