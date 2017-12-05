package sdk.user.query;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Condition {
    @JsonProperty("operator")
    private String operator;
    @JsonProperty("clauses")
    private Clause[] clauses;


    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Clause[] getClauses() {
        return clauses;
    }

    public void setClauses(Clause... clauses) {
        this.clauses = clauses;
    }
}
