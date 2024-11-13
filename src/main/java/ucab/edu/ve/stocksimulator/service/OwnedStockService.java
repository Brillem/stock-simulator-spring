package ucab.edu.ve.stocksimulator.service;

import org.springframework.stereotype.Service;
import ucab.edu.ve.stocksimulator.dto.OwnedStockDTO;
import ucab.edu.ve.stocksimulator.dto.StockDTO;
import ucab.edu.ve.stocksimulator.model.OwnedStock;
import ucab.edu.ve.stocksimulator.model.Stock;
import ucab.edu.ve.stocksimulator.model.User;
import ucab.edu.ve.stocksimulator.repository.OwnedStockRepo;
import ucab.edu.ve.stocksimulator.repository.UserRepo;

import java.util.ArrayList;
import java.util.List;

@Service
public class OwnedStockService {
    private final OwnedStockRepo ownedStockRepo;
    private final UserRepo userRepo;

    public OwnedStockService(OwnedStockRepo ownedStockRepo, UserRepo userRepo) {
        this.ownedStockRepo = ownedStockRepo;
        this.userRepo = userRepo;
    }

    public List<OwnedStockDTO> getOwnedStocksByUser(User user) {
        return mapOwnedListToOwnedDTOList(ownedStockRepo.findByUser(user));
    }


    public void addPurchase(String username, String ticker,int quantity,String name){
        User user = userRepo.findByUsername(username);
        List<OwnedStock> ownedstocks = getOwnedStocksByUserAndTicker(user,ticker);
        if(ownedstocks.isEmpty()){
            OwnedStock ownedStock = new OwnedStock();
            ownedStock.setUser(user);
            ownedStock.setTicker(ticker);
            ownedStock.setQuantity(quantity);
            ownedStock.setName(name);
            ownedStockRepo.save(ownedStock);
        }
       else{
           int current_quantity = ownedstocks.get(0).getQuantity();
           ownedstocks.get(0).setQuantity(current_quantity+quantity);
           ownedStockRepo.save(ownedstocks.get(0));
        }
    }

    public List<OwnedStock> getOwnedStocksByUserAndTicker(User user, String ticker){
        return ownedStockRepo.findByUserAndTicker(user,ticker);
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