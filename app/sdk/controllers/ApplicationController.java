package sdk.controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.Application;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import sdk.AppTree;
import sdk.ValidateRequestAction;
import sdk.attachment.AttachmentDataSource;
import sdk.auth.AuthenticationSource;
import sdk.user.UserDataSource;

import javax.inject.Inject;

/**
 * Created by matthew on 5/10/16.
 */
@With({ValidateRequestAction.class})
public class ApplicationController extends Controller {

    @Inject Application application;

    public Result describeEndpoints() {
        ObjectNode objectNode = Json.newObject();
        objectNode.put("success", true);
        String hostURL = application.configuration().getString("host");

        ArrayNode records = objectNode.putArray("records");
        AppTree.dataSources.forEach((endpoint, datasource) -> {
            String name = datasource.getServiceDescription() != null ? datasource.getServiceDescription() : endpoint;
            String url = hostURL + "/dataset/" + endpoint;
            addEndpoint("dataset/" + endpoint, name, url, "data", records);
        });
        AppTree.dataCollectionSources.forEach((endpoint, dataCollectionSource) -> {
            String name = dataCollectionSource.getServiceName() != null ? dataCollectionSource.getServiceName() : endpoint;
            String url = hostURL + "/datacollection/" + endpoint;
            addEndpoint("datacollection/" + endpoint, name, url, "data collection", records);
        });
        AppTree.listSources.forEach((endpoint, datasource) -> {
            String name = datasource.getServiceName() != null ? datasource.getServiceName() : endpoint;
            String url = hostURL + "/list/" + endpoint;
            addEndpoint("list/" + endpoint, name, url, "list", records);
        });
        UserDataSource userDataSource = AppTree.getUserDataSource();
        if ( userDataSource != null ) {
            addEndpoint("user", "User Info", hostURL + "/user", "user info", records);
        }
        AuthenticationSource authenticationSource = AppTree.getAuthenticationSource();
        if ( authenticationSource != null ) {
            addEndpoint("auth", "Authentication", hostURL + "/auth", "authentication", records);
        }
        AttachmentDataSource attachmentDataSource = AppTree.getAttachmentDataSource();
        if ( attachmentDataSource != null ) {
            addEndpoint("attachment", "Attachment", hostURL + "/attachments", "Attachment", records);
        }
        return ok(objectNode.toString());
    }

    private void addEndpoint(String endpoint, String name, String url, String type, ArrayNode records) {
        ObjectNode endpointJSON = Json.newObject();
        endpointJSON.put("endpoint", endpoint);
        endpointJSON.put("name", name);
        endpointJSON.put("type", type);
        endpointJSON.put("url", url);
        records.add(endpointJSON);
    }

}
