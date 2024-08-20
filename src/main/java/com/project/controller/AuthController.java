package com.project.controller;

import com.project.Entity.TwoFactorOtp;
import com.project.Entity.User;
import com.project.Repository.UserRepository;
import com.project.config.JwtProvider;
import com.project.response.AuthResponse;
import com.project.service.CustomUserDetailService;
import com.project.service.EmailService;
import com.project.service.TwoFactorOtpService;
import com.project.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private TwoFactorOtpService twoFactorOtpService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/signUp")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {

        User isEmailExist=userRepository.findByEmail(user.getEmail());

       if(isEmailExist!=null){
           throw new Exception("User already exist");
       }

        User newUser=new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());


        User savedUser=userRepository.save(newUser);

        Authentication authentication=new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=JwtProvider.generateToken(authentication);
        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setStatus(true);
        authResponse.setMessage("Register Success");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }


    @PostMapping("/signIn")
    public ResponseEntity<AuthResponse> login(@RequestBody User user) throws Exception {

        String userName= user.getEmail();
        String password=user.getPassword();

        Authentication authentication=authenicate(userName,password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt=JwtProvider.generateToken(authentication);

        User authUser=userRepository.findByEmail(userName);

        if(user.getTwoFactorAuth().isEnabled()){
            AuthResponse auth=new AuthResponse();
            auth.setMessage("Two factor enabled");
            auth.setTwoFactorAuthEnabled(true);
            String otp= OtpUtils.generateOtp();

            TwoFactorOtp oldTwoFactorOtp=twoFactorOtpService.findByUser(authUser.getId());

            if(oldTwoFactorOtp!=null){
                twoFactorOtpService.deleteTwoFactorOtp(oldTwoFactorOtp);
            }

            TwoFactorOtp newTwoFactorOtp=twoFactorOtpService.createTwoFactorOtp(authUser,otp,jwt);

            emailService.sendVerificationOtpEmail(authUser.getEmail(),otp);

            auth.setSession(newTwoFactorOtp.getId());
            return new ResponseEntity<>(auth,HttpStatus.ACCEPTED);

        }

        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(jwt);
        authResponse.setStatus(true);
        authResponse.setMessage("Login Success");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    private Authentication authenicate(String userName,String password){

        UserDetails userDetails= customUserDetailService.loadUserByUsername(userName);

        if(userDetails==null){
            throw  new BadCredentialsException("Invalid");
        }
        if(!password.equals(userDetails.getPassword())){
            throw  new BadCredentialsException("Invalid password");
        }

         return new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
    }

    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifyOtp(@PathVariable String otp,@RequestParam String id) throws Exception {
         TwoFactorOtp twoFactorOtp=twoFactorOtpService.findById(id);

         if(twoFactorOtpService.verifyTwoFactorOtp(twoFactorOtp,otp)){
                 AuthResponse response=new AuthResponse();
                 response.setMessage("Otp verification successful");
                 response.setTwoFactorAuthEnabled(true);
                 response.setJwt(twoFactorOtp.getJwt());

                 return new ResponseEntity<>(response,HttpStatus.OK);
         }

         throw new Exception("Invalid Otp");
    }

}
