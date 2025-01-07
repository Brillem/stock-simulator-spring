package ucab.edu.ve.stocksimulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ucab.edu.ve.stocksimulator.model.ContactForm;
import ucab.edu.ve.stocksimulator.model.User;

import java.util.List;

public interface ContactFormRepo extends JpaRepository<ContactForm, Long> {
    List<ContactForm> findByUser(User user);
    ContactForm findByUserAndTextMessage(User user, String textMessage);
    ContactForm findById(long id);
}
