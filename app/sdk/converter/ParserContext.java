package sdk.converter;

import sdk.data.DataSetItem.CRUDStatus;
import sdk.models.DateRange;
import sdk.models.DateTimeRange;
import sdk.models.SDKDateRange;


import java.util.HashMap;
import java.util.Map;

import static sdk.utils.ClassUtils.Null;

public class ParserContext {
    private Map<String, Object> extraInfo;
    private Map<Object, CRUDStatus> crudStatusMap;
    private String userId;
    private static final String APP_ID = "APP-ID";
    private Map<Integer, SDKDateRange> dateTimeRangeIntegerIndexMap;
    private Map<String, SDKDateRange> dateTimeRangeStringIndexMap;

    public CRUDStatus getState(Object object) {
        CRUDStatus status = getCrudStatusMap().get(object);
        if (status == null) return CRUDStatus.None;
        return status;
    }

    public boolean isCreated(Object object) {
        CRUDStatus status = getCrudStatusMap().get(object);
        return (status != null && status.equals(CRUDStatus.Create));
    }

    public boolean isUpdated(Object object) {
        CRUDStatus status = getCrudStatusMap().get(object);
        return (status != null && status.equals(CRUDStatus.Update));
    }

    public boolean isDeleted(Object object) {
        CRUDStatus status = getCrudStatusMap().get(object);
        return (status != null && status.equals(CRUDStatus.Delete));
    }

    <T> void setItemStatus(Object object, CRUDStatus status) {
        getCrudStatusMap().put(object, status);
    }

    public void setAppId(String appId) {
        getExtraInfo().put(APP_ID, appId);
    }

    /**
     * if app id doesnt exist this function will return an empty string
     *
     * @return
     */
    public String getAppId() {
        String appID = (String) getExtraInfo().get(APP_ID);
        if (Null(appID)) {
            return "";
        }
        return appID;
    }


    private Map<String, Object> getExtraInfo() {
        if (extraInfo == null) {
            extraInfo = new HashMap<>();
        }
        return extraInfo;
    }

    private Map<Object, CRUDStatus> getCrudStatusMap() {
        if (crudStatusMap == null) {
            crudStatusMap = new HashMap<>();
        }
        return crudStatusMap;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }


    public DateRange getDateRangeForField(int index) {
        return (DateRange) getSDKDateRangeForIndex(index);
    }

    public DateRange getDateRangeForField(String fieldName) {
        return (DateRange) getSDKDateRangeForFieldName(fieldName);
    }

    public DateTimeRange getDateTimeRangeForField(int index) {
        return (DateTimeRange) getSDKDateRangeForIndex(index);
    }

    public DateTimeRange getDateTimeRangeForField(String fieldName) {
        return (DateTimeRange) getSDKDateRangeForFieldName(fieldName);
    }


    private SDKDateRange getSDKDateRangeForIndex(int index) {
        return getDateTimeRangeIntegerIndexMap().get(index);
    }


    private SDKDateRange getSDKDateRangeForFieldName(String fieldName) {
        return getDateTimeRangeStringIndexMap().get(fieldName);
    }


    void putDateTimeRange(int index, String fieldName, SDKDateRange sdkDateRange) {
        putDateTimeRange(index, sdkDateRange);
        putDateTimeRange(fieldName, sdkDateRange);
    }

    private void putDateTimeRange(int index, SDKDateRange dateTimeRange) {
        getDateTimeRangeIntegerIndexMap().put(index, dateTimeRange);
    }

    private void putDateTimeRange(String fieldName, SDKDateRange dateTimeRange) {
        getDateTimeRangeStringIndexMap().put(fieldName, dateTimeRange);
    }

    private Map<Integer, SDKDateRange> getDateTimeRangeIntegerIndexMap() {
        if (dateTimeRangeIntegerIndexMap == null) {
            dateTimeRangeIntegerIndexMap = new HashMap<>();
        }
        return dateTimeRangeIntegerIndexMap;
    }

    public void setDateTimeRangeIntegerIndexMap(Map<Integer, SDKDateRange> dateTimeRangeIntegerIndexMap) {
        this.dateTimeRangeIntegerIndexMap = dateTimeRangeIntegerIndexMap;
    }

    private Map<String, SDKDateRange> getDateTimeRangeStringIndexMap() {
        if (dateTimeRangeStringIndexMap == null) {
            dateTimeRangeStringIndexMap = new HashMap<>();
        }
        return dateTimeRangeStringIndexMap;
    }

    public void setDateTimeRangeStringIndexMap(Map<String, SDKDateRange> dateTimeRangeStringIndexMap) {
        this.dateTimeRangeStringIndexMap = dateTimeRangeStringIndexMap;
    }
}
