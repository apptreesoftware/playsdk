call C:\AppTree\connector_env_meridian.config.bat

echo "Checking if existing app is running at $PID_FILE"
set PID_FILE=%DEPLOY_LOCATION%\bin\RUNNING_PID
SET /p PID=<%DEPLOY_LOCATION%\bin\RUNNING_PIDtaskkill /pid %PID%del %PID_FILE%SET PID_FILE2=C:\Windows\System32\RUNNING_PIDSET /p PID2=<C:\Windows\System32\RUNNING_PIDtaskkill /pid %PID2%del %PID_FILE2%
