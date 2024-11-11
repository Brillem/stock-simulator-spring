package ucab.edu.ve.stocksimulator.dto;

public class StockDTO {
    public String ticker;
    public String name;
    public String description;

    public StockDTO() {
    }

    public StockDTO(String ticker, String name, String description) {
        this.ticker = ticker;
        this.name = name;
        this.description = description;
    }

}
