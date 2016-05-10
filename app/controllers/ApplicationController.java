package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.Application;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import sdk.AppTree;

import javax.inject.Inject;

/**
 * Created by matthew on 5/10/16.
 */
public class ApplicationController extends Controller {

    @Inject Application application;

    public Result describeEndpoints() {
        ObjectNode objectNode = Json.newObject();
        objectNode.put("success", true);
        ArrayNode records = objectNode.putArray("records");
        AppTree.dataSources.forEach((endpoint, datasource) -> {
            ObjectNode endpointJSON = Json.newObject();
            endpointJSON.put("endpoint", endpoint);
            String name = datasource.getServiceDescription() != null ? datasource.getServiceDescription() : endpoint;
            endpointJSON.put("name", name);
            endpointJSON.put("type", "data");
            endpointJSON.put("url", application.configuration().getString("host") + "/dataSet/" + endpoint);
            records.add(endpointJSON);
        });
        AppTree.listSources.forEach((endpoint, datasource) -> {
            ObjectNode endpointJSON = Json.newObject();
            String name = datasource.getServiceName() != null ? datasource.getServiceName() : endpoint;
            endpointJSON.put("endpoint", endpoint);
            endpointJSON.put("name", name);
            endpointJSON.put("type", "list");
            endpointJSON.put("url", application.configuration().getString("host") + "/list/" + endpoint);
            records.add(endpointJSON);
        });
        return ok(objectNode.toString());
    }
}
