package com.mycompany.app.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table
@Entity
public class Role {

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank
    @Column(unique=true)
    private String name;
}
