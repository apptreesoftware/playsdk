package com.apptree.models;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by alexis on 2/5/15.
 */
public class ATDefaultItem {
    private String stringValue;
    private int dataAttributeIndex;

    /**
     * Creates a default item
     */
    public ATDefaultItem(String value, int index) {
        stringValue = value;
        dataAttributeIndex = index;
    }

    /**
     * Converts the default item to a json object
     * @return a json object of the default item
     */
    public JSONObject toJSON() {
        JSONObject jsonObject;

        jsonObject = new JSONObject();
        jsonObject.put("value", this.stringValue);
        jsonObject.put("data_attribute_index", this.dataAttributeIndex);

        return jsonObject;
    }

    public class Builder {
        private int mIndex;
        private String mStringValue;

        /**
         * Constructs a builder object with the given index
         * @param index int index
         */
        public Builder(int index) {
            mIndex = index;
        }

        /**
         * Sets the type as String
         * @param value string value
         * @return this builder object
         */
        public Builder asString(String value) {
            mStringValue = value;
            return this;
        }

        /**
         * Sets the type as int
         * @param value int value
         * @return this Builder
         */
        public Builder asInt(int value) {
            mStringValue = value + "";
            return this;
        }

        /**
         * Sets the type as double
         * @param value double value
         * @return this Builder
         */
        public Builder asDouble(double value) {
            mStringValue = value + "";
            return this;
        }

        /**
         * Sets the type as boolean
         * @param value boolean value
         * @return this Builder
         */
        public Builder asBoolean(boolean value) {
            mStringValue = value + "";
            return this;
        }

        /**
         * Sets the type as date
         * @param date dateTime value
         * @return this Builder
         */
        public Builder asDate(DateTime date) {
            mStringValue = ATDataSetItemAttribute.AppTreeDateFormat.print(date);
            return this;
        }

        /**
         * Sets the type as dateTime
         * @param date dateTime value
         * @return this Builder
         */
        public Builder asDateTime(DateTime date) {
            mStringValue = ATDataSetItemAttribute.AppTreeDateTimeFormat.print(date);
            return this;
        }

        /**
         * Sets the type as a date range
         * @param dateRange ATDateRange value
         * @return this builder
         */
        public Builder asDateRange(ATDateRange dateRange) {
            mStringValue = dateRange.toJSON().toString();
            return this;
        }

        /**
         * Sets the type as date time range
         * @param dateTimeRange ATDateTimeRange value
         * @return this Builder
         */
        public Builder asDateTimeRange(ATDateTimeRange dateTimeRange) {
            mStringValue = dateTimeRange.toJSON().toString();
            return this;
        }

        /**
         * Sets the type as list item
         * @param item ATListItem value
         * @return this Builder
         */
        public Builder asListItem(ATListItem item) {
            mStringValue = item.toJSON().toString();
            return this;
        }

        /**
         * Sets the type as Color
         * @param color ATColor value
         * @return this Builder
         */
        public Builder asColor(ATColor color) {
            mStringValue = color.toJSON().toString();
            return this;
        }

        /**
         * Create a default item with the specified builder configuration set
         * @return an ATDefaultItem made from the builder values
         */
        public ATDefaultItem build() {
            return new ATDefaultItem(mStringValue, mIndex);
        }
    }
}
