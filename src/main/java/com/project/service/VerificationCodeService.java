package com.project.service;

import com.project.Entity.User;
import com.project.Entity.VerificationCode;
import com.project.helper.VerificationType;

public interface VerificationCodeService {

    VerificationCode sendVerificationCode(User user, VerificationType verificationType);

    VerificationCode getVerificationCodeById(Long id) throws Exception;

    VerificationCode getVerificationCodeByUserId(Long id);

    void deleteVerificationCode(VerificationCode verificationCode);

}
