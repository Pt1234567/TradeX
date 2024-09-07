package com.project.service;

import com.project.Entity.PaymentDetails;
import com.project.Entity.User;

public interface PaymentDetailService {

    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifscCode, String bankName, User user);
    public PaymentDetails getUserPaymentDetail(User user);

}
