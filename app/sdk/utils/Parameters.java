package sdk.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.joda.time.DateTime;
import play.libs.Json;
import sdk.models.DateRange;
import sdk.models.Location;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alexis on 5/3/16.
 */
public class Parameters {
    private HashMap<String,String> parameters = new HashMap<>();

    public JsonNode toJSON() {
        ObjectNode json = Json.newObject();
        for (Map.Entry<String, String> entry : parameters.entrySet() ) {
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

    public Parameters(Map<String, String[]> parameters) {
        parameters.forEach((k,v) -> {
            this.parameters.putIfAbsent(k, v[0]);
        });
    }

    public Map<String, String> toMap() {
        return parameters;
    }

    public void addEntriesFromHashMap(HashMap<String,String> map) {
        this.parameters.putAll(map);
    }

    /**
     *
     * @param key the key of the URL parameter to be fetched
     * @return the String value for that key
     */
    public String getStringValueForKey(String key) {
        return parameters.get(key);
    }

    /**
     *
     * @param key the key of the URL parameter to be fetched
     * @return the ATDateRange for that key
     */
    public DateRange getDateRangeForKey(String key) {
        String dateRangeString;
        DateRange dateRange = null;
        List<String> dateList;

        if ( parameters.get(key) != null ) {
            try {
                dateRangeString = java.net.URLDecoder.decode(parameters.get(key), "UTF-8");
                dateList = Arrays.asList(dateRangeString.split(","));
                if (dateList.size() == 2) {
                    dateRange = new DateRange(DateTime.parse(dateList.get(0)), DateTime.parse(dateList.get(1)));
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

        if ( parameters.get(key) != null ) {
            intString = parameters.get(key);
            try {
                intValue = Integer.parseInt(intString);
            } catch (NumberFormatException e) {}
        }
        return intValue;
    }

    public Boolean getBooleanForKey(String key) {
        boolean value;
        if ( parameters.get(key) != null ) {
            String boolString = parameters.get(key);
            return Boolean.valueOf(boolString);
        }
        return false;
    }

    /**
     *
     * @param key the key of the URL parameter to be fetched
     * @return the Date value for that key
     */
    public DateTime getDateForKey(String key) {
        String dateString;
        DateTime dateTime = null;

        if ( parameters.get(key) != null ) {
            dateString = parameters.get(key);
            dateTime = DateTime.parse(dateString);
        }

        return dateTime;
    }

    /**
     *
     * @param key the key of the URL parameter to be fetched
     * @return the ATLocation value for that key
     */
    public Location getLocationForKey(String key) {
        String locationString;
        Location location = null;
        List<String> locationList;

        if ( parameters.get(key) != null ) {
            try {
                locationString = java.net.URLDecoder.decode(parameters.get(key), "UTF-8");
                locationList = Arrays.asList(locationString.split(","));
                if (locationList.size() == 2) {
                    try {
                        location = new Location();
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

        if ( parameters.get("pageSize") != null ) {
            pageSize = Integer.parseInt(parameters.get("pageSize"));
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

        if ( parameters.get("offset") != null ) {
            offset = Integer.parseInt(parameters.get("offset"));
        }

        return offset;
    }
}
