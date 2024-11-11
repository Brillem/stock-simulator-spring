package ucab.edu.ve.stocksimulator.dto.response;

public class UserResponseDTO {
    public final int code;
    public String firstName;
    public String lastName;
    public String username;
    public String email;
    public boolean verified;


    public UserResponseDTO() {
        this.code = 0;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
}
