package com.project.service;

import com.project.Entity.User;
import com.project.helper.VerificationType;

public interface UserService
{

     User findUserByJwt(String jwt) throws Exception;
     User findUserByEmail(String email) throws Exception;
     User findUserById(Long Id) throws Exception;

     User enableTwoFactorAuthentication(VerificationType verificationType,User user,String sendTo);

    User UpdatePassword(User user,String newPassword);

}
