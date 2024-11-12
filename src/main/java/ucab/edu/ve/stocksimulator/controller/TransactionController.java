package ucab.edu.ve.stocksimulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ucab.edu.ve.stocksimulator.dto.TransactionDTO;
import ucab.edu.ve.stocksimulator.repository.TransactionRepo;
import ucab.edu.ve.stocksimulator.service.TransactionService;

@Controller
public class TransactionController {
    public TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/buy-sell")
    public ResponseEntity<TransactionDTO> getComprasYVentas(@PathVariable Long id) {
        return


    }



}
