package ucab.edu.ve.stocksimulator.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class StockEODService {
    private final String API_KEY =  "f187e17dd5f064487da0add13fddda26d53fbe3b";

    public Object getLatestStockEODData(String ticker){
        String url = "https://api.tiingo.com/tiingo/daily/"+ticker+"/prices?token=" + API_KEY;
        RestTemplate restTemplate = new RestTemplate();
        try {
            Object[] response = restTemplate.getForObject(url, Object[].class);
            assert response != null;
            return response[0];
        }catch(Exception NullPointerException){
            return null;
        }
    }

    public Object[] getLastMonthStockEODData(String ticker) {
        LocalDate endDate = LocalDate.now().minusDays(1);
        LocalDate startDate = endDate.minusMonths(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");

        String url = "https://api.tiingo.com/tiingo/daily/" + ticker + "/prices?startDate=" + startDate.format(formatter) + "&endDate=" + endDate.format(formatter) + "&token=" + API_KEY;

        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(url, Object[].class);
        } catch (Exception e) {
            return null;
        }

    }
}

