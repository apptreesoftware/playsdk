package sdk.data;

import sdk.utils.Response;

/**
 * Created by alexis on 5/3/16.
 */
public class ConfigurationResponse extends Response {
    public ServiceConfiguration records;

    /**
     * Creates a configuration response
     * @param success A boolean indicating the success of the call
     * @param message A String message about the call optional
     * @param configuration A service configuration containing the attributes
     */
    private ConfigurationResponse(boolean success, String message, ServiceConfiguration configuration) {
        super(success, message);
        this.records = configuration;
    }

    public static class Builder {
        private boolean success;
        private String message;
        private ServiceConfiguration configuration;

        public Builder setSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setConfiguration(ServiceConfiguration configuration) {
            this.configuration = configuration;
            return this;
        }

        public ConfigurationResponse createConfigurationResponse() {
            return new ConfigurationResponse(success, message, configuration);
        }
    }
}
