package ucab.edu.ve.stocksimulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucab.edu.ve.stocksimulator.dto.TransactionDTO;
import ucab.edu.ve.stocksimulator.model.Transaction;
import ucab.edu.ve.stocksimulator.model.User;
import ucab.edu.ve.stocksimulator.repository.TransactionRepo;
import ucab.edu.ve.stocksimulator.repository.UserRepo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import java.util.Date;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepo transactionRepo;

    @Autowired
    public TransactionService(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    public List<TransactionDTO> findAllPurchase(User user){
        List<Transaction> transactions = transactionRepo.findAllByEmisorIDAndType(user,"buy");
        return mapListTransactionToDTO(transactions);
    }

    public List<TransactionDTO> findAllSales(User user){
        List<Transaction> transactions = transactionRepo.findAllByEmisorIDAndType(user,"sell");
        return mapListTransactionToDTO(transactions);
    }

    public Optional<Transaction> findStockById(Long id){
        return transactionRepo.findById(id);
    }

    public TransactionDTO mapTransactiontoDTO(Transaction transaction){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.stockTicker = transaction.getNameStock();
        transactionDTO.type = transaction.getType();
        transactionDTO.emisorUsername= transaction.getEmisorID().getUsername();
        if(transaction.getCompradorID()!= null){
            transactionDTO.receptorUsername= transaction.getCompradorID().getUsername();
        }else{
            transactionDTO.receptorUsername= null;
        }
        transactionDTO.price = transaction.getValor();
        transactionDTO.amount = transaction.getCantidad();
        transactionDTO.date = transaction.getFecha();
        return transactionDTO;
    }

    public List<TransactionDTO> mapListTransactionToDTO(List<Transaction> transactions){
        List<TransactionDTO> transactionsDTO = new ArrayList<TransactionDTO>();
        for(Transaction transaction : transactions){
            transactionsDTO.add(mapTransactiontoDTO(transaction));
        }
        return transactionsDTO;
    }

}
