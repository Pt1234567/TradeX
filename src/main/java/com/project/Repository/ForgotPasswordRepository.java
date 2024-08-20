package com.project.Repository;

import com.project.Entity.ForgotPasswordToken;
import com.project.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordToken,String> {

    ForgotPasswordToken findByUserId(Long userId);

}
