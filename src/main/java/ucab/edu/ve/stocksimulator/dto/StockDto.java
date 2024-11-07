package ucab.edu.ve.stocksimulator.dto;


/*Se usara cuando el usuario solo consulte las acciones
del mercado sin que desee ver detalles como high,low,open*/
public class StockDto {
    private String ticker;
    private String name;
    private double growth_pct;
    private double current_price;

    public StockDto() {
    }

    public StockDto(String ticker, String name, double growth_pct, double current_price) {
        this.ticker = ticker;
        this.name = name;
        this.growth_pct = growth_pct;
        this.current_price = current_price;
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

    public double getGrowth_pct() {
        return growth_pct;
    }

    public void setGrowth_pct(double growth_pct) {
        this.growth_pct = growth_pct;
    }

    public double getCurrent_price() {
        return current_price;
    }

    public void setCurrent_price(double current_price) {
        this.current_price = current_price;
    }
}
