package com.project.service.impl;

import com.project.Entity.ForgotPasswordToken;
import com.project.Entity.User;
import com.project.Repository.ForgotPasswordRepository;
import com.project.helper.VerificationType;
import com.project.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordTokenServiceImpl implements ForgotPasswordService {

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Override
    public ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo) {
        ForgotPasswordToken forgotPasswordToken=new ForgotPasswordToken();
        forgotPasswordToken.setId(id);
        forgotPasswordToken.setSendTo(sendTo);
        forgotPasswordToken.setOtp(otp);
        forgotPasswordToken.setUser(user);
        forgotPasswordToken.setVerificationType(verificationType);

        return forgotPasswordRepository.save(forgotPasswordToken);
    }

    @Override
    public ForgotPasswordToken findById(String id) {
        return forgotPasswordRepository.findById(id).orElse(null);
    }

    @Override
    public ForgotPasswordToken findByUserId(Long userId) {
        return forgotPasswordRepository.findByUserId(userId);
    }

    @Override
    public void deleteToken(ForgotPasswordToken forgotPasswordToken) {
        forgotPasswordRepository.delete(forgotPasswordToken);
    }
}
