package ucab.edu.ve.stocksimulator.dto.request;

public class ConfirmUserRequestDTO {
    private String username;
    private String confirmationCode;

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public String getUsername() {
        return username;
    }
}
