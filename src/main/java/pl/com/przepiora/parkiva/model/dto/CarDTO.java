package pl.com.przepiora.parkiva.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CarDTO {
    @NotBlank(message = "Name must be not null")
    private String mark;
    @NotBlank(message = "Name must be not null")
    private String model;
    @NotBlank(message = "Name must be not null")
    private String color;
    @NotBlank(message = "Name must be not null")
    private String registrationNumber;

}
