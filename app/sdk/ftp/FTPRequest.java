package sdk.ftp;

/**
 * Created by alexisandreason on 6/12/17.
 */
public class FTPRequest {
    String hostName;
    String username;
    String password;
    String remoteDirectory;
    String filename;

    public String getFilename() {
        return this.filename;
    }

    private FTPRequest(String hostName, String username, String password, String remoteDirectory, String filename) {
        this.hostName = hostName;
        this.username = username;
        this.password = password;
        this.remoteDirectory = remoteDirectory;
        this.filename = filename;
    }

    public static class Builder {
        String hostName;
        String username;
        String password;
        String remoteDirectory;
        String filename;

        public Builder(String hostname) {
            this.hostName = hostname;
        }

        public Builder withUserInfo(String username, String password) {
            this.username = username;
            this.password = password;
            return this;
        }

        public Builder withFileInfo(String remoteDirectory, String filename) {
            this.remoteDirectory = remoteDirectory;
            this.filename = filename;
            return this;
        }

        public FTPRequest build() {
            return new FTPRequest(hostName, username, password, remoteDirectory, filename);
        }
    }
}
