package ucab.edu.ve.stocksimulator.model;

import jakarta.persistence.*;

@Entity
@Table(name = "contactForms")

public class ContactForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    private String textMessage;

    public ContactForm() {
    }

    public ContactForm(Long id, User user, String textMessage) {
        this.id = id;
        this.user = user;
        this.textMessage = textMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
