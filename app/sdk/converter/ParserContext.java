package sdk.converter;

import sdk.data.DataSetItem.CRUDStatus;

import java.util.HashMap;
import java.util.Map;

import static sdk.utils.ClassUtils.Null;

public class ParserContext {
    private boolean datasetItemUpdated;
    private Map<Integer, CRUDStatus> statusMap;
    private Map<String, String> extraInfo;
    private String userId;
    private CRUDStatus itemStatus;



    public void setChildItemStatus(int index, CRUDStatus status) {
        getStatusMap().put(index, status);
    }

    public void setItemStatus(CRUDStatus status) {
        this.itemStatus = status;
        datasetItemUpdated = CRUDStatus.updatedStatus(status);
    }

    public void itemWasUpdated() {
        datasetItemUpdated = true;
    }

    public boolean wasItemUpdated() {
        return datasetItemUpdated;
    }

    public CRUDStatus status(int index) {
        CRUDStatus currentStatus = statusMap.get(index);
        if (Null(currentStatus)) {
            return CRUDStatus.None;
        }
        return currentStatus;
    }

    public boolean isUpdated(int index) {
        CRUDStatus currentStatus = statusMap.get(index);
        if (Null(currentStatus)) {
            return false;
        }
        return currentStatus.equals(CRUDStatus.Update);
    }

    public boolean isCreated(int index) {
        CRUDStatus currentStatus = statusMap.get(index);
        if (Null(currentStatus)) {
            return false;
        }
        return currentStatus.equals(CRUDStatus.Create);
    }

    public boolean isDeleted(int index) {
        CRUDStatus currentStatus = statusMap.get(index);
        if (Null(currentStatus)) {
            return false;
        }
        return currentStatus.equals(CRUDStatus.Delete);
    }

    public Map<Integer, CRUDStatus> getStatusMap() {
        if (statusMap == null) {
            statusMap = new HashMap<>();
        }
        return statusMap;
    }

    public Map<String, String> getExtraInfo() {
        if (extraInfo == null) {
            extraInfo = new HashMap<>();
        }
        return extraInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public CRUDStatus getItemStatus() {
        return itemStatus;
    }
}
