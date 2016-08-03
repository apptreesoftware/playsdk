#!/usr/bin/env bash

CURRENT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. ${CURRENT_DIR}/../connector_env.config

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