package ucab.edu.ve.stocksimulator.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import ucab.edu.ve.stocksimulator.dto.StockEOD;
import ucab.edu.ve.stocksimulator.dto.response.StockListResponseDTO;
import ucab.edu.ve.stocksimulator.service.StockEODService;
import ucab.edu.ve.stocksimulator.service.StockService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api/stock")
public class StockController {
    public StockService stockservice;
    public StockEODService stockEODService;

    @Autowired
    public StockController(StockService stockservice, StockEODService stockEODService) {
        this.stockservice = stockservice;
        this.stockEODService = stockEODService;
    }

    @GetMapping("/all")
    public ResponseEntity<StockListResponseDTO> getAvailableStocks() {
        StockListResponseDTO responseStockDTO = new StockListResponseDTO();
        responseStockDTO = stockservice.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(responseStockDTO);
    }
    @GetMapping("/{ticker}")
    public ResponseEntity<Object>getLatestStockEODData(@PathVariable String ticker){
        Object response = stockEODService.getLatestStockEODData(ticker);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/{ticker}/month")
    public ResponseEntity<Object>getLastMonthStockEODData(@PathVariable String ticker) {
        Object response = stockEODService.getLastMonthStockEODData(ticker);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
