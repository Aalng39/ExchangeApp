package vttp2022.workshop171.Model;

import java.math.BigDecimal;
import java.util.UUID;

public class ExchangeRate{
    private Query query;
    private BigDecimal result;
    private String historyId =  UUID.randomUUID().toString().substring(0,8);
    // private Date date;

  
    public String getHistoryId() {
        return historyId;
    }


    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public Query getQuery() {
        return this.query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public BigDecimal getResult() {
        return this.result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "{" +
            " query='" + getQuery() + "'" +
            ", result='" + getResult() + "'" +
            "}";
    }

}
