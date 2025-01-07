package ucab.edu.ve.stocksimulator.dto.response;

public class MessageResponseDTO {
    public Number code;
    public String message;

    public MessageResponseDTO() {
    }

    public MessageResponseDTO(Number code, String message) {
        this.code = code;
        this.message = message;
    }

    public Number getCode() {
        return code;
    }

    public void setCode(Number code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}