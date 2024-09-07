package com.project.Entity;

import com.project.helper.PaymentMethod;
import com.project.helper.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private Long amount;
    private PaymentOrderStatus orderStatus;

    private PaymentMethod paymentMethod;

    @ManyToOne
    private User user;
}
