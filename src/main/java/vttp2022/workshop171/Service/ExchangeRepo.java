package vttp2022.workshop171.Service;

import vttp2022.workshop171.Model.ExchangeRate;

public interface ExchangeRepo {

    public void save(ExchangeRate exchangeRate);

    public ExchangeRate findById(String historyId);
}
