package sdk.datasources;

import sdk.AppTreeSource;
import sdk.data.ServiceConfiguration;

/**
 * Created by Orozco on 9/5/17.
 */
public interface ConversionDataSourceBase extends AppTreeSource {

    public abstract String getServiceDescription();

    public abstract ServiceConfiguration getConfiguration();
}
