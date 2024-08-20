package com.project.service.impl;

import com.project.Entity.User;
import com.project.Entity.VerificationCode;
import com.project.Repository.VerificationCodeRepository;
import com.project.helper.VerificationType;
import com.project.service.VerificationCodeService;
import com.project.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Override
    public VerificationCode sendVerificationCode(User user, VerificationType verificationType) {
          VerificationCode verificationCode=new VerificationCode();

          verificationCode.setOtp(OtpUtils.generateOtp());
          verificationCode.setVerificationType(verificationType);
           verificationCode.setUser(user);
          return verificationCodeRepository.save(verificationCode);
    }

    @Override
    public VerificationCode getVerificationCodeById(Long id) throws Exception {
        Optional<VerificationCode> vc=verificationCodeRepository.findById(id);
        if(vc.isPresent()){
            return vc.get();
        }
        throw new Exception("verification code not found");
    }

    @Override
    public VerificationCode getVerificationCodeByUserId(Long id) {
        return verificationCodeRepository.findByUserId(id);
    }

    @Override
    public void deleteVerificationCode(VerificationCode verificationCode) {
         verificationCodeRepository.delete(verificationCode);
    }
}
