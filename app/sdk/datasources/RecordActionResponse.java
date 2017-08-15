package sdk.datasources;

import sdk.data.DataSetItem;

/**
 * Created by Matthew Smith on 10/14/16.
 * Copyright AppTree Software, Inc.
 */
public class RecordActionResponse {
    DataSetItem dataSetItem;
    Object object;
    String message;
    boolean showAsAlert;
    boolean hasDataSetItem;

    public boolean isDataSetItem() {
        return hasDataSetItem;
    }

    public DataSetItem getDataSetItem() {
        return dataSetItem;
    }

    public Object getObject() { return object; }

    public String getMessage() {
        return message;
    }

    public boolean isShowAsAlert() {
        return showAsAlert;
    }

    public static class Builder {
        private DataSetItem dataSetItem;
        private boolean showAsAlert = false;
        private String message;
        private boolean hasDataSetItem = false;
        private Object object;

        public Builder withRecord(DataSetItem dataSetItem) {
            hasDataSetItem = true;
            this.dataSetItem = dataSetItem;
            return this;
        }

        public <T> Builder withRecord(T object) {
            hasDataSetItem = false;
            this.object = object;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder showAsAlert(boolean alert) {
            this.showAsAlert = alert;
            return this;
        }

        public RecordActionResponse build() {
            RecordActionResponse response;
            response = new RecordActionResponse();
            response.dataSetItem = dataSetItem;
            response.message = message;
            response.showAsAlert = showAsAlert;
            response.hasDataSetItem = hasDataSetItem;
            response.object = object;
            return response;
        }
    }
}
