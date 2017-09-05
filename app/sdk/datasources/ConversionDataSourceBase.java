package sdk.datasources;

import sdk.AppTreeSource;
import sdk.converter.ObjectConverter;
import sdk.data.ConversionServiceConfiguration;
import sdk.data.ServiceConfiguration;

import java.util.ArrayList;

/**
 * Created by Orozco on 9/5/17.
 */
public interface ConversionDataSourceBase extends AppTreeSource {

    public abstract String getServiceDescription();

    default ServiceConfiguration getConfiguration(Class sourceClass, Class destinationClass) {
        ConversionServiceConfiguration conversionServiceConfiguration = new ConversionServiceConfiguration();
        conversionServiceConfiguration.setName(getServiceDescription());
        conversionServiceConfiguration.attributes = new ArrayList<>(ObjectConverter.generateConfigurationAttributes(sourceClass));
        conversionServiceConfiguration.setDestinationAttributes(ObjectConverter.generateConfigurationAttributes(destinationClass));
        return conversionServiceConfiguration;
    }
}
