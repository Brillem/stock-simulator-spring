package ucab.edu.ve.stocksimulator.service;

import org.springframework.stereotype.Service;
import ucab.edu.ve.stocksimulator.dto.OwnedStockDTO;
import ucab.edu.ve.stocksimulator.dto.StockDTO;
import ucab.edu.ve.stocksimulator.dto.request.BuyRequestDTO;
import ucab.edu.ve.stocksimulator.dto.request.SellRequestDTO;
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

    public List<OwnedStockDTO> getOwnedStocksByUser(String username) {
        return mapOwnedListToOwnedDTOList(ownedStockRepo.findByUser(this.userRepo.findByUsername(username)));
    }


    public void addPurchase(BuyRequestDTO buyRequestDTO){
        User user = userRepo.findByUsername(buyRequestDTO.username);
        OwnedStock ownedstock = getOwnedStockByUserAndTicker(user, buyRequestDTO.ticker);
        if(ownedstock == null){
            OwnedStock ownedStock = new OwnedStock();
            ownedStock.setUser(user);
            ownedStock.setTicker(buyRequestDTO.ticker);
            ownedStock.setQuantity(buyRequestDTO.quantity);
            ownedStock.setName(buyRequestDTO.name);
            ownedStockRepo.save(ownedStock);
        }
       else{
           int current_quantity = ownedstock.getQuantity();
           ownedstock.setQuantity(current_quantity + buyRequestDTO.quantity);
           ownedStockRepo.save(ownedstock);
        }
    }

    public void sellStock(SellRequestDTO sellRequestDTO){
        User user = userRepo.findByUsername(sellRequestDTO.username);
        OwnedStock ownedstock = getOwnedStockByUserAndTicker(user, sellRequestDTO.ticker);
        int current_quantity = ownedstock.getQuantity();
        if (current_quantity - sellRequestDTO.quantity <= 0) {
            ownedStockRepo.delete(ownedstock);
        }
        else {
            ownedstock.setQuantity(current_quantity - sellRequestDTO.quantity);
            ownedStockRepo.save(ownedstock);

        }

    }

    public OwnedStock getOwnedStockByUserAndTicker(User user, String ticker){
        return ownedStockRepo.findByUserAndTicker(user, ticker);
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