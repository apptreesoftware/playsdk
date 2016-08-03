package sdk.list;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import play.api.Play;

/**
 * Created by alexis on 5/4/16.
 */
public class CacheListSQLGenerator {
    static String TmpCacheDirectory = Play.current().path().getPath() + "/tmp";

    public static File generateDatabaseFromCacheListResponse(List list, String listRESTPath) throws RuntimeException {
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

        createTempDirectory();
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to find jdbc");
        }

        filePath = TmpCacheDirectory + "/" + listRESTPath + ".sqlite";
        listFile = new File(filePath);
        if ( listFile.exists() ) {
            listFile.delete();
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + filePath);
            statement = connection.createStatement();
            createTableSQL = "CREATE TABLE LIST_ITEM (" +
                    "ID VARCHAR(32) PRIMARY KEY NOT NULL," +
                    "PARENT_ID VARCHAR (500)," +
                    "ITEM_ORDER INT," +
                    "VALUE VARCHAR(500),";
            for ( int i = 1; i < 81; i++ ) {
                if ( i < 10 ) {
                    createTableSQL += "ATTRIBUTE0" + i + " VARCHAR(500),";
                } else {
                    createTableSQL += "ATTRIBUTE" + i + " VARCHAR(500),";
                }
            }
            createTableSQL += "LATITUDE REAL, LONGITUDE REAL);";
            statement.executeUpdate(createTableSQL);
            statement.close();
            System.gc();
            parseListResponse(list, connection);
            outputPath = TmpCacheDirectory + "/" + listRESTPath + ".zip";
            sqlFileInputStream = new FileInputStream(listFile);
            fileOutputStream = new FileOutputStream(outputPath);
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            zipOutputStream.putNextEntry(new ZipEntry(listFile.getName()));
            while ((length = sqlFileInputStream.read(buffer)) > 0) {
                zipOutputStream.write(buffer, 0, length);
            }
            zipFile = new File(outputPath);
            zipOutputStream.closeEntry();
            zipOutputStream.close();
            fileOutputStream.close();
            sqlFileInputStream.close();
            return zipFile;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(CacheListSQLGenerator.class.getCanonicalName() + " Error generating DB: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("There was an IO Exception");
        }
    }

    private static int parseListResponse(List list, Connection connection) throws SQLException {
        String sql;
        PreparedStatement statement;
        int count = 0;
        ListItem item;

        sql = "INSERT INTO LIST_ITEM values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        statement = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        for ( ListItem listItem : list.listItems ) {
            statement.setString(1, listItem.id);
            statement.setString(2, listItem.parentID);
            statement.setInt(3, -1);
            statement.setString(4, listItem.value);
            int counter = 5;
            while ( counter < 85 ) {
                statement.setString(counter, listItem.getAttributeForIndex(counter - 5) != null ? listItem.getAttributeForIndex(counter - 5).getStringValue() : null);
                counter++;
            }
            statement.setDouble(85, listItem.latitude);
            statement.setDouble(86, listItem.longitude);
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
    }

    private static void createTempDirectory() {
        File file = new File(TmpCacheDirectory);
        if ( !file.exists() ) {
            file.mkdirs();
        }
    }
}
