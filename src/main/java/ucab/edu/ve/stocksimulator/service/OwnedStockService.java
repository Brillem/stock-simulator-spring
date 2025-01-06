package ucab.edu.ve.stocksimulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucab.edu.ve.stocksimulator.dto.OwnedStockDTO;
import ucab.edu.ve.stocksimulator.dto.StockDTO;
import ucab.edu.ve.stocksimulator.dto.request.BuyRequestDTO;
import ucab.edu.ve.stocksimulator.dto.request.SellRequestDTO;
import ucab.edu.ve.stocksimulator.dto.request.TransferRequestDTO;
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

    @Autowired
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

    public void transferStock(TransferRequestDTO transferRequestDTO){
        User issuerUser = userRepo.findByUsername(transferRequestDTO.issuerUsername);
        User receptorUser = userRepo.findByUsername(transferRequestDTO.receptorUsername);

        //Elimina acciones del usuario emisor
        OwnedStock issuerOwnedStocks = getOwnedStockByUserAndTicker(issuerUser, transferRequestDTO.ticker);
        int issuer_current_quantity = issuerOwnedStocks.getQuantity();
        if (issuer_current_quantity - transferRequestDTO.quantity <= 0) {
            ownedStockRepo.delete(issuerOwnedStocks);
        }
        else {
            issuerOwnedStocks.setQuantity(issuer_current_quantity - transferRequestDTO.quantity);
            ownedStockRepo.save(issuerOwnedStocks);
        }

        //Agrega acciones al usuario receptor
        OwnedStock receptorOwnedStocks = getOwnedStockByUserAndTicker(receptorUser, transferRequestDTO.ticker);
        if(receptorOwnedStocks == null){
            System.out.println("Creating new OwnedStock for user: " + receptorUser.getUsername() + "ID"+ receptorUser.getId() + ", ticker: " + transferRequestDTO.ticker);
            OwnedStock ownedStock = new OwnedStock();
            ownedStock.setUser(receptorUser);
            ownedStock.setTicker(transferRequestDTO.ticker);
            ownedStock.setQuantity(transferRequestDTO.quantity);
            ownedStock.setName(transferRequestDTO.name);
            ownedStockRepo.save(ownedStock);
        }
        else{
            System.out.println("Updating OwnedStock for user: " + receptorUser.getUsername() + ", ticker: "+ transferRequestDTO.ticker);
            int receptor_current_quantity = receptorOwnedStocks.getQuantity();
            receptorOwnedStocks.setQuantity(receptor_current_quantity + transferRequestDTO.quantity);
            ownedStockRepo.save(receptorOwnedStocks);
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