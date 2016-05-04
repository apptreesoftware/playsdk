package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import models.InspectionConfiguration;
import play.db.ebean.Transactional;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
//import views.html.index;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by Matthew Smith on 4/25/16.
 * Copyright AppTree Software, Inc.
 */
public class InspectionController extends Controller {

    @Transactional
    public CompletionStage<Result> getInspections() {
        return CompletableFuture
                .supplyAsync(() -> InspectionConfiguration.find.all())
                .thenApply(inspections -> ok(Json.toJson(inspections)));
    }

    @Transactional
    public CompletionStage<Result> getInspection(long inspectionID) {
        return CompletableFuture
                .supplyAsync(() -> InspectionConfiguration.find.byId(inspectionID))
                .thenApply(inspection -> {
                    if ( inspection == null ) { return notFound(); }
                    return ok(Json.toJson(inspection));
                });
    }

    @BodyParser.Of(BodyParser.Json.class)
    @Transactional
    public CompletionStage<Result> createInspectionConfiguration() {
        JsonNode json = request().body().asJson();
        return CompletableFuture.supplyAsync(() -> {
            InspectionConfiguration inspection = Json.fromJson(json, InspectionConfiguration.class);
            inspection.save();
            return ok(Json.toJson(inspection));
        });
    }

    @BodyParser.Of(BodyParser.Json.class)
    @Transactional
    public CompletionStage<Result> updateInspectionConfiguration(long inspectionID) {
        JsonNode json = request().body().asJson();
        InspectionConfiguration updateConfiguration = Json.fromJson(json, InspectionConfiguration.class);
        return CompletableFuture.supplyAsync(() -> {
            Ebean.update(updateConfiguration);
            return updateConfiguration;
        }).thenApply(inspectionConfiguration -> {
            return ok(Json.toJson(inspectionConfiguration));
        });
    }
}
