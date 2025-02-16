package com.mycompany.app.entity;


import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Table
@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;

    @Enumerated(EnumType.STRING)
    private CartStatus cartStatus = CartStatus.IN_PROGRESS;

    @ElementCollection
    private List<Integer> productIds = new ArrayList<>();
}
