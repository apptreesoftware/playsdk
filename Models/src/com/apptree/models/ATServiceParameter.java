package com.apptree.models;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by alexis on 9/1/15.
 */
public class ATServiceParameter {
    public enum ParameterType {
        String,
        Boolean,
        SingleList,
        MultiList,
        Location,
        DateRange;
    }

    String key;
    ParameterType parameterType;
    List<String> possibleValues;
    boolean availableInQueryBuilder;
    boolean required;

    public ATServiceParameter() {}

    public void setRequired(boolean required) { this.required = required; }

    public boolean getRequired() { return required; }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setParameterType(ParameterType parameterType) {
        this.parameterType = parameterType;
    }

    public ParameterType getParameterType() {
        return parameterType;
    }

    public void addPossibleValue(String possibleValue) {
        if ( possibleValues == null ) {
            possibleValues = new ArrayList<String>();
        }
        possibleValues.add(possibleValue);
    }

    public void addPossibleValues(Collection<String> possibleValues) {
        if ( possibleValues != null ) {
            if ( this.possibleValues == null ) {
                this.possibleValues = new ArrayList<String>();
            }
            this.possibleValues.addAll(possibleValues);
        }
    }

    public List<String> getPossibleValues() {
        return possibleValues;
    }

    public ParameterType getParameterTypeFromString(String parameterString) {
        if (parameterString.equals("Boolean")) {
            return ParameterType.Boolean;
        } else if (parameterString.equals("SingleList")) {
            return ParameterType.SingleList;
        } else if (parameterString.equals("MultiList")) {
            return ParameterType.MultiList;
        } else if (parameterString.equals("DateRange")) {
            return ParameterType.DateRange;
        } else if (parameterString.equals("Location")) {
            return ParameterType.Location;
        } else {
            return ParameterType.String;
        }
    }

    public String getStringFromParameterType(ParameterType type) {
        switch(type) {
            case Boolean:
                return "Boolean";
            case SingleList:
                return "SingleList";
            case MultiList:
                return "MultiList";
            case DateRange:
                return "DateRange";
            case Location:
                return "Location";
            default:
                return "String";
        }
    }

    public JSONObject toJSON() {
        JSONObject jsonObject;
        jsonObject = new JSONObject();
        jsonObject.putOpt("key", key);
        jsonObject.putOpt("type", getStringFromParameterType(parameterType));
        jsonObject.putOpt("possibleValues", possibleValues);
        jsonObject.putOpt("availableInQueryBuilder", availableInQueryBuilder);
        jsonObject.putOpt("required", required);
        return jsonObject;
    }

    public static class Builder {
        ParameterType mParameterType;
        String mKey;
        List<String> mPossibleValues;
        boolean availableInQueryBuilder;
        boolean mRequired = false;

        /**
         * Creates a builder object
         * @param key a String key value for a URL parameter
         */
        public Builder(String key) {
            mKey = key;
        }

        /**
         * Sets the parameter value type as a string
         * @return the builder with a string parameter value type
         */
        public Builder asTextParameter() {
            mParameterType = ParameterType.String;
            mPossibleValues = null;
            return this;
        }

        /**
         * Sets the available in query builder to be true
         * @return the builder that is available in query builder
         */
        public Builder availableInQueryBuilder() {
            availableInQueryBuilder = true;
            return this;
        }

        /**
         * Sets the parameter value type as a boolean
         * @return the builder with a boolean value type
         */
        public Builder asBooleanParameter() {
            mParameterType = ParameterType.Boolean;
            mPossibleValues = null;
            return this;
        }

        /**
         * Sets the parameter type as string
         * @param possibleValue the string value that the parameter can have
         * @return the builder with the string value type and possible value
         */
        public Builder asSingleListParameter(String possibleValue) {
            mParameterType = ParameterType.SingleList;
            mPossibleValues = new ArrayList<String>();
            mPossibleValues.add(possibleValue);
            return this;
        }

        /**
         * Sets the parameter type as multi list parameter
         * @param possibleValues a collection of the possible values the parameter can have
         * @return the builder with the multi list value type and list of possible values
         */
        public Builder asMultiListParameter(Collection<String> possibleValues) {
            mParameterType = ParameterType.MultiList;
            mPossibleValues = new ArrayList<String>();
            mPossibleValues.addAll(possibleValues);
            return this;
        }

        /**
         * Sets the parameter value type as date range
         * @return the builder with the date range value type
         */
        public Builder asDateRange() {
            mParameterType = ParameterType.DateRange;
            mPossibleValues = null;
            return this;
        }

        /**
         * Sets the parameter value type as location
         * @return the builder with the location value type
         */
        public Builder asLocation() {
            mParameterType = ParameterType.Location;
            mPossibleValues = null;
            return this;
        }

        /**
         * Makes this URL parameter required for all calls to the associated service
         * @return a builder with a required flag as true
         */
        public Builder required() {
            mRequired = true;
            return this;
        }

        /**
         * Creates and returns an ATService parameter with the given values
         * @return
         */
        public ATServiceParameter build() {
            ATServiceParameter parameter = new ATServiceParameter();
            parameter.setKey(mKey);
            parameter.setParameterType(mParameterType);
            parameter.addPossibleValues(mPossibleValues);
            parameter.availableInQueryBuilder = this.availableInQueryBuilder;
            parameter.required = mRequired;
            return parameter;
        }
    }
}
