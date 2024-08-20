package com.project.Repository;

import com.project.Entity.TwoFactorOtp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOtp,String> {

    TwoFactorOtp findByUserId(Long userId);

}
