package sdk;

import play.api.Logger;
import play.api.Play;
import play.db.Database;

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
}
