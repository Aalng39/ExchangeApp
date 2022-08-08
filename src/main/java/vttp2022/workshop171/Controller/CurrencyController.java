package vttp2022.workshop171.Controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2022.workshop171.Model.Currency;
import vttp2022.workshop171.Model.ExchangeRate;
import vttp2022.workshop171.Service.ExchangeService;



@Controller
public class CurrencyController {

    @Autowired
    ExchangeService service;
    
    @GetMapping
    public String returnIndexPage(Model model){
        service.getCurrency();
        model.addAttribute("currency", Currency.currencyCodeAndValue);
        
        return "index";
    }

    @GetMapping("/result")
    public String returnResultPage(@RequestParam String to, 
                                    @RequestParam String from, 
                                    @RequestParam BigDecimal amount,
                                    Model model){
        ExchangeRate exchangeRate = service.getResult(to, from, amount);
        model.addAttribute("exchangeRate", exchangeRate);
        service.save(exchangeRate);
        return "resultPage";

    }

    @GetMapping("/result/{historyId}")
    public String getHistory (Model model, @PathVariable(value = "historyId") String historyId) {
        ExchangeRate hist = service.findById(historyId);
        model.addAttribute("exchangeRate", hist);
        return "resultPage";
    }

}
