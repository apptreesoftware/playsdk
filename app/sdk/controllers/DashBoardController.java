package sdk.controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.inject.Inject;
import play.libs.Json;
import play.libs.ws.WSClient;
import play.libs.ws.WSRequest;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.With;
import sdk.AppTree;
import sdk.ValidateRequestAction;
import sdk.dashboard.DashBoard;
import sdk.datasources.DashBoardSource_Internal;
import sdk.utils.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import static sdk.utils.CallbackLogger.logCallbackInfo;
import static sdk.utils.CallbackLogger.logExceptionCallback;

public class DashBoardController extends Controller {
    @Inject
    protected WSClient wsClient;

    @With({ValidateRequestAction.class})
    public CompletionStage<Result> getDashBoard(String name) {
        Http.Request request = request();
        String callBackURl = request.getHeader(Constants.CORE_CALLBACK_URL);
        AuthenticationInfo authenticationInfo = new AuthenticationInfo(request.headers());
        Parameters parameters = new Parameters(request.queryString());
        DashBoardSource_Internal dataSource_internal = AppTree.lookupDashBoardHandler(name);
        if(dataSource_internal == null) return CompletableFuture.completedFuture(notFound());
        if (callBackURl != null) {
            generateDashboardSourceResponse(dataSource_internal, callBackURl, authenticationInfo, parameters);
            return CompletableFuture.completedFuture(ok(JsonUtils.toJson(Response.asyncSuccess())));
        } else {
            return dataSource_internal.getItems(authenticationInfo, parameters)
                               .thenApply(dashBoard -> ok(JsonUtils.toJson(dashBoard)))
                               .exceptionally(ResponseExceptionHandler::handleException);
        }
    }


    private void generateDashboardSourceResponse(DashBoardSource_Internal dashBoardSource_internal, String callBackURL, AuthenticationInfo authenticationInfo, Parameters parameters) {
        dashBoardSource_internal.getItems(authenticationInfo, parameters)
                                .whenComplete((dashBoard, throwable) -> {
                                    if(throwable != null) {
                                        sendDashboardExceptionCallBack(throwable, callBackURL);
                                    } else {
                                        sendDashBoardResponse(dashBoard, callBackURL);
                                    }
                                });
    }


    private void sendDashboardExceptionCallBack(Throwable throwable, String callBackURL) {
        CompletableFuture.runAsync(() -> {
            WSRequest wsRequest = wsClient.url(callBackURL);
            ResponseExceptionHandler.updateCallbackWithException(wsRequest, throwable);
            wsRequest.execute("POST")
                     .whenComplete((wsResponse, callbackThrowable) -> {
                         logExceptionCallback(wsResponse, throwable, callbackThrowable, callBackURL);
                     });
        });
    }

    private void sendDashBoardResponse(DashBoard dashBoard, String callBackURL) {
        WSRequest request = wsClient.url(callBackURL);
        if(dashBoard.isSuccess()) {
            request.setHeader(Constants.CORE_CALLBACK_TYPE, Constants.CORE_CALLBACK_TYPE_SUCCESS);
            request.setHeader(Constants.CORE_ITEM_COUNT_HEADER, dashBoard.getTotalRecords()+"");
        } else {
            request.setHeader(Constants.CORE_CALLBACK_TYPE, Constants.CORE_CALLBACK_TYPE_WARNING);
            ObjectNode json = Json.newObject();
            json.put(Constants.CORE_CALLBACK_MESSAGE, dashBoard.getMessage() != null? dashBoard.getMessage() : "");
            request.setBody(json);
        }

        try {
            ObjectNode node = dashBoard.toJson();
            request.post(node)
                   .whenComplete((response, throwable) -> {
                    logCallbackInfo(response, throwable, callBackURL);
                   });
        } catch (Exception e) {
            sendDashboardExceptionCallBack(e, callBackURL);
        }
    }
}
