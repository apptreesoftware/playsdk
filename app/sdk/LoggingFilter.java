package sdk;

import akka.stream.Materializer;
import play.Logger;
import play.mvc.Filter;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

/**
 * Created by matthew on 4/5/16.
 */
public class LoggingFilter extends Filter {

    @Inject
    public LoggingFilter(Materializer mat) {
        super(mat);
    }

    @Override
    public CompletionStage<Result> apply(Function<Http.RequestHeader, CompletionStage<Result>> next, Http.RequestHeader requestHeader) {
        Long startTime = System.currentTimeMillis();
        String uuid = UUID.randomUUID().toString();
        if ( Logger.isInfoEnabled() ) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n=========================== Handling request ")
                         .append(uuid)
                         .append(" ===========================")
                         .append("\n")
                         .append(requestHeader.method())
                         .append(" ")
                         .append(requestHeader.uri())
                         .append("\n")
                         .append("Headers:")
                         .append("\n");
            requestHeader.headers().forEach((key, value) -> {
                stringBuilder.append(key).append(":");
                if ( value.length > 0 ) {
                    stringBuilder.append(value[0]);
                }
                stringBuilder.append("\n");
            });
            stringBuilder.append("=======================================================================");
            Logger.info(stringBuilder.toString());
        }
        return next.apply(requestHeader).thenApply(result -> {
            Long endTime = System.currentTimeMillis();
            Long requestTime = endTime - startTime;
            if (Logger.isInfoEnabled()) {
                Logger.info("Request " + uuid + ": " + requestHeader.method() + " " + requestHeader.uri() + " took " + requestTime + "ms and returned " + result.status());
            }
            return result.withHeader("Request-Time", "" + requestTime);
        });
    }
}
