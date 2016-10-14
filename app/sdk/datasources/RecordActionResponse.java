package sdk.datasources;

import sdk.data.DataSetItem;

/**
 * Created by Matthew Smith on 10/14/16.
 * Copyright AppTree Software, Inc.
 */
public class RecordActionResponse {
    DataSetItem dataSetItem;
    String message;
    boolean showAsAlert;

    public DataSetItem getDataSetItem() {
        return dataSetItem;
    }

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

        public Builder withRecord(DataSetItem dataSetItem) {
            this.dataSetItem = dataSetItem;
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
            return response;
        }
    }
}
