package com.project.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double quantity;
    private double buyPrice;

    @ManyToOne
    private Coin coin;  //one coin can have many asset and many asset have one coin

    @ManyToOne
    private User user; //one user can have many asset

}
