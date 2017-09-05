package sdk.data;

import sdk.utils.Response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Orozco on 9/5/17.
 */
public class ConversionServiceConfiguration extends ServiceConfiguration {
    private Collection<ServiceConfigurationAttribute> destinationAttributes;

    public Collection<ServiceConfigurationAttribute> getDestinationAttributes() {
        return destinationAttributes;
    }

    public void setDestinationAttributes(Collection<ServiceConfigurationAttribute> destinationAttributes) {
        this.destinationAttributes = destinationAttributes;
    }
}
