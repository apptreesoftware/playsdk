package sdk.user.query;

import java.util.List;

public class UserQueryBuilder {
    private String[] attributes;
    private String queryType;
    private List<Condition> conditions;

    public enum QueryOperator{

    }

    public UserQueryBuilder withAttributes(String ...values) {
        this.attributes = values;
        return this;
    }

    public UserQueryBuilder queryType(String queryType) {
        this.queryType = queryType;
        return this;
    }

}



