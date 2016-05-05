package com.apptree.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by matthew on 1/5/15.
 */

public class ATListServiceConfiguration {

    String mListName;
    boolean mIncludesLocation;
    boolean mAuthenticationRequired;
    int userIDIndex = -1;
    boolean canCache;
    boolean canSearch;

    ArrayList<ATListServiceConfigurationAttribute> attributes = new ArrayList<ATListServiceConfigurationAttribute>();
    ArrayList<ATServiceParameter> serviceParameters = new ArrayList<ATServiceParameter>();

    /**
     *
     * @param parameters a list of parameters that can be used to filter or modify how the service should behave
     */
    public void setServiceParameters(ArrayList<ATServiceParameter> parameters) {
        this.serviceParameters = parameters;
    }

    /**
     * Adds a service filter parameter
     * @param parameter a service parameter that can be used to filter or modify how the service should behave
     */
    public void addServiceParameter(ATServiceParameter parameter) {
        this.serviceParameters.add(parameter);
    }

    /**
     * Gets the current list of service parameters
     * @return
     */
    public ArrayList<ATServiceParameter> getServiceParameters() {
        return this.serviceParameters;
    }

    public ATListServiceConfiguration(String name) {
        mListName = name;
    }

    /**
     * Returns the list name
     * @return
     */
    public String getListName() {
        return mListName;
    }

    /**
     * Sets the list name
     * @param listName
     */
    public void setListName(String listName) {
        mListName = listName;
    }

    /**
     * Returns a boolean indicating if the list includes location
     * @return
     */
    public boolean isIncludesLocation() {
        return mIncludesLocation;
    }

    /**
     * Sets a boolean indicating if the list includes location
     * @param includesLocation
     */
    public void setIncludesLocation(boolean includesLocation) {
        mIncludesLocation = includesLocation;
    }


    public boolean canCache() {
        return canCache;
    }

    public void setCanCache(boolean canCache) {
        this.canCache = canCache;
    }

    public boolean canSearch() {
        return canSearch;
    }

    public void setCanSearch(boolean canSearch) {
        this.canSearch = canSearch;
    }

    public void setUserIDIndex(int index) { this.userIDIndex = index; }

    public int getUserIDIndex() { return this.userIDIndex; }

    /**
     * Returns attribute 1 description
     * @return
     */
    public ATListServiceConfigurationAttribute getAttribute1() {
        if ( attributes.size() > 0 ) {
            return attributes.get(0);
        }
        return null;
    }

    public void setAttribute(int index, ATListServiceConfigurationAttribute attribute) {
        switch (index) {
            case 1:
                setAttribute1(attribute);
                break;
            case 2:
                setAttribute2(attribute);
                break;
            case 3:
                setAttribute3(attribute);
                break;
            case 4:
                setAttribute4(attribute);
                break;
            case 5:
                setAttribute5(attribute);
                break;
            case 6:
                setAttribute6(attribute);
                break;
            case 7:
                setAttribute7(attribute);
                break;
            case 8:
                setAttribute8(attribute);
                break;
            case 9:
                setAttribute9(attribute);
                break;
            case 10:
                setAttribute10(attribute);
                break;
            default:
                throw new IllegalArgumentException("Index " + index + " out of range 1 - 10");
        }
    }

    /**
     * Sets attribute 1 description
     * @param attribute1
     */
    public void setAttribute1(ATListServiceConfigurationAttribute attribute1) {
        attribute1.setAttributeIndex(1);
        attributes.add(0, attribute1);
    }

    /**
     * Gets the attribute 2
     * @return
     */
    public ATListServiceConfigurationAttribute getAttribute2() {
        if ( attributes.size() > 1 ) {
            return attributes.get(1);
        }
        return null;
    }

    /**
     * Sets attribute 2
     * @param attribute2
     */
    public void setAttribute2(ATListServiceConfigurationAttribute attribute2) {
        attribute2.setAttributeIndex(2);
        if ( attributes.size() >= 1 ) {
            attributes.add(1, attribute2);
        }
    }

    /**
     * Gets the attribute 3
     * @return
     */
    public ATListServiceConfigurationAttribute getAttribute3() {
        if ( attributes.size() > 2 ) {
            return attributes.get(2);
        }
        return null;
    }

    /**
     * Sets attribute 3
     * @param attribute3
     */
    public void setAttribute3(ATListServiceConfigurationAttribute attribute3) {
        attribute3.setAttributeIndex(3);
        if ( attributes.size() >= 2 ) {
            attributes.add(2, attribute3);
        }
    }

    /**
     * Gets the attribute 4
     * @return
     */
    public ATListServiceConfigurationAttribute getAttribute4() {
        if ( attributes.size() > 3 ) {
            return attributes.get(3);
        }
        return null;
    }

    /**
     * Sets attribute 4
     * @param attribute4
     */
    public void setAttribute4(ATListServiceConfigurationAttribute attribute4) {
        attribute4.setAttributeIndex(4);
        if ( attributes.size() >= 3 ) {
            attributes.add(3, attribute4);
        }
    }

