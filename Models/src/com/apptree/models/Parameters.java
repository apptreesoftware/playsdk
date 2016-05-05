package com.apptree.models;

import org.joda.time.DateTime;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alexis on 11/5/15.
 */
public class Parameters {
    private HashMap<String,String> urlParameters;
    Parameters(HashMap<String, String> urlParameters) {
        this.urlParameters = urlParameters;
    }

    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        for (Map.Entry<String, String> entry : urlParameters.entrySet() ) {
            String key = entry.getKey();
            String value = entry.getValue();
            if ( key != null && value != null ) {
                if ( value.equalsIgnoreCase("true") ) {
                    json.put(key, true);
                } else if ( value.equalsIgnoreCase("false") ) {
                    json.put(key, false);
                } else {
                    json.put(key, value);
                }
            }
        }
        return json;
    }
    /**
     *
     * @param key the key of the URL parameter to be fetched
     * @return the String value for that key
     */
    public String getStringValueForKey(String key) {
        return urlParameters.get(key);
    }

    /**
     *
     * @param key the key of the URL parameter to be fetched
     * @return the ATDateRange for that key
     */
    public ATDateRange getDateRangeForKey(String key) {
        String dateRangeString;
        ATDateRange dateRange = null;
        List<String> dateList;

        if ( urlParameters.get(key) != null ) {
            try {
                dateRangeString = java.net.URLDecoder.decode(urlParameters.get(key), "UTF-8");
                dateList = Arrays.asList(dateRangeString.split(","));
                if (dateList.size() == 2) {
                    dateRange = new ATDateRange(DateTime.parse(dateList.get(0)), DateTime.parse(dateList.get(1)));
                }
            } catch (UnsupportedEncodingException e) {
                dateRange = null;
            }
        }
        return dateRange;
    }

    /**
     *
     * @param key the key of the URL parameter to be fetched
     * @return the int value of that key
     */
    public int getIntValueForKey(String key) {
        String intString;
        int intValue = 0;

        if ( urlParameters.get(key) != null ) {
            intString = urlParameters.get(key);
            try {
                intValue = Integer.parseInt(intString);
            } catch (NumberFormatException e) {}
        }
        return intValue;
    }

    /**
     *
     * @param key the key of the URL parameter to be fetched
     * @return the Date value for that key
     */
    public DateTime getDateForKey(String key) {
        String dateString;
        DateTime dateTime = null;

        if ( urlParameters.get(key) != null ) {
            dateString = urlParameters.get(key);
            dateTime = DateTime.parse(dateString);
        }

        return dateTime;
    }

    /**
     *
     * @param key the key of the URL parameter to be fetched
     * @return the ATLocation value for that key
     */
    public ATLocation getLocationForKey(String key) {
        String locationString;
        ATLocation location = null;
        List<String> locationList;

        if ( urlParameters.get(key) != null ) {
            try {
                locationString = java.net.URLDecoder.decode(urlParameters.get(key), "UTF-8");
                locationList = Arrays.asList(locationString.split(","));
                if (locationList.size() == 2) {
                    try {
                        location = new ATLocation();
                        location.setLatitude(Double.parseDouble(locationList.get(0)));
                        location.setLongitude(Double.parseDouble(locationList.get(1)));
                    } catch (NumberFormatException e) {
                        location = null;
                    }
                }
            } catch (UnsupportedEncodingException e) {
                location = null;
            }
        }

        return location;
    }

    /**
     *
     * @return The page size sent in the URL parameters
     * @throws NumberFormatException
     */
    public int getPageSize() throws NumberFormatException {
        int pageSize = 0;

        if ( urlParameters.get("pageSize") != null ) {
            pageSize = Integer.parseInt(urlParameters.get("pageSize"));
        }

        return pageSize;
    }

    /**
     *
     * @return The offset sent in the URL parameters
     * @throws NumberFormatException
     */
    public int getOffset() throws NumberFormatException {
        int offset = 0;

        if ( urlParameters.get("offset") != null ) {
            offset = Integer.parseInt("offset");
        }

        return offset;
    }
}
