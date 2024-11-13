package ucab.edu.ve.stocksimulator.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "transactions")

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    private Long id;
    private String nameStock;
    private String ticker;
    private String type;
    @ManyToOne
    @JoinColumn(name = "issuer_id", nullable = false)
    private User issuer;
    @ManyToOne
    @JoinColumn(name = "receptor_id", nullable = true)
    private User receptor;
    private Float amount;
    private int quantity;
    private LocalDate date;

    public Transaction() {
    }

    public Transaction(Long id, String nameStock, String type, User issuer, User receptor, Float amount, int quantity, LocalDate date) {
        this.id = id;
        this.nameStock = nameStock;
        this.type = type;
        this.issuer = issuer;
        this.receptor = receptor;
        this.amount = amount;
        this.quantity = quantity;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameStock() {
        return nameStock;
    }

    public void setNameStock(String nameStock) {
        this.nameStock = nameStock;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getIssuer() {
        return issuer;
    }

    public void setIssuer(User issuer) {
        this.issuer = issuer;
    }

    public User getReceptor() {
        return receptor;
    }

    public void setReceptor(User receptor) {
        this.receptor = receptor;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}