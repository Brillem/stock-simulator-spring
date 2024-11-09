package ucab.edu.ve.stocksimulator.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ucab.edu.ve.stocksimulator.dto.StockListResponseDTO;
import ucab.edu.ve.stocksimulator.service.StockService;



@Controller
public class StockController {
    public StockService stockservice;

    @Autowired
    public StockController(StockService stockservice) {
        this.stockservice = stockservice;
    }

    @GetMapping("/stocks")
    public ResponseEntity<StockListResponseDTO> getAvailableStocks() {
        StockListResponseDTO responseStockDTO = new StockListResponseDTO();
        responseStockDTO = stockservice.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(responseStockDTO);
    }
}
