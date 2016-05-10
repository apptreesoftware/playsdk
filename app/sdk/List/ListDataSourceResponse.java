package sdk.list;

import sdk.utils.Response;

/**
 * Created by alexis on 5/4/16.
 */
public class ListDataSourceResponse extends Response {
    private List records;

    public ListDataSourceResponse(boolean success, String message, List records) {
        super(success, message);
        this.records = records;
    }

    public List getList() { return records; }

    public static class Builder {
        private boolean success;
        private String message;
        private List records;

        public Builder setSuccess(boolean success) {
            this.success = success;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setRecords(List records) {
            this.records = records;
            return this;
        }

        public ListDataSourceResponse createListDataSourceResponse() {
            return new ListDataSourceResponse(success, message, records);
        }
    }
}
