package ucab.edu.ve.stocksimulator.dto;

import java.time.LocalDate;
import java.util.Date;

public class TransactionDTO {
    public String stockTicker;
    public String type;
    public String issuerUsername;
    public String receptorUsername;
    public Float amount;
    public int quantity;
    public LocalDate date;
}
