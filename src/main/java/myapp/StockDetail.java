package myapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class StockDetail {
    @Id
    private Long id;
    private String symbol;
    private BigDecimal price;

    public StockDetail(Long id, String symbol, BigDecimal price) {
        this.id = id;
        this.symbol = symbol;
        this.price = price;
    }

    public StockDetail() {
    }

    @JsonProperty("symbol")
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty("t")
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @JsonProperty("price")
    public BigDecimal getPrice() {
        return price;
    }

    @JsonProperty("l")
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
