package com.ciclo.Dto;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class UserRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String email;
    private String imageurl;
    private Set<String> role;
    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }
}
