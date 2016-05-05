package models.sdk.Data;

import models.sdk.Utils.Response;

/**
 * Created by alexis on 5/3/16.
 */
public class DataSourceResponse extends Response {
    public DataSet records;

    private DataSourceResponse(boolean success, String message, DataSet dataSet) {
        super(success, message);
        records = dataSet;
    }

    public static class Builder {
        private boolean success;
        private String message;
        private DataSet dataSet;

        public Builder setSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public Builder withAuthFailure() {
            success = false;
            message = "Invalid Authentication";
            dataSet = null;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setDataSet(DataSet dataSet) {
            this.dataSet = dataSet;
            return this;
        }

        public DataSourceResponse createDataSourceResponse() {
            return new DataSourceResponse(success, message, dataSet);
        }
    }
}
