package ucab.edu.ve.stocksimulator.service;

import org.springframework.stereotype.Service;
import ucab.edu.ve.stocksimulator.repository.TransactionRepo;

@Service
public class TransactionService {
    private final TransactionRepo transactionRepo;

    public TransactionService(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

}
