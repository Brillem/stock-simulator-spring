package ucab.edu.ve.stocksimulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ucab.edu.ve.stocksimulator.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
