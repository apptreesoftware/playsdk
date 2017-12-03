package sdk.roles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import sdk.roles.models.AppRole;
import sdk.roles.models.RoleRequest;
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
    private static final String getRolesEndURL = "http://localhost:9001/public/1/roles";
    private static final String addRolesURL = "http://localhost:9001/public/1/users/%s/roles/add";
    private static final String removeRolesURL = "http://localhost:9001/public/1/users/%s/roles/remove";
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


    public boolean removeRoleFromUser(String username, String appId, String ...roleNames) {
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
        return request;
    }


    public WSRequest getAddRoleRequest(String appId, String username) {
        WSRequest request = wsClient.url(getAddRolesURL(username));
        request.setHeader(Constants.APP_ID_HEADER, appId);
        addHeadersToRequest(request);
        return request;
    }



    public WSRequest getRemoveRoleRequest(String appId, String username) {
        WSRequest request = wsClient.url(getRemovesRolesURL(username));
        request.setHeader(Constants.APP_ID_HEADER, appId);
        addHeadersToRequest(request);
        return request;
    }


    public void addHeadersToRequest(WSRequest request) {
        request.setHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);
        request.setHeader(Constants.X_APPTREE_TOKEN_ID, this.programmaticUserToken);
    }


    private String getAddRolesURL(String username) {
        return String.format(addRolesURL, username);
    }


    private String getRemovesRolesURL(String username) {
        return String.format(removeRolesURL, username);
    }

    private ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

    private AppRole[] jsonNodeToAppRoles(JsonNode node) {
        try {
            return getObjectMapper().treeToValue(node, AppRole[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("There was an error parsing the roles response");
        }
    }


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
