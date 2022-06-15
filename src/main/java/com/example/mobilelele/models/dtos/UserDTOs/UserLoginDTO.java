package com.example.mobilelele.models.dtos.UserDTOs;

import com.example.mobilelele.utils.validators.CorrectCredentialsValidator.CredentialsValidator;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@CredentialsValidator(
        firstField = "username",
        secondField = "password"
)
public class UserLoginDTO {
    private String username;
    private String password;

    public UserLoginDTO() {
    }

    @NotBlank
    @Length(min = 3)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotBlank
    @Size(min = 5)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
