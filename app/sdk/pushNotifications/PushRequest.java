package sdk.pushNotifications;

import java.util.List;

/**
 * Created by alexis on 9/7/16.
 */
public class PushRequest {
    public String message;
    public String title;
    public String dataSetName;
    public String primaryKey;
    public String navigationID;
    public List<String> statuses;
    public List<String> roles;
    public List<String> userIDs;
    public String operator;

    public PushRequest() {}
}
