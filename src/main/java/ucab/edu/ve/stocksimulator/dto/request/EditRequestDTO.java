package ucab.edu.ve.stocksimulator.dto.request;

public class EditRequestDTO extends UserRequestDTO {
    private String oldUsername;

    public String getOldUsername() {
        return oldUsername;
    }

    public void setOldUsername(String oldUsername) {
        this.oldUsername = oldUsername;
    }
}
