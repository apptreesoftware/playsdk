#!/usr/bin/env bash
. ../connector_env.config

export JAVA_HOME=$JAVA_INSTALL_PATH
export PATH=$JAVA_HOME/bin:$PATH

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
APP_COMMAND="${DEPLOY_LOCATION}/bin/connector -Dhttp.port=disabled -Dhttps.port=${CONNECTOR_PORT}"
echo $APP_COMMAND
nohup $APP_COMMAND >> $LOG_FILE &