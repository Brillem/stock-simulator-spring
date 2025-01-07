package ucab.edu.ve.stocksimulator.dto;

public class ContactFormDTO {
    public Long id;
    public String username;
    public String textMessage;

    public ContactFormDTO() {
    }

    public ContactFormDTO(String username, String textMessage) {
        this.username = username;
        this.textMessage = textMessage;
    }
}
