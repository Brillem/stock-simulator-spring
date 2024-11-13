package ucab.edu.ve.stocksimulator.model;


import jakarta.persistence.*;

@Entity
//Se tuve que utilizar otro nombre para la tabla porque 'user' ya está reservado para otra cosa en PostgresQL
@Table(name = "stockuser")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(nullable = false, updatable = false)
    private String username;
    private String email;
    private Boolean verified;
    private String hashedPassword;

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getVerified() {
        return verified;
    }

    public String getHashedPassword() {
        return hashedPassword;
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

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }
}


