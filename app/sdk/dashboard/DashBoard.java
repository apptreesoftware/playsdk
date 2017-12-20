package sdk.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import sdk.utils.Response;

import java.util.ArrayList;
import java.util.List;

public class DashBoard extends Response {
    @JsonProperty("cards")
    private List<DashBoardItem> cards;

    private int totalRecords;

    public ObjectNode toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.valueToTree(this);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public List<DashBoardItem> getCards() {
        if(cards == null) {
            cards = new ArrayList<>();
        }
        return cards;
    }

    public void setCards(List<DashBoardItem> cards) {
        this.cards = cards;
    }

    public void addCard(DashBoardItem item) {
        getCards().add(item);
    }

}
