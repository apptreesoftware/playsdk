package sdk.utils;

import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by alexis on 5/3/16.
 */
public class Constants {
    public static final DateTimeFormatter AppTreeDateFormat = DateTimeFormat.forPattern("yyyy-MM-dd").withZone(DateTimeZone.forID("Etc/GMT"));
    public static final DateTimeFormatter AppTreeDateTimeFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(DateTimeZone.forID("Etc/GMT"));
    public static final String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
    public static final String USERNAME_TOKEN_HEADER = "X-USERNAME";
    public static final String CORE_CALLBACK_URL = "X-CALLBACK-URL";
    public static final String CORE_CALLBACK_TYPE = "X-CALLBACK-TYPE";
    public static final String CORE_CALLBACK_MESSAGE = "X-CALLBACK-MESSAGE";
    public static final String APPLICATION_API_KEY_HEADER = "APPLICATION-API-KEY";
    public static final String APP_ID_HEADER = "X-APPTREE-APPLICATION-ID";

    public static final String CORE_CALLBACK_TYPE_SUCCESS = "SUCCESS";
    public static final String CORE_CALLBACK_TYPE_WARNING = "WARNING";
    public static final String CORE_CALLBACK_TYPE_ERROR = "ERROR";
    public static final String CORE_CALLBACK_TYPE_AUTH_FAILURE = "AUTHORIZATION_FAILURE";

    public static final String defaultCoreURL = "https://services1.apptreesoftware.com";

    public static final String PLATFORM_VERSION = "5.5";

    public static final int SDK_ERROR_STATUS_CODE = 540;
}
