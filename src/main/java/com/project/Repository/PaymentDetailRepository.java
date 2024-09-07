package com.project.Repository;

import com.project.Entity.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailRepository extends JpaRepository<PaymentDetails,Long> {

    PaymentDetails findByUserId(Long userId);
}
