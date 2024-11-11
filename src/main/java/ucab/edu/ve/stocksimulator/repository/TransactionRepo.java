package ucab.edu.ve.stocksimulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ucab.edu.ve.stocksimulator.model.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction,Long> {
}
