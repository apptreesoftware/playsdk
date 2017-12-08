package sdk.user.query;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class UserQuery {
    @JsonProperty("query_type")
    private String queryType;
    @JsonProperty("attributes")
    private String[] attributes;
    @JsonProperty("conditions")
    private Condition[] conditions;

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String[] getAttributes() {
        return attributes;
    }

    public void setAttributes(String[] attributes) {
        this.attributes = attributes;
    }

    public Condition[] getConditions() {
        return conditions;
    }

    public void setConditions(Condition ... conditions) {
        this.conditions = conditions;
    }
}
