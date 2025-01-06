package ucab.edu.ve.stocksimulator.model;

import jakarta.persistence.*;

@Entity
public class OwnedStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    private Long id;
    @Column(unique = false)
    private String ticker;
    private String name;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "stockuser_id", nullable = false)
    private User user;

    public OwnedStock() {
    }

    public OwnedStock(Long id, String ticker, String name, int quantity, User user) {
        this.id = id;
        this.ticker = ticker;
        this.name = name;
        this.quantity = quantity;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
