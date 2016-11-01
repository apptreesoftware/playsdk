package sdk.list;

import sdk.utils.Response;
import sdk.utils.ServiceParameter;

import java.util.ArrayList;


public class ListServiceConfiguration extends Response {
    String listName;
    boolean includesLocation;
    public boolean authenticationRequired;
    int userIDIndex = -1;
    public boolean canCache;
    public boolean canSearch;

    ArrayList<ListServiceConfigurationAttribute> attributes = new ArrayList();
    ArrayList<ServiceParameter> serviceFilterParameters = new ArrayList();

    public ArrayList<ListServiceConfigurationAttribute> getAttributes() { return this.attributes; }

    public ListServiceConfiguration() {}

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


    public boolean getCanCache() {
        return canCache;
    }

    public void setCanCache(boolean canCache) {
        this.canCache = canCache;
    }

    public boolean getCanSearch() {
        return canSearch;
    }

    public void setCanSearch(boolean canSearch) {
        this.canSearch = canSearch;
    }

    public void setUserIDIndex(int index) { this.userIDIndex = index; }

    public int getUserIDIndex() { return this.userIDIndex; }

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
