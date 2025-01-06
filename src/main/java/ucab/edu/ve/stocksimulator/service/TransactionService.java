package ucab.edu.ve.stocksimulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucab.edu.ve.stocksimulator.dto.TransactionDTO;
import ucab.edu.ve.stocksimulator.dto.request.BuyRequestDTO;
import ucab.edu.ve.stocksimulator.dto.request.SellRequestDTO;
import ucab.edu.ve.stocksimulator.dto.request.TransferRequestDTO;
import ucab.edu.ve.stocksimulator.model.Transaction;
import ucab.edu.ve.stocksimulator.model.User;
import ucab.edu.ve.stocksimulator.repository.TransactionRepo;
import ucab.edu.ve.stocksimulator.repository.UserRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepo transactionRepo;
    private final UserRepo userRepo;

    @Autowired
    public TransactionService(TransactionRepo transactionRepo, UserRepo userRepo) {
        this.transactionRepo = transactionRepo;
        this.userRepo = userRepo;
    }

    //a
    public List<TransactionDTO> findAllTransactions(String username){
        User user = userRepo.findByUsername(username);
        List<Transaction> transactions = transactionRepo.findAllByIssuer(user);
        transactions.addAll(transactionRepo.findAllByReceptor(user));
        return mapListTransactionToDTO(transactions);
    }

   /*public List<TransactionDTO> findAllSales(String username){
        User user = userRepo.findByUsername(username);
        List<Transaction> transactions = transactionRepo.findAllByIssuerAndType(user,"sell");
        return mapListTransactionToDTO(transactions);
    }*/

    public void registerPurchase(BuyRequestDTO buyRequestDTO){
        User user = userRepo.findByUsername(buyRequestDTO.username);
        Transaction transaction = new Transaction();
        transaction.setIssuer(user);
        transaction.setQuantity(buyRequestDTO.quantity);
        transaction.setReceptor(null);
        transaction.setNameStock(buyRequestDTO.name);
        transaction.setType("buy");
        transaction.setAmount(buyRequestDTO.amount);
        transaction.setDate(LocalDate.now());
        transactionRepo.save(transaction);
    }

    public void registerSell(SellRequestDTO sellRequestDTO){
        User user = userRepo.findByUsername(sellRequestDTO.username);
        Transaction transaction = new Transaction();
        transaction.setIssuer(user);
        transaction.setQuantity(sellRequestDTO.quantity);
        transaction.setReceptor(null);
        transaction.setNameStock(sellRequestDTO.name);
        transaction.setType("sell");
        transaction.setAmount(sellRequestDTO.amount);
        transaction.setDate(LocalDate.now());
        transactionRepo.save(transaction);
    }

    public void registerTransfer(TransferRequestDTO transferRequestDTO){
        User issuerUser = userRepo.findByUsername(transferRequestDTO.issuerUsername);
        User receptorUser = userRepo.findByUsername(transferRequestDTO.receptorUsername);
        Transaction transaction = new Transaction();
        transaction.setIssuer(issuerUser);
        transaction.setQuantity(transferRequestDTO.quantity);
        transaction.setReceptor(receptorUser);
        transaction.setNameStock(transferRequestDTO.name);
        transaction.setType("transfer");
        transaction.setAmount(transferRequestDTO.amount);
        transaction.setDate(LocalDate.now());
        transactionRepo.save(transaction);
    }


    public Optional<Transaction> findStockById(Long id){
        return transactionRepo.findById(id);
    }

    public TransactionDTO mapTransactiontoDTO(Transaction transaction){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.stockTicker = transaction.getNameStock();
        transactionDTO.type = transaction.getType();
        transactionDTO.issuerUsername= transaction.getIssuer().getUsername();
        if(transaction.getReceptor()!= null){
            transactionDTO.receptorUsername= transaction.getReceptor().getUsername();
        }else{
            transactionDTO.receptorUsername= null;
        }
        transactionDTO.amount = transaction.getAmount();
        transactionDTO.quantity = transaction.getQuantity();
        transactionDTO.date = transaction.getDate();
        return transactionDTO;
    }

    public List<TransactionDTO> mapListTransactionToDTO(List<Transaction> transactions){
        List<TransactionDTO> transactionsDTO = new ArrayList<TransactionDTO>();
        for(Transaction transaction : transactions){
            transactionsDTO.add(mapTransactiontoDTO(transaction));
        }
        return transactionsDTO;
    }
    public boolean verifyVISA(String cardNumber){
        // Check if the card number matches the VISA card pattern
        if (!cardNumber.matches("^4[0-9]{12}(?:[0-9]{3})?(?:[0-9]{3})?$")) {
            return false;
        }

        // Implement the Luhn algorithm to validate the card number
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

}
