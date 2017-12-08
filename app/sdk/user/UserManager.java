package sdk.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import play.Play;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;
import sdk.pushNotifications.PushNotificationResponse;
import sdk.pushNotifications.PushNotificationUtils;
import sdk.roles.RoleManager;
import sdk.roles.models.AppRole;
import sdk.user.models.AppUser;
import sdk.user.query.Clause;
import sdk.user.query.Condition;
import sdk.user.query.UserQuery;
import sdk.utils.Constants;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static sdk.utils.ValidationUtils.NullOrEmpty;

public class UserManager {
    private String programmaticUserToken;
    private WSClient wsClient;
    private ObjectMapper objectMapper;
    private static final String queryUserRoute = "/public/1/users/query";
    private static final String QUERY_USER_URL = getQueryUserURL();

    private UserManager() {
    }

    public UserManager(String programmaticUserToken, WSClient wsClient) {
        this.programmaticUserToken = programmaticUserToken;
        this.wsClient = wsClient;
    }

    public static UserManager getInstance(String programmaticUserToken, WSClient wsClient) {
        return new UserManager(programmaticUserToken, wsClient);
    }

    public void setProgrammaticUserToken(String programmaticUserToken) {
        this.programmaticUserToken = programmaticUserToken;
    }


    public boolean sendPushToUsersWithRole(String roleName, String appID, String message,
                                           String appAPIKey) {
        RoleManager roleManager = RoleManager.getInstance(programmaticUserToken, wsClient);
        try {
            List<AppRole> appRoles = roleManager.getRolesForAppID(appID);
            int roleId = 0;
            for (AppRole role : appRoles) {
                if (role.getRoleName().equalsIgnoreCase(roleName)) {
                    roleId = role.getRoleID();
                }
            }
            if (roleId == 0) {
                throw new RuntimeException(String.format(
                    "The given role did not match any of the roles for App ID : %s", appID));
            }
            UserQuery userQuery = getUserQueryForRoleAndAppID(roleId);
            Optional<AppUser[]> optionalUsers = getAppUsersFromQuery(userQuery, appID);
            if (!optionalUsers.isPresent()) {
                return false;
            } else {
                AppUser[] users = optionalUsers.get();
                sendPush(users, message, appAPIKey, appID);
                return true;
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }


    private void sendPush(AppUser[] users, String message, String appAPIKey, String appID) {
        PushNotificationUtils utils = PushNotificationUtils.getDefaultInstance();
        List<String> userIds =
            Arrays.stream(users).map(AppUser::getId).map(String::valueOf).collect(
                Collectors.toList());
        try {
            PushNotificationResponse response =
                utils.sendPushToUsers(userIds, "User has requested access", message, appAPIKey,
                                      appID).toCompletableFuture().get();
            System.out.println(response);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("There was an issue pushing notifications");
        }
    }


    private Optional<AppUser[]> getAppUsersFromQuery(UserQuery userQuery, String appID) {
        WSRequest request = wsClient.url(QUERY_USER_URL);
        request.setHeader(Constants.APP_ID_HEADER, appID);
        request.setHeader(Constants.X_APPTREE_TOKEN_ID, this.programmaticUserToken);
        request.setHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON);

        JsonNode node = getObjectMapper().valueToTree(userQuery);
        try {
            return Optional.of(request.post(node)
                                      .thenApply(WSResponse::asJson)
                                      .thenApply(this::usersFromJsonNode)
                                      .toCompletableFuture()
                                      .get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    private UserQuery getUserQueryForRoleAndAppID(int roleID) {
        UserQuery userQuery = new UserQuery();
        userQuery.setQueryType("select");
        Condition condition = new Condition();
        condition.setOperator("AND");
        Clause clause = new Clause();
        clause.setColumnName("roles");
        clause.setQueryOperator("JSON");
        clause.setValues(String.valueOf(roleID));
        condition.setClauses(clause);
        userQuery.setConditions(condition);
        return userQuery;
    }


    private ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }


    private AppUser[] usersFromJsonNode(JsonNode node) {
        if (node == null) {
            return new AppUser[0];
        }
        try {
            return getObjectMapper().treeToValue(node, AppUser[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new AppUser[0];
        }
    }


    public static String getQueryUserURL() {
        String value = Play.application().configuration().getString("base_core_url");
        if (NullOrEmpty((value))) {
            throw new RuntimeException("Query user url was not set or configured");
        }
        return value + queryUserRoute;
    }


}
