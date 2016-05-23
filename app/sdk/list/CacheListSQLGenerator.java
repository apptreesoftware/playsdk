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
                    "LONGITUDE REAL);";
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

        sql = "INSERT INTO LIST_ITEM values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        statement = connection.prepareStatement(sql);
        connection.setAutoCommit(false);
        for ( ListItem listItem : list.listItems ) {
            statement.setString(1, listItem.id);
            statement.setString(2, listItem.parentID);
            statement.setInt(3, -1);
            statement.setString(4, listItem.value);
            statement.setString(5, listItem.getAttributeForIndex(ListItem.ATTRIBUTE_1) != null ? listItem.getAttributeForIndex(ListItem.ATTRIBUTE_1).getStringValue() : null);
            statement.setString(6, listItem.getAttributeForIndex(ListItem.ATTRIBUTE_2) != null ? listItem.getAttributeForIndex(ListItem.ATTRIBUTE_2).getStringValue() : null);
            statement.setString(7, listItem.getAttributeForIndex(ListItem.ATTRIBUTE_3) != null ? listItem.getAttributeForIndex(ListItem.ATTRIBUTE_3).getStringValue() : null);
            statement.setString(8, listItem.getAttributeForIndex(ListItem.ATTRIBUTE_4) != null ? listItem.getAttributeForIndex(ListItem.ATTRIBUTE_4).getStringValue() : null);
            statement.setString(9, listItem.getAttributeForIndex(ListItem.ATTRIBUTE_5) != null ? listItem.getAttributeForIndex(ListItem.ATTRIBUTE_5).getStringValue() : null);
            statement.setString(10, listItem.getAttributeForIndex(ListItem.ATTRIBUTE_6) != null ? listItem.getAttributeForIndex(ListItem.ATTRIBUTE_6).getStringValue() : null);
            statement.setString(11, listItem.getAttributeForIndex(ListItem.ATTRIBUTE_7) != null ? listItem.getAttributeForIndex(ListItem.ATTRIBUTE_7).getStringValue() : null);
            statement.setString(12, listItem.getAttributeForIndex(ListItem.ATTRIBUTE_8) != null ? listItem.getAttributeForIndex(ListItem.ATTRIBUTE_8).getStringValue() : null);
            statement.setString(13, listItem.getAttributeForIndex(ListItem.ATTRIBUTE_9) != null ? listItem.getAttributeForIndex(ListItem.ATTRIBUTE_9).getStringValue() : null);
            statement.setString(14, listItem.getAttributeForIndex(ListItem.ATTRIBUTE_10) != null ? listItem.getAttributeForIndex(ListItem.ATTRIBUTE_10).getStringValue() : null);
            statement.setDouble(15, listItem.latitude);
            statement.setDouble(16, listItem.longitude);
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
