package vttp2022.workshop171.Model;

import java.math.BigDecimal;

public class Query {
    private BigDecimal amount;
    private String from;
    private String to;

    public BigDecimal getAmount() {
        return this.amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }


    @Override
    public String toString() {
        return "{" +
            " amount='" + getAmount() + "'" +
            ", from='" + getFrom() + "'" +
            ", to='" + getTo() + "'" +
            "}";
    }

}