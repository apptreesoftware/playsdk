package sdk.list;

import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import play.Logger;
import sdkmodels.list.List;
import sdkmodels.list.ListItem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by alexis on 5/4/16.
 */
public class CacheListSQLGenerator {

    public static File generateDatabaseForList(List list) throws RuntimeException {
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
        String fileName = UUID.randomUUID().toString();
        ensureTempDirectoryExists();
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to find jdbc");
        }

        filePath = getTemporaryDirectoryPath() + File.separator + fileName + ".sqlite";
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
            createIndexes(connection);
            System.gc();
            parseListResponse(list, connection);
            outputPath = getTemporaryDirectoryPath() + File.separator + fileName + ".zip";
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
        } finally {
            cleanup();
        }
    }

    private static void createIndexes(Connection connection) throws SQLException {

        PreparedStatement indexStatement = connection.prepareStatement("Create INDEX list_item_index_id ON LIST_ITEM(ID COLLATE  NOCASE);");
        indexStatement.execute();
        indexStatement.close();

        indexStatement = connection.prepareStatement("Create INDEX list_item_index_value ON LIST_ITEM(VALUE COLLATE  NOCASE);");
        indexStatement.execute();
        indexStatement.close();

        indexStatement = connection.prepareStatement("Create INDEX list_item_index_1 ON LIST_ITEM(ATTRIBUTE01 COLLATE  NOCASE);");
        indexStatement.execute();
        indexStatement.close();

        indexStatement = connection.prepareStatement("Create INDEX list_item_index_2 ON LIST_ITEM(ATTRIBUTE02 COLLATE  NOCASE);");
        indexStatement.execute();
        indexStatement.close();

        indexStatement = connection.prepareStatement("Create INDEX list_item_index_3 ON LIST_ITEM(ATTRIBUTE03 COLLATE  NOCASE);");
        indexStatement.execute();
        indexStatement.close();

        indexStatement = connection.prepareStatement("Create INDEX list_item_index_4 ON LIST_ITEM(ATTRIBUTE04 COLLATE  NOCASE);");
        indexStatement.execute();
        indexStatement.close();

        indexStatement = connection.prepareStatement("Create INDEX list_item_index_5 ON LIST_ITEM(ATTRIBUTE05 COLLATE  NOCASE);");
        indexStatement.execute();
        indexStatement.close();

        indexStatement = connection.prepareStatement("Create INDEX list_item_index_6 ON LIST_ITEM(ATTRIBUTE06 COLLATE  NOCASE);");
        indexStatement.execute();
        indexStatement.close();

        indexStatement = connection.prepareStatement("Create INDEX list_item_index_7 ON LIST_ITEM(ATTRIBUTE07 COLLATE  NOCASE);");
        indexStatement.execute();
        indexStatement.close();

        indexStatement = connection.prepareStatement("Create INDEX list_item_index_8 ON LIST_ITEM(ATTRIBUTE08 COLLATE  NOCASE);");
        indexStatement.execute();
        indexStatement.close();

        indexStatement = connection.prepareStatement("Create INDEX list_item_index_9 ON LIST_ITEM(ATTRIBUTE09 COLLATE  NOCASE);");
        indexStatement.execute();
        indexStatement.close();

        indexStatement = connection.prepareStatement("Create INDEX list_item_index_10 ON LIST_ITEM(ATTRIBUTE10 COLLATE  NOCASE);");
        indexStatement.execute();
        indexStatement.close();
    }

    private static int parseListResponse(List list, Connection connection) throws SQLException {
        String sql;
        PreparedStatement statement;
        int count = 0;

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

    private static String getTemporaryDirectoryPath() {
        String tmpDir = FileUtils.getTempDirectoryPath();
        if (!tmpDir.endsWith(File.separator) ) {
            tmpDir += File.separator;
        }
        return tmpDir + "com.apptreesoftware.revolution" + File.separator + "lists";
    }

    private static File getTemporaryDirectory() {
        File file = new File(getTemporaryDirectoryPath());
        if ( !file.exists() ) {
            file.mkdirs();
        }
        return file;
    }

    private static void ensureTempDirectoryExists() {
        getTemporaryDirectory();
    }

    private static void cleanup() {
        File[] files = getTemporaryDirectory().listFiles();
        if ( files == null ) return;
        java.util.List<File> fileList = Arrays.asList(files);
        fileList.forEach(file -> {
            if ( FileUtils.isFileOlder(file, DateTime.now().minusMinutes(10).getMillis()) ) {
                if (!file.delete()) {
                    Logger.debug("Could not delete temp file " + file.getPath());
                }
            }
        });
    }
}
