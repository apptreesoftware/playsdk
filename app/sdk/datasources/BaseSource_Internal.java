package sdk.datasources;

import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.concurrent.CompletableFuture;

/**
 * Created by Matthew Smith on 9/2/16.
 * Copyright AppTree Software, Inc.
 */
public class BaseSource_Internal {
    protected <T> CompletableFuture<T> observableToFuture(Observable<T> observable) {
        CompletableFuture<T> future = new CompletableFuture<T>();
        observable
                .subscribeOn(Schedulers.io())
                .subscribe(future::complete, future::completeExceptionally);
        return future;
    }
}
