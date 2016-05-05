package com.apptree.models;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServlet;
import java.io.*;
import java.sql.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by alexis on 1/7/16.
 */
public class CacheListSQLGenerator {
    static String TOMCAT_TEMP_DIR = "javax.servlet.context.tempdir";

    public static File generateDatabaseFromCacheListResponse(HttpServlet servlet, JSONArray listItemRecords, String listRESTPath) throws Exception {
        String directoryPath;
        String filePath;
        File listFile;
        Connection connection;
        Statement statement;
        String createTableSQL;
        String outputPath;
        FileInputStream sqlFileInputStream;
        FileOutputStream fileOutputStream;
        ZipOutputStream zipOutputStream;
        int length;
        byte[] buffer = new byte[1024];
        File zipFile;

        directoryPath = System.getenv("OPENSHIFT_TMP_DIR");
        if ( directoryPath == null ) {
            directoryPath = (String)servlet.getServletContext().getAttribute(TOMCAT_TEMP_DIR).toString();
        }

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        filePath = directoryPath + "/" + listRESTPath + ".sqllite";
        listFile = new File(filePath);
        if ( listFile.exists() ) {
            listFile.delete();
        }
        connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
        statement = connection.createStatement();
        createTableSQL = "CREATE TABLE LIST_ITEM (" +
                "ID VARCHAR(32) PRIMARY KEY NOT NULL," +
                "PARENT_ID VARCHAR (500)," +
                "ITEM_ORDER INT," +
                "VALUE VARCHAR(500)," +
                "ATTRIBUTE01 VARCHAR(500)," +
                "ATTRIBUTE02 VARCHAR(500)," +
                "ATTRIBUTE03 VARCHAR(500)," +
                "ATTRIBUTE04 VARCHAR(500)," +
                "ATTRIBUTE05 VARCHAR(500)," +
                "ATTRIBUTE06 VARCHAR(500)," +
                "ATTRIBUTE07 VARCHAR(500)," +
                "ATTRIBUTE08 VARCHAR(500)," +
                "ATTRIBUTE09 VARCHAR(500)," +
                "ATTRIBUTE10 VARCHAR(500)," +
                "LATITUDE REAL," +
                "LONGITUDE REAL," +
                "AUTO_FILL VARCHAR(500));";
        statement.executeUpdate(createTableSQL);
        statement.close();
        System.gc();
        parseListResponse(listItemRecords, connection);
        outputPath = directoryPath + "/" + listRESTPath + ".zip";
        sqlFileInputStream = new FileInputStream(listFile);
        fileOutputStream = new FileOutputStream(outputPath);
        zipOutputStream = new ZipOutputStream(fileOutputStream);
        zipOutputStream.putNextEntry(new ZipEntry(listFile.getName()));
        while ( (length = sqlFileInputStream.read(buffer)) > 0 ) {
            zipOutputStream.write(buffer, 0, length);
        }
        zipFile = new File(outputPath);
        zipOutputStream.closeEntry();
        zipOutputStream.close();
        fileOutputStream.close();
        sqlFileInputStream.close();
        return zipFile;
    }

    private static int parseListResponse(JSONArray listRecords, Connection connection) throws Exception {
        String sql;
        PreparedStatement statement;
        int count = 0;
        JSONObject item;

        try {
            sql = "INSERT INTO LIST_ITEM values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            for ( int i = 0; i < listRecords.length(); i++ ) {
                item = listRecords.getJSONObject(i);
                statement.setString(1, JSONUtils.getString(item, "id"));
                statement.setString(2, JSONUtils.getString(item, "parentID"));
                statement.setInt(3, JSONUtils.getInt(item, "orderBy"));
                statement.setString(4, JSONUtils.getString(item, "value"));
                statement.setString(5, JSONUtils.getString(item, "attribute01"));
                statement.setString(6, JSONUtils.getString(item, "attribute02"));
                statement.setString(7, JSONUtils.getString(item, "attribute03"));
                statement.setString(8, JSONUtils.getString(item, "attribute04"));
                statement.setString(9, JSONUtils.getString(item, "attribute05"));
                statement.setString(10, JSONUtils.getString(item, "attribute06"));
                statement.setString(11, JSONUtils.getString(item, "attribute07"));
                statement.setString(12, JSONUtils.getString(item, "attribute08"));
                statement.setString(13, JSONUtils.getString(item, "attribute09"));
                statement.setString(14, JSONUtils.getString(item, "attribute10"));
                statement.setDouble(15, JSONUtils.getDouble(item, "latitude"));
                statement.setDouble(16, JSONUtils.getDouble(item, "longitude"));
                statement.setString(17, JSONUtils.getString(item, "autoFill"));
                statement.addBatch();
                count++;
                if ( count % 5000 == 0 ) {
                    statement.executeBatch();
                    connection.commit();
                    System.gc();
                }
            }
            statement.executeBatch();
            connection.commit();
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("There was an SQLException generating cache file from records");
        }
    }
}
