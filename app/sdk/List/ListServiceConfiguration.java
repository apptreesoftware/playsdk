package sdk.list;

import sdk.utils.ServiceParameter;

import java.util.ArrayList;

/**
 * Created by alexis on 5/3/16.
 */
public class ListServiceConfiguration {
    String listName;
    boolean includesLocation;
    boolean authenticationRequired;
    int userIDIndex = -1;
    boolean canCache;
    boolean canSearch;

    ArrayList<ListServiceConfigurationAttribute> attributes = new ArrayList();
    ArrayList<ServiceParameter> serviceFilterParameters = new ArrayList();

    /**
     *
     * @param parameters a list of parameters that can be used to filter or modify how the service should behave
     */
    public void setServiceFilterParameters(ArrayList<ServiceParameter> parameters) {
        this.serviceFilterParameters = parameters;
    }

    /**
     * Adds a service filter parameter
     * @param parameter a service parameter that can be used to filter or modify how the service should behave
     */
    public void addServiceParameter(ServiceParameter parameter) {
        this.serviceFilterParameters.add(parameter);
    }

    /**
     * Gets the current list of service parameters
     * @return
     */
    public ArrayList<ServiceParameter> getServiceFilterParameters() {
        return this.serviceFilterParameters;
    }

    public ListServiceConfiguration(String name) {
        listName = name;
    }

    /**
     * Returns the list name
     * @return
     */
    public String getListName() {
        return listName;
    }

    /**
     * Sets the list name
     * @param listName
     */
    public void setListName(String listName) {
        this.listName = listName;
    }

    /**
     * Returns a boolean indicating if the list includes location
     * @return
     */
    public boolean isIncludesLocation() {
        return includesLocation;
    }

    /**
     * Sets a boolean indicating if the list includes location
     * @param includesLocation
     */
    public void setIncludesLocation(boolean includesLocation) {
        this.includesLocation = includesLocation;
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
    public ListServiceConfigurationAttribute getAttribute1() {
        if ( attributes.size() > 0 ) {
            return attributes.get(0);
        }
        return null;
    }

    public void setAttribute(int index, ListServiceConfigurationAttribute attribute) {
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
    public void setAttribute1(ListServiceConfigurationAttribute attribute1) {
        attribute1.setAttributeIndex(1);
        attributes.add(0, attribute1);
    }

    /**
     * Gets the attribute 2
     * @return
     */
    public ListServiceConfigurationAttribute getAttribute2() {
        if ( attributes.size() > 1 ) {
            return attributes.get(1);
        }
        return null;
    }

    /**
     * Sets attribute 2
     * @param attribute2
     */
    public void setAttribute2(ListServiceConfigurationAttribute attribute2) {
        attribute2.setAttributeIndex(2);
        if ( attributes.size() >= 1 ) {
            attributes.add(1, attribute2);
        }
    }

    /**
     * Gets the attribute 3
     * @return
     */
    public ListServiceConfigurationAttribute getAttribute3() {
        if ( attributes.size() > 2 ) {
            return attributes.get(2);
        }
        return null;
    }

    /**
     * Sets attribute 3
     * @param attribute3
     */
    public void setAttribute3(ListServiceConfigurationAttribute attribute3) {
        attribute3.setAttributeIndex(3);
        if ( attributes.size() >= 2 ) {
            attributes.add(2, attribute3);
        }
    }

    /**
     * Gets the attribute 4
     * @return
     */
    public ListServiceConfigurationAttribute getAttribute4() {
        if ( attributes.size() > 3 ) {
            return attributes.get(3);
        }
        return null;
    }

    /**
     * Sets attribute 4
     * @param attribute4
     */
    public void setAttribute4(ListServiceConfigurationAttribute attribute4) {
        attribute4.setAttributeIndex(4);
        if ( attributes.size() >= 3 ) {
            attributes.add(3, attribute4);
        }
    }

    /**
     * Gets the attribute 5
     * @return
     */
    public ListServiceConfigurationAttribute getAttribute5() {
        if ( attributes.size() > 4 ) {
            return attributes.get(4);
        }
        return null;
    }

    /**
     * Sets attribute 5
     * @param attribute5
     */
    public void setAttribute5(ListServiceConfigurationAttribute attribute5) {
        attribute5.setAttributeIndex(5);
        if ( attributes.size() >= 4 ) {
            attributes.add(4, attribute5);
        }
    }

    /**
     * Gets attribute 6
     * @return
     */
    public ListServiceConfigurationAttribute getAttribute6() {
        if ( attributes.size() > 5 ) {
            return attributes.get(5);
        }
        return null;
    }

    /**
     * Sets attribute 6
     * @param attribute6
     */
    public void setAttribute6(ListServiceConfigurationAttribute attribute6) {
        attribute6.setAttributeIndex(6);
        if ( attributes.size() >= 5 ) {
            attributes.add(5, attribute6);
        }
    }

    /**
     * Gets attribute 7
     * @return
     */
    public ListServiceConfigurationAttribute getAttribute7() {
        if ( attributes.size() > 6 ) {
            return attributes.get(6);
        }
        return null;
    }

    /**
     * Sets attribute 7
     * @param attribute7
     */
    public void setAttribute7(ListServiceConfigurationAttribute attribute7) {
        attribute7.setAttributeIndex(7);
        if ( attributes.size() >= 6 ) {
            attributes.add(6, attribute7);
        }
    }

    /**
     * Gets attribute 8
     * @return
     */
    public ListServiceConfigurationAttribute getAttribute8() {
        if ( attributes.size() > 7 ) {
            return attributes.get(7);
        }
        return null;
    }

    /**
     * Sets attribute 8
     * @param attribute8
     */
    public void setAttribute8(ListServiceConfigurationAttribute attribute8) {
        attribute8.setAttributeIndex(8);
        if ( attributes.size() >= 7 ) {
            attributes.add(7, attribute8);
        }
    }

    /**
     * Gets attribute 9
     * @return
     */
    public ListServiceConfigurationAttribute getAttribute9() {
        if ( attributes.size() > 8 ) {
            return attributes.get(8);
        }
        return null;
    }

    /**
     * Sets attribute 9
     * @param attribute9
     */
    public void setAttribute9(ListServiceConfigurationAttribute attribute9) {
        attribute9.setAttributeIndex(9);
        if ( attributes.size() >= 8 ) {
            attributes.add(8, attribute9);
        }
    }

    /**
     * Gets attribute 10
     * @return
     */
    public ListServiceConfigurationAttribute getAttribute10() {
        if ( attributes.size() > 9 ) {
            return attributes.get(9);
        }
        return null;
    }

    /**
     * Sets attribute 10
     * @param attribute10
     */
    public void setAttribute10(ListServiceConfigurationAttribute attribute10) {
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
        return authenticationRequired;
    }

    /**
     * Sets the boolean indicating whether authentication is required for this list
     * @param authenticationRequired
     */
    public void setAuthenticationRequired(boolean authenticationRequired) {
        this.authenticationRequired = authenticationRequired;
    }
}
