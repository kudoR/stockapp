package myapp;

import javax.persistence.*;

@Entity
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long alertId;
    private AlertTrigger alertTrigger;
    private int alertValue;

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    private boolean processed;

    @ManyToOne
    private User user;

    @OneToOne
    private StockDetail stockDetail;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public StockDetail getStockDetail() {
        return stockDetail;
    }

    public void setStockDetail(StockDetail stockDetail) {
        this.stockDetail = stockDetail;
    }

    public int getAlertValue() {
        return alertValue;
    }

    public void setAlertValue(int alertValue) {
        this.alertValue = alertValue;
    }

    public AlertTrigger getAlertTrigger() {
        return alertTrigger;
    }

    public void setAlertTrigger(AlertTrigger alertTrigger) {
        this.alertTrigger = alertTrigger;
    }

    public Long getAlertId() {
        return alertId;
    }

    public void setAlertId(Long alertId) {
        this.alertId = alertId;
    }
}
