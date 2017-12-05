package sdk.roles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.Play;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import sdk.roles.models.AppRole;
import sdk.roles.models.RoleRequest;
import sdk.utils.ConfigUtils;
import sdk.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import static sdk.utils.ValidationUtils.NullOrEmpty;

public class RoleManager {
    private String programmaticUserToken;
    private WSClient wsClient;
    private static final String getRolesEndURL = ConfigUtils.getStringFromConfig("get_role_url");
    private static final String addRolesURL = ConfigUtils.getStringFromConfig("add_role_url");
    private static final String removeRolesURL = ConfigUtils.getStringFromConfig("remove_role_url");
    private static ObjectMapper objectMapper;

    private RoleManager() {
    }

    private RoleManager(String programmaticUserToken, WSClient client) {
        this.programmaticUserToken = programmaticUserToken;
        this.wsClient = client;
    }

    public static RoleManager getInstance(String programmaticUserToken, WSClient client) {
        return new RoleManager(programmaticUserToken, client);
    }

    public boolean addRolesToUser(String username, String appId, String... roleNames) {
        if (NullOrEmpty(this.programmaticUserToken) || wsClient == null) {
            throw new RuntimeException("Role Manager was not initialized correctly.");
        }
        WSRequest request = getAddRoleRequest(appId, username);
        RoleRequest roleRequest = new RoleRequest(roleNames);
        JsonNode bodyNode = getObjectMapper().valueToTree(roleRequest);
        try {
            int status = request.post(bodyNode)
                                .thenApply(WSResponse::getStatus)
                                .toCompletableFuture()
                                .get();
            if (status != 200) return false;
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean removeRoleFromUser(String username, String appId, String... roleNames) {
        WSRequest request = getRemoveRoleRequest(appId, username);
        RoleRequest roleRequest = new RoleRequest(roleNames);
        JsonNode bodyNode = getObjectMapper().valueToTree(roleRequest);
        try {
            int status = request.post(bodyNode)
                                .thenApply(WSResponse::getStatus)
                                .toCompletableFuture()
                                .get();
            if (status != 200) return false;
            return true;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<AppRole> getRolesForAppID(String appID) throws ExecutionException,
                                                               InterruptedException {
        WSRequest request = getAppRolesRequest(appID);
        CompletionStage<WSResponse> response = request.get();
        AppRole[] roles = response.thenApply(WSResponse::asJson)
                                  .thenApply(this::jsonNodeToAppRoles)
                                  .toCompletableFuture()
                                  .get();
        return Arrays.asList(roles);
    }


    public CompletableFuture<Collection<AppRole>> getAppRolesForIDCompletableFuture(String appID) {
        WSRequest request = getAppRolesRequest(appID);
        CompletionStage<WSResponse> response = request.get();
        return response
            .thenApply(WSResponse::asJson)
            .thenApply(this::jsonNodeToAppRolesCollection)
            .toCompletableFuture();
    }

    public WSRequest getAppRolesRequest(String appID) {
        if (wsClient == null) {
            throw new RuntimeException("WS Client was not initialized correctly");
        }
        WSRequest request = wsClient.url(getRolesEndURL);
        if (NullOrEmpty(programmaticUserToken)) {
            throw new RuntimeException("Programmatic user token was not initialized correctly");
        }
        request.setHeader(Constants.APP_ID_HEADER, appID);
        request.setHeader(Constants.X_APPTREE_TOKEN_ID, programmaticUserToken);
        return request;
    }

    /**
     * builds the add roles rest request using the appId and username
     *
     * @param appId
     * @param username
     * @return
     */
    public WSRequest getAddRoleRequest(String appId, String username) {
        WSRequest request = wsClient.url(getAddRolesURL(username));
        request.setHeader(Constants.APP_ID_HEADER, appId);
        addHeadersToRequest(request);
        return request;
    }


    /**
     * Builds the remove roles rest request using the appId and username
     *
     * @param appId
     * @param username
     * @return
     */
    public WSRequest getRemoveRoleRequest(String appId, String username) {
        WSRequest request = wsClient.url(getRemovesRolesURL(username));
        request.setHeader(Constants.APP_ID_HEADER, appId);
        addHeadersToRequest(request);
        return request;
    }


    /**
     * Adds common headers to the [Roles] requests
     *
     * @param request
     */
    public void addHeadersToRequest(WSRequest request) {
        request.setHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);
        request.setHeader(Constants.X_APPTREE_TOKEN_ID, this.programmaticUserToken);
    }


    /**
     * Inserts path variable username into the Add Role rest path
     *
     * @param username
     * @return
     */
    private String getAddRolesURL(String username) {
        return String.format(addRolesURL, username);
    }


    /**
     * Inserts path variables username into the Remove Role rest path
     *
     * @param username
     * @return
     */
    private String getRemovesRolesURL(String username) {
        return String.format(removeRolesURL, username);
    }

    /**
     * Returns a single object mapper per Role Manager
     *
     * @return
     */
    private ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }


    /**
     * Takes a [AppRole[] JsonNode] converts to an AppRole[]
     *
     * @param node
     * @return
     */
    private AppRole[] jsonNodeToAppRoles(JsonNode node) {
        try {
            return getObjectMapper().treeToValue(node, AppRole[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("There was an error parsing the roles response");
        }
    }


    /**
     * Converts a [AppRole[] JsonNode] into a Collection<AppRole>
     *
     * @param node
     * @return
     */
    private Collection<AppRole> jsonNodeToAppRolesCollection(JsonNode node) {
        try {
            AppRole[] roles = getObjectMapper().treeToValue(node, AppRole[].class);
            return new ArrayList<AppRole>(Arrays.asList(roles));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("There was an error parsing the roles response");
        }
    }


}
