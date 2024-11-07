package ucab.edu.ve.stocksimulator.model;

public class Stock {
    private String ticker;
    private String name;
    private double close;
    private double high;
    private double low;
    private double open;
    private String exchangeCode;

    public Stock(String ticker, String name, double close, double high, double low, double open, String exchangeCode) {
        this.ticker = ticker;
        this.name = name;
        this.close = close;
        this.high = high;
        this.low = low;
        this.open = open;
        this.exchangeCode = exchangeCode;
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

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }
}
