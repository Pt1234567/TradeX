package com.project.service;

import com.project.Entity.ForgotPasswordToken;
import com.project.Entity.User;
import com.project.helper.VerificationType;

public interface ForgotPasswordService {

    ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType,String sendTo);

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUserId(Long userId);

    void deleteToken(ForgotPasswordToken forgotPasswordToken);


}
