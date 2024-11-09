package ucab.edu.ve.stocksimulator.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ucab.edu.ve.stocksimulator.dto.StockEOD;

import java.util.Arrays;
import java.util.List;

@Service
public class StockEODService {
    private final String API_KEY =  "f187e17dd5f064487da0add13fddda26d53fbe3b";

    public StockEOD getLatestStockEODData(String ticker){
        String url = "https://api.tiingo.com/tiingo/daily/aapl/prices?token=" + API_KEY;
        RestTemplate restTemplate = new RestTemplate();
        StockEOD response = restTemplate.getForObject(url, StockEOD.class);
        return response;
    }
}
