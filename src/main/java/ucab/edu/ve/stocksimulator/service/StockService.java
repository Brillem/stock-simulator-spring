package ucab.edu.ve.stocksimulator.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucab.edu.ve.stocksimulator.dto.StockDTO;
import ucab.edu.ve.stocksimulator.dto.StockListResponseDTO;
import ucab.edu.ve.stocksimulator.model.Stock;
import ucab.edu.ve.stocksimulator.repository.StockRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    private final StockRepo stockRepo;

    @Autowired
    public StockService(StockRepo stockRepo) {
        this.stockRepo = stockRepo;
    }

    public StockListResponseDTO findAll(){
         List<Stock> stockList = new ArrayList<>();
         stockList = stockRepo.findAll();
         List<StockDTO> stockDTOList = new ArrayList<>();
         stockDTOList = mapStockListToStockDTOList(stockList);
         return mapStockDTOtoResponse(stockDTOList);
    }

    public Optional<Stock> findStockById(Long id){
        return stockRepo.findById(id);
    }

    public List<StockDTO> mapStockListToStockDTOList(List<Stock> stockList){
        List<StockDTO> stockDTOList = new ArrayList<StockDTO>();
        if(stockList.isEmpty()){
            return stockDTOList;
        }
        for(Stock stock: stockList){
            stockDTOList.add(mapStocktoDTO(stock));
        }
        return stockDTOList;
    }

    public StockDTO mapStocktoDTO(Stock stock){
        StockDTO stockDTO = new StockDTO();
        stockDTO.name = stock.getName();
        stockDTO.ticker = stock.getTicker();
        stockDTO.description = stock.getDescription();
        return stockDTO;
    }

    public StockListResponseDTO mapStockDTOtoResponse(List<StockDTO> stockDTOList){
        StockListResponseDTO stockListResponseDTO = new StockListResponseDTO();
        stockListResponseDTO.stockDTOList = stockDTOList;
        if(stockDTOList.isEmpty()){
            stockListResponseDTO.codeMessage = 1;
            return stockListResponseDTO;
        }
        stockListResponseDTO.codeMessage = 0;
        return stockListResponseDTO;
    }







}
