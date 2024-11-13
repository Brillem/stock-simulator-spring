package ucab.edu.ve.stocksimulator.service;

import org.springframework.stereotype.Service;
import ucab.edu.ve.stocksimulator.dto.OwnedStockDTO;
import ucab.edu.ve.stocksimulator.dto.StockDTO;
import ucab.edu.ve.stocksimulator.model.OwnedStock;
import ucab.edu.ve.stocksimulator.model.Stock;
import ucab.edu.ve.stocksimulator.model.User;
import ucab.edu.ve.stocksimulator.repository.OwnedStockRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnedStockService {
    private final OwnedStockRepo ownedStockRepo;

    public OwnedStockService(OwnedStockRepo ownedStockRepo) {
        this.ownedStockRepo = ownedStockRepo;
    }

    public List<OwnedStockDTO> getOwnedStocksByUser(User user) {
        return mapOwnedListToOwnedDTOList(ownedStockRepo.findByUser(user));
    }

    public List<OwnedStockDTO> mapOwnedListToOwnedDTOList(List<OwnedStock> ownedList){
        List<OwnedStockDTO> ownedDTOList = new ArrayList<OwnedStockDTO>();
        if(ownedList.isEmpty()){
            return ownedDTOList;
        }
        for(OwnedStock owned: ownedList){
            ownedDTOList.add(mapOwnedtoDTO(owned));
        }
        return ownedDTOList;
    }

    public OwnedStockDTO mapOwnedtoDTO(OwnedStock owned){
        OwnedStockDTO ownedDTO = new OwnedStockDTO();
        ownedDTO.name = owned.getName();
        ownedDTO.ticker = owned.getTicker();
        ownedDTO.quantity = owned.getQuantity();
        return ownedDTO;
    }
}