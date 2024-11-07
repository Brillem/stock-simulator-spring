package ucab.edu.ve.stocksimulator.dto.response;

public class MessageResponseDTO {
    public Number code;
    public String message;

    public MessageResponseDTO(Number code, String message) {
        this.code = code;
        this.message = message;
    }

}
