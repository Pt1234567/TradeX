package com.project.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {

    private String Otp;
    private String password;

}
