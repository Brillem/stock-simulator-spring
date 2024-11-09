package ucab.edu.ve.stocksimulator.dto.transaction;

import java.util.Date;

public class TransactionDTO {
    private String nameStock;
    private String type;
    private Long emisorID;
    private Long compradorID;
    private Float valor;
    private int cantidad;
    private Date fecha;

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

    public Long getEmisorID() {
        return emisorID;
    }

    public void setEmisorID(Long emisorID) {
        this.emisorID = emisorID;
    }

    public Long getCompradorID() {
        return compradorID;
    }

    public void setCompradorID(Long compradorID) {
        this.compradorID = compradorID;
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
}
