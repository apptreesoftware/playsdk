package sdk;

import play.api.Logger;
import play.api.Play;
import play.db.Database;
import play.libs.ws.WSClient;
import play.cache.CacheApi;

/**
 * Created by Matthew Smith on 5/12/16.
 * Copyright AppTree Software, Inc.
 */
public interface AppTreeSource {
    default Database getDatabase() {
        return Play.current().injector().instanceOf(Database.class);
    }
    default Logger getLogger() {
        return Play.current().injector().instanceOf(Logger.class);
    }
    default WSClient getWSClient() { return Play.current().injector().instanceOf(WSClient.class); }
    default CacheApi getCache() { return Play.current().injector().instanceOf(CacheApi.class); }
}
