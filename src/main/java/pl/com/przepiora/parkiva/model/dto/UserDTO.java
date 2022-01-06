package pl.com.przepiora.parkiva.model.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDTO {

    @Email(message = "Email must be not null and valid formatted - using @")
    private String username;
    @NotBlank(message = "Name must be not null")
    private String name;
    @NotBlank(message = "Surname must be not null")
    private String surname;
    @NotBlank(message = "Phone must be not null")
    private String phone;
    private String address;
}
