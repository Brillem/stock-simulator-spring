package ucab.edu.ve.stocksimulator.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ucab.edu.ve.stocksimulator.model.OwnedStock;
import ucab.edu.ve.stocksimulator.model.User;

import java.util.List;

public interface OwnedStockRepo extends JpaRepository<OwnedStock, Long> {
    List<OwnedStock> findByUser(User user);
    OwnedStock findByUserAndTicker(User user, String ticker);
}
