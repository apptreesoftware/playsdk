call connector_env.config.bat

set JAVA_HOME=%JAVA_INSTALL_PATH%
set PATH=%JAVA_HOME%/bin:%PATH%
set APPLICATION_SECRET=%APPLICATION_SECRET%

echo "Checking if existing app is running at $PID_FILE"
set PID_FILE=%DEPLOY_LOCATION%\bin\RUNNING_PID
SET /p PID=<%DEPLOY_LOCATION%\bin\RUNNING_PID
taskkill /pid %PID%
del %PID_FILE%

SET PID_FILE2=C:\Windows\System32\RUNNING_PID
SET /p PID2=<C:\Windows\System32\RUNNING_PID
taskkill /pid %PID2%
del %PID_FILE2%

echo "Starting Connector Application Server”


    IF "%HTTP_PORT."!="." (
        SET APP_COMMAND = %DEPLOY_LOCATION%/bin/connector -Dhttp.port=%HTTP_PORT%
    )
    IF "%HTTP_PORT."!="." (
        SET APP_COMMAND = %DEPLOY_LOCATION%/bin/connector -Dhttp.port=%HTTPS_PORT%
    )
    IF "%HTTP_PORT."=="." IF "%HTTPS_PORT."=="." ( EXIT)

echo %APP_COMMAND%
%APP_COMMAND%