package ucab.edu.ve.stocksimulator.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ucab.edu.ve.stocksimulator.dto.StockDTO;
import ucab.edu.ve.stocksimulator.dto.response.MessageResponseDTO;
import ucab.edu.ve.stocksimulator.dto.response.StockListResponseDTO;
import ucab.edu.ve.stocksimulator.model.Stock;
import ucab.edu.ve.stocksimulator.service.StockEODService;
import ucab.edu.ve.stocksimulator.service.StockService;



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
    @PostMapping("/admin/create")
    public ResponseEntity<MessageResponseDTO> createStocks(@RequestBody StockDTO[] stockDTOList) {
        for (StockDTO stockDTO : stockDTOList) {
            Stock stock = new Stock();
            stock.setDescription(stockDTO.description);
            stock.setName(stockDTO.name);
            stock.setTicker(stockDTO.ticker);
            this.stockservice.save(stock);
        }
        MessageResponseDTO message = new MessageResponseDTO(0, "Acciones creadas");
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
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
