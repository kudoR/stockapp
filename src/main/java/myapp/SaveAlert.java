package myapp;

/**
 * Created by J on 10.11.2016.
 */
public class SaveAlert {
    private Long userId;
    private int value;
    private String trigger;
    private Long stockDetailId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public Long getStockDetailId() {
        return stockDetailId;
    }

    public void setStockDetailId(Long stockDetailId) {
        this.stockDetailId = stockDetailId;
    }


}
