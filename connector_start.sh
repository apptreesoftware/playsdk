#!/usr/bin/env bash

CURRENT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
. ${CURRENT_DIR}/../connector_env.config

echo $CURRENT_DIR

export JAVA_HOME=$JAVA_INSTALL_PATH
export PATH=$JAVA_HOME/bin:$PATH
export APPLICATION_SECRET=$APPLICATION_SECRET

PID_FILE="${DEPLOY_LOCATION}/RUNNING_PID"
echo "Checking if existing app is running at $PID_FILE"
if [ -f ${PID_FILE} ]
 then
    echo "IT DOES"
    EXISTING_PID=$(cat ${PID_FILE})
    echo "Killing running app"
    kill ${EXISTING_PID}
    rm ${PID_FILE}
 else
        echo "$PID_FILE not found"
fi
echo "Starting"
chmod +x "${DEPLOY_LOCATION}/bin/connector"
if [ -n ${HTTP_PORT} ]; then
        APP_COMMAND="${DEPLOY_LOCATION}/bin/connector -Dhttp.port=${HTTP_PORT} -Dhttps.port=${HTTPS_PORT}"
else
        APP_COMMAND="${DEPLOY_LOCATION}/bin/connector -Dhttp.port=disabled -Dhttps.port=${HTTPS_PORT}"
fi
echo $APP_COMMAND
nohup $APP_COMMAND >> $LOG_FILE &