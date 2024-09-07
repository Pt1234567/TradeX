package com.project.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PaymentDetails {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String accountNumber;

        private String accountHolderName;

        private String ifsc;

        private String bankName;

        @OneToOne
        private User user;

}
