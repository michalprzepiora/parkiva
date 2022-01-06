package pl.com.przepiora.parkiva.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Car {
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String registrationNumber;
    private String mark;
    private String model;
    private String color;
}
