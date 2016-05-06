package models.sdk.Data;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import models.sdk.Utils.Response;

import java.io.IOException;

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

        public DataSourceResponse build() {
            return new DataSourceResponse(success, message, dataSet);
        }
    }

}
