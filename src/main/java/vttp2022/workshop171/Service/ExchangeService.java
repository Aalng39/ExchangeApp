package vttp2022.workshop171.Service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import vttp2022.workshop171.Model.Currency;
import vttp2022.workshop171.Model.ExchangeRate;

@Service
public class ExchangeService implements ExchangeRepo{
    
    @Value("${currency}")
    private String currencyURL;
    @Value("${baseURL}")
    private String baseURL;
    // @Value("${apikey}")
    // private String apiKey;
    

    @Autowired
    private RestTemplate restTemplate;

    public void getCurrency(){
        ResponseEntity<String> response = restTemplate.getForEntity(currencyURL, String.class);
        String responseBody = response.getBody();
        ObjectMapper mapper = new ObjectMapper();
        try {
            Currency.currencyCodeAndValue = mapper.readValue(responseBody, Map.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public ExchangeRate getResult(String to, String from, BigDecimal amount){
        String apiKey = System.getenv("FIXER_CURRENCY_API_KEY");
        String fullURL = UriComponentsBuilder.fromUriString(baseURL).
        queryParam("to", to).
        queryParam("from", from).
        queryParam("amount", amount).
        toUriString();
        System.out.println(fullURL);

        RequestEntity<Void> reqEntity = 
                    RequestEntity.get(fullURL).header("apikey", apiKey).build();

        ResponseEntity<ExchangeRate> respEntity = 
                    restTemplate.exchange(reqEntity, ExchangeRate.class);

        ExchangeRate exchangeRate = respEntity.getBody();
        return exchangeRate;
    }
 


    @Autowired
    RedisTemplate<String, ExchangeRate> redisTemplate;
    
    @Override
    public void save(ExchangeRate exchangeRate) {
        System.out.println(exchangeRate.getHistoryId());
        redisTemplate.opsForValue().set(exchangeRate.getHistoryId(), exchangeRate);
          
    }

    @Override
    public ExchangeRate findById(String historyId) {
        ExchangeRate history = (ExchangeRate) redisTemplate.opsForValue().get(historyId);
        return history;
    }

}