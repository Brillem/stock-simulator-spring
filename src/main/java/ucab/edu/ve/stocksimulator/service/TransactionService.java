package ucab.edu.ve.stocksimulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucab.edu.ve.stocksimulator.dto.TransactionDTO;
import ucab.edu.ve.stocksimulator.model.Transaction;
import ucab.edu.ve.stocksimulator.repository.TransactionRepo;

import java.util.Date;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepo transactionRepo;

    @Autowired
    public TransactionService(TransactionRepo transactionRepo) {
        this.transactionRepo = transactionRepo;
    }

    public Optional<Transaction> findStockById(Long id){
        return transactionRepo.findById(id);
    }

    public TransactionDTO mapTransactiontoDTO(Transaction transaction){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.nameStock = transaction.getNameStock();
        transactionDTO.type = transaction.getType();
        transactionDTO.emisorID = transaction.getEmisorID();
        transactionDTO.receptorID = transaction.getReceptorID();
        transactionDTO.valor = transaction.getValor();
        transactionDTO.cantidad = transaction.getCantidad();
        transactionDTO.fecha = transaction.getFecha();
        return transactionDTO;
    }
}
