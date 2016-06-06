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
}
