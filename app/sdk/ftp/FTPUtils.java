package sdk.ftp;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by alexisandreason on 6/12/17.
 */
public class FTPUtils {
    private static FTPClient startFTP(String serverAddress, String userID, String password) throws IOException {
        FTPClient client = new FTPClient();
        client.connect(serverAddress);
        if ( !client.login(userID, password) ) {
            client.logout();
            throw new RuntimeException("Unable to login to FTP client");
        }
        int reply = client.getReplyCode();
        if ( !FTPReply.isPositiveCompletion(reply) ) {
            client.disconnect();
            throw new RuntimeException("Unable to login to FTP client");
        }
        client.enterLocalPassiveMode();
        client.setFileType(FTP.BINARY_FILE_TYPE);
        return client;
    }

    private static void closeClient(FTPClient client) {
        if ( client != null && client.isConnected() ) {
            try {
                client.logout();
                client.disconnect();
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
        }
    }

    public static boolean downloadFile(FTPRequest request, FileOutputStream outputStream) {
        FTPClient client = null;
        try {
            boolean success;
            client = startFTP(request.hostName, request.username, request.password);
            if ( !StringUtils.isEmpty(request.remoteDirectory) ) {
                success = client.changeWorkingDirectory(request.remoteDirectory);
                if ( !success ) throw new RuntimeException("Error updating directory");
            }
            success = client.retrieveFile(request.filename, outputStream);
            if ( !success ) {
                throw new RuntimeException("Error downloading file");
            }
             return success;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IOException occurred trying to access FTP server");
        } finally {
            closeClient(client);
        }
    }

    public static List<FTPFile> getFiles(FTPRequest request) {
        FTPClient client = null;
        try {
            client = startFTP(request.hostName, request.username, request.password);
            FTPFile[] fileArray = client.listFiles(request.remoteDirectory);
            if ( fileArray == null ) {
                throw new RuntimeException("No files found");
            }
            return Arrays.asList(fileArray);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IOException occured trying to access FTP server");
        } finally {
            closeClient(client);
        }
    }

    public static void unzipFile(File sourceFile, File outFile) {
        try {
            byte[] buffer = new byte[1024];
            ZipInputStream zis = new ZipInputStream(new FileInputStream(sourceFile));
            ZipEntry zipEntry = zis.getNextEntry();
            FileOutputStream fos = new FileOutputStream(outFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zis.closeEntry();
            zis.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found");
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred");
        }
    }

    public static boolean uploadFile(FTPRequest request, InputStream inputStream) {
        FTPClient client = null;
        try {
            boolean success;
            client = startFTP(request.hostName, request.username, request.password);
            if ( !StringUtils.isEmpty(request.remoteDirectory) ) {
                success = client.changeWorkingDirectory(request.remoteDirectory);
                if ( !success ) {
                    throw new RuntimeException("Error uploading file");
                }
            }
            success = client.storeFile(request.filename, inputStream);
            if ( !success ) {
                throw new RuntimeException("Error uploading file");
            }
            return success;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("IOException occurred accessing FTP server");
        }
    }
}
