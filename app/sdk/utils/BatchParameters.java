package sdk.utils;

import org.joda.time.DateTime;

import java.util.Map;
import java.util.Optional;

/**
 * Created by alexisandreason on 6/14/17.
 */
public class BatchParameters extends Parameters {
    public BatchParameters(Map<String, String[]> parameters) {
        super(parameters);
    }

    public Optional<DateTime> getUpdatedAfterDate() {
        return Optional.ofNullable(getDateForKey(Constants.UPDATED_DATE_PARAM));
    }
}
