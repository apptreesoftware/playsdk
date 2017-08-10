package sdk.pushNotifications;

import com.fasterxml.jackson.databind.JsonNode;
import org.joda.time.DateTime;

/**
 * Created by alexis on 9/7/16.
 */
public class PushNotificationResponse {
    public int pushID;
    public int numberSent = 0;
    public DateTime creationDate;
    public DateTime scheduledDate;
    public PushRequest pushRequest;
    public JsonNode iosPushPayload;
    public JsonNode androidPushPayload;

    public PushNotificationResponse() {}
}
