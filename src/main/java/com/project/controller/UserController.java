package com.project.controller;

import com.project.Entity.ForgotPasswordToken;
import com.project.Entity.User;
import com.project.Entity.VerificationCode;
import com.project.request.ForgotPasswordTokenRequest;
import com.project.helper.VerificationType;
import com.project.request.ResetPasswordRequest;
import com.project.response.ApiResponse;
import com.project.response.AuthResponse;
import com.project.service.EmailService;
import com.project.service.ForgotPasswordService;
import com.project.service.UserService;
import com.project.service.VerificationCodeService;
import com.project.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @GetMapping("/api/users/profile")
    public ResponseEntity<User>  getUserProfile(@RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserByJwt(jwt);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String>  sendVerificationOtp(@RequestHeader("Authorization")String jwt, @PathVariable VerificationType verificationType) throws Exception {
        User user=userService.findUserByJwt(jwt);

        VerificationCode verificationCode=verificationCodeService.getVerificationCodeByUserId(user.getId());

        if(verificationCode==null){
            verificationCode=verificationCodeService.sendVerificationCode(user,verificationType);
        }
        if(verificationType.equals(VerificationType.EMAIL)){
            emailService.sendVerificationOtpEmail(user.getEmail(), verificationCode.getOtp());
        }

        return new ResponseEntity<>("Otp sent successfully",HttpStatus.OK);
    }

    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User>  enableTwoFactorAuthentication(@PathVariable  String otp,@RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserByJwt(jwt);

        VerificationCode verificationCode=verificationCodeService.getVerificationCodeByUserId(user.getId());

        String sendTo=verificationCode.getVerificationType().equals(VerificationType.EMAIL)?
                verificationCode.getEmail() : verificationCode.getMobile();

        boolean isVerified=verificationCode.getOtp().equals(otp);

        if(isVerified){
            User updatedUser=userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(),user,sendTo);
            verificationCodeService.deleteVerificationCode(verificationCode);

            return new ResponseEntity<>(updatedUser,HttpStatus.OK);
        }
         throw new Exception("wrong otp");
    }


    //send forgot password otp
    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse>  sendForgotPasswordOtp( @RequestBody ForgotPasswordTokenRequest forgotPasswordTokenRequest) throws Exception {

        User user=userService.findUserByJwt(forgotPasswordTokenRequest.getSendTo());

        String otp= OtpUtils.generateOtp();
        UUID uuid=UUID.randomUUID();
        String id=uuid.toString();

        ForgotPasswordToken forgotPasswordToken=forgotPasswordService.findByUserId(user.getId());

        if(forgotPasswordToken==null){
            forgotPasswordToken=forgotPasswordService.createToken(user,id,otp,forgotPasswordTokenRequest.getVerificationType(), forgotPasswordTokenRequest.getSendTo());
        }

        AuthResponse authResponse=new AuthResponse();
        authResponse.setSession(forgotPasswordToken.getId());
        authResponse.setMessage("Password sent Successfully");

        return new ResponseEntity<>(authResponse,HttpStatus.OK);
    }

    //verify otp
    @PatchMapping("/auth/users/reset-password/verify-otp")
    public ResponseEntity<ApiResponse>  resetPassword(@RequestParam  String id, @RequestBody ResetPasswordRequest req, @RequestHeader("Authorization")String jwt) throws Exception {
        ForgotPasswordToken forgotPasswordToken=forgotPasswordService.findById(id);

        boolean isVerified=forgotPasswordToken.getOtp().equals(req.getOtp());

        if(isVerified){
            userService.UpdatePassword(forgotPasswordToken.getUser(), req.getPassword());
            ApiResponse apiResponse=new ApiResponse();
            apiResponse.setMessage("Password Updated Successfully");
            return new ResponseEntity<>(apiResponse,HttpStatus.OK);
        }
        throw  new Exception("Wrong otp");
    }


}
