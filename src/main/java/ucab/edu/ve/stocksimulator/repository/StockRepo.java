package ucab.edu.ve.stocksimulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ucab.edu.ve.stocksimulator.model.Stock;

public interface StockRepo extends JpaRepository<Stock,Long> {
}
