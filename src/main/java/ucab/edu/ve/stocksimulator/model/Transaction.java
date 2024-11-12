package ucab.edu.ve.stocksimulator.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")

public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    private Long id;
    private String nameStock; //nombre de la accion
    private String type; // (venta, compra y transferencia) lo dejo como string mientras tanto
    @ManyToOne
    @JoinColumn(name = "stockUser_id", nullable = false)
    private User emisorID; //market o usario
    @ManyToOne
    @JoinColumn(name = "stockUser_id", nullable = true)
    private User receptorID; //market o usuario
    private Float valor; //valor de la accion en la transferencia
    private int cantidad; //cantidad de acciones en la transferencia
    private Date fecha; //fecha de la transaccion

    public Transaction() {
    }

    public Transaction(Long id, String nameStock, String type,User emisorID, User receptorID, Float valor, int cantidad, Date fecha) {
        this.id = id;
        this.nameStock = nameStock;
        this.type = type;
        this.emisorID = emisorID;
        this.receptorID = receptorID;
        this.valor = valor;
        this.cantidad = cantidad;
        this.fecha = fecha;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setReceptorID(User receptorID) {
        this.receptorID = receptorID;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public User getEmisorID() {
        return emisorID;
    }

    public void setEmisorID(User emisorID) {
        this.emisorID = emisorID;
    }

    public User getReceptorID() {
        return receptorID;
    }
}
