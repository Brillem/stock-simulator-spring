package ucab.edu.ve.stocksimulator.dto;

import java.util.Date;

public class TransactionDTO {
    public String nameStock;
    public String type;
    public Long emisorID;
    public Long receptorID;
    public Float valor;
    public int cantidad;
    public Date fecha;

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

    public Long getReceptorID() {
        return receptorID;
    }

    public void setReceptorID(Long receptorID) {
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
}
