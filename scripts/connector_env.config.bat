#HTTP Port enabled for testing only. Removing/Commenting out this line will disable http
set HTTP_PORT=9001
#set HTTPS_PORT=9001
set CONNECTOR_PORT=%HTTP_PORT%
#Path to your JAVA 8 installation
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_92
set JAVA_INSTALL_PATH=C:\Program Files\Java\jdk1.8.0_92
#The location on disk where the connector is deployed.
set DEPLOY_LOCATION=C:\AppTree\acad_connector
set LOG_FILE=%DEPLOY_LOCATION%/connector.log
set HOST=http://touchdemo.acad-plus.com:%CONNECTOR_PORT%
#The JDBC thin client connection and schema credentials the connector will be accessing
set MERIDIAN_JDBC_URL=jdbc:sqlserver://acadsqla\mssqlserver8;databaseName=EXP_ACAD-Plus
set MERIDIAN_JDBC_USERNAME=apptree
set MERIDIAN_JDBC_PASSWORD=apptree123
#Application secret. This will be used to register this connector in the builder.
set APPLICATION_SECRET='lr2A/[pbWVsqHZZWOm6hKkS5axh_qdS]6IF^Oz0El6l_TGCI`mO6@2vTwA]AeRUA'