package ucab.edu.ve.stocksimulator.dto;

public class StockEOD {
    public String date;
    public float open;
    public float high;
    public float low;
    public float close;
    public long volume;

    public StockEOD() {
    }

    public StockEOD(String date, float open, float high, float low, float close, long volume) {
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }
}