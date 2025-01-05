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
    private User username;
    private String textMessage;

    public ContactForm() {
    }

    public ContactForm(Long id, User username, String textMessage) {
        this.id = id;
        this.username = username;
        this.textMessage = textMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUsername() {
        return username;
    }

    public void setUsername(User username) {
        this.username = username;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
