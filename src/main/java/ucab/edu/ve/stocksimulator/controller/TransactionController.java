package ucab.edu.ve.stocksimulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ucab.edu.ve.stocksimulator.dto.TransactionDTO;
import ucab.edu.ve.stocksimulator.dto.response.MessageResponseDTO;
import ucab.edu.ve.stocksimulator.model.Transaction;
import ucab.edu.ve.stocksimulator.model.User;
import ucab.edu.ve.stocksimulator.service.OwnedStockService;
import ucab.edu.ve.stocksimulator.service.StockEODService;
import ucab.edu.ve.stocksimulator.service.StockService;
import ucab.edu.ve.stocksimulator.service.TransactionService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {
    public TransactionService transactionService;
    public OwnedStockService ownedStockService;
    public StockEODService  stockEODService;

    @Autowired
    public TransactionController(TransactionService transactionService, OwnedStockService ownedStockService,StockEODService stockEODService) {
        this.transactionService = transactionService;
        this.ownedStockService = ownedStockService;
        this.stockEODService = stockEODService;
    }

    //metodo que devuelve la lista de acciones compradas y vendidas
    @GetMapping("/buy-sell")
    public ResponseEntity<List<TransactionDTO>> getBuysAndSell(String username){
        List<TransactionDTO> responseTransactionDTOS = transactionService.findAllPurchase(username);
        responseTransactionDTOS.addAll(transactionService.findAllSales(username));
        return ResponseEntity.status(HttpStatus.OK).body(responseTransactionDTOS);
    }

    @PostMapping(value = "/buy")
    public ResponseEntity<MessageResponseDTO>buy(String username, String ticker, String name, int quantity, String cardNumber, float price) {
        MessageResponseDTO messageResponseDTO = new MessageResponseDTO();
        if (!transactionService.verifyVISA(cardNumber)) {
            messageResponseDTO.setCode(1);
            messageResponseDTO.setMessage("INVALID CARD NUMBER");
            return ResponseEntity.status(HttpStatus.OK).body(messageResponseDTO);
        }
        ownedStockService.addPurchase(username,ticker,quantity,name);
        transactionService.registerPurchase(username,name,quantity,price);
        messageResponseDTO.setCode(0);
        messageResponseDTO.setMessage("Purchase completed successfully");
        return ResponseEntity.status(HttpStatus.OK).body(messageResponseDTO);
    }
}
