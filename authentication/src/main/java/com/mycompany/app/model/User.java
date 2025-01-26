package com.mycompany.app.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "_user")
@Builder
@Data
public class User {

    @Id
    @GeneratedValue
    private Integer Id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;
    private String password;
}
