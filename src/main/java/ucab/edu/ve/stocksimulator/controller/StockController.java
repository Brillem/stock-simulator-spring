package ucab.edu.ve.stocksimulator.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ucab.edu.ve.stocksimulator.dto.OwnedStockDTO;
import ucab.edu.ve.stocksimulator.model.User;
import ucab.edu.ve.stocksimulator.dto.response.StockListResponseDTO;
import ucab.edu.ve.stocksimulator.service.StockEODService;
import ucab.edu.ve.stocksimulator.service.StockService;
import ucab.edu.ve.stocksimulator.service.OwnedStockService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/stock")
public class StockController {
    private final OwnedStockService ownedStockService;
    public StockService stockservice;
    public StockEODService stockEODService;

    @Autowired
    public StockController(StockService stockservice, StockEODService stockEODService, OwnedStockService ownedStockService) {
        this.stockservice = stockservice;
        this.stockEODService = stockEODService;
        this.ownedStockService = ownedStockService;
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
    @GetMapping("/ownedstocks/{user}")
        public ResponseEntity<List<OwnedStockDTO>> getOwnedStocksByUser(@PathVariable User user) {
        List<OwnedStockDTO> response = ownedStockService.getOwnedStocksByUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
