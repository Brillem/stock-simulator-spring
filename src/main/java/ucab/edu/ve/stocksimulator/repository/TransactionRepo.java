package ucab.edu.ve.stocksimulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ucab.edu.ve.stocksimulator.model.Transaction;
import ucab.edu.ve.stocksimulator.model.User;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction,Long> {
    List<Transaction> findAllByEmisorIDAndType(User user, String type);
    List<Transaction> findAllByReceptorIDAndType(User user, String type);
}

