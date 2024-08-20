package com.project.service.impl;

import com.project.Entity.TwoFactorAuth;
import com.project.Entity.User;
import com.project.Repository.UserRepository;
import com.project.config.JwtProvider;
import com.project.helper.VerificationType;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserByJwt(String jwt) throws Exception {
        String email= JwtProvider.generateEmailFromToken(jwt);
        User user=userRepository.findByEmail(email);

        if(user==null){
            throw new Exception("User Not Found");
        }

        return user;
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        User user=userRepository.findByEmail(email);

        if(user==null){
            throw new Exception("User Not Found");
        }

        return user;
    }

    @Override
    public User findUserById(Long Id) throws Exception {
        User user=userRepository.findById(Id).orElse(null);

        if(user==null){
            throw new Exception("User Not Found");
        }

        return user;
    }

    @Override
    public User enableTwoFactorAuthentication(VerificationType verificationType, User user, String sendTo) {
        TwoFactorAuth twoFactorAuth=new TwoFactorAuth();
        twoFactorAuth.setEnabled(true);
        twoFactorAuth.setVerificationType(verificationType);
        user.setTwoFactorAuth(twoFactorAuth);

        return userRepository.save(user);
    }


    @Override
    public User UpdatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        return userRepository.save(user);
    }
}
