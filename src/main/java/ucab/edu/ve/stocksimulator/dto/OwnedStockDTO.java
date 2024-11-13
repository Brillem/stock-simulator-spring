package ucab.edu.ve.stocksimulator.dto;

public class OwnedStockDTO {

    public String name;
    public String ticker;
    public int quantity;

    public OwnedStockDTO() {}

    public OwnedStockDTO(int quantity, String ticker, String name) {
        this.quantity = quantity;
        this.ticker = ticker;
        this.name = name;
    }

}
