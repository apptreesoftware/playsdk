package com.apptree.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

public class HttpUtil {

    /**
     * Gets an http parameter
     * @param req The servlet request
     * @param param The parameter key
     * @return
     */
	public static String getHttpParameter( HttpServletRequest req, String param){
		String strReturn;
		try{
		strReturn=req.getParameter(param);
		}catch(Exception e){
			strReturn=null;			
		}
		return strReturn;
	}

    /**
     * Gets a header
     * @param req The servlet request
     * @param param The header key
     * @return
     */
	public static String getHttpHeader( HttpServletRequest req, String param){
		String strReturn;
		try{
		strReturn=req.getHeader(param);;
		}catch(Exception e){
			strReturn=null;			
		}
		return strReturn;
	}

    /**
     * Gets the authorization token
     * @param request The servlet request
     * @return
     */
    public static String getAPIToken(HttpServletRequest request) {
        return HttpUtil.getHttpHeader(request, "X-Auth-Token");
    }

    /**
     * Gets the json object from the given request body
     * @param request The servlet request
     * @return a json object
     * @throws JSONException
     */
    public static JSONObject getJSONObjectBody(HttpServletRequest request) throws JSONException {
        String bodyString;
        JSONObject jsonObject = null;

        bodyString = getStringBody(request);
        if ( bodyString != null && bodyString.length() > 0 ) {
            jsonObject = new JSONObject(bodyString);
        }
        return jsonObject;
    }

    /**
     * Gets the json array from the given request body
     * @param request The servlet request
     * @return a json array
     * @throws JSONException
     */
    public static JSONArray getJSONArrayBody(HttpServletRequest request) {
        String bodyString;
        JSONArray jsonArray = null;

        bodyString = getStringBody(request);
        if ( bodyString != null && bodyString.length() > 0 ) {
            try {
                jsonArray = new JSONArray(bodyString);
            } catch ( JSONException e) {}
        }
        return jsonArray;
    }

    /**
     * Gets the string from the given request body
     * @param request The servlet request
     * @return a string
     * @throws JSONException
     */
    public static String getStringBody(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader;
        try {
            reader = request.getReader();
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } finally {
                reader.close();
            }
        } catch ( Exception e ) {
            return null;
        }
        return sb.toString();
    }

	
}