    /**
     * Gets the attribute 5
     * @return
     */
    public ATListServiceConfigurationAttribute getAttribute5() {
        if ( attributes.size() > 4 ) {
            return attributes.get(4);
        }
        return null;
    }

    /**
     * Sets attribute 5
     * @param attribute5
     */
    public void setAttribute5(ATListServiceConfigurationAttribute attribute5) {
        attribute5.setAttributeIndex(5);
        if ( attributes.size() >= 4 ) {
            attributes.add(4, attribute5);
        }
    }

    /**
     * Gets attribute 6
     * @return
     */
    public ATListServiceConfigurationAttribute getAttribute6() {
        if ( attributes.size() > 5 ) {
            return attributes.get(5);
        }
        return null;
    }

    /**
     * Sets attribute 6
     * @param attribute6
     */
    public void setAttribute6(ATListServiceConfigurationAttribute attribute6) {
        attribute6.setAttributeIndex(6);
        if ( attributes.size() >= 5 ) {
            attributes.add(5, attribute6);
        }
    }

    /**
     * Gets attribute 7
     * @return
     */
    public ATListServiceConfigurationAttribute getAttribute7() {
        if ( attributes.size() > 6 ) {
            return attributes.get(6);
        }
        return null;
    }

    /**
     * Sets attribute 7
     * @param attribute7
     */
    public void setAttribute7(ATListServiceConfigurationAttribute attribute7) {
        attribute7.setAttributeIndex(7);
        if ( attributes.size() >= 6 ) {
            attributes.add(6, attribute7);
        }
    }

    /**
     * Gets attribute 8
     * @return
     */
    public ATListServiceConfigurationAttribute getAttribute8() {
        if ( attributes.size() > 7 ) {
            return attributes.get(7);
        }
        return null;
    }

    /**
     * Sets attribute 8
     * @param attribute8
     */
    public void setAttribute8(ATListServiceConfigurationAttribute attribute8) {
        attribute8.setAttributeIndex(8);
        if ( attributes.size() >= 7 ) {
            attributes.add(7, attribute8);
        }
    }

    /**
     * Gets attribute 9
     * @return
     */
    public ATListServiceConfigurationAttribute getAttribute9() {
        if ( attributes.size() > 8 ) {
            return attributes.get(8);
        }
        return null;
    }

    /**
     * Sets attribute 9
     * @param attribute9
     */
    public void setAttribute9(ATListServiceConfigurationAttribute attribute9) {
        attribute9.setAttributeIndex(9);
        if ( attributes.size() >= 8 ) {
            attributes.add(8, attribute9);
        }
    }

    /**
     * Gets attribute 10
     * @return
     */
    public ATListServiceConfigurationAttribute getAttribute10() {
        if ( attributes.size() > 9 ) {
            return attributes.get(9);
        }
        return null;
    }

    /**
     * Sets attribute 10
     * @param attribute10
     */
    public void setAttribute10(ATListServiceConfigurationAttribute attribute10) {
        attribute10.setAttributeIndex(10);
        if ( attributes.size() >= 9 ) {
            attributes.add(9, attribute10);
        }
    }

    /**
     * Gets a boolean indicating whether authentication is required for this list
     * @return
     */
    public boolean isAuthenticationRequired() {
        return mAuthenticationRequired;
    }

    /**
     * Sets the boolean indicating whether authentication is required for this list
     * @param authenticationRequired
     */
    public void setAuthenticationRequired(boolean authenticationRequired) {
        mAuthenticationRequired = authenticationRequired;
    }

    /**
     * Converts the list to a json object
     * @return
     */
    public JSONObject toJSON() {
        JSONObject jsonObject;
        JSONArray attributeArray;

        attributeArray = new JSONArray();
        for ( ATListServiceConfigurationAttribute attribute : attributes ) {
            attributeArray.put(attribute.toJSON());
        }

        jsonObject = new JSONObject();
        jsonObject.putOpt("listName",mListName);
        jsonObject.putOpt("includesLocation",mIncludesLocation);
        if ( userIDIndex != - 1) {
            jsonObject.putOpt("userList", true);
            jsonObject.putOpt("userIDIndex", userIDIndex);
        } else {
            jsonObject.putOpt("userList", false);
        }
        jsonObject.putOpt("attributes", attributeArray);
        jsonObject.putOpt("canCache", canCache);
        jsonObject.putOpt("canSearch", canSearch);
        jsonObject.put("authenticationRequired",mAuthenticationRequired);

        if ( serviceParameters != null && serviceParameters.size() > 0 ) {
            JSONArray parameterArray = new JSONArray();
            for ( ATServiceParameter parameter : serviceParameters ) {
                parameterArray.put(parameter.toJSON());
            }
            jsonObject.putOpt("serviceFilterParameters", parameterArray);
        }
        return jsonObject;
    }
}
