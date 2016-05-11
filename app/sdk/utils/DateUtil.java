package sdk.utils;

import org.joda.time.DateTime;

/**
 * Created by Matthew Smith on 5/10/16.
 * Copyright AppTree Software, Inc.
 */
public class DateUtil {
    public static DateTime dateFromString(String dateString) {
        if ( dateString == null ) return null;
        try {
            return Constants.AppTreeDateFormat.parseDateTime(dateString);
        } catch (Exception ignored) {}
        return null;
    }

    public static DateTime dateTimeFromString(String dateString) {
        if ( dateString == null ) return null;
        try {
            return Constants.AppTreeDateTimeFormat.parseDateTime(dateString);
        } catch (Exception ignored) {}
        return null;
    }
}
