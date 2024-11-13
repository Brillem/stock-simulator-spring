package ucab.edu.ve.stocksimulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import ucab.edu.ve.stocksimulator.dto.TransactionDTO;
import ucab.edu.ve.stocksimulator.model.Transaction;
import ucab.edu.ve.stocksimulator.model.User;
import ucab.edu.ve.stocksimulator.service.TransactionService;

import java.util.ArrayList;
import java.util.List;

public class TransactionController {
    public TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    //metodo que devuelve la lista de acciones compradas y vendidas
    @GetMapping("/buy-sell")
    public ResponseEntity<List<TransactionDTO>> getBuysAndSell(User user){
        List<TransactionDTO> responseTransactionDTOS = transactionService.findAllPurchase(user);
        responseTransactionDTOS.addAll(transactionService.findAllSales(user));
        return ResponseEntity.status(HttpStatus.OK).body(responseTransactionDTOS);
    }

}
