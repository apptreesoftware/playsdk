package sdk.converter;

import sdk.data.DataSetItem.CRUDStatus;

import java.util.HashMap;
import java.util.Map;

import static sdk.utils.ClassUtils.Null;

public class ParserContext {
    private Map<String, Object> extraInfo;
    private Map<Object, CRUDStatus> crudStatusMap;
    private String userId;
    private static final String APP_ID = "APP-ID";

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

    public <T extends Object> void setItemStatus(Object object, CRUDStatus status) {
        getCrudStatusMap().put(object, status);
    }

    public void setAppId(String appId) {
        getExtraInfo().put(APP_ID, appId);
    }

    /**
     * if app id doesnt exist this function will return an empty string
     * @return
     */
    public String getAppId(){
        String appID = (String) getExtraInfo().get(APP_ID);
        if (Null(appID)){
            return "";
        }
        return appID;
    }


    public Map<String, Object> getExtraInfo() {
        if (extraInfo == null) {
            extraInfo = new HashMap<>();
        }
        return extraInfo;
    }

    public Map<Object, CRUDStatus> getCrudStatusMap() {
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
}
