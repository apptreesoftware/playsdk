package com.apptree.models;

import com.apptree.models.ATDataSetItem;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Matthew Smith, AppTree Software on 10/17/14.
 */
public class JSONUtils {

    public static DateTime getDateTime(JSONArray array, int index) {
        String dateString = array.optString(index);
        if ( dateString != null && dateString.length() > 0 ) {
            try {
                DateTime dateTime = ATDataSetItemAttribute.AppTreeDateTimeFormat.parseDateTime(dateString);
                return dateTime;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static DateTime getDate(JSONArray array, int index) {
        String dateString = array.optString(index);
        if ( dateString != null && dateString.length() > 0 ) {
            try {
                DateTime dateTime = ATDataSetItemAttribute.AppTreeDateFormat.parseDateTime(dateString);
                return dateTime;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static DateTime getDate(JSONObject json, String key) {
        String dateString = json.optString(key, null);
        if ( dateString != null && dateString.length() > 0 ) {
            try {
                return ATDataSetItemAttribute.AppTreeDateFormat.parseDateTime(dateString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static DateTime getDateTime(JSONObject json, String key) {
        String dateString = json.optString(key, null);
        if ( dateString != null && dateString.length() > 0 ) {
            try {
                return ATDataSetItemAttribute.AppTreeDateTimeFormat.parseDateTime(dateString);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    /**
     * A static method to convert a string to a boolean, default is false
     * @param string The string to be converted
     * @return
     */
    public static boolean getBooleanFromString(String string) {
        if ( string == null ) {
            return false;
        }
        if ( string.equalsIgnoreCase("y") || string.equalsIgnoreCase("1") || string.equalsIgnoreCase("true") ) {
            return true;
        }
        return false;
    }

    /**
     * Gets a boolean object from a json object, default is false
     * @param obj The json object
     * @param key The key the boolean value is at
     * @return
     */
    public static boolean getBoolean(JSONObject obj, String key) {
        try {
            Object val = obj.get(key);
            if ("Y".equals(val)) {
                return true;
            } else if ( new Integer(1).equals(val) ) {
                return true;
            } else if ("N".equals(val)) {
                return false;
            }
            return obj.getBoolean(key);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Gets a string from a json object
     * @param obj The json object
     * @param key The key of the string object to get
     * @return
     */
    public static String getString(JSONObject obj, String key) {
        String value;
        try {
            value = obj.getString(key);
            if ( value != null && value.length() > 0 && !"null".equals(value) ) {
                return value;
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets a string from a json array
     * @param array The json array
     * @param index The index of the string in the array
     * @return
     */
    public static String getString(JSONArray array, int index) {
        try {
            return array.getString(index);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets a double from a json array
     * @param array The json array
     * @param index The index of the double in the array
     * @return
     */
    public static double getDouble(JSONArray array, int index) {
        try {
            return array.getDouble(index);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Gets an int from a json object
     * @param obj The json object
     * @param key The key of the int
     * @return
     */
    public static int getInt(JSONObject obj, String key) {
        try {
            return obj.getInt(key);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Gets a double from a json object
     * @param obj The json object
     * @param key The key for the double
     * @return
     */
    public static double getDouble(JSONObject obj, String key) {
        try {
            return obj.getDouble(key);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Gets a json object from a json object
     * @param obj The json object to search
     * @param key The key of the json object
     * @return
     */
    public static JSONObject getJSONObject(JSONObject obj, String key) {
        try {
            return obj.getJSONObject(key);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Gets a json array from a json object
     * @param obj The json object to search
     * @param key The key to get the json array from
     * @return
     */
    public static JSONArray getJSONArray(JSONObject obj, String key) {
        try {
            return obj.getJSONArray(key);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Checks if a string is a json object or a json array
     * @param str The string to check
     * @return A boolean indicating that the string is a json object/array or not
     */
    public static boolean isJson(String str) {
        try {
            if (new JSONObject(str) != null) {
                return true;
            }
        } catch (Exception e) {
        }
        try {
            if (new JSONArray(str) != null) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * Adds an object to a json object
     * @param object The json object to add the parameter to
     * @param key The key for the object to be added
     * @param value The object
     */
    public static void putOpt(JSONObject object,String key, Object value) {
        try {
            object.putOpt(key, value);
        } catch (JSONException jsonE ) {
            jsonE.printStackTrace();;
        }
    }

    /**
     * Adds a long to a json object
     * @param object The json object
     * @param key The key for the long value
     * @param value The long value
     */
    public static void put(JSONObject object,String key, long value) {
        try {
            object.putOpt(key, value);
        } catch (JSONException jsonE ) {
            jsonE.printStackTrace();;
        }
    }

    /**
     * Adds an int value to a json object
     * @param object The json object
     * @param key The key for the int being added
     * @param value The int to add
     */
    public static void put(JSONObject object,String key, int value) {
        try {
            object.putOpt(key, value);
        } catch (JSONException jsonE ) {
            jsonE.printStackTrace();;
        }
    }

    /**
     * Adds a boolean to a json object
     * @param object The json object to add the boolean to
     * @param key The key for the boolean value
     * @param value The boolean value to add to the json object
     */
    public static void put(JSONObject object,String key, boolean value) {
        try {
            object.putOpt(key, value);
        } catch (JSONException jsonE ) {
            jsonE.printStackTrace();
        }
    }

    /**
     * Adds a double value to a json object
     * @param object The json object to add the double value to
     * @param key The key for the double
     * @param value The double to be added
     */
    public static void put(JSONObject object,String key, double value) {
        try {
            object.putOpt(key, value);
        } catch (JSONException jsonE ) {
            jsonE.printStackTrace();
        }
    }

    /**
     * Converts a string to a json object
     * @param jsonString The string to be converted
     * @return a json object
     */
    public static JSONObject jsonObjectFromString(String jsonString) {
        try {
            return new JSONObject(jsonString);
        } catch ( JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
