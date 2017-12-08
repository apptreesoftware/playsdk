package sdk.user.query;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Clause {
    @JsonProperty("query_operator")
    private String queryOperator;
    @JsonProperty("column_name")
    private String columnName;
    @JsonProperty("values")
    private String[] values;

    public String getQueryOperator() {
        return queryOperator;
    }

    public void setQueryOperator(String queryOperator) {
        this.queryOperator = queryOperator;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String... values) {
        this.values = values;
    }
}
